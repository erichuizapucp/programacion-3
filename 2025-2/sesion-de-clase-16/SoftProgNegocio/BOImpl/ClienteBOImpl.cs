using PUCP.SoftProg.Db;
using PUCP.SoftProg.Modelo;
using PUCP.SoftProg.Modelo.Clientes;
using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Persistencia.DAO.Clientes;
using PUCP.SoftProg.Persistencia.DAO.RRHH;
using PUCP.SoftProg.Persistencia.DAOImpl.Clientes;
using PUCP.SoftProg.Persistencia.DAOImpl.RRHH;
using System;
using System.Collections.Generic;
using System.Data.Common;

namespace PUCP.SoftProg.Negocio.BOImpl {
    public class ClienteBOImpl : IClienteBO {
        private readonly IClienteDAO clienteDAO;
        private readonly ICuentaUsuarioDAO cuentaUsuarioDAO;

        public ClienteBOImpl() {
            this.clienteDAO = new ClienteDAOImpl();
            this.cuentaUsuarioDAO = new CuentaUsuarioDAOImpl();
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

        public Cliente BuscarPorDni(string dni) {
            return this.clienteDAO.BuscarPorDni(dni);
        }

        public Cliente BuscarPorCuenta(string cuenta) {
            return this.clienteDAO.BuscarPorCuenta(cuenta);
        }

        public void Registrar(Cliente cliente) {
            using (DbConnection conexion = DBFactoryProvider.GetManager().GetConnection()) {
                conexion.Open();
                DbTransaction transaction = conexion.BeginTransaction();
                try {
                    cliente.CuentaUsuario.Id = 
                        cuentaUsuarioDAO.Crear(cliente.CuentaUsuario, transaction);
                    clienteDAO.Crear(cliente, transaction);

                    transaction.Commit();
                }
                catch (Exception ex) {
                    Console.Error.WriteLine("Error al Registrar el Cliente: " + ex.Message);
                    transaction.Rollback();
                    throw;
                }
            }
        }
    }
}
