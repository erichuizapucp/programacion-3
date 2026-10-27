package pe.edu.pucp.softprog.ws.mapper;

import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import pe.edu.pucp.softprog.ws.dto.CuentaUsuarioDTO;

import java.util.ArrayList;
import java.util.List;

public final class CuentaUsuarioMapper {
    private CuentaUsuarioMapper() {
    }

    public static CuentaUsuarioDTO toDTO(CuentaUsuario cuentaUsuario) {
        CuentaUsuarioDTO dto = new CuentaUsuarioDTO();
        dto.setId(cuentaUsuario.getId());
        dto.setActivo(cuentaUsuario.isActivo());
        dto.setUserName(cuentaUsuario.getUserName());
        dto.setPassword(cuentaUsuario.getPassword());
        return dto;
    }

    public static List<CuentaUsuarioDTO> toDTOs(List<CuentaUsuario> cuentasUsuario) {
        List<CuentaUsuarioDTO> dtos = new ArrayList<>();
        for (CuentaUsuario cuentaUsuario : cuentasUsuario) {
            dtos.add(toDTO(cuentaUsuario));
        }
        return dtos;
    }

    public static CuentaUsuario toModelo(CuentaUsuarioDTO dto) {
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setId(dto.getId());
        cuentaUsuario.setActivo(dto.isActivo());
        cuentaUsuario.setUserName(dto.getUserName());
        cuentaUsuario.setPassword(dto.getPassword());
        return cuentaUsuario;
    }

    public static CuentaUsuario toReferencia(CuentaUsuarioDTO dto) {
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setId(dto.getId());
        return cuentaUsuario;
    }
}
