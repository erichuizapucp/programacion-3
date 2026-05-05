using System.Data;
using System.Data.Common;
using SoftProgModelo.Modelos.Clientes;
using SoftProgPersistencia.Dao.Cuentas;

namespace SoftProgPersistencia.Dao.Clientes;

public class ClienteDaoImpl : PersonaBaseDao<Cliente>, IClienteDao
{
    protected override DbCommand ComandoCrear(DbConnection conn, Cliente modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = @"INSERT INTO CLIENTE (idCuentaUsuario, dni, nombre, apellidoPaterno, genero, fechaNacimiento, categoria, lineaCredito, activo)
                            VALUES (@p1, @p2, @p3, @p4, @p5, @p6, @p7, @p8, @p9);";
        SetCamposCliente(cmd, modelo);
        return cmd;
    }

    protected override DbCommand ComandoActualizar(DbConnection conn, Cliente modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = @"UPDATE CLIENTE
                            SET idCuentaUsuario = @p1, dni = @p2, nombre = @p3, apellidoPaterno = @p4,
                                genero = @p5, fechaNacimiento = @p6, categoria = @p7, lineaCredito = @p8, activo = @p9
                            WHERE id = @p10;";
        SetCamposCliente(cmd, modelo);
        CrearParametro(cmd, "@p10", modelo.Id);
        return cmd;
    }

    protected override DbCommand ComandoEliminar(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "DELETE FROM CLIENTE WHERE id = @p1;";
        CrearParametro(cmd, "@p1", id);
        return cmd;
    }

    protected override DbCommand ComandoLeer(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM CLIENTE WHERE id = @p1;";
        CrearParametro(cmd, "@p1", id);
        return cmd;
    }

    protected override DbCommand ComandoLeerTodos(DbConnection conn)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM CLIENTE;";
        return cmd;
    }

    protected override DbCommand ComandoBuscarPorDni(DbConnection conn, string dni)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM CLIENTE WHERE dni = @p1;";
        CrearParametro(cmd, "@p1", dni);
        return cmd;
    }

    protected override Cliente MapearModelo(DbDataReader reader)
    {
        var modelo = new Cliente
        {
            Id = LeerEntero(reader, "id"),
            Categoria = Enum.Parse<CategoriaCliente>(LeerTexto(reader, "categoria")),
            LineaCredito = LeerDecimal(reader, "lineaCredito"),
            Activo = LeerBooleano(reader, "activo")
        };

        var idCuentaUsuario = LeerEnteroNullable(reader, "idCuentaUsuario");
        if (idCuentaUsuario.HasValue)
        {
            modelo.CuentaUsuario = new CuentaUsuarioDaoImpl().Leer(idCuentaUsuario.Value);
        }

        MapearCamposPersona(reader, modelo);
        return modelo;
    }

    private void SetCamposCliente(DbCommand cmd, Cliente modelo)
    {
        CrearParametro(cmd, "@p1", GetIdCuentaUsuario(modelo), DbType.Int32);
        CrearParametro(cmd, "@p2", modelo.Dni);
        CrearParametro(cmd, "@p3", modelo.Nombre);
        CrearParametro(cmd, "@p4", modelo.ApellidoPaterno);
        CrearParametro(cmd, "@p5", modelo.Genero.ToString());
        CrearParametro(cmd, "@p6", modelo.FechaNacimiento, DbType.Date);
        CrearParametro(cmd, "@p7", modelo.Categoria.ToString());
        CrearParametro(cmd, "@p8", modelo.LineaCredito);
        CrearParametro(cmd, "@p9", modelo.Activo);
    }
}
