package pe.edu.pucp.softprog.ws.mapper;

import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.ventas.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;
import pe.edu.pucp.softprog.ws.dto.ClienteDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class ClienteMapper {
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

    public static Cliente toModelo(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setActivo(dto.isActivo());
        cliente.setCuentaUsuario(dto.getCuentaUsuario() != null
                ? CuentaUsuarioMapper.toReferencia(dto.getCuentaUsuario())
                : null);
        cliente.setDni(dto.getDni());
        cliente.setNombre(dto.getNombre());
        cliente.setApellidoPaterno(dto.getApellidoPaterno());
        cliente.setGenero(Genero.valueOf(dto.getGenero()));
        cliente.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        cliente.setCategoria(CategoriaCliente.valueOf(dto.getCategoria()));
        cliente.setLineaCredito(dto.getLineaCredito());
        return cliente;
    }

    public static Cliente toReferencia(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        return cliente;
    }
}
