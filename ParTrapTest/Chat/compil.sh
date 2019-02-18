#!/bin/bash
rm lib/*
rm classes/*

javac -d classes -classpath .:classes src/Broadcast_itf.java 
cd classes 
jar cvf ../lib/Broadcast_itf.jar Broadcast_itf.class
cd .. 

javac -d classes -classpath .:classes src/Broadcast_itfImpl.java
cd classes 
jar cvf ../lib/Broadcast_itfImpl.jar Broadcast_itfImpl.class
cd .. 

javac -d classes -classpath .:classes src/Info_itf.java 
cd classes 
jar cvf ../lib/Info_itf.jar Info_itf.class
cd .. 

javac -d classes -classpath .:classes src/Info_itfImpl.java
cd classes 
jar cvf ../lib/Info_itfImpl.jar Info_itfImpl.class
cd .. 

javac -d classes -classpath .:classes src/Chat.java 
cd classes 
jar cvf ../lib/Chat.jar Chat.class
cd .. 

javac -d classes -classpath .:classes src/PrivateChatImpl.java 
cd classes 
jar cvf ../lib/PrivateChatImpl.jar PrivateChatImpl.class
cd ..

javac -d classes -classpath .:classes src/ChatImpl.java
cd classes 
jar cvf ../lib/ChatImpl.jar ChatImpl.class
cd .. 

javac -d classes -classpath .:classes src/Factory_Itf.java 
cd classes 
jar cvf ../lib/Factory_Itf.jar Factory_Itf.class
cd .. 

javac -d classes -classpath .:classes src/ChatFactory.java 
cd classes 
jar cvf ../lib/ChatFactory.jar ChatFactory.class
cd ..

javac -d classes -classpath .:classes src/Accounts_Itf.java 
cd classes 
jar cvf ../lib/Accounts_Itf.jar Accounts_Itf.class
cd .. 

javac -d classes -classpath .:classes src/Accounts_ItfImpl.java
cd classes 
jar cvf ../lib/Accounts_ItfImpl.jar Accounts_ItfImpl.class
cd .. 


javac -d classes -cp .:classes:lib/Chat.jar:lib/ChatImpl.jar:lib/Accounts_Itf.jar:lib/Accounts_ItfImpl.jar: src/HelloServer.java
javac -d classes -cp .:classes:lib/Chat.jar:lib/Info_itf.jar:lib/Info_itfImpl.jar:lib/Broadcast_itf.jar:lib/Broadcast_itfImpl.jar:lib/Accounts_Itf.jar src/HelloClient.java

echo Compil finit!
