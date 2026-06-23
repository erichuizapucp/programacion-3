using System.Security.Claims;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using SoftProgWeb.Servicios.Cuentas;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Extensiones;

public static class AuthExtensions {
    public static IEndpointRouteBuilder MapAuthEndpoints(this IEndpointRouteBuilder endpoints) {
        endpoints.MapPost("/auth/login", IniciarSesionAsync);
        endpoints.MapGet("/auth/logout", (Delegate)CerrarSesionAsync);

        return endpoints;
    }

    private static async Task<IResult> IniciarSesionAsync(HttpContext context, ICuentasUsuarioServiceClient cuentaUsuarioServiceClient) {
        var form = await context.Request.ReadFormAsync();

        var usuario = form["usuario"].ToString();
        var contrasena = form["contrasena"].ToString();
        var tipoUsuarioRaw = form["tipoUsuario"].ToString();
        var returnUrl = form["returnUrl"].ToString();

        if (string.IsNullOrWhiteSpace(usuario) || string.IsNullOrWhiteSpace(contrasena)) {
            return Results.LocalRedirect("/Login?error=1");
        }

        var tipoUsuario = Enum.TryParse<TipoUsuarioEnum>(tipoUsuarioRaw, true, out var tipo) && Enum.IsDefined(tipo)
            ? tipo
            : TipoUsuarioEnum.Cliente;

        if (!cuentaUsuarioServiceClient.Login(usuario, contrasena)) {
            return Results.LocalRedirect("/Login?error=1");
        }

        var cuenta = cuentaUsuarioServiceClient.ObtenerPorUsername(usuario);

        var claims = new List<Claim>
        {
            new(ClaimTypes.NameIdentifier, (cuenta?.Id ?? 0).ToString()),
            new(ClaimTypes.Name, usuario),
            new(ClaimTypes.Role, tipoUsuario.ToString())
        };

        var identidad = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
        var principal = new ClaimsPrincipal(identidad);

        await context.SignInAsync(
            CookieAuthenticationDefaults.AuthenticationScheme,
            principal,
            new AuthenticationProperties {
                IsPersistent = true,
                ExpiresUtc = DateTimeOffset.UtcNow.AddHours(8)
            });

        var destino = ObtenerDestinoSeguro(returnUrl);
        return Results.LocalRedirect(destino);
    }

    private static async Task<IResult> CerrarSesionAsync(HttpContext context) {
        await context.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
        return Results.LocalRedirect("/Login");
    }

    private static string ObtenerDestinoSeguro(string? returnUrl) {
        if (string.IsNullOrWhiteSpace(returnUrl)) {
            return "/Home";
        }

        if (!Uri.IsWellFormedUriString(returnUrl, UriKind.Relative)) {
            return "/Home";
        }

        if (!returnUrl.StartsWith('/')) {
            return "/Home";
        }

        return returnUrl;
    }
}