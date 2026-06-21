@echo off
set "JAVA_HOME=D:\cxdownload\jdk-17.0.18+8"
set "MAVEN_HOME=C:\Program Files\apache-maven-3.9.14\apache-maven-3.9.14"
set "PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%"
"%MAVEN_HOME%\bin\mvn.cmd" %*
