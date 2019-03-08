#!/bin/sh

#############################################################
#  Startup Script
#############################################################

NOHUP_LOG_FILE=./nohup.log
PID_FILE=./pid.txt

echo "Starting Face Recognizer service"
echo "--------------------------------"

nohup python3 -m face_recognizer > $NOHUP_LOG_FILE 2>&1 &
echo $! > $PID_FILE

echo "started ..."
echo "log file " $NOHUP_LOG_FILE