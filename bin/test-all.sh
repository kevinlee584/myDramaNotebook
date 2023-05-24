#!/bin/bash

npm run test:unit --prefix ../frontend
mvn -f ../backend/pom.xml test