using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.Clientes;
using PUCP.Edu.Pe.SoftProg.Negocio.BO;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.Clientes;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.Clientes;

namespace PUCP.Edu.Pe.SoftProg.Negocio.BOImpl {
    public class ClienteBOImpl : IClienteBO {
        private readonly IClienteDAO clienteDAO;

        public ClienteBOImpl() {
            this.clienteDAO = new ClienteDAOImpl();
        }

        public void Guardar(Cliente cliente, Estado estado) {
            if (estado == Estado.Nuevo) {
                this.clienteDAO.Insertar(cliente);
            }
            else {
                this.clienteDAO.Modificar(cliente);
            }
        }

        public void Eliminar(int id) {
            this.clienteDAO.Eliminar(id);
        }

        public Cliente Obtener(int id) {
            return this.clienteDAO.Buscar(id);
        }

        public List<Cliente> Listar() {
            return this.clienteDAO.Listar();
        }

        public Cliente BuscarPorDni(string dni)
        {
            return this.clienteDAO.BuscarPorDni(dni);
        }
    }
}
