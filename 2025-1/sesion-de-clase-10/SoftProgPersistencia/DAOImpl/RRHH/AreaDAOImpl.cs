using System.Data.SqlClient;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.RRHH {
    public class AreaDAOImpl : BaseDAOImpl<Area>, IAreaDAO {
        protected override SqlCommand CommandoInsertar(
            SqlConnection conn, Area area) {
            SqlCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarArea";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            cmd.Parameters.Add(
                new SqlParameter("p_nombre", System.Data.SqlDbType.VarChar) { 
                Value = area.Nombre 
            });
            cmd.Parameters.Add(
                new SqlParameter("p_activo", System.Data.SqlDbType.Bit) { 
                Value = area.IsActive 
            });
            cmd.Parameters.Add(
                new SqlParameter("p_id", System.Data.SqlDbType.Int) { 
                Direction = System.Data.ParameterDirection.Output 
            });

            return cmd;
        }

        protected override SqlCommand CommandoModificar(
            SqlConnection conn, Area area) {
            SqlCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarArea";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            cmd.Parameters.Add(new SqlParameter("p_nombre", System.Data.SqlDbType.VarChar) {
                Value = area.Nombre
            });
            cmd.Parameters.Add(new SqlParameter("p_activo", System.Data.SqlDbType.Bit) {
                Value = area.IsActive
            });
            cmd.Parameters.Add(
                new SqlParameter("p_id", System.Data.SqlDbType.Int) {
                Value = area.Id
            });

            return cmd;
        }

        protected override SqlCommand CommandoEliminar(
            SqlConnection conn, int id) {
            SqlCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarArea";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            cmd.Parameters.Add(
                new SqlParameter("p_id", System.Data.SqlDbType.Int) {
                Value = id
            });

            return cmd;
        }

        protected override SqlCommand CommandoBuscar(SqlConnection conn, int id) {
            SqlCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarAreaPorId";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            cmd.Parameters.Add(
                new SqlParameter("p_id", System.Data.SqlDbType.Int) {
                Value = id
            });

            return cmd;
        }

        protected override SqlCommand CommandoListar(SqlConnection conn) {
            SqlCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarAreas";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            return cmd;
        }

        protected override Area mapearModelo(SqlDataReader reader) {
            return new Area {
                Id = (int)reader["id"], 
                Nombre = reader["nombre"].ToString(),
                IsActive = (bool)reader["activo"],
            };
        }
    }
}
