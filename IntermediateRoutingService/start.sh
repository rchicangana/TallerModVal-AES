mvn install:install-file -Dfile=lib/ServiciosPublicosAPI-1.0.0.jar -DgroupId=co.com.javeriana -DartifactId=ServiciosPublicosAPI -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=lib/RedisServiceClient-1.0.0.jar -DgroupId=co.com.javeriana -DartifactId=RedisServiceClient -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=lib/RestClient-1.0.0.jar -DgroupId=co.com.javeriana -DartifactId=RestClient -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=lib/SoapClient-1.0.0.jar -DgroupId=co.com.javeriana -DartifactId=SoapClient -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
mvn clean package
docker build -t intermediateroutingservice .
docker run -p 9494:8080 -d --name intermediateroutingservice intermediateroutingservice java -jar /opt/payara/payara-micro.jar --deploymentDir /opt/payara/deployments

