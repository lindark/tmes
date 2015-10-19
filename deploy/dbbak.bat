mysqldump --default-character-set=utf8 --skip-lock-tables -h localhost -P 3306 -uroot -pzllc,2009 tcrmnew > %date:~0,10%_tcrmnew.sql  

forfiles /d -7 /m *.sql /c "cmd /c del /f @file" 