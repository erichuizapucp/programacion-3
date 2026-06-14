using System.Net.Http.Json;

namespace SoftProgWeb.Servicios.Rest;

// Cliente REST orientado a encapsular:
// - Creación del HttpClient desde configuración + IHttpClientFactory.
// - Ejecución síncrona de operaciones HTTP.
// - Validación de respuestas exitosas (2XX).
public sealed class RestClient {
    private readonly IHttpClientFactory _httpClientFactory;
    private readonly Uri _baseUri;

    private RestClient(IHttpClientFactory httpClientFactory, string baseUrl) {
        _httpClientFactory = httpClientFactory;
        _baseUri = BuildBaseUri(baseUrl);
    }

    public static RestClient Create(IHttpClientFactory httpClientFactory, string baseUrl) {
        return new RestClient(httpClientFactory, baseUrl);
    }

    public TResponse Get<TResponse>() {
        return Get<TResponse>(string.Empty);
    }

    public TResponse Get<TResponse>(string path) {
        return Get<TResponse>(path, queryParams: null);
    }

    public TResponse Get<TResponse>(IEnumerable<KeyValuePair<string, string?>> queryParams) {
        return Get<TResponse>(string.Empty, queryParams);
    }

    public TResponse Get<TResponse>(string path, IEnumerable<KeyValuePair<string, string?>>? queryParams) {
        var requestPath = BuildRequestPath(path, queryParams);
        using var client = CreateHttpClient();
        using var response = client.GetAsync(requestPath).GetAwaiter().GetResult();
        EnsureSuccess(response);
        return response.Content.ReadFromJsonAsync<TResponse>().GetAwaiter().GetResult()
            ?? throw new InvalidOperationException("La respuesta REST devolvió un cuerpo vacío.");
    }

    public void Post<TRequest>(TRequest payload) {
        Post(string.Empty, payload);
    }

    public void Post<TRequest>(string path, TRequest payload) {
        var requestPath = BuildRequestPath(path, queryParams: null);
        using var client = CreateHttpClient();
        using var response = client.PostAsJsonAsync(requestPath, payload).GetAwaiter().GetResult();
        EnsureSuccess(response);
    }

    public void Put<TRequest>(string path, TRequest payload) {
        var requestPath = BuildRequestPath(path, queryParams: null);
        using var client = CreateHttpClient();
        using var response = client.PutAsJsonAsync(requestPath, payload).GetAwaiter().GetResult();
        EnsureSuccess(response);
    }

    public void Delete() {
        Delete(string.Empty);
    }

    public void Delete(string path) {
        var requestPath = BuildRequestPath(path, queryParams: null);
        using var client = CreateHttpClient();
        using var response = client.DeleteAsync(requestPath).GetAwaiter().GetResult();
        EnsureSuccess(response);
    }

    private HttpClient CreateHttpClient() {
        var client = _httpClientFactory.CreateClient();
        client.BaseAddress = _baseUri;
        return client;
    }

    private static void EnsureSuccess(HttpResponseMessage response) {
        if (response.IsSuccessStatusCode) {
            return;
        }

        var body = response.Content.ReadAsStringAsync().GetAwaiter().GetResult();
        throw new HttpRequestException(
            $"La solicitud REST falló con estado {(int)response.StatusCode}: {body}",
            inner: null,
            statusCode: response.StatusCode);
    }

    private static string BuildRequestPath(string? path, IEnumerable<KeyValuePair<string, string?>>? queryParams) {
        var normalizedPath = string.IsNullOrWhiteSpace(path)
            ? string.Empty
            : path.Trim().TrimStart('/');
        if (queryParams is null) {
            return normalizedPath;
        }

        var query = BuildQueryString(queryParams);
        if (string.IsNullOrWhiteSpace(query)) {
            return normalizedPath;
        }

        return string.IsNullOrWhiteSpace(normalizedPath)
            ? $"?{query}"
            : $"{normalizedPath}?{query}";
    }

    private static string BuildQueryString(IEnumerable<KeyValuePair<string, string?>> queryParams) {
        var segments = new List<string>();
        foreach (var parameter in queryParams) {
            if (string.IsNullOrWhiteSpace(parameter.Key) || parameter.Value is null) {
                continue;
            }

            var key = Uri.EscapeDataString(parameter.Key);
            var value = Uri.EscapeDataString(parameter.Value);
            segments.Add($"{key}={value}");
        }

        return string.Join("&", segments);
    }

    private static Uri BuildBaseUri(string endpoint) {
        return new Uri(endpoint.EndsWith('/') ? endpoint : $"{endpoint}/", UriKind.Absolute);
    }
}
