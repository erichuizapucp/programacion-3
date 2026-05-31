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
builder.Services.AddScoped<ICuentasUsuarioServiceClient, CuentasUsuarioSoapClient>();
builder.Services.AddScoped<IAreaServiceClient, AreasServiceSoapClient>();
builder.Services.AddScoped<IEmpleadosServiceClient, EmpleadosServiceSoapClient>();
builder.Services.AddScoped<IOrdenesVentaServiceClient, OrdenesVentaServiceSoapClient>();
builder.Services.AddScoped<IClientesServiceClient, ClientesServiceSoapClient>();
builder.Services.AddScoped<IProductosServiceClient, ProductosServiceSoapClient>();

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
