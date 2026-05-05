using System.Data;
using System.Data.Common;
using SoftProgModelo.Modelos;

namespace SoftProgPersistencia.Dao;

public abstract class PersonaBaseDao<M> : DefaultBaseDao<M>, IPersonaDao<M> where M : Persona
{
    public M? BuscarPorDni(string dni)
    {
        return EjecutarComando(conn =>
        {
            using var cmd = ComandoBuscarPorDni(conn, dni);
            AdjuntarTransaccionActiva(cmd);
            using var reader = cmd.ExecuteReader();
            return reader.Read() ? MapearModelo(reader) : null;
        });
    }

    protected int SetCamposPersona(DbCommand cmd, int startIndex, M modelo)
    {
        CrearParametro(cmd, $"@p{startIndex}", modelo.Dni);
        CrearParametro(cmd, $"@p{startIndex + 1}", modelo.Nombre);
        CrearParametro(cmd, $"@p{startIndex + 2}", modelo.ApellidoPaterno);
        CrearParametro(cmd, $"@p{startIndex + 3}", modelo.Genero.ToString());
        CrearParametro(cmd, $"@p{startIndex + 4}", modelo.FechaNacimiento, DbType.Date);
        return startIndex + 5;
    }

    protected void MapearCamposPersona(DbDataReader reader, M modelo)
    {
        modelo.Dni = LeerTexto(reader, "dni");
        modelo.Nombre = LeerTexto(reader, "nombre");
        modelo.ApellidoPaterno = LeerTexto(reader, "apellidoPaterno");
        modelo.Genero = Enum.Parse<Genero>(LeerTexto(reader, "genero"));
        modelo.FechaNacimiento = LeerFecha(reader, "fechaNacimiento");
    }

    protected int? GetIdCuentaUsuario(M modelo) => modelo.CuentaUsuario?.Id;
    protected abstract DbCommand ComandoBuscarPorDni(DbConnection conn, string dni);
}
