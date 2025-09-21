using System;
using System.Data;
using System.Data.Common;
using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Persistencia.DAO.RRHH;

namespace PUCP.SoftProg.Persistencia.DAOImpl.RRHH {
    public class CuentaUsuarioDAOImpl : BaseDAOImpl<CuentaUsuario>, ICuentaUsuarioDAO {
        protected override DbCommand ComandoCrear(DbConnection conn, CuentaUsuario cuentaUsuario) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarCuentaUsuario";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_userName", DbType.String, cuentaUsuario.UserName);
            this.AgregarParametroEntrada(cmd, "@p_password", DbType.String, cuentaUsuario.Password);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, cuentaUsuario.IsActive);
            this.AgregarParametroSalida(cmd, "@p_id", DbType.Int32);

            return cmd;
        }

        protected override DbCommand ComandoActualizar(DbConnection conn, CuentaUsuario cuentaUsuario) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarCuentaUsuario";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_userName", DbType.String, cuentaUsuario.UserName);
            this.AgregarParametroEntrada(cmd, "@p_password", DbType.String, cuentaUsuario.Password);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, cuentaUsuario.IsActive);
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, cuentaUsuario.Id);

            return cmd;
        }

        protected override DbCommand ComandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarCuentaUsuario";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeer(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarCuentaUsuarioPorId";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeerTodos(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarCuentaUsuarios";
            cmd.CommandType = CommandType.StoredProcedure;
            return cmd;
        }

        protected override CuentaUsuario MapearModelo(DbDataReader reader) {
            return new CuentaUsuario {
                Id = Convert.ToInt32(reader["id"]),
                UserName = Convert.ToString(reader["userName"]),
                Password = Convert.ToString(reader["password"]),
                IsActive = (bool)reader["activo"],
            };
        }
    }
}
