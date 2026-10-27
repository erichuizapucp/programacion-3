package pe.edu.pucp.softprog.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import pe.edu.pucp.softprog.bl.AreaBL;
import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.impl.AreaBLImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Area;
import pe.edu.pucp.softprog.ws.dto.AreaDTO;
import pe.edu.pucp.softprog.ws.mapper.AreaMapper;

import java.util.List;

@WebService(serviceName = "AreaWS",
        targetNamespace = "http://ws.softprog.pucp.edu.pe/area")
public class AreaWS {
    private final AreaBL areaBL = new AreaBLImpl();

    @WebMethod(operationName = "findAllAreas")
    public List<AreaDTO> findAllAreas() throws BLException {
        return AreaMapper.toDTOs(areaBL.findAll());
    }

    @WebMethod(operationName = "findAreaById")
    public AreaDTO findAreaById(@WebParam(name = "id") int id) throws BLException {
        Area area = areaBL.findById(id);
        return area != null ? AreaMapper.toDTO(area) : null;
    }

    @WebMethod(operationName = "insertArea")
    public int insertArea(@WebParam(name = "area") AreaDTO dto) throws BLException {
        Area area = AreaMapper.toModelo(dto);
        areaBL.insert(area);
        return area.getId();
    }

    @WebMethod(operationName = "updateArea")
    public void updateArea(@WebParam(name = "area") AreaDTO dto) throws BLException {
        areaBL.update(AreaMapper.toModelo(dto));
    }

    @WebMethod(operationName = "deleteArea")
    public void deleteArea(@WebParam(name = "id") int id) throws BLException {
        areaBL.delete(id);
    }

    @WebMethod(operationName = "filterAreasByName")
    public List<AreaDTO> filterAreasByName(@WebParam(name = "nombre") String nombre) throws BLException {
        return AreaMapper.toDTOs(areaBL.filterByName(nombre));
    }
}
