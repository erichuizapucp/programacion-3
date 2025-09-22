using System.Collections.Generic;
using PUCP.SoftProg.Modelo;
using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Persistencia.DAO.RRHH;
using PUCP.SoftProg.Persistencia.DAOImpl.RRHH;

namespace PUCP.SoftProg.Negocio.BOImpl {
    public class CuentaUsuarioBOImpl : ICuentaUsuarioBO {
        private ICuentaUsuarioDAO cuentausuarioDAO;

        public CuentaUsuarioBOImpl() {
            this.cuentausuarioDAO = new CuentaUsuarioDAOImpl();
        }

        public void Eliminar(int id) {
            this.cuentausuarioDAO.Eliminar(id);
        }

        public void Guardar(CuentaUsuario cuentaUsuario, Estado estado) {
            if (estado == Estado.Nuevo) {
                this.cuentausuarioDAO.Crear(cuentaUsuario);
            }
            else if (estado == Estado.Modificado) {
                this.cuentausuarioDAO.Actualizar(cuentaUsuario);
            }
        }

        public List<CuentaUsuario> Listar() {
            return this.cuentausuarioDAO.LeerTodos();
        }

        public CuentaUsuario Obtener(int id) {
            return this.cuentausuarioDAO.Leer(id);
        }
    }
}
