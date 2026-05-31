using System.Data;
using System.Data.Common;
using SoftProgModelo.Modelos.Rrhh;

namespace SoftProgPersistencia.Dao.Rrhh;

public class AreaDaoImpl : DefaultBaseDao<Area>, IAreaDao
{
    protected override DbCommand ComandoCrear(DbConnection conn, Area modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "insertarArea";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_nombre", modelo.Nombre);
        CrearParametro(cmd, "@p_activo", modelo.Activo);
        CrearParametro(cmd, "p_id", DbType.Int32);
        return cmd;
    }

    protected override DbCommand ComandoActualizar(DbConnection conn, Area modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "modificarArea";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_nombre", modelo.Nombre);
        CrearParametro(cmd, "@p_activo", modelo.Activo);
        CrearParametro(cmd, "@p_id", modelo.Id);
        return cmd;
    }

    protected override DbCommand ComandoEliminar(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "eliminarArea";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", id);
        return cmd;
    }

    protected override DbCommand ComandoLeer(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "buscarAreaPorId";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", id);
        return cmd;
    }

    protected override DbCommand ComandoLeerTodos(DbConnection conn)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "listarAreas";
        cmd.CommandType = CommandType.StoredProcedure;
        return cmd;
    }

    protected override Area MapearModelo(DbDataReader reader)
    {
        return new Area
        {
            Id = LeerEntero(reader, "id"),
            Nombre = LeerTexto(reader, "nombre"),
            Activo = LeerBooleano(reader, "activo")
        };
    }
}
