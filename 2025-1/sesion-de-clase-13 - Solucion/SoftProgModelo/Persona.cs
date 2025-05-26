using System;

namespace PUCP.Edu.Pe.SoftProg.Modelo {
    public class Persona : ModeloBase {
        private string Dni { get; set; }
        private string Nombre { get; set; }
        private string ApellidoPaterno { get; set; }
        private char Genero { get; set; }
        private DateTime FechaNacimiento { get; set; }
    }
}
