using System.Data.Common;
using Microsoft.Data.SqlClient;
using Microsoft.Extensions.Configuration;

namespace SoftProg.DbManager {
    public class DBManagerMSSQL : DBManager {
        internal DBManagerMSSQL() : base(LeerConfiguracion().GetConnectionString("mssql")) {
        }

        protected override DbConnection CrearConexion() {
            return new SqlConnection(CadenaConexion);
        }
    }
}
