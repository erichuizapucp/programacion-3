using System.Collections.Generic;
using PUCP.SoftProg.Modelo;
using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Persistencia.DAO.RRHH;
using PUCP.SoftProg.Persistencia.DAOImpl.RRHH;

namespace PUCP.SoftProg.Negocio.BOImpl {
    public class CuentaUsuarioBOImpl : ICuentaUsuarioBO {
        private readonly ICuentaUsuarioDAO cuentaUsuarioDAO;

        public CuentaUsuarioBOImpl() {
            this.cuentaUsuarioDAO = new CuentaUsuarioDAOImpl();
        }

        public void Eliminar(int id) {
            this.cuentaUsuarioDAO.Eliminar(id);
        }

        public void Guardar(CuentaUsuario cuentaUsuario, Estado estado) {
            if (estado == Estado.Nuevo) {
                this.cuentaUsuarioDAO.Crear(cuentaUsuario);
            }
            else if (estado == Estado.Modificado) {
                this.cuentaUsuarioDAO.Actualizar(cuentaUsuario);
            }
        }

        public List<CuentaUsuario> Listar() {
            return this.cuentaUsuarioDAO.LeerTodos();
        }

        public CuentaUsuario Obtener(int id) {
            return this.cuentaUsuarioDAO.Leer(id);
        }

        public bool Login(string userName, string password) {
            return this.cuentaUsuarioDAO.Login(userName, password);
        }
    }
}
