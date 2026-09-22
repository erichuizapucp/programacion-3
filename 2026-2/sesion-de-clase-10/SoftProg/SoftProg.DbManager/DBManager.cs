using System.Data.Common;
using Microsoft.Extensions.Configuration;
using MySql.Data.MySqlClient;

namespace SoftProg.DbManager {
    public class DBManager {
        protected string CadenaConexion { get; }

        private static DBManager? Instance { get; set; }
        private static readonly Lock Candado = new();

        protected DBManager(string cadenaConexion) { 
            ArgumentNullException.ThrowIfNullOrEmpty(cadenaConexion, nameof(cadenaConexion));
            CadenaConexion = cadenaConexion;
        }

        public static DBManager GetInstance() {
            lock (Candado) {
                return Instance ??= CrearInstancia();
            }
        }

        public DbConnection GetConnection() {
            DbConnection conn = new MySqlConnection(CadenaConexion);
            conn.Open();
            return conn;
        }

        private static DBManager CrearInstancia() {
            var builder = new ConfigurationBuilder()
                .SetBasePath(AppContext.BaseDirectory)
                .AddJsonFile("appsettings.json").Build();

            string cadenaConexion = builder.GetConnectionString("softprog") ?? 
                throw new InvalidOperationException("No se pudo encontrar la cadena de conexion");

            return new DBManager(cadenaConexion);
        }
    }
}
