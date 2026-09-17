package pe.edu.pucp.softprog.modelo.ventas;

import pe.edu.pucp.softprog.modelo.Persona;

public class Cliente extends Persona {
    private CategoriaCliente categoria;
    private double lineaCredito;

    public Cliente() {
    }

    public Cliente(final Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        super(cliente);
        setCategoria(cliente.getCategoria());
        setLineaCredito(cliente.getLineaCredito());
    }

    public CategoriaCliente getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaCliente categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría del cliente no puede ser nula");
        }
        this.categoria = categoria;
    }

    public double getLineaCredito() {
        return lineaCredito;
    }

    public void setLineaCredito(double lineaCredito) {
        if (lineaCredito < 0) {
            throw new IllegalArgumentException("La línea de crédito no puede ser negativa");
        }
        this.lineaCredito = lineaCredito;
    }
}
