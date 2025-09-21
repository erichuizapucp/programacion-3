using PUCP.SoftProg.Db.Utils;
using System;
using System.Data.Common;
using System.Data.SqlClient;

namespace PUCP.SoftProg.Db {
    public class MSSQLDBManager : DBManager {
        private static MSSQLDBManager instancia;

        protected MSSQLDBManager() { }

        protected MSSQLDBManager(string servidor, int puerto, string esquema,
                                 string usuario, string password)
            : base(servidor, puerto, esquema, usuario, password, TipoDB.MSSQL) {
        }

        public static MSSQLDBManager GetInstance(string host, int puerto,
                                                 string esquema,
                                                 string usuario,
                                                 string password) {
            if (instancia == null) {
                instancia = new MSSQLDBManager(host, puerto, esquema, usuario, password);
            }
            return instancia;
        }

        public override DbConnection GetConnection() {
            try {
                /*
                Por ahora creamos una conexión cada vez que se necesita acceder
                a la base de datos. Para una aplicación académica es aceptable,
                pero en un sistema productivo se debe usar un pool de conexiones.
                */
                string cadenaConexion = CadenaConexion();
                return new SqlConnection(cadenaConexion);
            }
            catch (Exception e) {
                Console.Error.WriteLine(e);
                throw;
            }
        }
    }
}
