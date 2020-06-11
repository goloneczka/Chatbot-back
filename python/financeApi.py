import pandas_datareader as web
import psycopg2 as pg
import predictions
from datetime import date
from datetime import datetime


def save_stocks_to_db(df, symbol, cursor, is_historical):
    data = df['Close'].to_numpy().flatten()
    indexes = df.index
    for i in range(len(indexes)):
        cursor.execute("INSERT into stock(symbol, value, date, is_historical) values(%s,%s,%s,%s)",
                       (symbol, data[i], indexes[i], is_historical))


def scheduled_stocks():
    conn = pg.connect(host="trainings",
                      port=5432,
                      user="app",
                      password="Ao4eiT2w",
                      database="appdb")
    cursor = conn.cursor()
    cursor.execute("SET search_path to fortune")
    cursor.execute("SELECT symbol FROM symbol")
    symbols = cursor.fetchall()
    for symbol in symbols:
        stocks = web.DataReader(symbol[0], data_source='yahoo', start=date.today(), end=date.today())
        save_stocks_to_db(stocks, symbol, cursor, True)
        forecasts = predictions.learn_and_predict(symbol[0], "2000-01-01", date.today())
        save_stocks_to_db(forecasts, symbol, cursor, False)
        conn.commit()


today = datetime.today()
if today.hour == 8 and today.minute == 0 and today.second == 0:
    scheduled_stocks()
