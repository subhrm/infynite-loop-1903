#!/bin/sh

#############################################################
#  Stop Script
#############################################################

PID_FILE=./pid.txt

echo "Stoping Face Recognizer service"
echo "--------------------------------"

kill -9 `cat $PID_FILE`
rm $PID_FILE
echo "stopped"