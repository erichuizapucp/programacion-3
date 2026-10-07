using SoftProg.AccessoDatos.DAO;
using SoftProg.AccessoDatos.DAO.Impl;
using SoftProg.Modelo.Ventas;
using System.Text.RegularExpressions;

namespace SoftProg.Negocio.BL.Impl {
    public class ClienteBLImpl : IClienteBL {
        private const int EdadMinima = 18;

        private readonly IClienteDAO clienteDAO = new ClienteDAOImpl();

        public List<Cliente> FindAll() {
            try {
                return clienteDAO.FindAll();
            } catch (Exception ex) {
                throw new BLException("No se pudo listar los clientes", ex);
            }
        }

        public Cliente? FindById(int id) {
            try {
                return clienteDAO.FindById(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo recuperar el cliente", ex);
            }
        }

        public void Insert(Cliente cliente) {
            ValidarDatos(cliente);
            ValidarLineaCredito(cliente);
            ValidarDniUnico(cliente);
            try {
                clienteDAO.Insert(cliente);
            } catch (Exception ex) {
                throw new BLException("No se pudo registrar el cliente", ex);
            }
        }

        public void Update(Cliente cliente) {
            ValidarExiste(cliente.Id);
            ValidarDatos(cliente);
            ValidarLineaCredito(cliente);
            ValidarDniUnico(cliente);
            try {
                clienteDAO.Update(cliente);
            } catch (Exception ex) {
                throw new BLException("No se pudo actualizar el cliente", ex);
            }
        }

        public void Delete(int id) {
            try {
                clienteDAO.Delete(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo eliminar el cliente", ex);
            }
        }

        private void ValidarDatos(Cliente cliente) {
            if (!Regex.IsMatch(cliente.Dni, @"^\d{8}$")) {
                throw new BLException("El DNI debe tener exactamente 8 dígitos");
            }

            DateTime hoy = DateTime.Today;
            DateTime nacimiento = cliente.FechaNacimiento.Date;
            if (nacimiento > hoy) {
                throw new BLException("La fecha de nacimiento no puede ser futura");
            }
            if (nacimiento.AddYears(EdadMinima) > hoy) {
                throw new BLException($"El cliente debe tener al menos {EdadMinima} años");
            }
        }

        private void ValidarLineaCredito(Cliente cliente) {
            double tope = cliente.Categoria switch {
                CategoriaCliente.ORO => 20_000.0,
                CategoriaCliente.PLATA => 10_000.0,
                CategoriaCliente.BRONCE => 3_000.0,
                _ => throw new BLException($"La categoría {cliente.Categoria} no es válida")
            };

            if (cliente.LineaCredito > tope) {
                throw new BLException($"La línea de crédito de un cliente {cliente.Categoria} no puede exceder S/ {tope:F2}");
            }
        }

        private void ValidarExiste(int id) {
            Cliente? existente;
            try {
                existente = clienteDAO.FindById(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo verificar la existencia del cliente", ex);
            }

            if (existente == null) {
                throw new BLException($"No existe un cliente con id {id}");
            }
        }

        private void ValidarDniUnico(Cliente cliente) {
            Cliente? existente;
            try {
                existente = clienteDAO.FindByDni(cliente.Dni);
            } catch (Exception ex) {
                throw new BLException("No se pudo verificar la unicidad del DNI", ex);
            }

            if (existente != null && existente.Id != cliente.Id) {
                throw new BLException($"Ya existe un cliente con el DNI {cliente.Dni}");
            }
        }
    }
}
