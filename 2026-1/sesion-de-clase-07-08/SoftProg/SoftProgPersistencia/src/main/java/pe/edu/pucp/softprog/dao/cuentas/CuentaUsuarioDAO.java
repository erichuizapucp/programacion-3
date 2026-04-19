package pe.edu.pucp.softprog.dao.cuentas;

import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;

import java.util.List;

public interface CuentaUsuarioDAO {
    Integer crear(CuentaUsuario modelo);
    boolean actualizar(CuentaUsuario modelo);
    boolean eliminar(int id);
    CuentaUsuario leer(int id);
    List<CuentaUsuario> leerTodos();

    boolean login(String username, String password);
}

