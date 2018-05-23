/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.restclient.api;

import co.com.javeriana.restclient.exceptions.ErrorConsultaException;
import co.com.javeriana.serviciospublicosapi.dto.RequestOperation;
import co.com.javeriana.serviciospublicosapi.dto.ResponseOperation;




/**
 * runt.com.co
 *
 * @author rchicangana
 */
public interface RestClient {

    /**
     * Permite realizar el registro de una empresa
     *
     * @param id
     * @param dato
     * @return
     * @throws co.com.javeriana.redisserviceclient.impl.exceptions.ErrorConsultaException
     */
    public ResponseOperation consultar(Integer numeroFactura, Integer idEmpresa) throws ErrorConsultaException;
    
    
    /**
     * Permite desregistrar una empresa
     * @param id
     * @return 
     * @throws co.com.javeriana.redisserviceclient.impl.exceptions.ErrorConsultaException 
     */
    public ResponseOperation pagar(RequestOperation operation) throws ErrorConsultaException;
    
    /**
     * Consultar una empresa
     * @param id
     * @return
     * @throws ErrorConsultaException 
     */
    public ResponseOperation compensar(Integer numeroFactura, Integer idEmpresa) throws ErrorConsultaException;
    
    
}
