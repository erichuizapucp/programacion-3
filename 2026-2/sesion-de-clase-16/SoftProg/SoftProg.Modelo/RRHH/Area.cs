namespace SoftProg.Modelo.RRHH {
    public class Area : Registro {
        public Area() { }

        public Area(Area area) : base(area) {
            Nombre = area.Nombre;
        }

        public string Nombre { 
            get; 
            set {
                ArgumentNullException.ThrowIfNullOrEmpty(value);
                field = value;
            }
        }

        public override string ToString() {
            return base.ToString() + $"Nombre: {Nombre}";
        }
    }
}
