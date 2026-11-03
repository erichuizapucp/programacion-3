using System.Data.Common;
using Microsoft.Extensions.Configuration;

namespace SoftProg.DbManager {
    public abstract class DBManager {
        protected string CadenaConexion { get; }

        private static DBManager? Instance { get; set; }
        private static readonly Lock Candado = new();

        protected DBManager(string? cadenaConexion) {
            ArgumentNullException.ThrowIfNullOrEmpty(cadenaConexion, nameof(cadenaConexion));
            CadenaConexion = cadenaConexion;
        }

        public static DBManager GetInstance() {
            lock (Candado) {
                return Instance ??= CrearInstancia();
            }
        }

        public DbConnection GetConnection() {
            DbConnection conn = CrearConexion();
            conn.Open();
            return conn;
        }

        protected abstract DbConnection CrearConexion();

        protected static IConfiguration LeerConfiguracion() {
            return new ConfigurationBuilder()
                .SetBasePath(AppContext.BaseDirectory)
                .AddJsonFile("appsettings.json").Build();
        }

        private static DBManager CrearInstancia() {
            string? motor = LeerConfiguracion()["Motor"];

            return motor switch {
                "MySQL" => new DBManagerMySql(),
                "MSSQL" => new DBManagerMSSQL(),
                _ => throw new InvalidOperationException($"Motor de base de datos no soportado: {motor}")
            };
        }
    }
}
