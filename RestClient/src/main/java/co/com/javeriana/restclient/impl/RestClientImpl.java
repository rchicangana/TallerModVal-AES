/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.restclient.impl;


import co.com.javeriana.restclient.api.Factura;
import co.com.javeriana.restclient.api.RestClient;
import co.com.javeriana.restclient.api.Resultado;
import co.com.javeriana.restclient.exceptions.ErrorConsultaException;
import co.com.javeriana.serviciospublicosapi.dto.RequestOperation;
import co.com.javeriana.serviciospublicosapi.dto.ResponseOperation;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import static javax.ws.rs.client.Entity.entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 * runt.com.co
 *
 * @author rchicangana
 */
public class RestClientImpl implements RestClient {

    private final Client client;
    private final WebTarget target;
    private static final String PATH = "/payments";
    

    private final static Logger LOG = Logger.getLogger(RestClientImpl.class.getName());
    

    public RestClientImpl(final String url, final int port, final String contexo) {
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
        client.register(JsonBodyManager.class);//Comentariar esta linea antes de la integaciÃ³n y eliminar clase JsonBodyManager

        if (port == 443) {
            target = client.target("https://" + url + ":" + port + "/" + contexo + "v1/");
        } else {
            target = client.target("http://" + url + ":" + port + "/" + contexo + "v1/");
        }
    }

    @Override
    public ResponseOperation consultar(Integer numeroFactura, Integer idEmpresa) throws ErrorConsultaException {
        final Invocation.Builder requestBuilder = target.path(PATH+"/" + numeroFactura).request();
        final Response resp = requestBuilder.get();
        Factura respuesta = resp.readEntity(Factura.class);
        ResponseOperation salida = new ResponseOperation();
        salida.setSaldo(respuesta.getValorFactura());
        salida.setStatus("200");
        return salida;
    }

    @Override
    public ResponseOperation pagar(RequestOperation operation) throws ErrorConsultaException {
        final Invocation.Builder requestBuilder = target.path(PATH+"/" + operation.getFactura().getNumero()).request();
        Factura factura = new Factura();
        factura.setIdFactura(operation.getFactura().getNumero());
        factura.setValorFactura(operation.getFactura().getValor());
        final Response resp = requestBuilder.post(Entity.json(factura));
        Resultado resultado = resp.readEntity(Resultado.class);
        ResponseOperation salida = new ResponseOperation();
        salida.setMensaje(resultado.getMensaje());
        salida.setStatus("200");
        return salida;
    }

    @Override
    public ResponseOperation compensar(Integer numeroFactura, Integer idEmpresa) throws ErrorConsultaException {
        final Invocation.Builder requestBuilder = target.path(PATH+"/" + numeroFactura).request();
        Factura factura = new Factura();
        factura.setIdFactura(numeroFactura);
        factura.setValorFactura(0.0);
        final Response resp = requestBuilder.delete();
        Resultado resultado = resp.readEntity(Resultado.class);
        ResponseOperation salida = new ResponseOperation();
        salida.setMensaje(resultado.getMensaje());
        salida.setStatus("200");
        return salida;
    }
    
   

   

}
