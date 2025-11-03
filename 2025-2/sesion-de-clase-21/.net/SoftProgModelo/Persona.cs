using System;

namespace PUCP.SoftProg.Modelo {
    public class Persona : Registro {
        public string Dni { get; set; }
        public string Nombre { get; set; }
        public string ApellidoPaterno { get; set; }
        public Genero Genero { get; set; }
        public DateTime FechaNacimiento { get; set; }
    }
}
