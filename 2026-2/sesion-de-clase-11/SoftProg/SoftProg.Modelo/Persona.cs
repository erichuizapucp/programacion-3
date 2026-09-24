using SoftProg.Modelo.Seguridad;

namespace SoftProg.Modelo {
    public class Persona : Registro {
        public Persona() { }
        public Persona(Persona persona) : base(persona) { 

        }

        public CuentaUsuario? CuentaUsuario {
            get {
                return field != null ? new CuentaUsuario(field) : null;
            }
            set {
                field = value != null ? new CuentaUsuario(value) : null;
            }
        }

        public string Dni { 
            get;
            set {
                ArgumentNullException.ThrowIfNullOrEmpty(value);
                field = value;
            }
        }

        public string AppellidoPaterno {
            get;
            set {
                ArgumentNullException.ThrowIfNullOrEmpty(value);
                field = value;

            }
        }
        
        public Genero Genero { get; set; }
        public DateTime FechaNacimiento { get; set; }
    }
}
