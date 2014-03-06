#!/bin/sh

DUMMY_FILES="/home/prenderj/dummy/*"
DB_NAME="halton_energy"
DB_USER="root"
DB_PASS=

i=0
for f in $DUMMY_FILES
do
    echo "> $f"
    if [ "$i" -ne 0 ]; then
        sql+=";"
    fi
    table=$(basename "${f%%.*}")
    sql+="LOAD DATA LOCAL INFILE \"$f\" REPLACE INTO TABLE $db_name.$table FIELDS ESCAPED BY \";\""
    i=$((i+1))
done

if [ "$i" -ne 0 ]; then
    sql+=";"
    echo "$sql" | mysql --user="$DB_USER" --pass="$DB_PASS" -vvv
fi
