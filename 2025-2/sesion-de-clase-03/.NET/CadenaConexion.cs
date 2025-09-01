/*
 * Esta es una implementacion del patron Fluent Builder
 */
namespace PUCP.Inf30.SoftProg.Config {
    public class CadenaConexion {
        public string Servidor { get; set; }
        public string Schema { get; set; }
        public int Puerto { get; set; }

        CadenaConexion(Builder builder) {
            this.Servidor = builder.Servidor;
            this.Schema = builder.Schema;
            this.Puerto = builder.Puerto;
        }

        public class Builder {
            public string Servidor { get; private set; }
            public string Schema { get; private set; }
            public int Puerto { get; private set; }

            public Builder ConServidor(string servidor) {
                this.Servidor = servidor;
                return this;
            }

            public Builder ConSchema(string schema) { 
                this.Schema = schema;
                return this;
            }

            public Builder EnPuerto(int puerto) {
                this.Puerto = puerto;
                return this;
            }

            public CadenaConexion Build() {
                return new CadenaConexion(this);
            }
        }

        public override string ToString() {
            return string.Format("version1-jdbc:mysql://{0}:{1}/{2}?useSSL=false&allowPublicKeyRetrieval=true", Servidor, Schema, Puerto);
        }
    }
}
