using SoftProg.AccessoDatos.DAO;
using SoftProg.AccessoDatos.DAO.Impl;
using SoftProg.Modelo.RRHH;

namespace SoftProg.Negocio.BL.Impl {
    public class AreaBLImpl : IAreaBL {
        private readonly IAreaDAO areaDAO = new AreaDAOImpl();

        public List<Area> FindAll() {
            try {
                return areaDAO.FindAll();
            } catch (Exception ex) {
                throw new BLException("No se pudo listar las áreas", ex);
            }
        }

        public Area? FindById(int id) {
            try {
                return areaDAO.FindById(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo recuperar el área", ex);
            }
        }

        public List<Area> FilterByName(string nombre) {
            try {
                return areaDAO.FilterByName(nombre);
            } catch (Exception ex) {
                throw new BLException("No se pudo filtrar las áreas por nombre", ex);
            }
        }

        public void Insert(Area area) {
            ValidarNombreUnico(area);
            try {
                areaDAO.Insert(area);
            } catch (Exception ex) {
                throw new BLException("No se pudo registrar el área", ex);
            }
        }

        public void Update(Area area) {
            ValidarExiste(area.Id);
            ValidarNombreUnico(area);
            try {
                areaDAO.Update(area);
            } catch (Exception ex) {
                throw new BLException("No se pudo actualizar el área", ex);
            }
        }

        public void Delete(int id) {
            try {
                areaDAO.Delete(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo eliminar el área", ex);
            }
        }

        private void ValidarExiste(int id) {
            Area? existente;
            try {
                existente = areaDAO.FindById(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo verificar la existencia del área", ex);
            }

            if (existente == null) {
                throw new BLException($"No existe un área con id {id}");
            }
        }

        private void ValidarNombreUnico(Area area) {
            Area? existente;
            try {
                existente = areaDAO.FindByName(area.Nombre);
            } catch (Exception ex) {
                throw new BLException("No se pudo verificar la unicidad del nombre del área", ex);
            }

            if (existente != null && existente.Id != area.Id) {
                throw new BLException($"Ya existe un área con el nombre '{area.Nombre}'");
            }
        }
    }
}
