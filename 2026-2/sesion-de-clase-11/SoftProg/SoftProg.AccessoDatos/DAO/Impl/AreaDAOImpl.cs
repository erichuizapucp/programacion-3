using SoftProg.DbManager;
using SoftProg.Modelo.RRHH;
using System.Data;
using System.Data.Common;
using System.Text;

namespace SoftProg.AccessoDatos.DAO.Impl {
    public class AreaDAOImpl : IAreaDAO {
        public List<Area> FindAll() {
            string sql = 
                """
                SELECT id, nombre, activo FROM area
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            using DbDataReader reader = cmd.ExecuteReader();

            List<Area> areas = [];
            while (reader.Read()) {
                areas.Add(Mapear(reader, new Area()));
            }

            return areas;
        }

        public Area? FindById(int id) {
            string sql =
                """
                SELECT id, nombre, activo 
                FROM area
                WHERE 
                    id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id", id);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Area()) : null;
        }

        public void Insert(Area area) {
            string sql =
                """
                INSERT INTO area(nombre, activo)
                VALUES (@nombre, @activo);
                SELECT LAST_INSERT_ID();
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroCadena("nombre", area.Nombre);
            cmd.AgregarParametroBoolean("activo", area.IsActivo);
            
            object? resultado = cmd.ExecuteScalar();
            if (resultado == null || resultado == DBNull.Value) {
                throw new Exception("No se pudo insertar el area");
            }

            area.Id = Convert.ToInt32(resultado);
        }

        public void Update(Area area) {
            string sql =
                """
                UPDATE area
                SET 
                    nombre = @nombre, 
                    activo = @activo
                WHERE
                    id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroCadena("nombre", area.Nombre);
            cmd.AgregarParametroBoolean("activo", area.IsActivo);
            cmd.AgregarParametroEntero("id", area.Id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No pudo acualizar el area");
            }
        }

        public void Delete(int id) {
            string sql =
                """
                DELETE FROM area WHERE id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id", id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No pudo eliminar el area");
            }
        }

        private Area Mapear(DbDataReader reader, Area area) {
            area.Id = reader.GetInt32("id");
            area.Nombre = reader.GetString("nombre");
            area.IsActivo = reader.GetBoolean("activo");
            return area;
        }
    }
}
