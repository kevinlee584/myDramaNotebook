#!/bin/bash
mvn -f ../backend/pom.xml -Dmaven.test.skip=true clean package 
java -jar ../backend/target/demo-0.0.1-SNAPSHOT.jar & 
echo $! > ./pid.file &