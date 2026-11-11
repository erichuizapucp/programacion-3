package pe.edu.pucp.softprog.rest.mapper;

import pe.edu.pucp.softprog.bl.error.BLException;
import pe.edu.pucp.softprog.bl.error.NoEncontradoException;
import pe.edu.pucp.softprog.bl.error.ValidacionException;
import pe.edu.pucp.softprog.bl.ClienteBL;
import pe.edu.pucp.softprog.bl.EmpleadoBL;
import pe.edu.pucp.softprog.bl.impl.ClienteBLImpl;
import pe.edu.pucp.softprog.bl.impl.EmpleadoBLImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;
import pe.edu.pucp.softprog.rest.dto.LineaOrdenVentaDTO;
import pe.edu.pucp.softprog.rest.dto.OrdenVentaDTO;

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

    public static OrdenVenta toModelo(OrdenVentaDTO dto) throws BLException {
        Cliente cliente = null;
        if (dto.getCliente() != null) {
            try {
                cliente = clienteBL.findById(dto.getCliente().getId());
            } catch (NoEncontradoException e) {
                throw new ValidacionException("No existe el cliente con id " + dto.getCliente().getId());
            }
        }
        Empleado empleado = null;
        if (dto.getEmpleado() != null) {
            try {
                empleado = empleadoBL.findById(dto.getEmpleado().getId());
            } catch (NoEncontradoException e) {
                throw new ValidacionException("No existe el empleado con id " + dto.getEmpleado().getId());
            }
        }

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
