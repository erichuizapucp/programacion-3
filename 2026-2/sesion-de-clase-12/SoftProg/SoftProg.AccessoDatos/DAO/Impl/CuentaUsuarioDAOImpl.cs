using SoftProg.DbManager;
using SoftProg.Modelo.Seguridad;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl {
    public class CuentaUsuarioDAOImpl : RegistroDAOImpl<CuentaUsuario>, ICuentaUsuarioDAO {
        public List<CuentaUsuario> FindAll() {
            string sql =
                """
                SELECT id, user_name, password, activo FROM cuenta_usuario
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            using DbDataReader reader = cmd.ExecuteReader();

            List<CuentaUsuario> cuentas = [];
            while (reader.Read()) {
                cuentas.Add(Mapear(reader, new CuentaUsuario()));
            }

            return cuentas;
        }

        public CuentaUsuario? FindById(int id) {
            string sql =
                """
                SELECT id, user_name, password, activo
                FROM cuenta_usuario
                WHERE
                    id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id", id);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new CuentaUsuario()) : null;
        }

        public CuentaUsuario? FindByUserName(string userName) {
            string sql =
                """
                SELECT id, user_name, password, activo
                FROM cuenta_usuario
                WHERE
                    user_name = @user_name
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroCadena("user_name", userName);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new CuentaUsuario()) : null;
        }

        public void Insert(CuentaUsuario cuentaUsuario) {
            string sql =
                """
                INSERT INTO cuenta_usuario(user_name, password, activo)
                VALUES (@user_name, @password, @activo);
                SELECT LAST_INSERT_ID();
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroCadena("user_name", cuentaUsuario.UserName);
            cmd.AgregarParametroCadena("password", cuentaUsuario.Password);
            cmd.AgregarParametroBoolean("activo", cuentaUsuario.IsActivo);

            object? resultado = cmd.ExecuteScalar();
            if (resultado == null || resultado == DBNull.Value) {
                throw new Exception("No se pudo insertar la cuenta de usuario");
            }

            cuentaUsuario.Id = Convert.ToInt32(resultado);
        }

        public void Update(CuentaUsuario cuentaUsuario) {
            string sql =
                """
                UPDATE cuenta_usuario
                SET
                    user_name = @user_name,
                    password = @password,
                    activo = @activo
                WHERE
                    id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroCadena("user_name", cuentaUsuario.UserName);
            cmd.AgregarParametroCadena("password", cuentaUsuario.Password);
            cmd.AgregarParametroBoolean("activo", cuentaUsuario.IsActivo);
            cmd.AgregarParametroEntero("id", cuentaUsuario.Id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo actualizar la cuenta de usuario");
            }
        }

        public void Delete(int id) {
            string sql =
                """
                DELETE FROM cuenta_usuario WHERE id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id", id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo eliminar la cuenta de usuario");
            }
        }

        protected override CuentaUsuario Mapear(DbDataReader reader, CuentaUsuario cuentaUsuario) {
            base.Mapear(reader, cuentaUsuario);
            cuentaUsuario.UserName = reader.GetString("user_name");
            cuentaUsuario.Password = reader.GetString("password");
            return cuentaUsuario;
        }
    }
}
