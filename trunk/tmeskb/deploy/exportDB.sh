#!/bin/bash
/usr/local/mysql/bin/mysqldump --default-character-set=utf8 --skip-lock-tables -h localhost -P 3306 -uroot -pzllc,2009 $1 > $2
