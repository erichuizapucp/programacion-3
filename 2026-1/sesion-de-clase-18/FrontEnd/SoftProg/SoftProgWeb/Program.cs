using Microsoft.AspNetCore.Authentication.Cookies;
using SoftProgDBManager.Db;
using SoftProgWeb.Components;
using SoftProgWeb.Extensiones;
using SoftProgWeb.Servicios.Almacen;
using SoftProgWeb.Servicios.Clientes;
using SoftProgWeb.Servicios.Cuentas;
using SoftProgWeb.Servicios.Rrhh;
using SoftProgWeb.Servicios.Ventas;

var builder = WebApplication.CreateBuilder(args);

ConfigurationContext.Initialize(builder.Configuration);

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
builder.Services.AddScoped<ICuentasUsuarioService, CuentasUsuarioServiceImpl>();
builder.Services.AddScoped<IAreaService, AreasServiceImpl>();
builder.Services.AddScoped<IEmpleadosService, EmpleadosServiceImpl>();
builder.Services.AddScoped<IOrdenesVentaService, OrdenesVentaServiceImpl>();
builder.Services.AddScoped<IClientesService, ClientesServicecsImpl>();
builder.Services.AddScoped<IProductosService, ProductosServiceImpl>();

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
