using System.Data;
using System.Data.Common;
using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Persistencia.DAO.RRHH;

namespace PUCP.SoftProg.Persistencia.DAOImpl.RRHH {
    public class AreaDAOImpl : BaseDAOImpl<Area>, IAreaDAO {
        protected override DbCommand ComandoCrear(DbConnection conn, Area area) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarArea";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_nombre", DbType.String, area.Nombre);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, area.IsActive);
            this.AgregarParametroSalida(cmd, "@p_id", DbType.Int32);

            return cmd;
        }

        protected override DbCommand ComandoActualizar(DbConnection conn, Area area) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarArea";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_nombre", DbType.String, area.Nombre);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, area.IsActive);
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, area.Id);

            return cmd;
        }

        protected override DbCommand ComandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarArea";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeer(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarAreaPorId";
            cmd.CommandType = CommandType.StoredProcedure;
            
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeerTodos(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarAreas";
            cmd.CommandType = CommandType.StoredProcedure;
            return cmd;
        }

        protected override Area MapearModelo(DbDataReader reader) {
            return new Area {
                Id = (int)reader["id"], 
                Nombre = reader["nombre"].ToString(),
                IsActive = (bool)reader["activo"],
            };
        }
    }
}
