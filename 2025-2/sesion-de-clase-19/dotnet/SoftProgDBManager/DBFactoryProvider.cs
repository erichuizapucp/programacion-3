using System;
using System.Configuration;

namespace PUCP.SoftProg.Db {
    public class DBFactoryProvider {
        private static DBManager instancia;
        private static readonly object lockObj = new object();

        public static DBManager GetManager() {
            lock (lockObj) {
                if (instancia == null) {
                    string host = ConfigurationManager.AppSettings["db.host"];
                    int puerto = int.Parse(ConfigurationManager.AppSettings["db.puerto"]);
                    string esquema = ConfigurationManager.AppSettings["db.basedatos"];
                    string usuario = ConfigurationManager.AppSettings["db.usuario"];
                    string password = ConfigurationManager.AppSettings["db.password"];
                    string provider = ConfigurationManager.AppSettings["db.provider"];

                    DBManagerFactory factory;
                    switch (provider) {
                        case "MySql.Data.MySqlClient":
                            factory = new MySQLDBManagerFactory();
                            break;
                        case "System.Data.SqlClient":
                            factory = new MSSQLDBManagerFactory();
                            break;
                        default:
                            throw new ArgumentException($"Proveedor no soportado: {provider}");
                    }

                    instancia = factory.CrearDBManager(host, puerto, esquema, usuario, password);
                }
            }

            return instancia;
        }
    }
}
