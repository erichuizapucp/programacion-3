package pe.edu.pucp.softprog.ws.mapper;

import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.CuentaUsuarioBL;
import pe.edu.pucp.softprog.bl.impl.CuentaUsuarioBLImpl;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import pe.edu.pucp.softprog.modelo.ventas.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;
import pe.edu.pucp.softprog.ws.dto.ClienteDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class ClienteMapper {
    private static final CuentaUsuarioBL cuentaUsuarioBL = new CuentaUsuarioBLImpl();

    private ClienteMapper() {
    }

    public static ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setActivo(cliente.isActivo());
        dto.setCuentaUsuario(cliente.getCuentaUsuario() != null
                ? CuentaUsuarioMapper.toDTO(cliente.getCuentaUsuario())
                : null);
        dto.setDni(cliente.getDni());
        dto.setNombre(cliente.getNombre());
        dto.setApellidoPaterno(cliente.getApellidoPaterno());
        dto.setGenero(cliente.getGenero().name());
        dto.setFechaNacimiento(cliente.getFechaNacimiento().toString());
        dto.setCategoria(cliente.getCategoria().name());
        dto.setLineaCredito(cliente.getLineaCredito());
        return dto;
    }

    public static List<ClienteDTO> toDTOs(List<Cliente> clientes) {
        List<ClienteDTO> dtos = new ArrayList<>();
        for (Cliente cliente : clientes) {
            dtos.add(toDTO(cliente));
        }
        return dtos;
    }

    public static Cliente toModelo(ClienteDTO dto) throws BLException {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setActivo(dto.isActivo());
        if (dto.getCuentaUsuario() != null) {
            CuentaUsuario cuentaUsuario = cuentaUsuarioBL.findById(dto.getCuentaUsuario().getId());
            if (cuentaUsuario == null) {
                throw new BLException("No existe la cuenta de usuario con id " + dto.getCuentaUsuario().getId());
            }
            cliente.setCuentaUsuario(cuentaUsuario);
        }
        cliente.setDni(dto.getDni());
        cliente.setNombre(dto.getNombre());
        cliente.setApellidoPaterno(dto.getApellidoPaterno());
        cliente.setGenero(Genero.valueOf(dto.getGenero()));
        cliente.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        cliente.setCategoria(CategoriaCliente.valueOf(dto.getCategoria()));
        cliente.setLineaCredito(dto.getLineaCredito());
        return cliente;
    }
}
