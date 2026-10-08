using System.Data.Common;
using Microsoft.Extensions.Configuration;
using MySql.Data.MySqlClient;

namespace SoftProg.DbManager {
    public class DBManagerMySql : DBManager {
        internal DBManagerMySql() : base(LeerConfiguracion().GetConnectionString("mysql")) {
        }

        protected override DbConnection CrearConexion() {
            return new MySqlConnection(CadenaConexion);
        }
    }
}
