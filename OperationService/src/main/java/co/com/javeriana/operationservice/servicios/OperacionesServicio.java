/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.operationservice.servicios;

import co.com.javeriana.operationservice.logica.OperacionesLogica;
import co.com.javeriana.serviciospublicosapi.dto.RequestOperation;
import co.com.javeriana.serviciospublicosapi.dto.ResponseOperation;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rchic
 */
@Path("operaciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OperacionesServicio {
    
    @EJB
    private OperacionesLogica operacionesLogica;
    
    @GET
    @Path("consultar/{idEmpresa}/{numeroFactura}")
    public ResponseOperation consultar(@PathParam("idEmpresa") String idEmpresa, @PathParam("numeroFactura") Integer numeroFactura){
        return operacionesLogica.consultar(numeroFactura, idEmpresa);
    }
    
    
    @POST
    @Path("pagar")
    public ResponseOperation pagar(RequestOperation operation){
        return operacionesLogica.pagar(operation);
    }
    
    @DELETE
    @Path("compensar")
    public ResponseOperation compensar(RequestOperation operation){
        return operacionesLogica.compensar(operation);
    }
    
    
}
