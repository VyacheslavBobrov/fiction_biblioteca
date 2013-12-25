#!/bin/sh
USER=postgres
HOST=localhost

MAIN_DB=fb2_lib.sql

psql -U $USER -h $HOST -f $MAIN_DB
