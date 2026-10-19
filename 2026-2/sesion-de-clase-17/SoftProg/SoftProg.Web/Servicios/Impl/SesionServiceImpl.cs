using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using SoftProg.Modelo.RRHH;
using SoftProg.Modelo.Seguridad;
using SoftProg.Negocio.BL;
using SoftProg.Web.Servicios;
using System.Security.Claims;

namespace SoftProg.Web.Servicios.Impl {
    public class SesionServiceImpl : ISesionService {
        private readonly ICuentaUsuarioBL cuentaUsuarioBL;
        private readonly IClienteBL clienteBL;
        private readonly IEmpleadoBL empleadoBL;
        private readonly IHttpContextAccessor httpContextAccessor;

        public SesionServiceImpl(ICuentaUsuarioBL cuentaUsuarioBL, IClienteBL clienteBL, IEmpleadoBL empleadoBL,
                IHttpContextAccessor httpContextAccessor) {
            this.cuentaUsuarioBL = cuentaUsuarioBL;
            this.clienteBL = clienteBL;
            this.empleadoBL = empleadoBL;
            this.httpContextAccessor = httpContextAccessor;
        }

        public async Task<string> IniciarSesion(HttpContext context, string userName, string password) {
            if (!cuentaUsuarioBL.Autenticar(userName, password)) {
                return "/login?error=true";
            }

            CuentaUsuario cuenta = cuentaUsuarioBL.FindByUserName(userName)!;
            string? rol = ObtenerRol(cuenta);
            if (rol == null) {
                return "/login?sinRol=true";
            }

            List<Claim> claims = [
                new Claim(ClaimTypes.NameIdentifier, cuenta.Id.ToString()),
                new Claim(ClaimTypes.Name, cuenta.UserName),
                new Claim(ClaimTypes.Role, rol)
            ];
            ClaimsIdentity identidad = new(claims, CookieAuthenticationDefaults.AuthenticationScheme);
            await context.SignInAsync(new ClaimsPrincipal(identidad));
            return "/";
        }

        public async Task CerrarSesion(HttpContext context) {
            await context.SignOutAsync();
        }

        public string ObtenerUserName() {
            ClaimsPrincipal usuario = httpContextAccessor.HttpContext!.User;
            return usuario.Identity!.Name!;
        }

        public int ObtenerIdCuentaUsuario() {
            ClaimsPrincipal usuario = httpContextAccessor.HttpContext!.User;
            return int.Parse(usuario.FindFirst(ClaimTypes.NameIdentifier)!.Value);
        }

        private string? ObtenerRol(CuentaUsuario cuenta) {
            if (cuenta.UserName == "administrador") {
                return "Administrador";
            }
            if (clienteBL.FindByCuenta(cuenta.UserName) != null) {
                return "Cliente";
            }
            Empleado? empleado = empleadoBL.FindByCuenta(cuenta.UserName);
            return empleado?.Cargo.ToString();
        }
    }
}
