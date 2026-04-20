package pe.edu.pucp.softprog.bo.clientes;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import pe.edu.pucp.softprog.dao.clientes.ClienteDAOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.clientes.Cliente;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ClienteBOImplTest {

    @Test
    void guardarDebeValidarCategoriaObligatoria() {
        try (MockedConstruction<ClienteDAOImpl> mocked = mockConstruction(ClienteDAOImpl.class)) {
            ClienteBOImpl bo = new ClienteBOImpl();

            Cliente cliente = crearClienteValido();
            cliente.setCategoria(null);

            assertThrows(NullPointerException.class, () -> bo.guardar(cliente, Estado.Nuevo));
            ClienteDAOImpl daoMock = mocked.constructed().getFirst();
            verifyNoInteractions(daoMock);
        }
    }

    @Test
    void guardarModificadoDebeFallarCuandoDaoNoActualiza() {
        try (MockedConstruction<ClienteDAOImpl> mocked = mockConstruction(ClienteDAOImpl.class,
                (dao, context) -> when(dao.actualizar(any(Cliente.class))).thenReturn(false))) {
            ClienteBOImpl bo = new ClienteBOImpl();
            Cliente cliente = crearClienteValido();
            cliente.setId(7);

            assertThrows(IllegalStateException.class, () -> bo.guardar(cliente, Estado.Modificado));
            ClienteDAOImpl daoMock = mocked.constructed().getFirst();
            verify(daoMock).actualizar(cliente);
        }
    }

    private Cliente crearClienteValido() {
        Cliente cliente = new Cliente();
        cliente.setDni("12345678");
        cliente.setNombre("Ana");
        cliente.setApellidoPaterno("Lopez");
        cliente.setGenero(Genero.FEMENINO);
        cliente.setFechaNacimiento(Date.valueOf("1998-02-10"));
        cliente.setLineaCredito(1000);
        cliente.setCategoria(pe.edu.pucp.softprog.modelo.clientes.CategoriaCliente.ESTANDARD);
        cliente.setActivo(true);
        return cliente;
    }
}

