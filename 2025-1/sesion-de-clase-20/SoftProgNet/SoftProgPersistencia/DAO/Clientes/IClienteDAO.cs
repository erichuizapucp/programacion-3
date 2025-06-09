using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.Clientes;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAO.Clientes {
    public interface IClienteDAO : ICrud<Cliente> {
        Cliente BuscarPorDni(string dni);
    }
}
