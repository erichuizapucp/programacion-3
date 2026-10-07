using SoftProg.Modelo;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl {
    public abstract class PersonaDAOImpl<T> : RegistroDAOImpl<T> where T : Persona {
        protected override T Mapear(DbDataReader reader, T persona) {
            base.Mapear(reader, persona);

            persona.Dni = reader.GetString("dni");
            persona.Nombre = reader.GetString("nombre");
            persona.ApellidoPaterno = reader.GetString("apellido_paterno");
            persona.Genero = Enum.Parse<Genero>(reader.GetString("genero"));
            persona.FechaNacimiento = reader.GetDateTime("fecha_nacimiento");

            if (!reader.IsDBNull("id_cuenta_usuario")) {
                persona.CuentaUsuario = new CuentaUsuarioDAOImpl().FindById(reader.GetInt32("id_cuenta_usuario"));
            }
            else {
                persona.CuentaUsuario = null;
            }

            return persona;
        }
    }
}
