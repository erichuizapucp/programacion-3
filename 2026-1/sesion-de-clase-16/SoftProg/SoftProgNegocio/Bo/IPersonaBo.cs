using SoftProgModelo.Modelos;

namespace SoftProgNegocio.Bo;

public interface IPersonaBo<M> : IGestionable<M> where M : Persona
{
}
