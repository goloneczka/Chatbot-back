from random import randint

import logging

class Logs:
    def __init__(self, level):
        self.__logger = None

        if level:
            self.__logger = logging.getLogger("chase")
            logging.basicConfig(filename="chase.log", level=level,
                                format="[%(asctime)s] [%(levelname)s] %(message)s")

    def debug(self, message):
        if self.__logger:
            logging.debug(message)

    def info(self, message):
        if self.__logger:
            logging.info(message)

    def warning(self, message):
        if self.__logger:
            logging.warning(message)

    def error(self, message):
        if self.__logger:
            logging.error(message)

    def critical(self, message):
        if self.__logger:
            logging.critical(message)