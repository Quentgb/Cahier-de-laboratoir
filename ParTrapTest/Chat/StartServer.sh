#!/bin/bash
CLASSPATH=$(<"classpath.txt")
export CLASSPATH
java -cp .:classes:/lib/Chat.jar:/lib/ChatImpl.jar HelloServer
