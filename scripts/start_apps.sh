#!/usr/bin/env bash
shopt -s expand_aliases

jenv local 11

echo "Starting Eureka Server..."
java -jar eureka-server-*.jar &
sleep 30s

echo "Starting Config Server..."
java -jar config-server-*.jar &
sleep 30s

echo "Starting Station Registry app..."
java -jar station-registry-*.jar &
sleep 5s

echo "Starting Timetable app..."
java -jar timetable-*.jar &
sleep 5s

echo "Starting Disruptions app..."
java -jar disruptions-*.jar &
sleep 30s

echo "Starting API Gateway app..."
java -jar api-gateway-*.jar &
sleep 20s

exit
