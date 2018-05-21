/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.javeriana.redisserviceclient.impl;

import co.com.javeriana.redisserviceclient.api.RedisClient;




/**
 * runt.com.co
 * @author rchicangana
 */
public class RedisClientBuilder {
    
    private String servidor;
    private String contexto = "redisservice";
    private int puerto = 9191;

    
    
     /**
     * Establece la IP o nombre del servidor donde se enviarán las perticiones
     *
     * @param servidor IP o nombre del servidor
     * @return
     */
    public RedisClientBuilder setServidor(final String servidor) {
        this.servidor = servidor;
        return this;
    }

    /**
     * Establece el contexto de consulta del servicio
     *
     * @param contexto el contexto de consulta del servicio
     * @return
     */
    public RedisClientBuilder setContexto(final String contexto) {
        this.contexto = contexto;
        return this;
    }

    /**
     * Establece el puerto donde se enviarán las perticiones
     *
     * @param puerto puerto
     * @return
     */
    public RedisClientBuilder setPuerto(final int puerto) {
        this.puerto = puerto;
        return this;
    }

 
    /**
     * Construye un nuevo cliente con los parámetros establecidos y lo retorna
     *
     * @return El cliente RUNT con los parámetros establecidos
     */
    public RedisClient build() {
            return new RedisClientImpl(servidor, puerto, contexto);
    }


}
