namespace PUCP.SoftProg.Modelo.RRHH {
    public class Empleado : Persona {
        public Area Area { get; set; }
        public CuentaUsuario CuentaUsuario { get; set; }
        public Cargo Cargo { get; set; }
        public double Sueldo { get; set; }
    }
}
