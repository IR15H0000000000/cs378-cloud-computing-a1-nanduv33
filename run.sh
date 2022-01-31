#!/bin/bash
rm *.txt
mvn clean compile
mvn exec:java -Dexec.executable="edu.bu.met.cs378.Main" -DargLine="-Xmx2g" -Dexec.args="taxi-data-sorted-small.csv.bz2 500000"
