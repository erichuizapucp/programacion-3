package pe.edu.pucp.softprog.ws.mapper;

import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;
import pe.edu.pucp.softprog.ws.dto.LineaOrdenVentaDTO;
import pe.edu.pucp.softprog.ws.dto.OrdenVentaDTO;

import java.util.ArrayList;
import java.util.List;

public final class OrdenVentaMapper {
    private OrdenVentaMapper() {
    }

    public static OrdenVentaDTO toDTO(OrdenVenta ordenVenta) {
        OrdenVentaDTO dto = new OrdenVentaDTO();
        dto.setId(ordenVenta.getId());
        dto.setActivo(ordenVenta.isActivo());
        dto.setCliente(ordenVenta.getCliente() != null ? ClienteMapper.toDTO(ordenVenta.getCliente()) : null);
        dto.setEmpleado(ordenVenta.getEmpleado() != null ? EmpleadoMapper.toDTO(ordenVenta.getEmpleado()) : null);
        dto.setTotal(ordenVenta.getTotal());

        List<LineaOrdenVentaDTO> lineas = new ArrayList<>();
        for (LineaOrdenVenta linea : ordenVenta.getLineas()) {
            lineas.add(LineaOrdenVentaMapper.toDTO(linea));
        }
        dto.setLineas(lineas);
        return dto;
    }

    public static List<OrdenVentaDTO> toDTOs(List<OrdenVenta> ordenes) {
        List<OrdenVentaDTO> dtos = new ArrayList<>();
        for (OrdenVenta ordenVenta : ordenes) {
            dtos.add(toDTO(ordenVenta));
        }
        return dtos;
    }

    public static OrdenVenta toModelo(OrdenVentaDTO dto) {
        List<LineaOrdenVenta> lineas = new ArrayList<>();
        for (LineaOrdenVentaDTO lineaDTO : dto.getLineas()) {
            lineas.add(LineaOrdenVentaMapper.toModelo(lineaDTO));
        }

        OrdenVenta ordenVenta = new OrdenVenta();
        ordenVenta.setId(dto.getId());
        ordenVenta.setActivo(dto.isActivo());
        ordenVenta.setCliente(dto.getCliente() != null ? ClienteMapper.toModelo(dto.getCliente()) : null);
        ordenVenta.setEmpleado(dto.getEmpleado() != null ? EmpleadoMapper.toModelo(dto.getEmpleado()) : null);
        ordenVenta.setLineas(lineas);
        return ordenVenta;
    }
}
