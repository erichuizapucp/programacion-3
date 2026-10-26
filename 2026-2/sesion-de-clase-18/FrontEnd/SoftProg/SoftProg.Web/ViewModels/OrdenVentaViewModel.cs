using System.ComponentModel.DataAnnotations;

namespace SoftProg.Web.ViewModels {
    public class OrdenVentaViewModel {
        public const double TasaIgv = 0.18;

        public int Id { get; set; }

        [Range(1, int.MaxValue, ErrorMessage = "Seleccione un cliente")]
        public int IdCliente { get; set; }

        public string NombreCliente { get; set; } = "";

        public int IdEmpleado { get; set; }

        public string? NombreEmpleado { get; set; }

        public List<LineaOrdenVentaViewModel> Lineas { get; set; } = [];

        public bool IsActivo { get; set; } = true;

        public double SubTotal {
            get {
                double subTotal = 0;
                foreach (LineaOrdenVentaViewModel linea in Lineas) {
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
    }
}
