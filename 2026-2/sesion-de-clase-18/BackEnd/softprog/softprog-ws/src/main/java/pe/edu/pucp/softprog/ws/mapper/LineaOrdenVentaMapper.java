package pe.edu.pucp.softprog.ws.mapper;

import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.ws.dto.LineaOrdenVentaDTO;

public final class LineaOrdenVentaMapper {
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

    public static LineaOrdenVenta toModelo(LineaOrdenVentaDTO dto) {
        LineaOrdenVenta linea = new LineaOrdenVenta();
        linea.setId(dto.getId());
        linea.setActivo(true);
        linea.setProducto(dto.getProducto() != null ? ProductoMapper.toModelo(dto.getProducto()) : null);
        linea.setCantidad(dto.getCantidad());
        linea.setSubTotal(dto.getSubTotal());
        return linea;
    }
}
