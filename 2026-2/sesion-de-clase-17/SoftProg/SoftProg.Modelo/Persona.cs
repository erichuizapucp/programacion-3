using SoftProg.Modelo.Seguridad;

namespace SoftProg.Modelo {
    public abstract class Persona : Registro {
        public Persona() { }

        public Persona(Persona persona) : base(persona) { 
            CuentaUsuario = persona.CuentaUsuario;
            Dni = persona.Dni;
            Nombre = persona.Nombre;
            ApellidoPaterno = persona.ApellidoPaterno;
            Genero = persona.Genero;
            FechaNacimiento = persona.FechaNacimiento;
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
                ArgumentException.ThrowIfNullOrEmpty(value);
                field = value;
            }
        }

        public string Nombre {
            get;
            set {
                ArgumentException.ThrowIfNullOrEmpty(value);
                field = value;
            }
        }

        public string ApellidoPaterno {
            get;
            set {
                ArgumentException.ThrowIfNullOrEmpty(value);
                field = value;
            }
        }
        
        public Genero Genero { get; set; }
        public DateTime FechaNacimiento { get; set; }

        public override string ToString() {
            return base.ToString() + 
                $"Dni: {Dni}, Nombre: {Nombre} {ApellidoPaterno}, Genero: {Genero}, " +
                $"FechaNacimiento: {FechaNacimiento:dd/MM/yyyy}, ";
        }
    }
}
