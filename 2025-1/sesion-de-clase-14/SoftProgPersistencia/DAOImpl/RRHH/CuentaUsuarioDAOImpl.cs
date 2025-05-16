using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.RRHH {
    public class CuentaUsuarioDAOImpl : BaseDAOImpl<CuentaUsuario>, ICuentaUsuarioDAO {
        protected override DbCommand CommandoInsertar(DbConnection conn, CuentaUsuario cuentaUsuario) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarCuentaUsuario";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_userName", DbType.String, cuentaUsuario.UserName);
            this.AgregarParametroEntrada(cmd, "@p_password", DbType.String, cuentaUsuario.Password);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, cuentaUsuario.IsActive);
            this.AgregarParametroSalida(cmd, "@p_id", DbType.Int32);

            return cmd;
        }

        protected override DbCommand CommandoModificar(DbConnection conn, CuentaUsuario cuentaUsuario) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarCuentaUsuario";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_userName", DbType.String, cuentaUsuario.UserName);
            this.AgregarParametroEntrada(cmd, "@p_password", DbType.String, cuentaUsuario.Password);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, cuentaUsuario.IsActive);
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, cuentaUsuario.Id);

            return cmd;
        }

        protected override DbCommand CommandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarCuentaUsuario";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoBuscar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarCuentaUsuarioPorId";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoListar(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarCuentaUsuarios";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            return cmd;
        }

        protected override CuentaUsuario mapearModelo(DbDataReader reader) {
            return new CuentaUsuario {
                Id = Convert.ToInt32(reader["id"]),
                UserName = Convert.ToString(reader["userName"]),
                Password = Convert.ToString(reader["password"]),
                IsActive = (bool)reader["activo"],
            };
        }
    }
}
