namespace SoftProg.Modelo.RRHH {
    public class Empleado : Persona {
        public Area Area {
            get {
                return new(field);
            }
            set {
                field = new(value);
            }
        }
        public Cargo Cargo { get; set; }
        public double Sueldo { 
            get;
            set {
                ArgumentOutOfRangeException.ThrowIfLessThan(value, 0);
                field = value;
            }
        }
    }
}
