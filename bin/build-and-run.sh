#!/bin/bash
mvn -f ../backend/pom.xml clean
mvn -f ../backend/pom.xml -Dmaven.test.skip=true package
rm -rf ../frontend/dist/*
npm run build --prefix ../frontend
java -jar ../backend/target/demo-0.0.1-SNAPSHOT.jar &
echo $! > ./pid.file &
npm run start --prefix ../frontend &