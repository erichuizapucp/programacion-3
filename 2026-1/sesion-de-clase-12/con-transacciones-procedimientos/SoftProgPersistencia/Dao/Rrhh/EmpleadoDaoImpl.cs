using System.Data;
using System.Data.Common;
using SoftProgModelo.Modelos.Rrhh;
using SoftProgPersistencia.Dao.Cuentas;

namespace SoftProgPersistencia.Dao.Rrhh;

public class EmpleadoDaoImpl : PersonaBaseDao<Empleado>, IEmpleadoDao
{
    protected override DbCommand ComandoCrear(DbConnection conn, Empleado modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "insertarEmpleado";
        cmd.CommandType = CommandType.StoredProcedure;
        SetCamposEmpleado(cmd, modelo);
        CrearParametro(cmd, "p_id", DbType.Int32);
        return cmd;
    }

    protected override DbCommand ComandoActualizar(DbConnection conn, Empleado modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "modificarEmpleado";
        cmd.CommandType = CommandType.StoredProcedure;
        SetCamposEmpleado(cmd, modelo);
        CrearParametro(cmd, "@p_id", modelo.Id);
        return cmd;
    }

    protected override DbCommand ComandoEliminar(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "eliminarEmpleado";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", id);
        return cmd;
    }

    protected override DbCommand ComandoLeer(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "buscarEmpleadoPorId";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", id);
        return cmd;
    }

    protected override DbCommand ComandoLeerTodos(DbConnection conn)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "listarEmpleados";
        cmd.CommandType = CommandType.StoredProcedure;
        return cmd;
    }

    protected override DbCommand ComandoBuscarPorDni(DbConnection conn, string dni)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "buscarEmpleadoPorDni";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_dni", dni);
        return cmd;
    }

    protected override Empleado MapearModelo(DbDataReader reader)
    {
        var modelo = new Empleado
        {
            Id = LeerEntero(reader, "id"),
            Cargo = Enum.Parse<Cargo>(LeerTexto(reader, "cargo")),
            Sueldo = LeerDecimal(reader, "sueldo"),
            Activo = LeerBooleano(reader, "activo"),
            Area = new AreaDaoImpl().Leer(LeerEntero(reader, "idArea"))
        };

        var idCuentaUsuario = LeerEnteroNullable(reader, "idCuentaUsuario");
        if (idCuentaUsuario.HasValue)
        {
            modelo.CuentaUsuario = new CuentaUsuarioDaoImpl().Leer(idCuentaUsuario.Value);
        }

        MapearCamposPersona(reader, modelo);
        return modelo;
    }

    private void SetCamposEmpleado(DbCommand cmd, Empleado modelo)
    {
        if (modelo.Area is null)
        {
            throw new InvalidOperationException("El area del empleado es obligatoria");
        }

        CrearParametro(cmd, "@p_idArea", modelo.Area.Id);
        CrearParametro(cmd, "@p_idCuentaUsuario", GetIdCuentaUsuario(modelo), DbType.Int32);
        CrearParametro(cmd, "@p_dni", modelo.Dni);
        CrearParametro(cmd, "@p_nombre", modelo.Nombre);
        CrearParametro(cmd, "@p_apellidoPaterno", modelo.ApellidoPaterno);
        CrearParametro(cmd, "@p_genero", modelo.Genero.ToString());
        CrearParametro(cmd, "@p_fechaNacimiento", modelo.FechaNacimiento, DbType.Date);
        CrearParametro(cmd, "@p_cargo", modelo.Cargo.ToString());
        CrearParametro(cmd, "@p_sueldo", modelo.Sueldo);
        CrearParametro(cmd, "@p_activo", modelo.Activo);
    }
}
