package pe.edu.pucp.softprog.ws.mapper;

import pe.edu.pucp.softprog.bl.AreaBL;
import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.CuentaUsuarioBL;
import pe.edu.pucp.softprog.bl.impl.AreaBLImpl;
import pe.edu.pucp.softprog.bl.impl.CuentaUsuarioBLImpl;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.rrhh.Area;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import pe.edu.pucp.softprog.ws.dto.EmpleadoDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class EmpleadoMapper {
    private static final AreaBL areaBL = new AreaBLImpl();
    private static final CuentaUsuarioBL cuentaUsuarioBL = new CuentaUsuarioBLImpl();

    private EmpleadoMapper() {
    }

    public static EmpleadoDTO toDTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(empleado.getId());
        dto.setActivo(empleado.isActivo());
        CuentaUsuario cuentaUsuario = empleado.getCuentaUsuario();
        dto.setIdCuentaUsuario(cuentaUsuario != null ? cuentaUsuario.getId() : 0);
        dto.setDni(empleado.getDni());
        dto.setNombre(empleado.getNombre());
        dto.setApellidoPaterno(empleado.getApellidoPaterno());
        dto.setGenero(empleado.getGenero().name());
        dto.setFechaNacimiento(empleado.getFechaNacimiento().toString());
        dto.setIdArea(empleado.getArea().getId());
        dto.setNombreArea(empleado.getArea().getNombre());
        dto.setCargo(empleado.getCargo().name());
        dto.setSueldo(empleado.getSueldo());
        return dto;
    }

    public static List<EmpleadoDTO> toDTOs(List<Empleado> empleados) {
        List<EmpleadoDTO> dtos = new ArrayList<>();
        for (Empleado empleado : empleados) {
            dtos.add(toDTO(empleado));
        }
        return dtos;
    }

    public static Empleado toModelo(EmpleadoDTO dto) throws BLException {
        Area area = areaBL.findById(dto.getIdArea());
        if (area == null) {
            throw new BLException("No existe un área con id " + dto.getIdArea());
        }
        CuentaUsuario cuentaUsuario = dto.getIdCuentaUsuario() > 0
                ? cuentaUsuarioBL.findById(dto.getIdCuentaUsuario())
                : null;

        Empleado empleado = new Empleado();
        empleado.setId(dto.getId());
        empleado.setActivo(dto.isActivo());
        empleado.setCuentaUsuario(cuentaUsuario);
        empleado.setDni(dto.getDni());
        empleado.setNombre(dto.getNombre());
        empleado.setApellidoPaterno(dto.getApellidoPaterno());
        empleado.setGenero(Genero.valueOf(dto.getGenero()));
        empleado.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        empleado.setArea(area);
        empleado.setCargo(Cargo.valueOf(dto.getCargo()));
        empleado.setSueldo(dto.getSueldo());
        return empleado;
    }
}
