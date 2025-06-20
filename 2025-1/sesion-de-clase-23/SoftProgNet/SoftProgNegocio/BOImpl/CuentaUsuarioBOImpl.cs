using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http.Headers;
using System.Security.Policy;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Negocio.BO;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.RRHH;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Negocio.BOImpl {
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
                this.cuentausuarioDAO.Insertar(cuentaUsuario);
            }
            else if (estado == Estado.Modificar) {
                this.cuentausuarioDAO.Modificar(cuentaUsuario);
            }
        }

        public List<CuentaUsuario> Listar() {
            return this.cuentausuarioDAO.Listar();
        }

        public CuentaUsuario Obtener(int id) {
            return this.cuentausuarioDAO.Buscar(id);
        }
    }
}
