#!/bin/bash
rm *.txt
mvn clean compile
mvn exec:java -Dexec.executable="edu.bu.met.cs378.Main" -Dexec.args="taxi-data-sorted-small.csv.bz2 1000000"