FROM python:3.7

RUN pip3 install tensorflow pandas pandas-datareader scikit-learn psycopg2-binary

COPY /fortune_populator.py /fortune_populator.py
COPY /predictions.py /predictions.py

CMD ["python", "fortune_populator.py"]