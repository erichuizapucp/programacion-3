package pe.edu.pucp.softprog.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.OrdenVentaBL;
import pe.edu.pucp.softprog.bl.impl.OrdenVentaBLImpl;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;
import pe.edu.pucp.softprog.ws.dto.OrdenVentaDTO;
import pe.edu.pucp.softprog.ws.mapper.OrdenVentaMapper;

import java.util.List;

@WebService(serviceName = "OrdenVentaWS", targetNamespace = "http://ws.softprog.pucp.edu.pe/ordenventa")
public class OrdenVentaWS {
    private final OrdenVentaBL ordenVentaBL = new OrdenVentaBLImpl();

    @WebMethod(operationName = "findAllOrdenesVenta")
    public List<OrdenVentaDTO> findAllOrdenesVenta() throws BLException {
        return OrdenVentaMapper.toDTOs(ordenVentaBL.findAll());
    }

    @WebMethod(operationName = "findOrdenVentaById")
    public OrdenVentaDTO findOrdenVentaById(@WebParam(name = "id") int id) throws BLException {
        OrdenVenta ordenVenta = ordenVentaBL.findById(id);
        return ordenVenta != null ? OrdenVentaMapper.toDTO(ordenVenta) : null;
    }

    @WebMethod(operationName = "insertOrdenVenta")
    public int insertOrdenVenta(@WebParam(name = "ordenVenta") OrdenVentaDTO dto) throws BLException {
        OrdenVenta ordenVenta = OrdenVentaMapper.toModelo(dto);
        ordenVentaBL.insert(ordenVenta);
        return ordenVenta.getId();
    }

    @WebMethod(operationName = "updateOrdenVenta")
    public void updateOrdenVenta(@WebParam(name = "ordenVenta") OrdenVentaDTO dto) throws BLException {
        ordenVentaBL.update(OrdenVentaMapper.toModelo(dto));
    }

    @WebMethod(operationName = "deleteOrdenVenta")
    public void deleteOrdenVenta(@WebParam(name = "id") int id) throws BLException {
        ordenVentaBL.delete(id);
    }

    @WebMethod(operationName = "findOrdenesVentaByCuenta")
    public List<OrdenVentaDTO> findOrdenesVentaByCuenta(@WebParam(name = "userName") String userName) throws BLException {
        return OrdenVentaMapper.toDTOs(ordenVentaBL.findByCuenta(userName));
    }
}
