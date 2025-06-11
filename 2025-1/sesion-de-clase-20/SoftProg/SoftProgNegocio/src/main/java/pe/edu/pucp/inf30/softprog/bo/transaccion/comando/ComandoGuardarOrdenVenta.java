package pe.edu.pucp.inf30.softprog.bo.transaccion.comando;

import java.sql.Connection;
import pe.edu.pucp.inf30.softprog.bo.transaccion.IComandoTransaccional;
import pe.edu.pucp.inf30.softprog.dao.logistica.ventas.ILineaOrdenVentaDAO;
import pe.edu.pucp.inf30.softprog.dao.logistica.ventas.IOrdenVentaDAO;
import pe.edu.pucp.inf30.softprog.model.Estado;
import pe.edu.pucp.inf30.softprog.model.logistica.ventas.LineaOrdenVenta;
import pe.edu.pucp.inf30.softprog.model.logistica.ventas.OrdenVenta;

/**
 *
 * @author eric
 */
public class ComandoGuardarOrdenVenta extends ComandoBase 
        implements IComandoTransaccional {
    public ComandoGuardarOrdenVenta(
        IOrdenVentaDAO ordenVentaDAO, 
        ILineaOrdenVentaDAO lineaOrdenVentaDAO, 
        Estado estado, 
        OrdenVenta modelo
    ) {
        super(ordenVentaDAO, lineaOrdenVentaDAO, estado, modelo);
    }
    
    @Override
    public void ejecutar(Connection conexion) {
        IOrdenVentaDAO ordenVentaDao = this.getOrdenVentaDAO();
        ILineaOrdenVentaDAO lineaOrdenVentaDao = this.getLineaOrdenVentaDAO();
        Estado estado = this.getEstado();
        OrdenVenta ordenVenta = this.getModelo();
        
        if (estado == Estado.Nuevo) {
            int orderId = ordenVentaDao.insertar(
                    ordenVenta, 
                    conexion);
            ordenVenta.setId(orderId);
            
            for (LineaOrdenVenta linea : ordenVenta.getLineasOrdenVenta()) {
                linea.setOrdenVenta(ordenVenta);
                lineaOrdenVentaDao.insertar(linea, conexion);
            }
        }
        else {
            ordenVentaDao.modificar(ordenVenta, conexion);
            for (LineaOrdenVenta linea : ordenVenta.getLineasOrdenVenta()) {
                linea.setOrdenVenta(ordenVenta);
                lineaOrdenVentaDao.modificar(linea, conexion);
            }
        }
    }  
}
