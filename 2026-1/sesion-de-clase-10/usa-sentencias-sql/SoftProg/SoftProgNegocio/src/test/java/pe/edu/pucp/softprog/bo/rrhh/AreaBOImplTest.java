package pe.edu.pucp.softprog.bo.rrhh;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import pe.edu.pucp.softprog.dao.rrhh.AreaDAOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.rrhh.Area;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AreaBOImplTest {

    @Test
    void guardarNuevoDebeAsignarIdCreado() {
        try (MockedConstruction<AreaDAOImpl> mocked = mockConstruction(AreaDAOImpl.class,
                (dao, context) -> when(dao.crear(any(Area.class))).thenReturn(11))) {
            AreaBOImpl bo = new AreaBOImpl();
            Area area = new Area();
            area.setNombre("TI");
            area.setActivo(true);

            bo.guardar(area, Estado.Nuevo);

            AreaDAOImpl daoMock = mocked.constructed().getFirst();
            assertEquals(11, area.getId());
            verify(daoMock).crear(area);
        }
    }

    @Test
    void obtenerConIdInvalidoDebeLanzarError() {
        try (MockedConstruction<AreaDAOImpl> mocked = mockConstruction(AreaDAOImpl.class)) {
            AreaBOImpl bo = new AreaBOImpl();
            assertThrows(IllegalArgumentException.class, () -> bo.obtener(0));
            AreaDAOImpl daoMock = mocked.constructed().getFirst();
            verifyNoInteractions(daoMock);
        }
    }
}

