using SoftProg.Modelo.RRHH;
using SoftProg.Modelo.Ventas;
using System.ComponentModel.DataAnnotations;

namespace SoftProg.Web.ViewModels {
    public class OrdenVentaViewModel {
        public const double TasaIgv = 0.18;

        public int Id { get; set; }

        [Range(1, int.MaxValue, ErrorMessage = "Seleccione un cliente")]
        public int IdCliente { get; set; }

        public int IdEmpleado { get; set; }

        public List<LineaOrdenVenta> Lineas { get; set; } = [];

        public bool IsActivo { get; set; } = true;

        public double SubTotal {
            get {
                double subTotal = 0;
                foreach (LineaOrdenVenta linea in Lineas) {
                    subTotal += linea.SubTotal;
                }
                return subTotal;
            }
        }

        public double Igv {
            get {
                return Math.Round(SubTotal * TasaIgv, 2);
            }
        }

        public double Total {
            get {
                return SubTotal + Igv;
            }
        }

        public OrdenVentaViewModel() { }

        public OrdenVentaViewModel(OrdenVenta ordenVenta) {
            Id = ordenVenta.Id;
            IdCliente = ordenVenta.Cliente?.Id ?? 0;
            IdEmpleado = ordenVenta.Empleado?.Id ?? 0;
            Lineas = new List<LineaOrdenVenta>(ordenVenta.Lineas);
            IsActivo = ordenVenta.IsActivo;
        }

        public OrdenVenta ToModelo(Cliente cliente, Empleado? empleado) {
            return new OrdenVenta {
                Id = Id,
                Cliente = cliente,
                Empleado = empleado,
                Lineas = Lineas,
                IsActivo = IsActivo
            };
        }
    }
}
