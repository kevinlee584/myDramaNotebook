#!/usr/bin/env bash

cd $(dirname $( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd ))

cd ./frontend && npm install --omit=dev && npm run start &