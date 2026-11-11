namespace SoftProg.Web.Servicios {
    public class ServicioException : Exception {
        public ServicioException(string mensaje) : base(mensaje) { }
        public ServicioException(string mensaje, Exception causa) : base(mensaje, causa) { }
    }
}
