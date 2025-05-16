using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Negocio.BO;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.RRHH;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Negocio.BOImpl {
    public class EmpleadoBOImpl : IEmpleadoBO {
        private readonly IEmpleadoDAO empleadoDAO;

        public EmpleadoBOImpl() { 
            this.empleadoDAO = new EmpleadoDAOImpl();
        }

        public void Eliminar(int id) {
            this.empleadoDAO.Eliminar(id);
        }

        public void Guardar(Empleado empleado, Estado estado) {
            if (estado == Estado.Nuevo) {
                this.empleadoDAO.Insertar(empleado);
            }
            else if (estado == Estado.Modificar) {
                this.empleadoDAO.Modificar(empleado);
            }
        }

        public List<Empleado> Listar() {
            return this.empleadoDAO.Listar();
        }

        public Empleado Obtener(int id) {
            return this.empleadoDAO.Buscar(id);
        }
    }
}
