#!/bin/sh
mvn compile assembly:single
rm ./HZ_server-1.0-jar-with-dependencies.jar
cp ./target/HZ_server-1.0-jar-with-dependencies.jar ./
