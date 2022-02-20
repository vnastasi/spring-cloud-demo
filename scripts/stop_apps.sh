#!/usr/bin/env bash

cd pid || exit

echo "Stopping API Gateway app"
cat api-gateway.pid | xargs kill
sleep 10s

echo "Stopping Disturbances app"
cat disturbances.pid | xargs kill
sleep 10s

echo "Stopping Timetable app"
cat timetable.pid | xargs kill
sleep 10s

echo "Stopping Station Registry app"
cat station-registry.pid | xargs kill
sleep 10s

echo "Stopping Config Server"
cat config-server.pid | xargs kill
sleep 10s

echo "Stopping Eureka Server"
cat eureka-server.pid | xargs kill
sleep 10s

exit
