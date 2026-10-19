using SoftProg.AccessoDatos.DAO;
using SoftProg.AccessoDatos.DAO.Impl;
using SoftProg.Modelo.Seguridad;
using System.Text.RegularExpressions;

namespace SoftProg.Negocio.BL.Impl {
    public class CuentaUsuarioBLImpl : ICuentaUsuarioBL {
        private static readonly Regex Correo = new(@"^[^@\s]+@[^@\s]+\.[^@\s]+$");
        private const int PasswordMin = 6;

        private readonly ICuentaUsuarioDAO cuentaUsuarioDAO = new CuentaUsuarioDAOImpl();

        public List<CuentaUsuario> FindAll() {
            try {
                return cuentaUsuarioDAO.FindAll();
            } catch (Exception ex) {
                throw new BLException("No se pudo listar las cuentas de usuario", ex);
            }
        }

        public CuentaUsuario? FindById(int id) {
            try {
                return cuentaUsuarioDAO.FindById(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo recuperar la cuenta de usuario", ex);
            }
        }

        public void Insert(CuentaUsuario cuentaUsuario) {
            ValidarFormato(cuentaUsuario);
            ValidarUserNameUnico(cuentaUsuario);
            try {
                cuentaUsuarioDAO.Insert(cuentaUsuario);
            } catch (Exception ex) {
                throw new BLException("No se pudo registrar la cuenta de usuario", ex);
            }
        }

        public void Update(CuentaUsuario cuentaUsuario) {
            ValidarExiste(cuentaUsuario.Id);
            ValidarFormato(cuentaUsuario);
            ValidarUserNameUnico(cuentaUsuario);
            try {
                cuentaUsuarioDAO.Update(cuentaUsuario);
            } catch (Exception ex) {
                throw new BLException("No se pudo actualizar la cuenta de usuario", ex);
            }
        }

        public void Delete(int id) {
            try {
                cuentaUsuarioDAO.Delete(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo eliminar la cuenta de usuario", ex);
            }
        }

        private void ValidarFormato(CuentaUsuario cuentaUsuario) {
            if (!Correo.IsMatch(cuentaUsuario.UserName)) {
                throw new BLException("El usuario debe tener formato de correo electrónico");
            }
            if (cuentaUsuario.Password.Length < PasswordMin) {
                throw new BLException($"La contraseña debe tener al menos {PasswordMin} caracteres");
            }
        }

        private void ValidarExiste(int id) {
            CuentaUsuario? existente;
            try {
                existente = cuentaUsuarioDAO.FindById(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo verificar la existencia de la cuenta", ex);
            }

            if (existente == null) {
                throw new BLException($"No existe una cuenta de usuario con id {id}");
            }
        }

        private void ValidarUserNameUnico(CuentaUsuario cuentaUsuario) {
            CuentaUsuario? existente;
            try {
                existente = cuentaUsuarioDAO.FindByUserName(cuentaUsuario.UserName);
            } catch (Exception ex) {
                throw new BLException("No se pudo verificar la unicidad del usuario", ex);
            }

            if (existente != null && existente.Id != cuentaUsuario.Id) {
                throw new BLException($"Ya existe una cuenta con el usuario '{cuentaUsuario.UserName}'");
            }
        }
    }
}
