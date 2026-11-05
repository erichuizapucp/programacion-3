using SoftProg.Web.Components;
using SoftProg.Web.Seguridad;
using SoftProg.Web.Servicios;
using Ws = SoftProg.Web.Servicios.Ws;
using Rest = SoftProg.Web.Servicios.Rest;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

builder.Services.AddSeguridad();

builder.Services.AddScoped<IProductoService, Rest.ProductoService>();
builder.Services.AddScoped<IAreaService, Ws.AreaService>();
builder.Services.AddScoped<IClienteService, Ws.ClienteService>();
builder.Services.AddScoped<IEmpleadoService, Ws.EmpleadoService>();
builder.Services.AddScoped<ICuentaUsuarioService, Ws.CuentaUsuarioService>();
builder.Services.AddScoped<IOrdenVentaService, Ws.OrdenVentaService>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment()) {
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}
app.UseStatusCodePagesWithReExecute("/not-found", createScopeForStatusCodePages: true);
app.UseHttpsRedirection();

app.UseAuthentication();
app.UseAuthorization();
app.UseAntiforgery();

app.MapStaticAssets();
app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

app.MapSesionEndpoints();

app.Run();
