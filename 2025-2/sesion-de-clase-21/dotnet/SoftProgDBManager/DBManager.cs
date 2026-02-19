using PUCP.SoftProg.Db.Utils;
using System;
using System.Data.Common;

namespace PUCP.SoftProg.Db {
    public abstract class DBManager {
        protected string Servidor;
        protected int Puerto;
        protected string Esquema;
        protected string Usuario;
        protected string Password;
        protected TipoDB TipoDB;

        protected DBManager() { }

        protected DBManager(string servidor, int puerto, string esquema, string usuario,
                            string password, TipoDB tipoDB) {
            try {
                Servidor = servidor;
                Puerto = puerto;
                Esquema = esquema;
                Usuario = usuario;
                Password = Crypto.Decrypt(password);
                TipoDB = tipoDB;
            }
            catch (Exception ex) {
                Console.Error.WriteLine("Hubo un error al configurar la conexión a la base de datos: " 
                    + ex.Message);
                throw;
            }
        }

        public abstract DbConnection GetConnection();

        protected string CadenaConexion() {
            var conexion = new CadenaConexion.Builder()
                .ConServidor(Servidor)
                .ConPuerto(Puerto)
                .ConSchema(Esquema)
                .ConUsuario(Usuario)
                .ConPassword(Password)
                .ConTipoDB(TipoDB)
                .Build();

            return conexion.ToString();
        }
    }
}
