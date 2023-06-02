#!/bin/bash
mvn -f ../backend/pom.xml clean
mvn -f ../backend/pom.xml -Dmaven.test.skip=true package
rm -rf ../frontend/dist/*
npm run build --prefix ../frontend