using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using SoftProg.Web.Servicios;
using SoftProg.Web.ViewModels;
using System.Security.Claims;

namespace SoftProg.Web.Servicios.Impl {
    public class SesionServiceImpl : ISesionService {
        private readonly ICuentaUsuarioService cuentaUsuarioService;
        private readonly IClienteService clienteService;
        private readonly IEmpleadoService empleadoService;
        private readonly IHttpContextAccessor httpContextAccessor;

        public SesionServiceImpl(ICuentaUsuarioService cuentaUsuarioService, IClienteService clienteService,
                IEmpleadoService empleadoService, IHttpContextAccessor httpContextAccessor) {
            this.cuentaUsuarioService = cuentaUsuarioService;
            this.clienteService = clienteService;
            this.empleadoService = empleadoService;
            this.httpContextAccessor = httpContextAccessor;
        }

        public async Task<string> IniciarSesion(HttpContext context, string userName, string password) {
            if (!cuentaUsuarioService.Autenticar(userName, password)) {
                return "/login?error=true";
            }

            CuentaUsuarioViewModel cuenta = cuentaUsuarioService.FindByUserName(userName)!;
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

        private string? ObtenerRol(CuentaUsuarioViewModel cuenta) {
            if (cuenta.UserName == "administrador") {
                return "Administrador";
            }
            if (clienteService.FindByCuenta(cuenta.UserName) != null) {
                return "Cliente";
            }
            EmpleadoViewModel? empleado = empleadoService.FindByCuenta(cuenta.UserName);
            return empleado?.Cargo.ToString();
        }
    }
}
