namespace SoftProgWeb.Servicios.Base;

public abstract class RestServiceClient<TViewModel, TRestDto>
    where TViewModel : class {
    protected IConfiguration Configuration { get; }
    protected IHttpClientFactory HttpClientFactory { get; }

    protected abstract string ResourceSetting { get; }
    protected abstract TViewModel ToViewModel(TRestDto source);
    protected abstract TRestDto ToRest(TViewModel source);

    protected RestServiceClient(IConfiguration configuration, IHttpClientFactory httpClientFactory) {
        Configuration = configuration;
        HttpClientFactory = httpClientFactory;
    }

    protected HttpClient CreateClient(string resourceSetting) {
        var endpoint = Configuration[resourceSetting]?.Trim();
        if (string.IsNullOrWhiteSpace(endpoint)) {
            throw new InvalidOperationException($"No se encontró configuración para '{resourceSetting}'.");
        }

        var client = HttpClientFactory.CreateClient();
        client.BaseAddress = BuildBaseUri(endpoint);
        return client;
    }

    protected static void EnsureSuccess(HttpResponseMessage response, string operation) {
        if (response.IsSuccessStatusCode) {
            return;
        }

        var body = response.Content.ReadAsStringAsync().GetAwaiter().GetResult();
        throw new HttpRequestException($"{operation} falló con estado {(int)response.StatusCode}: {body}");
    }

    protected static TTarget ParseEnum<TTarget>(string? value, TTarget fallback)
        where TTarget : struct, Enum {
        return Enum.TryParse<TTarget>(value, true, out var parsed) ? parsed : fallback;
    }

    private static Uri BuildBaseUri(string endpoint) {
        return new Uri(endpoint.EndsWith('/') ? endpoint : $"{endpoint}/", UriKind.Absolute);
    }
}
