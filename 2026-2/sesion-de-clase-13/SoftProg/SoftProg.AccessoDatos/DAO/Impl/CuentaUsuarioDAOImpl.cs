using SoftProg.DbManager;
using SoftProg.Modelo.Seguridad;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl {
    public class CuentaUsuarioDAOImpl : RegistroDAOImpl<CuentaUsuario>, ICuentaUsuarioDAO {
        public List<CuentaUsuario> FindAll() {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "listar_cuenta_usuarios";
            using DbDataReader reader = cmd.ExecuteReader();

            List<CuentaUsuario> cuentas = [];
            while (reader.Read()) {
                cuentas.Add(Mapear(reader, new CuentaUsuario()));
            }

            return cuentas;
        }

        public CuentaUsuario? FindById(int id) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "buscar_cuenta_usuario_por_id";
            cmd.AgregarParametroEntero("p_id", id);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new CuentaUsuario()) : null;
        }

        public CuentaUsuario? FindByUserName(string userName) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "buscar_cuenta_usuario_por_user_name";
            cmd.AgregarParametroCadena("p_user_name", userName);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new CuentaUsuario()) : null;
        }

        public void Insert(CuentaUsuario cuentaUsuario) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "insertar_cuenta_usuario";
            cmd.AgregarParametroCadena("p_user_name", cuentaUsuario.UserName);
            cmd.AgregarParametroCadena("p_password", cuentaUsuario.Password);
            cmd.AgregarParametroBoolean("p_activo", cuentaUsuario.IsActivo);
            cmd.AgregarParametroSalidaEntero("p_id");

            cmd.ExecuteNonQuery();

            object? resultado = cmd.Parameters["p_id"].Value;
            if (resultado == null || resultado == DBNull.Value) {
                throw new Exception("No se pudo insertar la cuenta de usuario");
            }

            cuentaUsuario.Id = Convert.ToInt32(resultado);
        }

        public void Update(CuentaUsuario cuentaUsuario) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "modificar_cuenta_usuario";
            cmd.AgregarParametroCadena("p_user_name", cuentaUsuario.UserName);
            cmd.AgregarParametroCadena("p_password", cuentaUsuario.Password);
            cmd.AgregarParametroBoolean("p_activo", cuentaUsuario.IsActivo);
            cmd.AgregarParametroEntero("p_id", cuentaUsuario.Id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo actualizar la cuenta de usuario");
            }
        }

        public void Delete(int id) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "eliminar_cuenta_usuario";
            cmd.AgregarParametroEntero("p_id", id);

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
