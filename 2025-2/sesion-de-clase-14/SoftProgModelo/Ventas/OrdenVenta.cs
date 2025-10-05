using System;
using System.Collections.Generic;
using PUCP.SoftProg.Modelo.Clientes;
using PUCP.SoftProg.Modelo.RRHH;

namespace PUCP.SoftProg.Modelo.Ventas {
    public class OrdenVenta : Registro {
        public Cliente Cliente { get; set; }
        public Empleado Empleado { get; set; }
        public List<LineaOrdenVenta> LineasOrdenVenta { get; set; }
        public double Total {  get; set; }
        public DateTime FechaHora { get; set; }
    }
}