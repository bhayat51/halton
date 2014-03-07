#!/bin/sh

MYSQL="/cygdrive/c/wamp/bin/mysql/mysql5.6.12/bin/mysql.exe"
DUMMY_FILES="/cygdrive/Users/Joshua/Documents/GitHub/halton/database/dummy/*"
DB_NAME="halton_energy"
DB_USER="root"

i=0
for f in $DUMMY_FILES
do
    echo "> $f"
    if [ "$i" -ne 0 ]; then
        sql+=";"
    fi
    table=$(basename "${f%%.*}")
    sql+="LOAD DATA LOCAL INFILE \"$f\" REPLACE INTO TABLE $db_name.$table FIELDS ESCAPED BY \"^\""
    i=$((i+1))
done

if [ "$i" -ne 0 ]; then
    sql+=";"
    echo "$sql" | "$MYSQL" --user="$DB_USER" -p -vvv
fi
