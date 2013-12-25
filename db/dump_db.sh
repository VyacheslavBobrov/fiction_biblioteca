#!/bin/sh
HOST=localhost
USER=postgres

DUMP_DIR=.

MAIN_DB=fb2_lib

echo Дамп базы данных..
pg_dump -C -s -h $HOST -U $USER -f $DUMP_DIR/$MAIN_DB.sql $MAIN_DB


