using SoftProgWeb.Servicios.Rest;

namespace SoftProgWeb.Servicios.Base;

public abstract class BaseRestServiceClient<TViewModel, TRestDto> {
    protected IConfiguration Configuration { get; }
    protected IHttpClientFactory HttpClientFactory { get; }
    protected RestClient Api { get; }

    protected abstract TViewModel ToViewModel(TRestDto source);
    protected abstract TRestDto ToRest(TViewModel source);

    protected BaseRestServiceClient(IConfiguration configuration, IHttpClientFactory httpClientFactory) {
        Configuration = configuration;
        HttpClientFactory = httpClientFactory;
        var baseUrl = Configuration["RestApiBaseUrl"]?.Trim();
        if (string.IsNullOrWhiteSpace(baseUrl)) {
            throw new InvalidOperationException("No se encontró configuración para 'RestApiBaseUrl'.");
        }

        Api = RestClient.Create(HttpClientFactory, baseUrl);
    }

    protected static TTarget ParseEnum<TTarget>(string? value, TTarget fallback)
        where TTarget : struct, Enum {
        return Enum.TryParse<TTarget>(value, true, out var parsed) ? parsed : fallback;
    }

    protected static DateTime ParseFecha(string? source) {
        if (string.IsNullOrWhiteSpace(source)) {
            return DateTime.Today;
        }

        var normalized = source.Trim();
        var bracketIndex = normalized.IndexOf('[');
        if (bracketIndex >= 0) {
            normalized = normalized[..bracketIndex];
        }

        if (DateTimeOffset.TryParse(normalized, out var dto)) {
            return dto.LocalDateTime;
        }

        if (DateTime.TryParse(normalized, out var dt)) {
            return dt;
        }

        return DateTime.Today;
    }

    protected static string FormatFecha(DateTime source) {
        return source.ToUniversalTime().ToString("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }
}
