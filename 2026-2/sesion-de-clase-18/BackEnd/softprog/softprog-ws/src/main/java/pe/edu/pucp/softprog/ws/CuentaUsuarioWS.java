package pe.edu.pucp.softprog.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.CuentaUsuarioBL;
import pe.edu.pucp.softprog.bl.impl.CuentaUsuarioBLImpl;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import pe.edu.pucp.softprog.ws.dto.CuentaUsuarioDTO;
import pe.edu.pucp.softprog.ws.mapper.CuentaUsuarioMapper;

import java.util.List;

@WebService(serviceName = "CuentaUsuarioWS", targetNamespace = "http://ws.softprog.pucp.edu.pe/cuentausuario")
public class CuentaUsuarioWS {
    private final CuentaUsuarioBL cuentaUsuarioBL = new CuentaUsuarioBLImpl();

    @WebMethod(operationName = "findAllCuentasUsuario")
    public List<CuentaUsuarioDTO> findAllCuentasUsuario() throws BLException {
        return CuentaUsuarioMapper.toDTOs(cuentaUsuarioBL.findAll());
    }

    @WebMethod(operationName = "findCuentaUsuarioById")
    public CuentaUsuarioDTO findCuentaUsuarioById(@WebParam(name = "id") int id) throws BLException {
        CuentaUsuario cuentaUsuario = cuentaUsuarioBL.findById(id);
        return cuentaUsuario != null ? CuentaUsuarioMapper.toDTO(cuentaUsuario) : null;
    }

    @WebMethod(operationName = "insertCuentaUsuario")
    public int insertCuentaUsuario(@WebParam(name = "cuentaUsuario") CuentaUsuarioDTO dto) throws BLException {
        CuentaUsuario cuentaUsuario = CuentaUsuarioMapper.toModelo(dto);
        cuentaUsuarioBL.insert(cuentaUsuario);
        return cuentaUsuario.getId();
    }

    @WebMethod(operationName = "updateCuentaUsuario")
    public void updateCuentaUsuario(@WebParam(name = "cuentaUsuario") CuentaUsuarioDTO dto) throws BLException {
        cuentaUsuarioBL.update(CuentaUsuarioMapper.toModelo(dto));
    }

    @WebMethod(operationName = "deleteCuentaUsuario")
    public void deleteCuentaUsuario(@WebParam(name = "id") int id) throws BLException {
        cuentaUsuarioBL.delete(id);
    }

    @WebMethod(operationName = "findByUserName")
    public CuentaUsuarioDTO findByUserName(@WebParam(name = "userName") String userName) throws BLException {
        CuentaUsuario cuentaUsuario = cuentaUsuarioBL.findByUserName(userName);
        return cuentaUsuario != null ? CuentaUsuarioMapper.toDTO(cuentaUsuario) : null;
    }

    @WebMethod(operationName = "autenticar")
    public boolean autenticar(@WebParam(name = "userName") String userName,
                              @WebParam(name = "password") String password) throws BLException {
        return cuentaUsuarioBL.autenticar(userName, password);
    }

    @WebMethod(operationName = "cambiarPassword")
    public void cambiarPassword(@WebParam(name = "id") int id,
                                @WebParam(name = "passwordActual") String passwordActual,
                                @WebParam(name = "passwordNueva") String passwordNueva) throws BLException {
        cuentaUsuarioBL.cambiarPassword(id, passwordActual, passwordNueva);
    }
}
