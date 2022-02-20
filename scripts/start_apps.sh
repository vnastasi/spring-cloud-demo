#!/usr/bin/env bash
shopt -s expand_aliases

jenv local 15

echo "Starting Eureka Server..."
java --enable-preview -jar eureka-server-*.jar &
sleep 15s

echo "Starting Config Server..."
java --enable-preview -jar config-server-*.jar &
sleep 15s

echo "Starting Station Registry app..."
java --enable-preview -jar station-registry-*.jar &
sleep 15s

echo "Starting Timetable app..."
java --enable-preview -jar timetable-*.jar &
sleep 15s

echo "Starting Disturbances app..."
java --enable-preview -jar disturbances-*.jar &
sleep 15s

echo "Starting API Gateway app..."
java --enable-preview -jar api-gateway-*.jar &
sleep 30s

exit
