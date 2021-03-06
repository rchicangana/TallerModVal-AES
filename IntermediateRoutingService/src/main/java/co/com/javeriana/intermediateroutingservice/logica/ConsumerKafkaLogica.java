/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.intermediateroutingservice.logica;

import co.com.javeriana.serviciospublicosapi.dto.RequestOperation;
import co.com.javeriana.serviciospublicosapi.dto.ResponseOperation;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.TopicAndPartition;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.OffsetRequest;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.MessageAndOffset;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 *
 * @author rchic
 */
@Singleton
@Startup
public class ConsumerKafkaLogica {
    
    private static final Logger LOG = Logger.getLogger(ConsumerKafkaLogica.class.getName());
    private static final int FETCH_SIZE = 100000;
    private static final int MAX_NUM_OFFSETS = 1;
    private static final int BUFFER_SIZE = 64 * 1024;
    private static final int TIMEOUT = 100000;
    private static final int PARTITION = 0;
    private static final String TOPIC = "operaciones";
    private static final String CLIENT = "testClient";
    
    private final ResourceBundle  props = ResourceBundle.getBundle("prop", Locale.ROOT);
    
    @EJB
    private IntermediateLogica intermediateLogica;
    
    @PostConstruct
    public void init(){
        try {
            LOG.info("CONSTRUYENDO");
            String host = props.getString("host");
            String port = props.getString("port");
            run(host, Integer.valueOf(port));
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ConsumerKafkaLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Asynchronous
    public void run(String broker, int port) throws Exception {
        
        SimpleConsumer consumer = new SimpleConsumer(broker, port, TIMEOUT, BUFFER_SIZE, CLIENT);
        long readOffset = getLastOffset(consumer, kafka.api.OffsetRequest.EarliestTime());
        Gson g = new Gson();
        //consumer never stops
        while (true) {
            final FetchRequest req = new FetchRequestBuilder().clientId(CLIENT).addFetch(TOPIC, PARTITION, readOffset, FETCH_SIZE).build();
            final FetchResponse fetchResponse = consumer.fetch(req);

            for (MessageAndOffset messageAndOffset : fetchResponse.messageSet(TOPIC, PARTITION)) {
                long currentOffset = messageAndOffset.offset();
                if (currentOffset < readOffset) {
                    continue;
                }

                readOffset = messageAndOffset.nextOffset();
                final ByteBuffer payload = messageAndOffset.message().payload();

                final byte[] bytes = new byte[payload.limit()];
                payload.get(bytes);
                
                
                //por cada evento se revisa si corresponde al id de Transaccion
                try {
                    String b64Evento = new String(bytes, "UTF-8");
                    String evento = new String(Base64.decodeBase64(b64Evento));
                    System.out.println("Evento "+evento);
                    RequestOperation salida = g.fromJson(evento, RequestOperation.class);
                    intermediateLogica.routing(salida);
                    
                } catch (JsonSyntaxException | UnsupportedEncodingException e) {
                }
            }
        }
    }

    public static long getLastOffset(SimpleConsumer consumer, long whichTime) {
        final TopicAndPartition topicAndPartition = new TopicAndPartition(TOPIC, PARTITION);
        final Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, MAX_NUM_OFFSETS));

        final OffsetRequest offsetRequest = new OffsetRequest(requestInfo, kafka.api.OffsetRequest.CurrentVersion(), CLIENT);
        if (consumer.getOffsetsBefore(offsetRequest).offsets(TOPIC, PARTITION) != null && consumer.getOffsetsBefore(offsetRequest).offsets(TOPIC, PARTITION).length > 0) {
            return consumer.getOffsetsBefore(offsetRequest).offsets(TOPIC, PARTITION)[0];
        } else {
            return 0;
        }
    }
    
}
