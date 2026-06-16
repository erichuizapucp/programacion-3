package pe.edu.pucp.softprog.bo.rrhh;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import pe.edu.pucp.softprog.dao.rrhh.EmpleadoDAOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.rrhh.Area;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class EmpleadoBOImplTest {

    @Test
    void guardarDebeValidarAreaObligatoria() {
        try (MockedConstruction<EmpleadoDAOImpl> mocked = mockConstruction(EmpleadoDAOImpl.class)) {
            EmpleadoBOImpl bo = new EmpleadoBOImpl();
            Empleado empleado = crearEmpleadoValido();
            empleado.setArea(null);

            assertThrows(NullPointerException.class, () -> bo.guardar(empleado, Estado.Nuevo));
            EmpleadoDAOImpl daoMock = mocked.constructed().getFirst();
            verifyNoInteractions(daoMock);
        }
    }

    @Test
    void guardarModificadoDebeFallarCuandoDaoNoActualiza() {
        try (MockedConstruction<EmpleadoDAOImpl> mocked = mockConstruction(EmpleadoDAOImpl.class,
                (dao, ignored) -> when(dao.actualizar(any())).thenReturn(false))) {
            EmpleadoBOImpl bo = new EmpleadoBOImpl();
            Empleado empleado = crearEmpleadoValido();
            empleado.setId(9);

            assertThrows(IllegalStateException.class, () -> bo.guardar(empleado, Estado.Modificado));
            EmpleadoDAOImpl daoMock = mocked.constructed().getFirst();
            verify(daoMock).actualizar(empleado);
        }
    }

    private Empleado crearEmpleadoValido() {
        Empleado empleado = new Empleado();
        Area area = new Area();
        area.setId(1);

        empleado.setArea(area);
        empleado.setDni("12345678");
        empleado.setNombre("Juan");
        empleado.setApellidoPaterno("Perez");
        empleado.setGenero(Genero.MASCULINO);
        empleado.setFechaNacimiento(Date.valueOf("1995-06-15"));
        empleado.setCargo(Cargo.ASISTENTE);
        empleado.setSueldo(2000);
        empleado.setActivo(true);
        return empleado;
    }
}


