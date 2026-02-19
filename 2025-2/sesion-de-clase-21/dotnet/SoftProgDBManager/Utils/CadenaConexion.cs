using System;

namespace PUCP.SoftProg.Db.Utils {
    public class CadenaConexion {
        public string Servidor { get; private set; }
        public string Schema { get; private set; }
        public int Puerto { get; private set; }
        public string Usuario { get; private set; }
        public string Password { get; private set; }
        public TipoDB TipoDB { get; private set; }

        private CadenaConexion(Builder builder) {
            Servidor = builder.Servidor;
            Schema = builder.Schema;
            Puerto = builder.Puerto;
            Usuario = builder.Usuario;
            Password = builder.Password;
            TipoDB = builder.TipoDB;
        }

        public override string ToString() {
            switch (TipoDB) {
                case TipoDB.MSSQL:
                    return $"Server={this.Servidor},{this.Puerto};Database={this.Schema};" +
                        $"User Id={this.Usuario};Password={this.Password};TrustServerCertificate=True;";

                case TipoDB.MySQL:
                    return $"Server={this.Servidor};Port={this.Puerto};Database={this.Schema};" +
                        $"Uid={this.Usuario};Pwd={this.Password};SslMode=Required;";

                default:
                    throw new NotSupportedException($"Tipo DB no soportado: {TipoDB}");
            }
        }

        public class Builder {
            public string Servidor { get; private set; }
            public string Schema { get; private set; }
            public int Puerto { get; private set; }
            public string Usuario { get; private set; }
            public string Password { get; private set; }
            public TipoDB TipoDB { get; private set; }

            public Builder ConServidor(string servidor) {
                Servidor = servidor;
                return this;
            }

            public Builder ConSchema(string schema) {
                Schema = schema;
                return this;
            }

            public Builder ConPuerto(int puerto) {
                Puerto = puerto;
                return this;
            }

            public Builder ConTipoDB(TipoDB tipoDB) {
                TipoDB = tipoDB;
                return this;
            }

            public Builder ConUsuario(string usuario) { 
                Usuario = usuario;
                return this;
            }

            public Builder ConPassword(string password) { 
                Password = password;
                return this;
            }

            public CadenaConexion Build() {
                return new CadenaConexion(this);
            }
        }
    }
}
