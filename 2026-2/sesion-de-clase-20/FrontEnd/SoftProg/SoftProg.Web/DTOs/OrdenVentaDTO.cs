namespace SoftProg.Web.DTOs {
    public class OrdenVentaDTO {
        public int Id { get; set; }
        public bool Activo { get; set; }
        public ClienteDTO Cliente { get; set; }
        public EmpleadoDTO Empleado { get; set; }
        public double Total { get; set; }
        public List<LineaOrdenVentaDTO> Lineas { get; set; } = [];
    }
}
