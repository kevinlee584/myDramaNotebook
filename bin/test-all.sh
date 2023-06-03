#!/bin/bash

npm run test:unit --prefix ../frontend

cd ../
docker compose up -d --build selenium_server

cd ./backend
./mvnw test

cd ../
docker compose down

cd ./bin