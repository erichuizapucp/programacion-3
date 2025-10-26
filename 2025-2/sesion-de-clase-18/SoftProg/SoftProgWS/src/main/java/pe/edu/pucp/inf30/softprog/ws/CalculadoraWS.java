package pe.edu.pucp.inf30.softprog.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;

/**
 *
 * @author eric
 */
@WebService(
        serviceName = "CalculadoraWS", 
        targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class CalculadoraWS {

    @WebMethod(operationName = "sumar")
    public int sumar(
        @WebParam(name = "a") int a, 
        @WebParam(name = "b") int b) {
        
        return a + b;
    }
    
    @WebMethod(operationName = "restar")
    public int restar(
            @WebParam(name = "a") int a, 
            @WebParam(name = "b") int b) {
        return a - b;
    }
    
    @WebMethod(operationName = "multiplicar")
    public int multiplicar(
            @WebParam(name = "a") int a, 
            @WebParam(name = "b") int b) {
        return a * b;
    }
}
