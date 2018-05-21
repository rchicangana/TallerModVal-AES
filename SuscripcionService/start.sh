mvn clean package
docker build -t suscripcionservice .
docker run -p 9292:8080 -d --name suscripcionservice suscripcionservice java -jar /opt/payara/payara-micro.jar --deploymentDir /opt/payara/deployments

