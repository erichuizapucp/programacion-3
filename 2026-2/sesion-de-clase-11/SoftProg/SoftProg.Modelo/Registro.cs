namespace SoftProg.Modelo {
    public abstract class Registro {
        public Registro() { }

        public Registro(Registro registro) { 
            Id = registro.Id;
            IsActivo = registro.IsActivo;
        }

        public int Id { get; set; }
        public bool IsActivo { get; set; }

        public override string ToString() {
            return $"Id: {Id}, Activo: {IsActivo}, ";
            ;
        }

    }
}
