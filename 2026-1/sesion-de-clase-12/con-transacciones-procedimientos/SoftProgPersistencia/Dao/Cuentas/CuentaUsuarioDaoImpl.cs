using System.Data;
using System.Data.Common;
using SoftProgModelo.Modelos.Rrhh;

namespace SoftProgPersistencia.Dao.Cuentas;

public class CuentaUsuarioDaoImpl : DefaultBaseDao<CuentaUsuario>, ICuentaUsuarioDao
{
    public bool Login(string username, string password)
    {
        return EjecutarComando(conn =>
        {
            using var cmd = ComandoLogin(conn, username, password);
            cmd.CommandType = CommandType.StoredProcedure;
            AdjuntarTransaccionActiva(cmd);
            _ = cmd.ExecuteNonQuery();
            var value = cmd.Parameters["p_valido"]?.Value;
            return value is not null and not DBNull && Convert.ToBoolean(value);
        });
    }

    protected override DbCommand ComandoCrear(DbConnection conn, CuentaUsuario modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "insertarCuentaUsuario";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_userName", modelo.UserName);
        CrearParametro(cmd, "@p_password", modelo.Password);
        CrearParametro(cmd, "@p_activo", modelo.Activo);
        CrearParametro(cmd, "p_id", DbType.Int32);
        return cmd;
    }

    protected override DbCommand ComandoActualizar(DbConnection conn, CuentaUsuario modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "modificarCuentaUsuario";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_userName", modelo.UserName);
        CrearParametro(cmd, "@p_password", modelo.Password);
        CrearParametro(cmd, "@p_activo", modelo.Activo);
        CrearParametro(cmd, "@p_id", modelo.Id);
        return cmd;
    }

    protected override DbCommand ComandoEliminar(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "eliminarCuentaUsuario";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", id);
        return cmd;
    }

    protected override DbCommand ComandoLeer(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "buscarCuentaUsuarioPorId";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", id);
        return cmd;
    }

    protected override DbCommand ComandoLeerTodos(DbConnection conn)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "listarCuentaUsuarios";
        cmd.CommandType = CommandType.StoredProcedure;
        return cmd;
    }

    private DbCommand ComandoLogin(DbConnection conn, string username, string password)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "loginUsuario";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_username", username);
        CrearParametro(cmd, "@p_password", password);
        CrearParametro(cmd, "p_valido", DbType.Boolean);
        return cmd;
    }

    protected override CuentaUsuario MapearModelo(DbDataReader reader)
    {
        return new CuentaUsuario
        {
            Id = LeerEntero(reader, "id"),
            UserName = LeerTexto(reader, "userName"),
            Password = LeerTexto(reader, "password"),
            Activo = LeerBooleano(reader, "activo")
        };
    }
}
