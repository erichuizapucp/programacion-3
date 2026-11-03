namespace SoftProg.Modelo.Almacen {
    public class Producto : Registro {
        public Producto() { }

        public Producto(Producto producto) : base(producto) {
            Nombre = producto.Nombre;
            UnidadMedida = producto.UnidadMedida;
            Precio = producto.Precio;
        }

        public string Nombre {
            get;
            set {
                ArgumentException.ThrowIfNullOrEmpty(value);
                field = value;
            }
        }
        public UnidadMedida UnidadMedida { get; set; }
        public double Precio {
            get;
            set {
                ArgumentOutOfRangeException.ThrowIfLessThan(value, 0);
                field = value;
            }
        }

        public override string ToString() {
            return base.ToString() + $"Nombre: {Nombre}, UnidadMedida: {UnidadMedida}, Precio: {Precio:F2}";
        }
    }
}
