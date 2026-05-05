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
        cmd.CommandText = "insertarCliente";
        cmd.CommandType = CommandType.StoredProcedure;
        SetCamposCliente(cmd, modelo);
        CrearParametro(cmd, "p_id", DbType.Int32);
        return cmd;
    }

    protected override DbCommand ComandoActualizar(DbConnection conn, Cliente modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "modificarCliente";
        cmd.CommandType = CommandType.StoredProcedure;
        SetCamposCliente(cmd, modelo);
        CrearParametro(cmd, "@p_id", modelo.Id);
        return cmd;
    }

    protected override DbCommand ComandoEliminar(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "eliminarCliente";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", id);
        return cmd;
    }

    protected override DbCommand ComandoLeer(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "buscarClientePorId";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", id);
        return cmd;
    }

    protected override DbCommand ComandoLeerTodos(DbConnection conn)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "listarClientes";
        cmd.CommandType = CommandType.StoredProcedure;
        return cmd;
    }

    protected override DbCommand ComandoBuscarPorDni(DbConnection conn, string dni)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "buscarClientePorDni";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_dni", dni);
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
        CrearParametro(cmd, "@p_idCuentaUsuario", GetIdCuentaUsuario(modelo), DbType.Int32);
        CrearParametro(cmd, "@p_dni", modelo.Dni);
        CrearParametro(cmd, "@p_nombre", modelo.Nombre);
        CrearParametro(cmd, "@p_apellidoPaterno", modelo.ApellidoPaterno);
        CrearParametro(cmd, "@p_genero", modelo.Genero.ToString());
        CrearParametro(cmd, "@p_fechaNacimiento", modelo.FechaNacimiento, DbType.Date);
        CrearParametro(cmd, "@p_categoria", modelo.Categoria.ToString());
        CrearParametro(cmd, "@p_lineaCredito", modelo.LineaCredito);
        CrearParametro(cmd, "@p_activo", modelo.Activo);
    }
}
