using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Almacen;
using SoftProgModelo.Modelos.Clientes;
using SoftProgModelo.Modelos.Rrhh;
using SoftProgModelo.Modelos.Ventas;
using SoftProgNegocio.Bo.Almacen;
using SoftProgNegocio.Bo.Clientes;
using SoftProgNegocio.Bo.Cuentas;
using SoftProgNegocio.Bo.Rrhh;
using SoftProgNegocio.Bo.Ventas;

namespace SoftProgTests;

public class NegocioValidationTests
{
    [Fact]
    public void AreaGuardarShouldThrowWhenNombreIsEmpty()
    {
        var bo = new AreaBoImpl();
        var area = new Area { Nombre = "" };

        Assert.Throws<ArgumentException>(() => bo.Guardar(area, Estado.Nuevo));
    }

    [Fact]
    public void ProductoGuardarShouldThrowWhenPrecioIsNegative()
    {
        var bo = new ProductoBoImpl();
        var producto = new Producto { Nombre = "A", Precio = -1, Activo = true, UnidadMedida = UnidadMedida.UND };

        Assert.Throws<ArgumentException>(() => bo.Guardar(producto, Estado.Nuevo));
    }

    [Fact]
    public void ClienteGuardarShouldThrowWhenLineaCreditoIsNegative()
    {
        var bo = new ClienteBoImpl();
        var cliente = new Cliente
        {
            Dni = "12345678",
            Nombre = "Ana",
            ApellidoPaterno = "Perez",
            Genero = Genero.FEMENINO,
            FechaNacimiento = DateTime.Today.AddYears(-20),
            LineaCredito = -10,
            Categoria = CategoriaCliente.ESTANDARD,
            Activo = true
        };

        Assert.Throws<ArgumentException>(() => bo.Guardar(cliente, Estado.Nuevo));
    }

    [Fact]
    public void OrdenVentaGuardarShouldThrowWhenNoLineas()
    {
        var bo = new OrdenVentaBoImpl();
        var orden = new OrdenVenta
        {
            Cliente = new Cliente { Id = 1 },
            Total = 0,
            Activo = true,
            Lineas = new List<LineaOrdenVenta>()
        };

        Assert.Throws<ArgumentException>(() => bo.Guardar(orden, Estado.Nuevo));
    }

    [Fact]
    public void CuentaUsuarioLoginShouldThrowWhenUsernameIsEmpty()
    {
        var bo = new CuentaUsuarioBoImpl();
        Assert.Throws<ArgumentException>(() => bo.Login("", "123"));
    }
}
