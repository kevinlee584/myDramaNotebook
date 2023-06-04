#!/usr/bin/env bash

cd $(dirname $( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd ))

cd ./backend && ./mvnw -Dmaven.test.skip=true clean package