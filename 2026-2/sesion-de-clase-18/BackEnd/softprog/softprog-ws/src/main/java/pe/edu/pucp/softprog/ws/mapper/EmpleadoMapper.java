package pe.edu.pucp.softprog.ws.mapper;

import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.ws.dto.EmpleadoDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class EmpleadoMapper {
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

    public static Empleado toModelo(EmpleadoDTO dto) {
        Empleado empleado = new Empleado();
        empleado.setId(dto.getId());
        empleado.setActivo(dto.isActivo());
        empleado.setCuentaUsuario(dto.getCuentaUsuario() != null
                ? CuentaUsuarioMapper.toReferencia(dto.getCuentaUsuario())
                : null);
        empleado.setDni(dto.getDni());
        empleado.setNombre(dto.getNombre());
        empleado.setApellidoPaterno(dto.getApellidoPaterno());
        empleado.setGenero(Genero.valueOf(dto.getGenero()));
        empleado.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        if (dto.getArea() != null) {
            empleado.setArea(AreaMapper.toReferencia(dto.getArea()));
        }
        empleado.setCargo(Cargo.valueOf(dto.getCargo()));
        empleado.setSueldo(dto.getSueldo());
        return empleado;
    }

    public static Empleado toReferencia(EmpleadoDTO dto) {
        Empleado empleado = new Empleado();
        empleado.setId(dto.getId());
        return empleado;
    }
}
