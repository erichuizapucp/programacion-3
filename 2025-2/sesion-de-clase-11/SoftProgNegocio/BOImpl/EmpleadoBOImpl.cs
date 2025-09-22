using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.SoftProg.Modelo;
using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Persistencia.DAO.RRHH;
using PUCP.SoftProg.Persistencia.DAOImpl.RRHH;

namespace PUCP.SoftProg.Negocio.BOImpl {
    public class EmpleadoBOImpl : IEmpleadoBO {
        private readonly IEmpleadoDAO empleadoDAO;

        public EmpleadoBOImpl() { 
            this.empleadoDAO = new EmpleadoDAOImpl();
        }

        public Empleado BuscarPorDni(string dni) {
            return this.empleadoDAO.BuscarPorDni(dni);
        }

        public void Eliminar(int id) {
            this.empleadoDAO.Eliminar(id);
        }

        public void Guardar(Empleado empleado, Estado estado) {
            if (estado == Estado.Nuevo) {
                this.empleadoDAO.Crear(empleado);
            }
            else if (estado == Estado.Modificado) {
                this.empleadoDAO.Actualizar(empleado);
            }
        }

        public List<Empleado> Listar() {
            return this.empleadoDAO.LeerTodos();
        }

        public Empleado Obtener(int id) {
            return this.empleadoDAO.Leer(id);
        }
    }
}
