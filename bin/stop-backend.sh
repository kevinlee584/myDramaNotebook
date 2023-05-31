#!/bin/bash
while kill $(cat ./pid.file) 2>/dev/null; do 
    sleep 1
done
rm ./pid.file