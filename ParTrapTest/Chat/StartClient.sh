#!/bin/bash
CLASSPATH=$(<"classpath.txt")

java -cp .:classes:lib/Chat.jar:lib/Info_itf.jar:lib/Info_itfImpl.jar:lib/Broadcast_itf.jar:lib/Broadcast_itfImpl.jar HelloClient localhost