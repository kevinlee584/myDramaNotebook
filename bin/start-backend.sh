#!/bin/bash
java -jar ../backend/target/demo-0.0.1-SNAPSHOT.jar &
echo $! > ./pid.file &