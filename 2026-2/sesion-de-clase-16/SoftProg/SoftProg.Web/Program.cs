using SoftProg.Negocio.BL;
using SoftProg.Negocio.BL.Impl;
using SoftProg.Web.Components;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

builder.Services.AddScoped<IProductoBL, ProductoBLImpl>();
builder.Services.AddScoped<IAreaBL, AreaBLImpl>();
builder.Services.AddScoped<IClienteBL, ClienteBLImpl>();
builder.Services.AddScoped<IEmpleadoBL, EmpleadoBLImpl>();
builder.Services.AddScoped<ICuentaUsuarioBL,  CuentaUsuarioBLImpl>();
builder.Services.AddScoped<IOrdenVentaBL, OrdenVentaBLImpl>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment()) {
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}
app.UseStatusCodePagesWithReExecute("/not-found", createScopeForStatusCodePages: true);
app.UseHttpsRedirection();

app.UseAntiforgery();

app.MapStaticAssets();
app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

app.Run();
