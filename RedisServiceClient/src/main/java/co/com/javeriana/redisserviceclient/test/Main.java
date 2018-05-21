/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.redisserviceclient.test;

import co.com.javeriana.redisserviceclient.api.RedisClient;
import co.com.javeriana.redisserviceclient.impl.RedisClientBuilder;
import co.com.javeriana.redisserviceclient.impl.exceptions.ErrorConsultaException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * runt.com.co
 *
 * @author rchicangana
 */
public class Main {

    public static void main(String[] args) {

        RedisClient cliente = getCliente();
        consultar(cliente);
        
    }
    
    
    private static void registrar(RedisClient cliente){
        try {
            String datos = cliente.registrar(1, "Empresa 1");
            System.out.println("salida =======================> "+datos);
            
        } catch (ErrorConsultaException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE.SEVERE, null, ex);
        }
    }
    
    private static void desregistrar(RedisClient cliente){
        try {
            String datos = cliente.desregistrar(1);
            System.out.println("salida =======================> "+datos);
            
        } catch (ErrorConsultaException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE.SEVERE, null, ex);
        }
    }
    
    private static void consultar(RedisClient cliente){
        try {
            String datos = cliente.obtener(1);
            System.out.println("salida =======================> "+datos);
            
        } catch (ErrorConsultaException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE.SEVERE, null, ex);
        }
    }
    
    
    
    public static RedisClient getCliente() {
        final RedisClientBuilder builder = new RedisClientBuilder();
        final RedisClient clienteValImp = builder
                .setServidor("35.185.30.69")
                .setPuerto(9191)
                .setContexto("redisservice/")
                .build();
        return clienteValImp;
    }

}
