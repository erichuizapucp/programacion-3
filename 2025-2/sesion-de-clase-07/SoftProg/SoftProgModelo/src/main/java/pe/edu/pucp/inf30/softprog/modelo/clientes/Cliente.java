package pe.edu.pucp.inf30.softprog.modelo.clientes;

import pe.edu.pucp.inf30.softprog.modelo.Persona;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;

/**
 *
 * @author eric
 */
public class Cliente extends Persona {
    private double lineaCredito;
    private CategoriaCliente categoria;
    private CuentaUsuario cuentaUsuario;

    public CuentaUsuario getCuentaUsuario() {
        return cuentaUsuario;
    }

    public void setCuentaUsuario(CuentaUsuario cuentaUsuario) {
        this.cuentaUsuario = cuentaUsuario;
    }

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
                ", cuentaUsuario=" + cuentaUsuario +
                '}';
    }
}
