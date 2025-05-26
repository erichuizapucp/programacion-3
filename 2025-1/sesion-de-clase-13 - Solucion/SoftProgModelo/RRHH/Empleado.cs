using System;

namespace PUCP.Edu.Pe.SoftProg.Modelo.RRHH {
    public class Empleado : Persona {
        private Area Area { get; set; }
        private CuentaUsuario CuentaUsuario { get; set; }
        private string Cargo { get; set; }
        private double Sueldo { get; set; }
    }
}
