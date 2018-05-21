/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.redisserviceclient.impl;

import co.com.javeriana.redisserviceclient.api.RedisClient;
import co.com.javeriana.redisserviceclient.impl.exceptions.ErrorConsultaException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.binary.Base64;

/**
 * runt.com.co
 *
 * @author rchicangana
 */
public class RedisClientImpl implements RedisClient {

    private final Client client;
    private final WebTarget target;
    private static final String PATH = "/redis/";
    

    private final static Logger LOG = Logger.getLogger(RedisClientImpl.class.getName());
    

    public RedisClientImpl(final String url, final int port, final String contexo) {
        HostnameVerifier hv = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        client = ClientBuilder.newBuilder().hostnameVerifier(hv).sslContext(sc).build();
        //client.register(JsonBodyManager.class);//Comentariar esta linea antes de la integaciÃ³n y eliminar clase JsonBodyManager

        if (port == 443) {
            target = client.target("https://" + url + ":" + port + "/" + contexo + "services/");
        } else {
            target = client.target("http://" + url + ":" + port + "/" + contexo + "services/");
        }
    }

    @Override
    public String registrar(Integer id, String datos) throws ErrorConsultaException {
        final Invocation.Builder requestBuilder = target.path(PATH + id+"/"+datos).request();
        final Response resp = requestBuilder.post(null);
        return resp.readEntity(String.class);
    }

    @Override
    public String desregistrar(Integer id) throws ErrorConsultaException {
        final Invocation.Builder requestBuilder = target.path(PATH + id).request();
        final Response resp = requestBuilder.delete();
        return resp.readEntity(String.class);
    }

    @Override
    public String obtener(Integer id) throws ErrorConsultaException {
        final Invocation.Builder requestBuilder = target.path(PATH + id).request();
        final Response resp = requestBuilder.get();
        return resp.readEntity(String.class);
    }
    
   

   

}
