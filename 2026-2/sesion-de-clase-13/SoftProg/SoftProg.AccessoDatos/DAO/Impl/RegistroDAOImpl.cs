using SoftProg.Modelo;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl {
    public abstract class RegistroDAOImpl<T> where T : Registro {
        protected virtual T Mapear(DbDataReader reader, T registro) {
            registro.Id = reader.GetInt32("id");
            registro.IsActivo = reader.GetBoolean("activo");
            return registro;
        }
    }
}
