package pe.edu.pucp.inf30.softprog.model.clientes;

import pe.edu.pucp.inf30.softprog.model.Persona;

/**
 *
 * @author eric
 */
public class Cliente extends Persona {
    private Categoria categoria;
    private double lineaCredito;
    
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public double getLineaCredito() {
        return lineaCredito;
    }

    public void setLineaCredito(double lineaCredito) {
        this.lineaCredito = lineaCredito;
    }
}
