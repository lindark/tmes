#!/bin/bash
/usr/local/mysql/bin/mysql --default-character-set=utf8 -h localhost -P 3306 -uroot -pzllc,2009 -f $1 < $2
