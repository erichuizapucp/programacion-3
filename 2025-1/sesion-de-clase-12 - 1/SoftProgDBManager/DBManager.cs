using System;
using System.Configuration;
using System.Data.Common;

namespace PUCP.Edu.Pe.SoftProg.Config {
    public class DBManager {
        private static DBManager instance;

        private string providerName;
        private string host;
        private int puerto;
        private string baseDatos;
        private string usuario;
        private string password;

        private DBManager() { 
        }

        public static DBManager GetInstance() {
            if (instance == null) {
                instance = new DBManager();
            }
            return instance;
        }

        public DbConnection GetConnection() {
            try {
                string cadenaConexion = this.CadenaConexion();
                DbProviderFactory factory = 
                    DbProviderFactories.GetFactory(this.providerName);

                var conexion = factory.CreateConnection();
                conexion.ConnectionString = cadenaConexion;
                conexion.Open();
                return conexion;
            }
            catch (Exception e) {
                Console.Error.WriteLine(e);
                throw;
            }
        }

        private string CadenaConexion() {
            this.providerName = ConfigurationManager.AppSettings["db.provider"];
            this.host = ConfigurationManager.AppSettings["db.host"];
            this.puerto = int.Parse(ConfigurationManager.AppSettings["db.puerto"]);
            this.baseDatos = ConfigurationManager.AppSettings["db.basedatos"];
            this.usuario = ConfigurationManager.AppSettings["db.usuario"];
            this.password = ConfigurationManager.AppSettings["db.password"];

            string cadenaConexion;
            switch (this.providerName) {
                case "System.Data.SqlClient":
                    cadenaConexion= $"Server={this.host},{this.puerto};Database={this.baseDatos};User Id={this.usuario};Password={this.password};TrustServerCertificate=True;";
                    break;
                case "MySql.Data.MySqlClient":
                    cadenaConexion = $"Server={this.host};Port={this.puerto};Database={this.baseDatos};Uid={this.usuario};Pwd={this.password};SslMode=none;";
                    break;
                default:
                    throw new NotSupportedException($"Proveedor no soportado: {this.providerName}");
            }

            return cadenaConexion;
        }
    }
}
