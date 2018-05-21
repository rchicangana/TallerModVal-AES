/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.redisservice.services;

import co.com.javeriana.redisservice.logica.RedisLogica;
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
@Path("redis")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public class RedisSevicio {
    
    @EJB
    private RedisLogica redisLogica;
    
    @GET
    @Path("{id}")
    public String getEmpresa(@PathParam("id") Integer id) {
        return redisLogica.getEmpresa(id);
    }
    
    @POST
    @Path("{id}/{datos}")
    public String registrar(@PathParam("id") Integer id, @PathParam("datos") String datos) {
        return redisLogica.registrar(id, datos);
    }
    
    @DELETE
    @Path("{id}")
    public String registrar(@PathParam("id") Integer id) {
        return redisLogica.desregistrar(id);
    }

}
