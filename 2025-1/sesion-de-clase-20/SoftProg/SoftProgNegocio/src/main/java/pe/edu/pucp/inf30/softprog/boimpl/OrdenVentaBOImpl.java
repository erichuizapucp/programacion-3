package pe.edu.pucp.inf30.softprog.boimpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.Estado;
import pe.edu.pucp.inf30.softprog.bo.IOrdenVentaBO;
import pe.edu.pucp.inf30.softprog.bo.transaccion.TransaccionalBO;
import pe.edu.pucp.inf30.softprog.bo.transaccion.comando.ComandoGuardarOrdenVenta;
import pe.edu.pucp.inf30.softprog.config.DBManager;
import pe.edu.pucp.inf30.softprog.dao.logistica.ventas.ILineaOrdenVentaDAO;
import pe.edu.pucp.inf30.softprog.dao.logistica.ventas.IOrdenVentaDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.logistica.ventas.LineaOrdenVentaDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.logistica.ventas.OrdenVentaDAOImpl;
import pe.edu.pucp.inf30.softprog.model.logistica.ventas.OrdenVenta;

/**
 *
 * @author eric
 */
public class OrdenVentaBOImpl 
        extends TransaccionalBO 
        implements IOrdenVentaBO 
{
    private final IOrdenVentaDAO ordenVentaDao;
    private final ILineaOrdenVentaDAO lineaOrdenVentaDao;
    
    public OrdenVentaBOImpl() {
        this.ordenVentaDao = new OrdenVentaDAOImpl();
        this.lineaOrdenVentaDao = new LineaOrdenVentaDAOImpl();
    }
    
    @Override
    public List<OrdenVenta> listar() {
        return this.ordenVentaDao.listar();
    }

    @Override
    public OrdenVenta obtener(int id) {
        return this.ordenVentaDao.buscar(id);
    }

    @Override
    public void guardar(OrdenVenta modelo, Estado estado) {
        ComandoGuardarOrdenVenta comando = 
                new ComandoGuardarOrdenVenta(
            this.ordenVentaDao, 
            this.lineaOrdenVentaDao, 
            estado, 
            modelo
        );
        this.ejecutarTransaccion(comando);
    }
    
    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
