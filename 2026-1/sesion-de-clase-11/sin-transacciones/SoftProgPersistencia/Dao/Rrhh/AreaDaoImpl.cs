using System.Data.Common;
using SoftProgModelo.Modelos.Rrhh;

namespace SoftProgPersistencia.Dao.Rrhh;

public class AreaDaoImpl : DefaultBaseDao<Area>, IAreaDao
{
    protected override DbCommand ComandoCrear(DbConnection conn, Area modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "INSERT INTO AREA (nombre, activo) VALUES (@p1, @p2);";
        CrearParametro(cmd, "@p1", modelo.Nombre);
        CrearParametro(cmd, "@p2", modelo.Activo);
        return cmd;
    }

    protected override DbCommand ComandoActualizar(DbConnection conn, Area modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "UPDATE AREA SET nombre = @p1, activo = @p2 WHERE id = @p3;";
        CrearParametro(cmd, "@p1", modelo.Nombre);
        CrearParametro(cmd, "@p2", modelo.Activo);
        CrearParametro(cmd, "@p3", modelo.Id);
        return cmd;
    }

    protected override DbCommand ComandoEliminar(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "DELETE FROM AREA WHERE id = @p1;";
        CrearParametro(cmd, "@p1", id);
        return cmd;
    }

    protected override DbCommand ComandoLeer(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM AREA WHERE id = @p1;";
        CrearParametro(cmd, "@p1", id);
        return cmd;
    }

    protected override DbCommand ComandoLeerTodos(DbConnection conn)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM AREA;";
        return cmd;
    }

    protected override Area MapearModelo(DbDataReader reader)
    {
        return new Area
        {
            Id = reader.GetInt32(reader.GetOrdinal("id")),
            Nombre = reader.GetString(reader.GetOrdinal("nombre")),
            Activo = reader.GetBoolean(reader.GetOrdinal("activo"))
        };
    }
}
