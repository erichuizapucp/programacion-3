namespace SoftProg.Modelo.RRHH {
    public class Empleado : Persona {
        public Empleado() { }

        public Empleado(Empleado empleado) : base(empleado) {
            Area = empleado.Area;
            Cargo = empleado.Cargo;
            Sueldo = empleado.Sueldo;
        }

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

        public override string ToString() {
            return base.ToString() + $"Area: {Area.Nombre}, Cargo: {Cargo}, Sueldo: {Sueldo:F2}";
        }
    }
}
