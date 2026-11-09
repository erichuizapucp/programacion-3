using SoftProgWS;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace SoftProg.Web.DTOs {
    public class ClienteDTO {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public CuentaUsuarioDTO CuentaUsuario { get; set; }
        public string Dni { get; set; }
        public string Nombre { get; set; }
        public string ApellidoPaterno { get; set; }
        public string Genero { get; set; }
        public string FechaNacimiento { get; set; }
        public string Categoria { get; set; }
        public double LineaCredito { get; set; }
    }
}
