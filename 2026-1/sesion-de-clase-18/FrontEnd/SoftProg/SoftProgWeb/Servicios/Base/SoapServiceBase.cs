using SoftProgModelo.Modelos;

namespace SoftProgWeb.Servicios.Base;

public abstract class SoapServiceBase {
    protected IConfiguration Configuration { get; }

    protected SoapServiceBase(IConfiguration configuration) {
        Configuration = configuration;
    }

    protected abstract object CreateClient();

    protected static TTarget ParseEnum<TSource, TTarget>(TSource value, TTarget fallback)
        where TSource : struct, Enum
        where TTarget : struct, Enum {
        return Enum.TryParse<TTarget>(value.ToString(), true, out var parsed) ? parsed : fallback;
    }

    protected static TSoapEstado ParseEstado<TSoapEstado>(Estado estadoActual)
        where TSoapEstado : struct, Enum {
        return Enum.TryParse<TSoapEstado>(estadoActual.ToString(), true, out var parsed)
            ? parsed
            : Enum.GetValues<TSoapEstado>()[0];
    }
}
