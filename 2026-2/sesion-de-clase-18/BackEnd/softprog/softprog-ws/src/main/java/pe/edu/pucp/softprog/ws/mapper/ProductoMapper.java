package pe.edu.pucp.softprog.ws.mapper;

import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.almacen.UnidadMedida;
import pe.edu.pucp.softprog.ws.dto.ProductoDTO;

import java.util.ArrayList;
import java.util.List;

public final class ProductoMapper {
    private ProductoMapper() {
    }

    public static ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setActivo(producto.isActivo());
        dto.setNombre(producto.getNombre());
        dto.setUnidadMedida(producto.getUnidadMedida().name());
        dto.setPrecio(producto.getPrecio());
        return dto;
    }

    public static List<ProductoDTO> toDTOs(List<Producto> productos) {
        List<ProductoDTO> dtos = new ArrayList<>();
        for (Producto producto : productos) {
            dtos.add(toDTO(producto));
        }
        return dtos;
    }

    public static Producto toModelo(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setActivo(dto.isActivo());
        producto.setNombre(dto.getNombre());
        producto.setUnidadMedida(dto.getUnidadMedida() != null ? UnidadMedida.valueOf(dto.getUnidadMedida()) : null);
        producto.setPrecio(dto.getPrecio());
        return producto;
    }
}
