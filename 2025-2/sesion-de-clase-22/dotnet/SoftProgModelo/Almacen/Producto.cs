namespace PUCP.SoftProg.Modelo.Almacen {
    public class Producto : Registro {
        public string Nombre { get; set; }
        public UnidadMedida UnidadMedida { get; set; }
        public double Precio { get; set; }
    }
}