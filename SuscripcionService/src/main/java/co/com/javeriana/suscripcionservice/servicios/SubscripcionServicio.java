/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.suscripcionservice.servicios;

import co.com.javeriana.serviciospublicosapi.dto.RequestSuscription;
import co.com.javeriana.serviciospublicosapi.dto.ResponseSuscription;
import co.com.javeriana.suscripcionservice.logica.SuscripcionLogica;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
@Path("subscripcion")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubscripcionServicio {
    
    @EJB
    private SuscripcionLogica suscripcionLogica;
    
    @GET
    @Path("obtener/{id}")
    public ResponseSuscription obtener(@PathParam("id") Integer id){
        return suscripcionLogica.obtener(id);
    }
    
    @POST
    @Path("suscribirse")
    public ResponseSuscription suscribirse(RequestSuscription entrada){
        return suscripcionLogica.suscribir(entrada);
    }
    
    @DELETE
    @Path("desuscribirse/{id}")
    public ResponseSuscription dessuscribirse(@PathParam("id") Integer id){
        return suscripcionLogica.dessuscribir(id);
    }
}
