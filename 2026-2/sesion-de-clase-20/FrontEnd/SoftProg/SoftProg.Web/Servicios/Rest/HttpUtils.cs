namespace SoftProg.Web.Servicios.Rest;
public static class HttpUtils {
    private static HttpClient http = new();

    public static async Task<TResponse> DoGet<TResponse>(string url) {
        var response = await http.GetAsync(url);
        response.EnsureSuccessStatusCode();
        return await response.Content.ReadFromJsonAsync<TResponse>();
    }

    public static async Task<TResponse> 
        DoPost<TRequest, TResponse>(string url, TRequest payload) {

        var response = await http.PostAsJsonAsync(url, payload);
        response.EnsureSuccessStatusCode();
        return await response.Content.ReadFromJsonAsync<TResponse>();
    }

    public static async Task<TResponse>
        DoPut<TRequest, TResponse>(string url, TRequest payload) {

        var response = await http.PutAsJsonAsync(url, payload);
        response.EnsureSuccessStatusCode();
        return await response.Content.ReadFromJsonAsync<TResponse>();
    }

    public static async Task DoDelete(string url) { 
        var response = await http.DeleteAsync(url);
        response.EnsureSuccessStatusCode();
    }
}
