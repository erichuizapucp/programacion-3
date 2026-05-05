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
        cmd.CommandText = @"INSERT INTO EMPLEADO (idArea, idCuentaUsuario, dni, nombre, apellidoPaterno, genero, fechaNacimiento, cargo, sueldo, activo)
                            VALUES (@p1, @p2, @p3, @p4, @p5, @p6, @p7, @p8, @p9, @p10);";
        SetCamposEmpleado(cmd, modelo);
        return cmd;
    }

    protected override DbCommand ComandoActualizar(DbConnection conn, Empleado modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = @"UPDATE EMPLEADO
                            SET idArea = @p1, idCuentaUsuario = @p2, dni = @p3, nombre = @p4, apellidoPaterno = @p5,
                                genero = @p6, fechaNacimiento = @p7, cargo = @p8, sueldo = @p9, activo = @p10
                            WHERE id = @p11;";
        SetCamposEmpleado(cmd, modelo);
        CrearParametro(cmd, "@p11", modelo.Id);
        return cmd;
    }

    protected override DbCommand ComandoEliminar(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "DELETE FROM EMPLEADO WHERE id = @p1;";
        CrearParametro(cmd, "@p1", id);
        return cmd;
    }

    protected override DbCommand ComandoLeer(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM EMPLEADO WHERE id = @p1;";
        CrearParametro(cmd, "@p1", id);
        return cmd;
    }

    protected override DbCommand ComandoLeerTodos(DbConnection conn)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM EMPLEADO;";
        return cmd;
    }

    protected override DbCommand ComandoBuscarPorDni(DbConnection conn, string dni)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM EMPLEADO WHERE dni = @p1;";
        CrearParametro(cmd, "@p1", dni);
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

        CrearParametro(cmd, "@p1", modelo.Area.Id);
        CrearParametro(cmd, "@p2", GetIdCuentaUsuario(modelo), DbType.Int32);
        CrearParametro(cmd, "@p3", modelo.Dni);
        CrearParametro(cmd, "@p4", modelo.Nombre);
        CrearParametro(cmd, "@p5", modelo.ApellidoPaterno);
        CrearParametro(cmd, "@p6", modelo.Genero.ToString());
        CrearParametro(cmd, "@p7", modelo.FechaNacimiento, DbType.Date);
        CrearParametro(cmd, "@p8", modelo.Cargo.ToString());
        CrearParametro(cmd, "@p9", modelo.Sueldo);
        CrearParametro(cmd, "@p10", modelo.Activo);
    }
}
