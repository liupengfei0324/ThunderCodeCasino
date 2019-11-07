#!/bin/sh

export JAVA_HOME=/home/ts/Downloads/jdk1.8.0_211

export JRE_HOME=$JAVA_HOME/jre

export CLASSPATH=$JAVA_HOME/lib/

export PATH=$PATH:$JAVA_HOME/bin

while [ true ]
do
   java Client && sleep 7s
done
   

