using System;

namespace PUCP.Edu.Pe.SoftProg.Modelo {
    public class Persona : ModeloBase {
        public string Dni { get; set; }
        public string Nombre { get; set; }
        public string ApellidoPaterno { get; set; }
        public char Genero { get; set; }
        public DateTime FechaNacimiento { get; set; }
    }
}
