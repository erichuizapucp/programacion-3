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
        if (dto.getProducto() != null) {
            linea.setProducto(ProductoMapper.toReferencia(dto.getProducto()));
        }
        linea.setCantidad(dto.getCantidad());
        linea.setSubTotal(dto.getSubTotal());
        return linea;
    }
}
