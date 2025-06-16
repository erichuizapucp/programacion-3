package pe.edu.pucp.inf30.softprog.bo.transaccion.comando;

import pe.edu.pucp.inf30.softprog.dao.logistica.ventas.ILineaOrdenVentaDAO;
import pe.edu.pucp.inf30.softprog.dao.logistica.ventas.IOrdenVentaDAO;
import pe.edu.pucp.inf30.softprog.model.Estado;
import pe.edu.pucp.inf30.softprog.model.logistica.ventas.OrdenVenta;

/**
 *
 * @author eric
 */
public abstract class ComandoBase {
    private final IOrdenVentaDAO ordenVentaDAO;
    private final ILineaOrdenVentaDAO lineaOrdenVentaDAO;
    private final Estado estado;
    private final OrdenVenta modelo;

    public ComandoBase(
        IOrdenVentaDAO ordenVentaDAO, 
        ILineaOrdenVentaDAO lineaOrdenVentaDAO, 
        Estado estado, 
        OrdenVenta modelo
    ) {
        this.ordenVentaDAO = ordenVentaDAO;
        this.lineaOrdenVentaDAO = lineaOrdenVentaDAO;
        this.estado = estado;
        this.modelo = modelo;
    }
    
    public IOrdenVentaDAO getOrdenVentaDAO() {
        return ordenVentaDAO;
    }

    public ILineaOrdenVentaDAO getLineaOrdenVentaDAO() {
        return lineaOrdenVentaDAO;
    }
    
    public Estado getEstado() {
        return estado;
    }

    public OrdenVenta getModelo() {
        return modelo;
    }
}
