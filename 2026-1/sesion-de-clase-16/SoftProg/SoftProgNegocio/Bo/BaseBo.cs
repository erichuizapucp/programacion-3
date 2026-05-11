using SoftProgModelo.Modelos;

namespace SoftProgNegocio.Bo;

public abstract class BaseBo
{
    protected void ValidarIdPositivo(int id, string nombreCampo)
    {
        if (id <= 0)
        {
            throw new ArgumentException($"El {nombreCampo} debe ser mayor a 0");
        }
    }

    protected void ValidarEstado(Estado estado)
    {
        _ = estado;
    }

    protected void ValidarTextoObligatorio(string? valor, string nombreCampo)
    {
        if (string.IsNullOrWhiteSpace(valor))
        {
            throw new ArgumentException($"El {nombreCampo} es obligatorio");
        }
    }
}
