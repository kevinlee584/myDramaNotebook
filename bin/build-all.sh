#!/bin/bash

cd ../backend
./mvnw -Dmaven.test.skip=true clean package
rm -rf ../frontend/dist/*
npm run build --prefix ../frontend
cd ../bin