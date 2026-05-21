using SoftProgDBManager.Db;
using SoftProgNegocio.Bo.Clientes;
using SoftProgNegocio.Bo.Cuentas;
using SoftProgNegocio.Bo.Rrhh;
using SoftProgWeb.Components;

var builder = WebApplication.CreateBuilder(args);

// Esto lo agrega el alumno
ConfigurationContext.Initialize(builder.Configuration);

builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

builder.Services.AddHttpContextAccessor();

// Esto lo agrega el alumno
builder.Services.AddScoped<ICuentaUsuarioBo, CuentaUsuarioBoImpl>();
builder.Services.AddScoped<IAreaBo, AreaBoImpl>();
builder.Services.AddScoped<IEmpleadoBo, EmpleadoBoImpl>();
builder.Services.AddScoped<IClienteBo, ClienteBoImpl>();

var app = builder.Build();

if (!app.Environment.IsDevelopment()) {
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    app.UseHsts();
    app.UseHttpsRedirection();
}
app.UseStatusCodePagesWithReExecute("/not-found", createScopeForStatusCodePages: true);

app.UseAntiforgery();

app.MapStaticAssets();
app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

app.Run();
