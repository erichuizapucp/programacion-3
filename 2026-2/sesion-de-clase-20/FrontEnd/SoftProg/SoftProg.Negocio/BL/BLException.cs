namespace SoftProg.Negocio.BL {
    public class BLException : Exception {
        public BLException(string mensaje) : base(mensaje) { }

        public BLException(string mensaje, Exception causa) : base(mensaje, causa) { }
    }
}
