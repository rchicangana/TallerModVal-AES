/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.restclient.test;


import co.com.javeriana.restclient.api.RestClient;
import co.com.javeriana.restclient.exceptions.ErrorConsultaException;
import co.com.javeriana.restclient.impl.RestClientBuilder;
import co.com.javeriana.serviciospublicosapi.dto.Factura;
import co.com.javeriana.serviciospublicosapi.dto.RequestOperation;
import co.com.javeriana.serviciospublicosapi.dto.ResponseOperation;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * runt.com.co
 *
 * @author rchicangana
 */
public class Main {

    public static void main(String[] args) {

        RestClient cliente = getCliente();
        pagar(cliente);
        
    }
    
    
    
    
       
    private static void consultar(RestClient cliente){
        try {
            ResponseOperation datos = cliente.consultar(123456, 123456);
            System.out.println("salida =======================> "+datos.getSaldo());
            
        } catch (ErrorConsultaException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE.SEVERE, null, ex);
        }
    }
    
     private static void pagar(RestClient cliente){
        try {
            RequestOperation operation = new RequestOperation();
            Factura factura = new Factura();
            factura.setNumero(123456);
            factura.setValor(1234567.00);
            operation.setFactura(factura);
            ResponseOperation datos = cliente.pagar(operation);
            System.out.println("salida =======================> "+datos.getMensaje());
            
        } catch (ErrorConsultaException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE.SEVERE, null, ex);
        }
    }
    
    
    
    public static RestClient getCliente() {
        final RestClientBuilder builder = new RestClientBuilder();
        final RestClient clienteValImp = builder
                .setServidor("35.185.30.69")
                .setPuerto(6060)
                .setContexto("servicios/pagos/")
                .build();
        return clienteValImp;
    }

}
