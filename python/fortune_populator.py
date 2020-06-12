import pandas_datareader as web
import psycopg2 as pg
from psycopg2 import extras
import predictions
import logging
from datetime import date

HOST = "trainings"
PORT = 5432
USER = "app"
PASSWORD = "Ao4eiT2w"
DATABASE = "appdb"

logging.basicConfig(level=logging.INFO)


def populate_table(df, symbol, cursor, is_historical):
    data = df['Close'].to_numpy().flatten()
    indexes = df.index
    extras.execute_values(cursor, "INSERT into stock(symbol, value, date, is_historical) values %s",
                          [(symbol, data[i], indexes[i], is_historical) for i in range(len(indexes))])


def populate_db():
    with pg.connect(host=HOST, port=PORT, user=USER, password=PASSWORD, database=DATABASE) as conn:
        cursor = conn.cursor()
        cursor.execute("SET search_path to fortune")
        cursor.execute("SELECT symbol FROM symbol")
        symbols = cursor.fetchall()
        logging.info(f"Symbols from db: {symbols}")
        for symbol in symbols:
            logging.info(f"Using symbol: {symbol}")

            stocks = web.DataReader(symbol[0], data_source='yahoo', start="2020-06-12", end="2020-06-12")
            logging.info(f"Stocks number: {len(stocks)}")
            populate_table(stocks, symbol, cursor, True)

            forecasts = predictions.learn_and_predict(symbol[0], "2000-01-01", date.today())
            logging.info(f"Forecasts number: {len(forecasts)}")
            populate_table(forecasts, symbol, cursor, False)

            conn.commit()


try:
    populate_db()
except KeyError as e:
    logging.error("Stocks not found")
except Exception as e:
    logging.error(repr(e))
