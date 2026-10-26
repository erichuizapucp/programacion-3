namespace SoftProg.Web.Servicios {
    public interface ISesionService {
        Task<string> IniciarSesion(HttpContext context, string userName, string password);
        Task CerrarSesion(HttpContext context);
        string ObtenerUserName();
        int ObtenerIdCuentaUsuario();
    }
}
