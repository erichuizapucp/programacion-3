package pe.edu.pucp.softprog.bo.rrhh;

import pe.edu.pucp.softprog.bo.PersonaBOImpl;
import pe.edu.pucp.softprog.dao.rrhh.EmpleadoDAO;
import pe.edu.pucp.softprog.dao.rrhh.EmpleadoDAOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.util.List;
import java.util.Objects;

public class EmpleadoBOImpl extends PersonaBOImpl<Empleado> implements EmpleadoBO {
    private final EmpleadoDAO empleadoDao;

    public EmpleadoBOImpl() {
        this.empleadoDao = new EmpleadoDAOImpl();
    }

    @Override
    public List<Empleado> listar() {
        return this.empleadoDao.leerTodos();
    }

    @Override
    public Empleado obtener(int id) {
        validarIdPositivo(id, "id");
        return this.empleadoDao.leer(id);
    }

    @Override
    public void eliminar(int id) {
        validarIdPositivo(id, "id");
        if (!this.empleadoDao.eliminar(id)) {
            throw new IllegalStateException("No se pudo eliminar el empleado con id: " + id);
        }
    }

    @Override
    public void guardar(Empleado modelo, Estado estado) {
        validarEmpleado(modelo);
        validarEstado(estado);

        if (estado == Estado.Nuevo) {
            int id = this.empleadoDao.crear(modelo);
            if (id <= 0) {
                throw new IllegalStateException("No se pudo crear el empleado");
            }
            modelo.setId(id);
        }
        else if (estado == Estado.Modificado) {
            validarIdPositivo(modelo.getId(), "id del empleado");
            if (!this.empleadoDao.actualizar(modelo)) {
                throw new IllegalStateException("No se pudo actualizar el empleado con id: " + modelo.getId());
            }
        }
        else {
            throw new IllegalArgumentException("Estado no soportado en guardar: " + estado);
        }
    }

    private void validarEmpleado(Empleado modelo) {
        validarPersonaBasica(modelo, "empleado");
        Objects.requireNonNull(modelo.getArea(), "El area del empleado es obligatoria");
        validarIdPositivo(modelo.getArea().getId(), "id de area");
        Objects.requireNonNull(modelo.getCargo(), "El cargo es obligatorio");
        if (modelo.getSueldo() < 0) {
            throw new IllegalArgumentException("El sueldo no puede ser negativo");
        }
    }
}
