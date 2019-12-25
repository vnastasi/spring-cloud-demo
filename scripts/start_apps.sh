#!/usr/bin/env bash
shopt -s expand_aliases

alias java11="/Library/Java/JavaVirtualMachines/jdk-11.0.2.jdk/Contents/Home/bin/java"

echo "Starting Eureka Server..."
java11 -jar eureka-server-*.jar &
sleep 30s

echo "Starting Config Server..."
java11 -jar config-server-*.jar &
sleep 30s

echo "Starting Station Registry app..."
java11 -jar station-registry-*.jar &
sleep 5s

echo "Starting Timetable app..."
java11 -jar timetable-*.jar &
sleep 30s

echo "Starting API Gateway app..."
java11 -jar api-gateway-*.jar &
sleep 20s

unalias java11

exit
