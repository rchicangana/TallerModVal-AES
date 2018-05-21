/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.serviciospublicosapi.dto;

/**
 *
 * @author rchic
 */
public class TipoServicio {
    
    private String tipo;
    private String endPoint;
    private String manejaCompensancion;
    private String correoCompensacion;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getManejaCompensancion() {
        return manejaCompensancion;
    }

    public void setManejaCompensancion(String manejaCompensancion) {
        this.manejaCompensancion = manejaCompensancion;
    }

    public String getCorreoCompensacion() {
        return correoCompensacion;
    }

    public void setCorreoCompensacion(String correoCompensacion) {
        this.correoCompensacion = correoCompensacion;
    }
    
    
    
    
}
