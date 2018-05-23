/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.operationservice.logica;

import co.com.javeriana.serviciospublicosapi.dto.Empresa;
import co.com.javeriana.serviciospublicosapi.dto.Factura;
import co.com.javeriana.serviciospublicosapi.dto.RequestOperation;
import co.com.javeriana.serviciospublicosapi.dto.ResponseOperation;
import com.google.gson.Gson;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author rchic
 */
@Stateless
public class OperacionesLogica {
    
     private ResourceBundle props = ResourceBundle.getBundle("prop", Locale.ROOT);
    
    @EJB
    private ConsumerLogica consumerLogica;
    
    @EJB
    private ProducerLogica producerLogica;
    
    
    public ResponseOperation consultar(Integer numeroFactura, String idEmpresa){
         try {
             RequestOperation operation = new RequestOperation();
             Empresa empresa= new Empresa();
             Factura factura = new Factura();
             empresa.setId(idEmpresa);
             factura.setNumero(numeroFactura);
             operation.setOperation("CONSULTAR");
             String id = UUID.randomUUID().toString();
             operation.setId(id);
             operation.setEmpresa(empresa);
             operation.setFactura(factura);
             Gson g = new Gson();
             String host = props.getString("host");
             String port = props.getString("port");
             producerLogica.runProducer(host+":"+port, Base64.encodeBase64String((g.toJson(operation)).getBytes()));
             return consumerLogica.run(id, host, Integer.valueOf(port));
         } catch (Exception ex) {
             Logger.getLogger(OperacionesLogica.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }
    
    public ResponseOperation pagar(RequestOperation operation){
         try {
             String id = UUID.randomUUID().toString();
             operation.setId(id);
             operation.setOperation("PAGAR");
             Gson g = new Gson();
             String host = props.getString("host");
             String port = props.getString("port");
             producerLogica.runProducer(host+":"+port, Base64.encodeBase64String((g.toJson(operation)).getBytes()));
             return consumerLogica.run(id, host, Integer.valueOf(port));
         } catch (Exception ex) {
             Logger.getLogger(OperacionesLogica.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }
    
    public ResponseOperation compensar(Integer numeroFactura, String idEmpresa){
         try {
             RequestOperation operation = new RequestOperation();
             String id = UUID.randomUUID().toString();
             operation.setId(id);
             operation.setOperation("COMPENSAR");
             Factura factura = new Factura();
             factura.setValor(Double.valueOf("0"));
             factura.setNumero(numeroFactura);
             Empresa empresa= new Empresa();
             empresa.setId(idEmpresa);
             operation.setEmpresa(empresa);
             operation.setFactura(factura);
             Gson g = new Gson();
             String host = props.getString("host");
             String port = props.getString("port");
             producerLogica.runProducer(host+":"+port, Base64.encodeBase64String((g.toJson(operation)).getBytes()));
             return consumerLogica.run(id, host, Integer.valueOf(port));
         } catch (Exception ex) {
             Logger.getLogger(OperacionesLogica.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }
    
}
