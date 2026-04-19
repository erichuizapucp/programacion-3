package pe.edu.pucp.softprog.dao.clientes;

import pe.edu.pucp.softprog.modelo.clientes.Cliente;
import java.util.List;

public interface ClienteDAO {
    Integer crear(Cliente modelo);
    boolean actualizar(Cliente modelo);
    boolean eliminar(int id);
    Cliente leer(int id);
    List<Cliente> leerTodos();

    Cliente buscarPorDni(String dni);
}
