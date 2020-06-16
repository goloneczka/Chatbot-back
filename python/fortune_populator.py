import pandas_datareader as web
import psycopg2 as pg
from psycopg2 import extras
# import predictions
import logging
import time
from datetime import date, timedelta, datetime

HOST = "trainings"
PORT = 5432
USER = "app"
PASSWORD = "Ao4eiT2w"
DATABASE = "appdb"
INTEGRATION_HOUR = 1
HOUR_TO_SECONDS = 3600
OLDEST_DATE = date(2000, 1, 1)

logging.basicConfig(level=logging.INFO)


def populate_table(df, symbol, cursor, is_historical):
    data = df['Close'].to_numpy().flatten()
    indexes = df.index
    extras.execute_values(cursor, "INSERT into stock(symbol, value, date, is_historical) values %s",
                          [(symbol, data[i], indexes[i], is_historical) for i in range(len(indexes))])


def get_last_date(cursor, symbol):
    cursor.execute("SELECT date FROM stock where symbol = %s and is_historical = true ORDER BY date DESC LIMIT 1",
                   symbol)
    date_of_last_stock = cursor.fetchone()
    if date_of_last_stock is None:
        date_of_last_stock = OLDEST_DATE
    else:
        date_of_last_stock = date_of_last_stock[0]

    return date_of_last_stock


def clear_db(cursor):
    cursor.execute("DELETE FROM stock where is_historical = 'false'")
    cursor.execute("delete from symbol "
                   "where is_currency = 'true' and name not in "
                   "(select CONCAT(c1.name,'/',c2.name) from currency c1, currency c2 where c1.name != c2.name)")


def init_currencies(cursor):
    cursor.execute("SELECT name FROM currency")
    currencies = cursor.fetchall()
    for i in range(len(currencies)):
        for j in range(len(currencies)):
            if j != i:
                symbol = currencies[i][0].strip('()') + currencies[j][0].strip('()') + '=X'
                name = currencies[i][0].strip('()') + '/' + currencies[j][0].strip('()')
                cursor.execute(
                    "INSERT INTO symbol(symbol, name, is_currency) VALUES (%s, %s, %s) ON CONFLICT DO NOTHING",
                    (symbol, name, 'true'))


def populate_db():
    with pg.connect(host=HOST, port=PORT, user=USER, password=PASSWORD, database=DATABASE) as conn:
        cursor = conn.cursor()
        cursor.execute("SET search_path to fortune")
        clear_db(cursor)
        init_currencies(cursor)
        conn.commit()

    with pg.connect(host=HOST, port=PORT, user=USER, password=PASSWORD, database=DATABASE) as conn:
        cursor = conn.cursor()
        cursor.execute("SET search_path to fortune")
        cursor.execute("SELECT symbol FROM symbol")
        symbols = cursor.fetchall()
        logging.info(f"Symbols from db: {symbols}")
        for symbol in symbols:
            logging.info(f"Using symbol: {symbol}")

            date_of_last_stock = get_last_date(cursor, symbol)
            if date_of_last_stock == date.today():
                logging.info("Stocks for today exists. Skipping stocks population")
            else:
                start_date = date_of_last_stock + timedelta(days=1)
                logging.info(f"Searching stocks from {start_date} to {date.today()}")
                stocks = web.DataReader(symbol[0], data_source='yahoo', start=start_date,
                                        end=date.today())
                logging.info(f"Stocks number: {len(stocks)}")
                populate_table(stocks, symbol, cursor, True)

            # forecasts = predictions.learn_and_predict(symbol[0], OLDEST_DATE, date.today())
            # logging.info(f"Forecasts number: {len(forecasts)}")
            #
            # populate_table(forecasts, symbol, cursor, False)

            conn.commit()


populate_flag = True
while True:
    current_hour = datetime.today().hour
    logging.info(f"Current hour is: {current_hour}, populate flag is: {populate_flag}")
    if populate_flag or current_hour == INTEGRATION_HOUR:
        try:
            logging.info("Populating DB...")
            populate_db()
            populate_flag = False
        except Exception:
            logging.exception("Problems while populating db")
            populate_flag = True
    else:
        logging.info("Conditions not met")
    logging.info("Going to sleep...")
    time.sleep(HOUR_TO_SECONDS)
