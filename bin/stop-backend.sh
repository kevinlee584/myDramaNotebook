#!/bin/bash
while kill -SIGINT $(cat ./pid.file) 2>/dev/null; do 
    sleep 1
done
rm ./pid.file