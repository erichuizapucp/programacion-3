using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PUCP.Edu.Pe.SoftProg.Modelo.Clientes {
    public class Cliente : Persona {
        public Categoria Categoria { get; set; }
        public double LineaCredito { get; set; }
    }
}