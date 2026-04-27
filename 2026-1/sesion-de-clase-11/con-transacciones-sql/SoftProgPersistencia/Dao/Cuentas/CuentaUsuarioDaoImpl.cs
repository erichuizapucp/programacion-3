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
            AdjuntarTransaccionActiva(cmd);
            using var reader = cmd.ExecuteReader();
            return reader.Read();
        });
    }

    protected override DbCommand ComandoCrear(DbConnection conn, CuentaUsuario modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "INSERT INTO CUENTA_USUARIO (userName, password, activo) VALUES (@p1, @p2, @p3);";
        CrearParametro(cmd, "@p1", modelo.UserName);
        CrearParametro(cmd, "@p2", modelo.Password);
        CrearParametro(cmd, "@p3", modelo.Activo);
        return cmd;
    }

    protected override DbCommand ComandoActualizar(DbConnection conn, CuentaUsuario modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "UPDATE CUENTA_USUARIO SET userName = @p1, password = @p2, activo = @p3 WHERE id = @p4;";
        CrearParametro(cmd, "@p1", modelo.UserName);
        CrearParametro(cmd, "@p2", modelo.Password);
        CrearParametro(cmd, "@p3", modelo.Activo);
        CrearParametro(cmd, "@p4", modelo.Id);
        return cmd;
    }

    protected override DbCommand ComandoEliminar(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "DELETE FROM CUENTA_USUARIO WHERE id = @p1;";
        CrearParametro(cmd, "@p1", id);
        return cmd;
    }

    protected override DbCommand ComandoLeer(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM CUENTA_USUARIO WHERE id = @p1;";
        CrearParametro(cmd, "@p1", id);
        return cmd;
    }

    protected override DbCommand ComandoLeerTodos(DbConnection conn)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM CUENTA_USUARIO;";
        return cmd;
    }

    protected virtual DbCommand ComandoLogin(DbConnection conn, string username, string password)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT id FROM CUENTA_USUARIO WHERE userName = @p1 AND password = @p2 AND activo = TRUE;";
        CrearParametro(cmd, "@p1", username);
        CrearParametro(cmd, "@p2", password);
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
