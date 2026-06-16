using Microsoft.AspNetCore.Authentication.Cookies;
using SoftProgWeb.Components;
using SoftProgWeb.Extensiones;
using SoftProgWeb.Servicios.Almacen;
using SoftProgWeb.Servicios.Clientes;
using SoftProgWeb.Servicios.Cuentas;
using SoftProgWeb.Servicios.Rrhh;
using SoftProgWeb.Servicios.Ventas;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

builder.Services
    .AddAuthentication(CookieAuthenticationDefaults.AuthenticationScheme)
    .AddCookie(opciones => {
        opciones.LoginPath = "/Login";
        opciones.AccessDeniedPath = "/Login";
        opciones.SlidingExpiration = true;
        opciones.ExpireTimeSpan = TimeSpan.FromHours(8);
    });

builder.Services.AddAuthorization();
builder.Services.AddCascadingAuthenticationState();

builder.Services.AddHttpContextAccessor();
builder.Services.AddHttpClient();
builder.Services.AddScoped<ICuentasUsuarioServiceClient, CuentasUsuarioRestClient>();
builder.Services.AddScoped<IAreaServiceClient, AreasServiceRestClient>();
builder.Services.AddScoped<IEmpleadosServiceClient, EmpleadosServiceRestClient>();
builder.Services.AddScoped<IOrdenesVentaServiceClient, OrdenesVentaServiceRestClient>();
builder.Services.AddScoped<IClientesServiceClient, ClientesServiceRestClient>();
builder.Services.AddScoped<IProductosServiceClient, ProductosServiceRestClient>();

var app = builder.Build();

if (!app.Environment.IsDevelopment()) {
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    app.UseHsts();
    app.UseHttpsRedirection();
}
app.UseStatusCodePagesWithReExecute("/not-found", createScopeForStatusCodePages: true);
app.UseAuthentication();
app.UseAuthorization();
app.MapAuthEndpoints();

app.UseAntiforgery();

app.MapStaticAssets();
app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

app.Run();
