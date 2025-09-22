using System;
using System.Collections.Generic;
using PUCP.SoftProg.Modelo;
using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Persistencia.DAO.RRHH;
using PUCP.SoftProg.Persistencia.DAOImpl.RRHH;

namespace PUCP.SoftProg.Negocio.BOImpl {
    public class AreaBOImpl : IAreaBO {
        private readonly IAreaDAO areaDAO;

        public AreaBOImpl() { 
            this.areaDAO = new AreaDAOImpl();
        }

        public void Eliminar(int id) {
            if (!this.areaDAO.Eliminar(id)) {
                throw new Exception("No se pudo eliminar el area");
            }
        }

        public void Guardar(Area area, Estado estado) {
            if (estado == Estado.Nuevo) {
                this.areaDAO.Crear(area);
            }
            else if (estado == Estado.Modificado) { 
                this.areaDAO.Crear(area);
            }
        }

        public List<Area> Listar() {
            return this.areaDAO.LeerTodos();
        }

        public Area Obtener(int id) {
            return this.areaDAO.Leer(id);
        }
    }
}
