package pe.edu.pucp.softprog.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.EmpleadoBL;
import pe.edu.pucp.softprog.bl.impl.EmpleadoBLImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.ws.dto.EmpleadoDTO;
import pe.edu.pucp.softprog.ws.mapper.EmpleadoMapper;

import java.util.List;

@WebService(serviceName = "EmpleadoWS", targetNamespace = "http://ws.softprog.pucp.edu.pe/")
public class EmpleadoWS {
    private final EmpleadoBL empleadoBL = new EmpleadoBLImpl();

    @WebMethod(operationName = "findAllEmpleados")
    public List<EmpleadoDTO> findAllEmpleados() throws BLException {
        return EmpleadoMapper.toDTOs(empleadoBL.findAll());
    }

    @WebMethod(operationName = "findEmpleadoById")
    public EmpleadoDTO findEmpleadoById(@WebParam(name = "id") int id) throws BLException {
        Empleado empleado = empleadoBL.findById(id);
        return empleado != null ? EmpleadoMapper.toDTO(empleado) : null;
    }

    @WebMethod(operationName = "insertEmpleado")
    public int insertEmpleado(@WebParam(name = "empleado") EmpleadoDTO dto) throws BLException {
        Empleado empleado = EmpleadoMapper.toModelo(dto);
        empleadoBL.insert(empleado);
        return empleado.getId();
    }

    @WebMethod(operationName = "updateEmpleado")
    public void updateEmpleado(@WebParam(name = "empleado") EmpleadoDTO dto) throws BLException {
        empleadoBL.update(EmpleadoMapper.toModelo(dto));
    }

    @WebMethod(operationName = "deleteEmpleado")
    public void deleteEmpleado(@WebParam(name = "id") int id) throws BLException {
        empleadoBL.delete(id);
    }

    @WebMethod(operationName = "filterEmpleadosByName")
    public List<EmpleadoDTO> filterEmpleadosByName(@WebParam(name = "nombre") String nombre) throws BLException {
        return EmpleadoMapper.toDTOs(empleadoBL.filterByName(nombre));
    }

    @WebMethod(operationName = "findEmpleadoByCuenta")
    public EmpleadoDTO findEmpleadoByCuenta(@WebParam(name = "userName") String userName) throws BLException {
        Empleado empleado = empleadoBL.findByCuenta(userName);
        return empleado != null ? EmpleadoMapper.toDTO(empleado) : null;
    }
}
