#!/bin/bash

cd src/fr/upem/server
javac *.java
rmic LibraryImpl
rmic BookImpl
rmic UserImpl
cp Book.java ../client/Book.java
cp User.java ../client/User.java
cp Library.java ../client/Library.java
cd ../client
javac *.java
