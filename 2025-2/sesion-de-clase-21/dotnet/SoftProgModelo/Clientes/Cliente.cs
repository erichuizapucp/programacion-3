using PUCP.SoftProg.Modelo.RRHH;

namespace PUCP.SoftProg.Modelo.Clientes {
    public class Cliente : Persona {
        public double LineaCredito { get; set; }
        public CategoriaCliente Categoria { get; set; }
        public CuentaUsuario CuentaUsuario { get; set; }
    }
}