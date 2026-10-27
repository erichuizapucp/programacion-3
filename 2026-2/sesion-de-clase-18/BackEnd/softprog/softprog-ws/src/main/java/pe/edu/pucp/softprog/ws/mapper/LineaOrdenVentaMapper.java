package pe.edu.pucp.softprog.ws.mapper;

import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.ProductoBL;
import pe.edu.pucp.softprog.bl.impl.ProductoBLImpl;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.ws.dto.LineaOrdenVentaDTO;

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
            throw new BLException("La línea de la orden debe tener un producto");
        }
        Producto producto = productoBL.findById(dto.getProducto().getId());
        if (producto == null) {
            throw new BLException("No existe el producto con id " + dto.getProducto().getId());
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
