namespace SoftProg.Web.DTOs {
    public class EmpleadoDTO {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public CuentaUsuarioDTO CuentaUsuario { get; set; }
        public string Dni { get; set; }
        public string Nombre { get; set; }
        public string ApellidoPaterno { get; set; }
        public string Genero { get; set; }
        public string FechaNacimiento { get; set; }
        public AreaDTO Area { get; set; }
        public string Cargo { get; set; }
        public double Sueldo { get; set; }
    }
}
