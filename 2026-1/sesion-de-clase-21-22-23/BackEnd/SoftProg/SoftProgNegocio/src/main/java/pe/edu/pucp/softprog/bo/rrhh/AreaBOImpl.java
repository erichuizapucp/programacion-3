package pe.edu.pucp.softprog.bo.rrhh;

import pe.edu.pucp.softprog.bo.BaseBO;
import pe.edu.pucp.softprog.dao.rrhh.AreaDAO;
import pe.edu.pucp.softprog.dao.rrhh.AreaDAOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.util.List;
import java.util.Objects;

public class AreaBOImpl extends BaseBO implements AreaBO {
    private final AreaDAO areaDao;

    public AreaBOImpl() {
        this.areaDao = new AreaDAOImpl();
    }

    @Override
    public List<Area> listar() {
        return this.areaDao.leerTodos();
    }

    @Override
    public Area obtener(int id) {
        validarIdPositivo(id, "id");
        return this.areaDao.leer(id);
    }

    @Override
    public void eliminar(int id) {
        validarIdPositivo(id, "id");
        if (!this.areaDao.eliminar(id)) {
            throw new IllegalStateException("No se pudo eliminar el area con id: " + id);
        }
    }

    @Override
    public void guardar(Area modelo, Estado estado) {
        validarArea(modelo);
        validarEstado(estado);

        if (estado == Estado.Nuevo) {
            int id = this.areaDao.crear(modelo);
            if (id <= 0) {
                throw new IllegalStateException("No se pudo crear el area");
            }
            modelo.setId(id);
        }
        else if (estado == Estado.Modificado) {
            validarIdPositivo(modelo.getId(), "id del area");
            if (!this.areaDao.actualizar(modelo)) {
                throw new IllegalStateException("No se pudo actualizar el area con id: " + modelo.getId());
            }
        }
        else {
            throw new IllegalArgumentException("Estado no soportado en guardar: " + estado);
        }
    }

    private void validarArea(Area modelo) {
        Objects.requireNonNull(modelo, "El area es obligatoria");
        if (modelo.getNombre() == null || modelo.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del area es obligatorio");
        }
    }
}
