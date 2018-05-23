/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.javeriana.soapclient.ws;

import co.com.javeriana.serviciospublicosapi.dto.RequestOperation;
import co.com.javeriana.serviciospublicosapi.dto.ResponseOperation;

/**
 *
 * @author rchic
 */
public class ClienteSoap {
    
    public ResponseOperation consultar(RequestOperation operation){
        ResponseOperation salida = new  ResponseOperation();
            salida.setId(operation.getId());
        try { 
            PagosServiceService service = new PagosServiceService();
            PagosInerface port = service.getPagosServicePort();
            ReferenciaFactura input = new ReferenciaFactura();
            input.setReferenciaFactura(operation.getFactura().getNumero().toString());
            ResultadoConsulta result = port.cosultar(input);
            
            salida.setSaldo(result.getTotalPagar());
            salida.setStatus("200");
        } catch (Exception ex) {
            salida.setMensaje(ex.getLocalizedMessage());
            salida.setStatus("500");
        }

        return salida;
    }
    
    public ResponseOperation pagar(RequestOperation operation){
        ResponseOperation salida = new  ResponseOperation();
        salida.setId(operation.getId());
        try { 
           PagosServiceService service = new PagosServiceService();
            PagosInerface port = service.getPagosServicePort();            
            Pago input = new Pago();
            ReferenciaFactura factura = new ReferenciaFactura();
            factura.setReferenciaFactura(operation.getFactura().getNumero().toString());
            input.setReferenciaFactura(factura);
            input.setTotalPagar(operation.getFactura().getValor());

            Resultado result = port.pagar(input);
            salida.setMensaje(result.getMensaje());
            salida.setStatus("200");
        } catch (Exception ex) {
            salida.setMensaje(ex.getLocalizedMessage());
            salida.setStatus("500");
        }

        return salida;
    }
    
    
     public ResponseOperation compensar(RequestOperation operation){
        ResponseOperation salida = new  ResponseOperation();
        salida.setId(operation.getId());
        
        try { 
            PagosServiceService service = new PagosServiceService();
            PagosInerface port = service.getPagosServicePort();
            
            Pago input = new Pago();
            ReferenciaFactura factura = new ReferenciaFactura();
            factura.setReferenciaFactura(operation.getFactura().getNumero().toString());
            input.setReferenciaFactura(factura);
            input.setTotalPagar(operation.getFactura().getValor());
            Resultado result = port.compensar(input);
            salida.setMensaje(result.getMensaje());
            salida.setStatus("200");
        } catch (Exception ex) {
            salida.setMensaje(ex.getLocalizedMessage());
            salida.setStatus("500");
        }

        return salida;
     }
    
}
