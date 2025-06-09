using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Almacen {
    public class Producto : ModeloBase {
        public string Nombre { get; set; }
        public string UnidadMedida { get; set; }
        public double Precio { get; set; }
    }
}