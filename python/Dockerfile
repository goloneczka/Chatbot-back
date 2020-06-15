FROM python:3.7-slim-buster

RUN pip3 install tensorflow pandas scikit-learn psycopg2-binary

COPY /fortune_pupulator.py /fortune_populator.py
COPY /predictions.py /predictions.py

CMD ["python", "fortune_populator.py"]