/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.intermediateroutingservice.logica;

import co.com.javeriana.redisserviceclient.api.RedisClient;
import co.com.javeriana.redisserviceclient.impl.RedisClientBuilder;
import co.com.javeriana.redisserviceclient.impl.exceptions.ErrorConsultaException;
import co.com.javeriana.restclient.api.RestClient;
import co.com.javeriana.restclient.impl.RestClientBuilder;
import co.com.javeriana.serviciospublicosapi.dto.RequestOperation;
import co.com.javeriana.serviciospublicosapi.dto.RequestSuscription;
import co.com.javeriana.serviciospublicosapi.dto.ResponseOperation;
import co.com.javeriana.soapclient.ws.ClienteSoap;

import com.google.gson.Gson;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author rchic
 */
@Stateless
public class IntermediateLogica {
    
    @EJB
    private ProducerLogica producerLogica;
            
    private final ResourceBundle props = ResourceBundle.getBundle("prop", Locale.ROOT);

    private RedisClient getCliente() {
        final RedisClientBuilder builder = new RedisClientBuilder();
        final RedisClient clienteValImp = builder
                .setServidor(props.getString("hostRedis"))
                .setPuerto(Integer.valueOf(props.getString("portRedis")))
                .setContexto("redisservice/")
                .build();
        return clienteValImp;
    }
    
    private RestClient getClienteRest() {
        final RestClientBuilder builder = new RestClientBuilder();
        final RestClient clienteValImp = builder
                .setServidor("35.185.30.69")
                .setPuerto(6060)
                .setContexto("servicios/pagos/")
                .build();
        return clienteValImp;
    }
    
    @Asynchronous
    public void routing(RequestOperation operation){
        Gson g = new Gson();
        ResponseOperation salida = new ResponseOperation();
        String host = props.getString("host");
        String port = props.getString("port");
        salida.setId(operation.getId());
        try {
            //recuperamos la empresa
            String empresaB64 = getCliente().obtener(Integer.valueOf(operation.getEmpresa().getId()));
            System.out.println("Empresa "+operation.getEmpresa().getId()+"    "+empresaB64);
            if(empresaB64 == null || "".equals(empresaB64)){
                
                salida.setStatus("200");
                salida.setMensaje("No se encontro empresa registrada con ese id");
                producerLogica.runProducer(host+":"+port, Base64.encodeBase64String(g.toJson(salida).getBytes()));
                return;
            }
            RequestSuscription empresa = g.fromJson(new String(Base64.decodeBase64(empresaB64)),RequestSuscription.class);
            //System.out.println("co.com.javeriana.intermediateroutingservice.logica.IntermediateLogica.routing() "+empresa.toString());
            if("CONSULTAR".equals(operation.getOperation())){
                if("SOAP".equals(empresa.getTipoServicio().getTipo())){
                    ClienteSoap cliente = new ClienteSoap();
                    salida = cliente.consultar(operation);
                } else {
                    salida = getClienteRest().consultar(operation.getFactura().getNumero(), Integer.BYTES);
                }
            }
            if("PAGAR".equals(operation.getOperation())){
                if("SOAP".equals(empresa.getTipoServicio().getTipo())){
                    ClienteSoap cliente = new ClienteSoap();
                    salida = cliente.pagar(operation);
                } else {
                    salida = getClienteRest().pagar(operation);
                }
                
            }
            if("COMPENSAR".equals(operation.getOperation())){
                if("SOAP".equals(empresa.getTipoServicio().getTipo())){
                    ClienteSoap cliente = new ClienteSoap();
                    salida = cliente.compensar(operation);
                } else {
                    salida = getClienteRest().compensar(operation.getFactura().getNumero(), Integer.BYTES);
                }
                
            }
            
            producerLogica.runProducer(host+":"+port, Base64.encodeBase64String(g.toJson(salida).getBytes()));
            
            
        } catch (ErrorConsultaException ex) {
            Logger.getLogger(IntermediateLogica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(IntermediateLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
