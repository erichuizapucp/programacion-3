using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Base;

public abstract class SoapServiceClient<TViewModel, TSoapModel> {
    protected IConfiguration Configuration { get; }

    protected SoapServiceClient(IConfiguration configuration) {
        Configuration = configuration;
    }

    protected abstract object CreateClient();
    protected abstract TViewModel ToViewModel(TSoapModel source);
    protected abstract TSoapModel ToSoap(TViewModel source);

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
