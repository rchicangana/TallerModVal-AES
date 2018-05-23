/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.javeriana.restclient.impl;

import co.com.javeriana.restclient.api.RestClient;






/**
 * runt.com.co
 * @author rchicangana
 */
public class RestClientBuilder {
    
    private String servidor;
    private String contexto = "servicios/pagos/";
    private int puerto = 6060;

    
    
     /**
     * Establece la IP o nombre del servidor donde se enviarán las perticiones
     *
     * @param servidor IP o nombre del servidor
     * @return
     */
    public RestClientBuilder setServidor(final String servidor) {
        this.servidor = servidor;
        return this;
    }

    /**
     * Establece el contexto de consulta del servicio
     *
     * @param contexto el contexto de consulta del servicio
     * @return
     */
    public RestClientBuilder setContexto(final String contexto) {
        this.contexto = contexto;
        return this;
    }

    /**
     * Establece el puerto donde se enviarán las perticiones
     *
     * @param puerto puerto
     * @return
     */
    public RestClientBuilder setPuerto(final int puerto) {
        this.puerto = puerto;
        return this;
    }

 
    /**
     * Construye un nuevo cliente con los parámetros establecidos y lo retorna
     *
     * @return El cliente RUNT con los parámetros establecidos
     */
    public RestClient build() {
            return new RestClientImpl(servidor, puerto, contexto);
    }


}
