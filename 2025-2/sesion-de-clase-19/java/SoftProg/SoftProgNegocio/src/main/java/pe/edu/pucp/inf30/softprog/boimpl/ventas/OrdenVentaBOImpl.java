package pe.edu.pucp.inf30.softprog.boimpl.ventas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.ventas.OrdenVentaBO;
import pe.edu.pucp.inf30.softprog.dao.ventas.LineaOrdenVentaDAO;
import pe.edu.pucp.inf30.softprog.dao.ventas.OrdenVentaDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.ventas.LineaOrdenVentaDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.ventas.OrdenVentaDAOImpl;
import pe.edu.pucp.inf30.softprog.db.DBFactoryProvider;
import pe.edu.pucp.inf30.softprog.db.DBManager;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.modelo.ventas.OrdenVenta;
import pe.edu.pucp.inf30.softprog.modelo.ventas.LineaOrdenVenta;

/**
 *
 * @author eric
 */
public class OrdenVentaBOImpl implements OrdenVentaBO {
    private final OrdenVentaDAO ordenVentaDao;
    private final LineaOrdenVentaDAO lineaOrdenVentaDao;
    
    public OrdenVentaBOImpl() {
        this.ordenVentaDao = new OrdenVentaDAOImpl();
        this.lineaOrdenVentaDao = new LineaOrdenVentaDAOImpl();
    }
    
    @Override
    public List<OrdenVenta> listar() {
        return this.ordenVentaDao.leerTodos();
    }

    @Override
    public OrdenVenta obtener(int id) {
        OrdenVenta orden = ordenVentaDao.leer(id);
        if (orden == null) return null;
        
        List<LineaOrdenVenta> lineas = 
                lineaOrdenVentaDao.leerTodosPorOrden(id);
        orden.setLineas(lineas);
        return orden;
    }

    @Override
    public void eliminar(int id) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection()) {
            conn.setAutoCommit(false);

            try {
                List<LineaOrdenVenta> lineas = 
                        lineaOrdenVentaDao.leerTodosPorOrden(id, conn);
                for (LineaOrdenVenta linea : lineas) {
                    if (linea.getOrdenVenta().getId() == id) {
                        lineaOrdenVentaDao.eliminar(linea.getId(), conn);
                    }
                }

                if (!ordenVentaDao.eliminar(id, conn)) {
                    throw new RuntimeException("La Orden: " + id + ", "
                            + "no se pudo eliminar");
                }
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new RuntimeException("Error eliminando OrdenVenta "
                        + "con id=" + id, ex);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error de conexión al eliminar "
                    + "OrdenVenta", e);
        }
    }

    @Override
    public void guardar(OrdenVenta modelo, Estado estado) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection()) {
            conn.setAutoCommit(false);

            try {
                switch (estado) {
                    case Nuevo -> {
                        int idOrden = this.ordenVentaDao.crear(modelo, conn);
                        modelo.setId(idOrden);
                        for (LineaOrdenVenta linea : modelo.getLineas()) {
                            linea.setOrdenVenta(modelo);
                            lineaOrdenVentaDao.crear(linea, conn);
                        }
                    }
                    case Modificado -> {
                        ordenVentaDao.actualizar(modelo, conn);
                        for (LineaOrdenVenta linea : modelo.getLineas()) {
                            if (linea.getId() == 0) {
                                linea.setOrdenVenta(modelo);
                                lineaOrdenVentaDao.crear(linea, conn);
                            } else {
                                lineaOrdenVentaDao.actualizar(linea, conn);
                            }
                        }
                    }
                }

                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw new RuntimeException("Error guardando OrdenVenta", ex);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error de conexión al guardar OrdenVenta"
                    + "", e);
        }
    }

    @Override
    public List<OrdenVenta> listarOrdenesVentaPorCuenta(String cuenta) {
        return this.ordenVentaDao.listarOrdenesVentaPorCuenta(cuenta);
    }
}
