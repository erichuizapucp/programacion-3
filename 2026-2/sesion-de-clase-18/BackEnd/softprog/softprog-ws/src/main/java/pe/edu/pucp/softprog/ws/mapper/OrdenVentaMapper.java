package pe.edu.pucp.softprog.ws.mapper;

import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.ClienteBL;
import pe.edu.pucp.softprog.bl.EmpleadoBL;
import pe.edu.pucp.softprog.bl.impl.ClienteBLImpl;
import pe.edu.pucp.softprog.bl.impl.EmpleadoBLImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;
import pe.edu.pucp.softprog.ws.dto.LineaOrdenVentaDTO;
import pe.edu.pucp.softprog.ws.dto.OrdenVentaDTO;

import java.util.ArrayList;
import java.util.List;

public final class OrdenVentaMapper {
    private static final ClienteBL clienteBL = new ClienteBLImpl();
    private static final EmpleadoBL empleadoBL = new EmpleadoBLImpl();

    private OrdenVentaMapper() {
    }

    public static OrdenVentaDTO toDTO(OrdenVenta ordenVenta) {
        OrdenVentaDTO dto = new OrdenVentaDTO();
        dto.setId(ordenVenta.getId());
        dto.setActivo(ordenVenta.isActivo());

        Cliente cliente = ordenVenta.getCliente();
        if (cliente != null) {
            dto.setIdCliente(cliente.getId());
            dto.setNombreCliente(cliente.getNombre() + " " + cliente.getApellidoPaterno());
        }

        Empleado empleado = ordenVenta.getEmpleado();
        if (empleado != null) {
            dto.setIdEmpleado(empleado.getId());
            dto.setNombreEmpleado(empleado.getNombre() + " " + empleado.getApellidoPaterno());
        }

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

    public static OrdenVenta toModelo(OrdenVentaDTO dto) throws BLException {
        Cliente cliente = dto.getIdCliente() > 0 ? clienteBL.findById(dto.getIdCliente()) : null;
        Empleado empleado = dto.getIdEmpleado() > 0 ? empleadoBL.findById(dto.getIdEmpleado()) : null;

        List<LineaOrdenVenta> lineas = new ArrayList<>();
        for (LineaOrdenVentaDTO lineaDTO : dto.getLineas()) {
            lineas.add(LineaOrdenVentaMapper.toModelo(lineaDTO));
        }

        OrdenVenta ordenVenta = new OrdenVenta();
        ordenVenta.setId(dto.getId());
        ordenVenta.setActivo(dto.isActivo());
        ordenVenta.setCliente(cliente);
        ordenVenta.setEmpleado(empleado);
        ordenVenta.setLineas(lineas);
        return ordenVenta;
    }
}
