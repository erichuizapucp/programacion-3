using System.Data;
using System.Data.Common;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.RRHH {
    public class AreaDAOImpl : BaseDAOImpl<Area>, IAreaDAO {
        protected override DbCommand CommandoInsertar(DbConnection conn, Area area) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarArea";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, 
                "@p_nombre", DbType.String, area.Nombre);
            this.AgregarParametroEntrada(cmd, 
                "@p_activo", DbType.Boolean, area.IsActive);
            this.AgregarParametroSalida(cmd, 
                "@p_id", DbType.Int32);

            return cmd;
        }

        protected override DbCommand CommandoModificar(DbConnection conn, 
            Area area) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarArea";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_nombre", 
                DbType.String, area.Nombre);
            this.AgregarParametroEntrada(cmd, "@p_activo", 
                DbType.Boolean, area.IsActive);
            this.AgregarParametroEntrada(cmd, "@p_id", 
                DbType.Int32, area.Id);

            return cmd;
        }

        protected override DbCommand CommandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarArea";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoBuscar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarAreaPorId";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoListar(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarAreas";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            return cmd;
        }

        protected override Area mapearModelo(DbDataReader reader) {
            return new Area {
                Id = (int)reader["id"], 
                Nombre = reader["nombre"].ToString(),
                IsActive = (bool)reader["activo"],
            };
        }
    }
}
