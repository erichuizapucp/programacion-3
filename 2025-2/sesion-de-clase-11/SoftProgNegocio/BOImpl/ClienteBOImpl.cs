using System.Collections.Generic;
using PUCP.SoftProg.Modelo;
using PUCP.SoftProg.Modelo.Clientes;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Persistencia.DAO.Clientes;
using PUCP.SoftProg.Persistencia.DAOImpl.Clientes;

namespace PUCP.SoftProg.Negocio.BOImpl {
    public class ClienteBOImpl : IClienteBO {
        private readonly IClienteDAO clienteDAO;

        public ClienteBOImpl() {
            this.clienteDAO = new ClienteDAOImpl();
        }

        public void Guardar(Cliente cliente, Estado estado) {
            if (estado == Estado.Nuevo) {
                this.clienteDAO.Crear(cliente);
            }
            else {
                this.clienteDAO.Actualizar(cliente);
            }
        }

        public void Eliminar(int id) {
            this.clienteDAO.Eliminar(id);
        }

        public Cliente Obtener(int id) {
            return this.clienteDAO.Leer(id);
        }

        public List<Cliente> Listar() {
            return this.clienteDAO.LeerTodos();
        }

        public Cliente BuscarPorDni(string dni)
        {
            return this.clienteDAO.BuscarPorDni(dni);
        }
    }
}
