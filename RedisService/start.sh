mvn clean package
docker build -t redisservice .
docker run -p 9191:8080 -d --name redisservice redisservice java -jar /opt/payara/payara-micro.jar --deploymentDir /opt/payara/deployments

