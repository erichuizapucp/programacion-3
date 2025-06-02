using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Security.Permissions;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.Clientes;
using PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Ventas;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Ventas {
    public class OrdenVenta : ModeloBase {
        public Cliente Cliente { get; set; }
        public Empleado Empleado { get; set; }
        public List<LineaOrdenVenta> LineasOrdenVenta { get; set; }
        public double Total {  get; set; }
        public DateTime FechaHora { get; set; }
    }
}