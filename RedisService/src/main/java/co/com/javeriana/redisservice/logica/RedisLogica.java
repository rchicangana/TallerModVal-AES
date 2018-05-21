/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.redisservice.logica;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.ejb.Stateless;
import redis.clients.jedis.Jedis;

/**
 *
 * @author rchic
 */
@Stateless
public class RedisLogica {
    
    private ResourceBundle props = ResourceBundle.getBundle("prop", Locale.ROOT);
    
    public Jedis getRedisHost(){
        
        return new Jedis(props.getString("hostRedis"), Integer.valueOf(props.getString("portRedis")));
    }
    
    public String getEmpresa(Integer id){
        
        Jedis jedis = getRedisHost();
        String value = jedis.get(id.toString());
        return value;
        
        
    }
    
    public String registrar(Integer id, String datos){
        
        Jedis jedis = getRedisHost();
        String value = jedis.get(id.toString());
        if (value==null){
            jedis.set(id.toString(), datos);
            return "OK";
        } else {
            return "Empresa Ya registrada";
        }
        
        
    }
    
    public String desregistrar(Integer id){
        
        Jedis jedis = getRedisHost();
        String value = jedis.get(id.toString());
        if (value!=null){
            jedis.del(id.toString());
            return "OK";
        } else {
            return "Empresa no esta Registrada";
        }
        
        
    }
            
    
}
