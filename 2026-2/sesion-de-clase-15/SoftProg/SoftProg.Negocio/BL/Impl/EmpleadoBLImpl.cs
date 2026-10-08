using SoftProg.AccessoDatos.DAO;
using SoftProg.AccessoDatos.DAO.Impl;
using SoftProg.Modelo.RRHH;
using System.Text.RegularExpressions;

namespace SoftProg.Negocio.BL.Impl {
    public class EmpleadoBLImpl : IEmpleadoBL {
        private const double SueldoMinimo = 1130.0;
        private const int EdadMinima = 18;

        private readonly IEmpleadoDAO empleadoDAO = new EmpleadoDAOImpl();

        public List<Empleado> FindAll() {
            try {
                return empleadoDAO.FindAll();
            } catch (Exception ex) {
                throw new BLException("No se pudo listar los empleados", ex);
            }
        }

        public Empleado? FindById(int id) {
            try {
                return empleadoDAO.FindById(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo recuperar el empleado", ex);
            }
        }

        public List<Empleado> FilterByName(string nombre) {
            try {
                return empleadoDAO.FilterByName(nombre);
            } catch (Exception ex) {
                throw new BLException("No se pudo filtrar los empleados por nombre", ex);
            }
        }

        public void Insert(Empleado empleado) {
            ValidarDatos(empleado);
            ValidarDniUnico(empleado);
            try {
                empleadoDAO.Insert(empleado);
            } catch (Exception ex) {
                throw new BLException("No se pudo registrar el empleado", ex);
            }
        }

        public void Update(Empleado empleado) {
            ValidarExiste(empleado.Id);
            ValidarDatos(empleado);
            ValidarDniUnico(empleado);
            try {
                empleadoDAO.Update(empleado);
            } catch (Exception ex) {
                throw new BLException("No se pudo actualizar el empleado", ex);
            }
        }

        public void Delete(int id) {
            try {
                empleadoDAO.Delete(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo eliminar el empleado", ex);
            }
        }

        private void ValidarDatos(Empleado empleado) {
            if (!Regex.IsMatch(empleado.Dni, @"^\d{8}$")) {
                throw new BLException("El DNI debe tener exactamente 8 dígitos");
            }

            DateTime hoy = DateTime.Today;
            DateTime nacimiento = empleado.FechaNacimiento.Date;
            if (nacimiento > hoy) {
                throw new BLException("La fecha de nacimiento no puede ser futura");
            }
            if (nacimiento.AddYears(EdadMinima) > hoy) {
                throw new BLException($"El empleado debe tener al menos {EdadMinima} años");
            }

            if (empleado.Sueldo < SueldoMinimo) {
                throw new BLException($"El sueldo no puede ser menor a la remuneración mínima (S/ {SueldoMinimo:F2})");
            }
        }

        private void ValidarExiste(int id) {
            Empleado? existente;
            try {
                existente = empleadoDAO.FindById(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo verificar la existencia del empleado", ex);
            }

            if (existente == null) {
                throw new BLException($"No existe un empleado con id {id}");
            }
        }

        private void ValidarDniUnico(Empleado empleado) {
            Empleado? existente;
            try {
                existente = empleadoDAO.FindByDni(empleado.Dni);
            } catch (Exception ex) {
                throw new BLException("No se pudo verificar la unicidad del DNI", ex);
            }

            if (existente != null && existente.Id != empleado.Id) {
                throw new BLException($"Ya existe un empleado con el DNI {empleado.Dni}");
            }
        }
    }
}
