Building aot cache for the application

mvn clean package

java -XX:AOTCacheOutput=app.aot "-Dspring.context.exit=onRefresh" -jar .\target\spring26-0.0.1-SNAPSHOT.jar

java -XX:AOTCache=app.aot -jar .\target\spring26-0.0.1-SNAPSHOT.jar  
