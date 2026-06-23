package pe.edu.pucp.softprog.modelo.clientes;

import pe.edu.pucp.softprog.modelo.Persona;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;

public class Cliente extends Persona {
    private double lineaCredito;
    private CategoriaCliente categoria;

    public double getLineaCredito() {
        return lineaCredito;
    }

    public void setLineaCredito(double lineaCredito) {
        this.lineaCredito = lineaCredito;
    }

    public CategoriaCliente getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaCliente categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + getId() +
                ", activo=" + isActivo() +
                ", dni='" + getDni() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", apellidoPaterno='" + getApellidoPaterno() + '\'' +
                ", genero=" + getGenero() +
                ", fechaNacimiento=" + getFechaNacimiento() +
                ", lineaCredito=" + lineaCredito +
                ", categoria=" + categoria +
                ", cuentaUsuario=" + getCuentaUsuario() +
                '}';
    }
}
