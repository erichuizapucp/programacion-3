using SoftProg.DbManager;
using SoftProg.Modelo.RRHH;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl {
    public class AreaDAOImpl : RegistroDAOImpl<Area>, IAreaDAO {
        public List<Area> FindAll() {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "listar_areas";
            using DbDataReader reader = cmd.ExecuteReader();

            List<Area> areas = [];
            while (reader.Read()) {
                areas.Add(Mapear(reader, new Area()));
            }

            return areas;
        }

        public Area? FindById(int id) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "buscar_area_por_id";
            cmd.AgregarParametroEntero("p_id", id);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Area()) : null;
        }

        public Area? FindByName(string nombre) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "buscar_area_por_nombre";
            cmd.AgregarParametroCadena("p_nombre", nombre);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Area()) : null;
        }

        public void Insert(Area area) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "insertar_area";
            cmd.AgregarParametroCadena("p_nombre", area.Nombre);
            cmd.AgregarParametroBoolean("p_activo", area.IsActivo);
            cmd.AgregarParametroSalidaEntero("p_id");

            cmd.ExecuteNonQuery();

            object? resultado = cmd.Parameters["p_id"].Value;
            if (resultado == null || resultado == DBNull.Value) {
                throw new Exception("No se pudo insertar el area");
            }

            area.Id = Convert.ToInt32(resultado);
        }

        public void Update(Area area) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "modificar_area";
            cmd.AgregarParametroCadena("p_nombre", area.Nombre);
            cmd.AgregarParametroBoolean("p_activo", area.IsActivo);
            cmd.AgregarParametroEntero("p_id", area.Id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No pudo acualizar el area");
            }
        }

        public void Delete(int id) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "eliminar_area";
            cmd.AgregarParametroEntero("p_id", id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No pudo eliminar el area");
            }
        }

        protected override Area Mapear(DbDataReader reader, Area area) {
            base.Mapear(reader, area);
            area.Nombre = reader.GetString("nombre");
            return area;
        }
    }
}
