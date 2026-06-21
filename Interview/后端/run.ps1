$env:JAVA_HOME = "D:\cxdownload\jdk-17.0.18+8"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
& mvn spring-boot:run -DskipTests
