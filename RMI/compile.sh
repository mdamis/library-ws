#!/bin/bash

cd src
javac *.java
rmic LibraryImpl
rmic BookImpl
rmic UserImpl
