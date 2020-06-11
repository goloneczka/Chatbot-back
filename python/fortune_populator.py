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

logging.basicConfig(level=logging.DEBUG)


def populate_db(df, symbol, cursor, is_historical):
    data = df['Close'].to_numpy().flatten()
    indexes = df.index
    extras.execute_values(cursor, "INSERT into stock(symbol, value, date, is_historical) values %s",
                          [(symbol, data[i], indexes[i], is_historical) for i in range(len(indexes))])


def scheduled_stocks():
    with pg.connect(host=HOST, port=PORT, user=USER, password=PASSWORD, database=DATABASE) as conn:
        cursor = conn.cursor()
        cursor.execute("SET search_path to fortune")
        cursor.execute("SELECT symbol FROM symbol")
        symbols = cursor.fetchall()
        logging.debug("Symbols from db: ", symbols)
        for symbol in symbols:
            logging.debug("Using symbol: ", symbol)

            stocks = web.DataReader(symbol[0], data_source='yahoo', start=date.today(), end=date.today())
            logging.debug("Stocks number: ", len(stocks))
            populate_db(stocks, symbol, cursor, True)

            forecasts = predictions.learn_and_predict(symbol[0], "2000-01-01", date.today())
            logging.debug("Forecasts number: ", len(forecasts))
            populate_db(forecasts, symbol, cursor, False)

            conn.commit()


scheduled_stocks()
