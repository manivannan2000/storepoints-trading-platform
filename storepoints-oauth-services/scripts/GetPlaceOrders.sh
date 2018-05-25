#!/bin/sh

IFILE=./../input.csv

DATE=`date +%Y-%m-%d`

while read f ; do

	if [ -n "`echo $f | grep -v \"^#\"`" ] ; then


	 	 if [ -n "`echo $f | grep -v  \"^[0-9]\"`" ] ; then
  
			line=`echo "$f" | sed 's/\*/0/g'`
			IFS=',' read -r -a array <<< "$line"   		
                        quote=`echo "${array[1]}"`
			`cat ./../nohup.out | grep -B 1 -e "$quote" | grep -B 20 -C 20 -e "PlaceEquityOrder" > ./../logs/nohup_"$DATE"_"$quote"_PlaceOrders.log`
                        

		fi

	fi

done < $IFILE
