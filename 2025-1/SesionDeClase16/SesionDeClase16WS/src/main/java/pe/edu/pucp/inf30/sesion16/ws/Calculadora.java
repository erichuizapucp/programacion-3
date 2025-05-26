package pe.edu.pucp.inf30.sesion16.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;

/**
 *
 * @author eric
 */
@WebService(serviceName = "Calculadora")
public class Calculadora {

    /**
     * This is a sample web service operation
     * @param a
     * @param b
     * @return 
     */
    @WebMethod(operationName = "sumar")
    public int sumar(
            @WebParam(name = "a") int a, 
            @WebParam(name = "b") int b
    ) {
        return a + b;
    }
}
