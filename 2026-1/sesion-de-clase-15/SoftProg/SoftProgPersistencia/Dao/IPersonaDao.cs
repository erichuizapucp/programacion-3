using SoftProgModelo.Modelos;

namespace SoftProgPersistencia.Dao;

public interface IPersonaDao<M> : IPersistible<M, int> where M : Persona
{
    M? BuscarPorDni(string dni);
}
