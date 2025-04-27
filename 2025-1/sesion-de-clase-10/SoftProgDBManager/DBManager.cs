using System;
using System.Data.SqlClient;
using System.Configuration;

namespace PUCP.Edu.Pe.SoftProg.Config {
    public class DBManager {
        private static DBManager instance;

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

        public SqlConnection GetConnection() {
            try {
                string cadenaConexion = this.CadenaConexion();
                var conexion = new SqlConnection(cadenaConexion);
                conexion.Open(); // Necesario en C#
                return conexion;
            }
            catch (SqlException e) {
                Console.Error.WriteLine(e);
                throw;
            }
        }

        private string CadenaConexion() {
            this.host = ConfigurationManager.AppSettings["db.host"];
            this.puerto = int.Parse(ConfigurationManager.AppSettings["db.puerto"]);
            this.baseDatos = ConfigurationManager.AppSettings["db.basedatos"];
            this.usuario = ConfigurationManager.AppSettings["db.usuario"];
            this.password = ConfigurationManager.AppSettings["db.password"];

            SqlConnectionStringBuilder csBuilder = new SqlConnectionStringBuilder() { 
                DataSource = $"{this.host},{this.puerto}",
                InitialCatalog = this.baseDatos,
                UserID = this.usuario,
                Password = this.password,
                TrustServerCertificate = true
            };

            return csBuilder.ConnectionString;
        }
    }
}
