namespace SoftProg.Modelo.Ventas {
    public class Cliente : Persona {
        public Cliente() { }

        public Cliente(Cliente cliente) : base(cliente) {
            Categoria = cliente.Categoria;
            LineaCredito = cliente.LineaCredito;
        }

        public CategoriaCliente Categoria { get; set; }
        public double LineaCredito {
            get;
            set {
                ArgumentOutOfRangeException.ThrowIfLessThan(value, 0);
                field = value;
            }
        }

        public override string ToString() {
            return base.ToString() + $"Categoria: {Categoria}, LineaCredito: {LineaCredito:F2}";
        }
    }
}
