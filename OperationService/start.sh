mvn install:install-file -Dfile=lib/ServiciosPublicosAPI-1.0.0.jar -DgroupId=co.com.javeriana -DartifactId=ServiciosPublicosAPI -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
mvn clean package
docker build -t operationservice .
docker run -p 9393:8080 -d --name operationservice operationservice java -jar /opt/payara/payara-micro.jar --deploymentDir /opt/payara/deployments

