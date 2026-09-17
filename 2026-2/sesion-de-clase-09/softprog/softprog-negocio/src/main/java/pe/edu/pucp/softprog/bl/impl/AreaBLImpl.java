package pe.edu.pucp.softprog.bl.impl;

import pe.edu.pucp.softprog.bl.AreaBL;
import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.dao.AreaDAO;
import pe.edu.pucp.softprog.dao.impl.AreaDAOImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.sql.SQLException;
import java.util.List;

public class AreaBLImpl implements AreaBL {
    private final AreaDAO areaDAO = new AreaDAOImpl();

    @Override
    public List<Area> findAll() throws BLException {
        try {
            return areaDAO.findAll();
        } catch (SQLException e) {
            throw new BLException("No se pudo listar las áreas", e);
        }
    }

    @Override
    public Area findById(Integer id) throws BLException {
        try {
            return areaDAO.findById(id);
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar el área", e);
        }
    }

    @Override
    public void insert(Area area) throws BLException {
        validarNombreUnico(area);
        try {
            areaDAO.insert(area);
        } catch (SQLException e) {
            throw new BLException("No se pudo registrar el área", e);
        }
    }

    @Override
    public void update(Area area) throws BLException {
        validarExiste(area.getId());
        validarNombreUnico(area);
        try {
            areaDAO.update(area);
        } catch (SQLException e) {
            throw new BLException("No se pudo actualizar el área", e);
        }
    }

    @Override
    public void delete(Integer id) throws BLException {
        try {
            areaDAO.delete(id);
        } catch (SQLException e) {
            throw new BLException("No se pudo eliminar el área", e);
        }
    }

    private void validarExiste(int id) throws BLException {
        try {
            if (areaDAO.findById(id) == null) {
                throw new BLException("No existe un área con id " + id);
            }
        } catch (SQLException e) {
            throw new BLException("No se pudo verificar la existencia del área", e);
        }
    }

    private void validarNombreUnico(Area area) throws BLException {
        try {
            Area existente = areaDAO.findByName(area.getNombre());
            if (existente != null && existente.getId() != area.getId()) {
                throw new BLException(
                        "Ya existe un área con el nombre '" + area.getNombre() + "'");
            }
        } catch (SQLException e) {
            throw new BLException("No se pudo verificar la unicidad del nombre del área", e);
        }
    }
}
