using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Negocio.BO;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.RRHH;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Negocio {
    public class AreaBOImpl : IAreaBO {
        private readonly IAreaDAO areaDAO;

        public AreaBOImpl() {
            this.areaDAO = new AreaDAOImpl();
        }

        public List<Area> Listar() { 
            return this.areaDAO.Listar();
        }

        public Area Obtener(int id) { 
            return this.areaDAO.Buscar(id);
        }

        public void Eliminar(int id) {
            if (!this.areaDAO.Eliminar(id)) {
                throw new Exception("No se pudo eliminar el area");
            }
        }

        public void Guardar(Area area, Estado estado) {
            //Dependiendo de la acción registramos o modificamos
            if (estado == Estado.Nuevo) {
                areaDAO.Insertar(area);
            }
            else if (estado == Estado.Modificar) {
                areaDAO.Modificar(area);
            }
        }
    }
}
