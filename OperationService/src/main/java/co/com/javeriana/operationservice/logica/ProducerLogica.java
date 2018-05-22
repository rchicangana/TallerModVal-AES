/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.operationservice.logica;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import javax.ejb.Stateless;
import kafka.javaapi.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 *
 * @author rchic
 */
@Stateless
public class ProducerLogica {

    private final static String TOPIC = "operaciones";

    private org.apache.kafka.clients.producer.Producer<Long, String> createProducer(String uri) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                uri);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        return new org.apache.kafka.clients.producer.KafkaProducer<>(props);
    }

    public void runProducer(final String uri, final String sendMessage) throws Exception {
        final org.apache.kafka.clients.producer.Producer<Long, String> producer = createProducer(uri);
        try {
            final ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC, sendMessage);
            producer.send(record).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            producer.flush();
            producer.close();
        }
    }

}
