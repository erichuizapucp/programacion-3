package pe.edu.pucp.softprog.rest.mapper;

import pe.edu.pucp.softprog.bl.AreaBL;
import pe.edu.pucp.softprog.bl.error.BLException;
import pe.edu.pucp.softprog.bl.error.NoEncontradoException;
import pe.edu.pucp.softprog.bl.error.ValidacionException;
import pe.edu.pucp.softprog.bl.CuentaUsuarioBL;
import pe.edu.pucp.softprog.bl.impl.AreaBLImpl;
import pe.edu.pucp.softprog.bl.impl.CuentaUsuarioBLImpl;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.rrhh.Area;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import pe.edu.pucp.softprog.rest.dto.EmpleadoDTO;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        dto.setCuentaUsuario(empleado.getCuentaUsuario() != null
                ? CuentaUsuarioMapper.toDTO(empleado.getCuentaUsuario())
                : null);
        dto.setDni(empleado.getDni());
        dto.setNombre(empleado.getNombre());
        dto.setApellidoPaterno(empleado.getApellidoPaterno());
        dto.setGenero(empleado.getGenero().name());
        dto.setFechaNacimiento(empleado.getFechaNacimiento().toString());
        dto.setArea(AreaMapper.toDTO(empleado.getArea()));
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
        if (dto.getArea() == null) {
            throw new ValidacionException("El empleado debe tener un área");
        }
        Area area;
        try {
            area = areaBL.findById(dto.getArea().getId());
        } catch (NoEncontradoException e) {
            throw new ValidacionException("No existe el área con id " + dto.getArea().getId());
        }

        Empleado empleado = new Empleado();
        empleado.setId(dto.getId());
        empleado.setActivo(dto.isActivo());
        if (dto.getCuentaUsuario() != null) {
            try {
                CuentaUsuario cuentaUsuario = cuentaUsuarioBL.findById(dto.getCuentaUsuario().getId());
                empleado.setCuentaUsuario(cuentaUsuario);
            } catch (NoEncontradoException e) {
                throw new ValidacionException("No existe la cuenta de usuario con id " + dto.getCuentaUsuario().getId());
            }
        }
        empleado.setDni(dto.getDni());
        empleado.setNombre(dto.getNombre());
        empleado.setApellidoPaterno(dto.getApellidoPaterno());
        try {
            empleado.setGenero(Genero.valueOf(dto.getGenero()));
            empleado.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
            empleado.setCargo(Cargo.valueOf(dto.getCargo()));
        } catch (IllegalArgumentException | NullPointerException | DateTimeParseException e) {
            throw new ValidacionException("Género, fecha de nacimiento (yyyy-MM-dd) o cargo inválidos");
        }
        empleado.setArea(area);
        empleado.setSueldo(dto.getSueldo());
        return empleado;
    }
}
