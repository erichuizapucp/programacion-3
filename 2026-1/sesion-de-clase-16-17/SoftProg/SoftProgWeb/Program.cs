using Microsoft.AspNetCore.Authentication.Cookies;
using SoftProgDBManager.Db;
using SoftProgNegocio.Bo.Almacen;
using SoftProgNegocio.Bo.Clientes;
using SoftProgNegocio.Bo.Cuentas;
using SoftProgNegocio.Bo.Rrhh;
using SoftProgNegocio.Bo.Ventas;
using SoftProgWeb.Components;
using SoftProgWeb.Extensiones;

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
builder.Services.AddScoped<ICuentaUsuarioBo, CuentaUsuarioBoImpl>();
builder.Services.AddScoped<IAreaBo, AreaBoImpl>();
builder.Services.AddScoped<IEmpleadoBo, EmpleadoBoImpl>();
builder.Services.AddScoped<IOrdenVentaBo, OrdenVentaBoImpl>();
builder.Services.AddScoped<IClienteBo, ClienteBoImpl>();
builder.Services.AddScoped<IProductoBo, ProductoBoImpl>();

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
