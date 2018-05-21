/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.suscripcionservice.logica;

import co.com.javeriana.redisserviceclient.api.RedisClient;
import co.com.javeriana.redisserviceclient.impl.RedisClientBuilder;
import co.com.javeriana.redisserviceclient.impl.exceptions.ErrorConsultaException;
import co.com.javeriana.serviciospublicosapi.dto.RequestSuscription;
import co.com.javeriana.serviciospublicosapi.dto.ResponseSuscription;
import com.google.gson.Gson;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author rchic
 */
@Stateless
public class SuscripcionLogica {

    private final ResourceBundle props = ResourceBundle.getBundle("prop", Locale.ROOT);

    private RedisClient getCliente() {
        final RedisClientBuilder builder = new RedisClientBuilder();
        final RedisClient clienteValImp = builder
                .setServidor(props.getString("host"))
                .setPuerto(Integer.valueOf(props.getString("port")))
                .setContexto("redisservice/")
                .build();
        return clienteValImp;
    }

    public ResponseSuscription suscribir(RequestSuscription entrada) {
        String resultado;
        ResponseSuscription salida = new ResponseSuscription();
        salida.setId(UUID.randomUUID().toString());

        try {
            Gson g = new Gson();
            resultado = getCliente().registrar(Integer.valueOf(entrada.getEmpresa().getId()), Base64.encodeBase64String((g.toJson(entrada)).getBytes()));
            salida.setMensaje(resultado);
            salida.setStatus("200");
        } catch (ErrorConsultaException ex) {
            Logger.getLogger(SuscripcionLogica.class.getName()).log(Level.SEVERE, null, ex);
            salida.setMensaje(ex.getLocalizedMessage());
            salida.setStatus("500");
        }
        return salida;

    }
    
    public ResponseSuscription dessuscribir(Integer id) {
        String resultado;
        ResponseSuscription salida = new ResponseSuscription();
        salida.setId(UUID.randomUUID().toString());

        try {
            Gson g = new Gson();
            resultado = getCliente().desregistrar(id);
            salida.setMensaje(resultado);
            salida.setStatus("200");
        } catch (ErrorConsultaException ex) {
            Logger.getLogger(SuscripcionLogica.class.getName()).log(Level.SEVERE, null, ex);
            salida.setMensaje(ex.getLocalizedMessage());
            salida.setStatus("500");
        }
        return salida;

    }
    
    public ResponseSuscription obtener(Integer id) {
        String resultado;
        ResponseSuscription salida = new ResponseSuscription();
        salida.setId(UUID.randomUUID().toString());

        try {
            Gson g = new Gson();
            resultado = getCliente().obtener(id);
            salida.setMensaje(resultado);
            salida.setStatus("200");
        } catch (ErrorConsultaException ex) {
            Logger.getLogger(SuscripcionLogica.class.getName()).log(Level.SEVERE, null, ex);
            salida.setMensaje(ex.getLocalizedMessage());
            salida.setStatus("500");
        }
        return salida;

    }

}
