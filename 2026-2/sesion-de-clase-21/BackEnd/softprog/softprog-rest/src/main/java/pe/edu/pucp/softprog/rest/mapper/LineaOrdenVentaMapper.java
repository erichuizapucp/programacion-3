package pe.edu.pucp.softprog.rest.mapper;

import pe.edu.pucp.softprog.bl.error.BLException;
import pe.edu.pucp.softprog.bl.error.NoEncontradoException;
import pe.edu.pucp.softprog.bl.error.ValidacionException;
import pe.edu.pucp.softprog.bl.ProductoBL;
import pe.edu.pucp.softprog.bl.impl.ProductoBLImpl;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.rest.dto.LineaOrdenVentaDTO;

public final class LineaOrdenVentaMapper {
    private static final ProductoBL productoBL = new ProductoBLImpl();

    private LineaOrdenVentaMapper() {
    }

    public static LineaOrdenVentaDTO toDTO(LineaOrdenVenta linea) {
        LineaOrdenVentaDTO dto = new LineaOrdenVentaDTO();
        dto.setId(linea.getId());
        dto.setProducto(ProductoMapper.toDTO(linea.getProducto()));
        dto.setCantidad(linea.getCantidad());
        dto.setSubTotal(linea.getSubTotal());
        return dto;
    }

    public static LineaOrdenVenta toModelo(LineaOrdenVentaDTO dto) throws BLException {
        if (dto.getProducto() == null) {
            throw new ValidacionException("La línea de la orden debe tener un producto");
        }
        Producto producto;
        try {
            producto = productoBL.findById(dto.getProducto().getId());
        } catch (NoEncontradoException e) {
            throw new ValidacionException("No existe el producto con id " + dto.getProducto().getId());
        }

        LineaOrdenVenta linea = new LineaOrdenVenta();
        linea.setId(dto.getId());
        linea.setActivo(true);
        linea.setProducto(producto);
        linea.setCantidad(dto.getCantidad());
        linea.setSubTotal(dto.getSubTotal());
        return linea;
    }
}
