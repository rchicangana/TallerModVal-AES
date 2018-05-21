# TallerModVal-AES

kafka-docker

Dockerfile for Apache Kafka
The image is available directly from Docker Hub

Pre-Requisites
install docker-compose https://docs.docker.com/compose/install/
modify the KAFKA_ADVERTISED_HOST_NAME in docker-compose.yml to match your docker host IP (Note: Do not use localhost or 127.0.0.1 as the host ip if you want to run multiple brokers.)
if you want to customize any Kafka parameters, simply add them as environment variables in docker-compose.yml, e.g. in order to increase the message.max.bytes parameter set the environment to KAFKA_MESSAGE_MAX_BYTES: 2000000. To turn off automatic topic creation set KAFKA_AUTO_CREATE_TOPICS_ENABLE: 'false'
Kafka's log4j usage can be customized by adding environment variables prefixed with LOG4J_. These will be mapped to log4j.properties. For example: LOG4J_LOGGER_KAFKA_AUTHORIZER_LOGGER=DEBUG, authorizerAppender
NOTE: There are several 'gotchas' with configuring networking. If you are not sure about what the requirements are, please check out the Connectivity Guide in the Wiki

Tutorial http://wurstmeister.github.io/kafka-docker/

