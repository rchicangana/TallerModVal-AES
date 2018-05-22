mvn install:install-file -Dfile=lib/RedisServiceClient-1.0.0.jar -DgroupId=co.com.javeriana -DartifactId=RedisServiceClient -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=lib/ServiciosPublicosAPI-1.0.0.jar -DgroupId=co.com.javeriana -DartifactId=ServiciosPublicosAPI -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
mvn clean package
docker build -t suscripcionservice .
docker run -p 9292:8080 -d --name suscripcionservice suscripcionservice java -jar /opt/payara/payara-micro.jar --deploymentDir /opt/payara/deployments


