package pe.edu.pucp.softprog.ws.mapper;

import pe.edu.pucp.softprog.modelo.rrhh.Area;
import pe.edu.pucp.softprog.ws.dto.AreaDTO;

import java.util.ArrayList;
import java.util.List;

public final class AreaMapper {
    private AreaMapper() {
    }

    public static AreaDTO toDTO(Area area) {
        AreaDTO dto = new AreaDTO();
        dto.setId(area.getId());
        dto.setActivo(area.isActivo());
        dto.setNombre(area.getNombre());
        return dto;
    }

    public static List<AreaDTO> toDTOs(List<Area> areas) {
        List<AreaDTO> dtos = new ArrayList<>();
        for (Area area : areas) {
            dtos.add(toDTO(area));
        }
        return dtos;
    }

    public static Area toModelo(AreaDTO dto) {
        Area area = new Area();
        area.setId(dto.getId());
        area.setActivo(dto.isActivo());
        area.setNombre(dto.getNombre());
        return area;
    }

    public static Area toReferencia(AreaDTO dto) {
        Area area = new Area();
        area.setId(dto.getId());
        return area;
    }
}
