using SoftProg.Modelo.RRHH;

namespace SoftProg.Modelo.Ventas {
    public class OrdenVenta : Registro {
        public OrdenVenta() { }

        public OrdenVenta(OrdenVenta ordenVenta) : base(ordenVenta) {
            Empleado = ordenVenta.Empleado;
            Cliente = ordenVenta.Cliente;
            Total = ordenVenta.Total;
            Lineas = ordenVenta.Lineas;
        }

        public Empleado? Empleado {
            get {
                return field != null ? new Empleado(field) : null;
            }
            set {
                field = value != null ? new Empleado(value) : null;
            }
        }

        public Cliente? Cliente {
            get {
                return field != null ? new Cliente(field) : null;
            }
            set {
                field = value != null ? new Cliente(value) : null;
            }
        }

        public double Total {
            get;
            set {
                ArgumentOutOfRangeException.ThrowIfLessThan(value, 0);
                field = value;
            }
        }

        public IReadOnlyList<LineaOrdenVenta> Lineas {
            get;
            set {
                ArgumentNullException.ThrowIfNull(value);
                field = new List<LineaOrdenVenta>(value).AsReadOnly();
            }
        } = [];

        public override string ToString() {
            string cliente = Cliente != null ? $"{Cliente.Nombre} {Cliente.ApellidoPaterno}" : "(sin cliente)";
            string empleado = Empleado != null ? $"{Empleado.Nombre} {Empleado.ApellidoPaterno}" : "(sin empleado)";
            return base.ToString() + 
                $"Cliente: {cliente}, Empleado: {empleado}, Lineas: {Lineas.Count}, Total: {Total:F2}";
        }
    }
}
