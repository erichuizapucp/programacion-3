using SoftProgModelo.Modelos;

namespace SoftProgNegocio.Bo;

public abstract class PersonaBoImpl<M> : BaseBo, IPersonaBo<M> where M : Persona
{
    protected void ValidarPersonaBasica(M modelo, string nombreEntidad)
    {
        _ = modelo ?? throw new ArgumentNullException(nombreEntidad, $"El {nombreEntidad} es obligatorio");
        ValidarTextoObligatorio(modelo.Dni, "dni");
        ValidarTextoObligatorio(modelo.Nombre, "nombre");
        ValidarTextoObligatorio(modelo.ApellidoPaterno, "apellido paterno");
    }

    public abstract List<M> Listar();
    public abstract M? Obtener(int id);
    public abstract void Eliminar(int id);
    public abstract void Guardar(M modelo, Estado estado);
}
