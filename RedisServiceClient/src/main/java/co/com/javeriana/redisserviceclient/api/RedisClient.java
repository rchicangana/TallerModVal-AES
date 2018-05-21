/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.redisserviceclient.api;

import co.com.javeriana.redisserviceclient.impl.exceptions.ErrorConsultaException;


/**
 * runt.com.co
 *
 * @author rchicangana
 */
public interface RedisClient {

    /**
     * Permite realizar el registro de una empresa
     *
     * @param id
     * @param dato
     * @return
     * @throws co.com.javeriana.redisserviceclient.impl.exceptions.ErrorConsultaException
     */
    public String registrar(Integer id, String dato) throws ErrorConsultaException;
    
    
    /**
     * Permite desregistrar una empresa
     * @param id
     * @return 
     * @throws co.com.javeriana.redisserviceclient.impl.exceptions.ErrorConsultaException 
     */
    public String desregistrar(Integer id) throws ErrorConsultaException;
    
    /**
     * Consultar una empresa
     * @param id
     * @return
     * @throws ErrorConsultaException 
     */
    public String obtener(Integer id) throws ErrorConsultaException;
    
    
}
