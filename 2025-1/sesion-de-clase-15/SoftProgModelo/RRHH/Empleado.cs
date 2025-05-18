using System;

namespace PUCP.Edu.Pe.SoftProg.Modelo.RRHH {
    public class Empleado : Persona {
        public Area Area { get; set; }
        public CuentaUsuario CuentaUsuario { get; set; }
        public string Cargo { get; set; }
        public double Sueldo { get; set; }
    }
}
