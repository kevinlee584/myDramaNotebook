#!/usr/bin/env bash

cd $(dirname $( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd ))

cd ./frontend && npm install && npm run test:unit

cd ../ && docker compose up -d --build selenium_server
cd ./backend && ./mvnw test
cd ../ && docker compose down