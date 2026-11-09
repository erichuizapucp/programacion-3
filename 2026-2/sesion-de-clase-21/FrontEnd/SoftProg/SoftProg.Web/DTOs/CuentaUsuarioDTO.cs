using static System.Runtime.InteropServices.JavaScript.JSType;

namespace SoftProg.Web.DTOs {
    public class CuentaUsuarioDTO {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public string UserName { get; set; }
        public string Password { get; set; }
    }
}
