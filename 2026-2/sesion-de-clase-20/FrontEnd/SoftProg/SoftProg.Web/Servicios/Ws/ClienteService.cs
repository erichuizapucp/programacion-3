using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.Globalization;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Ws {
    public class ClienteService : IClienteService {
        private const string FormatoFecha = "yyyy-MM-dd";

        private readonly ClienteWSClient clienteWS;

        public ClienteService(IConfiguration configuration) {
            string baseUrl = configuration["SoapWS:BaseUrl"]!;
            clienteWS = new ClienteWSClient(ClienteWSClient.EndpointConfiguration.ClienteWSPort, $"{baseUrl}/ClienteWS");
        }

        public async Task<List<ClienteViewModel>> FindAll() {
            try {
                findAllClientesResponse respuesta = await clienteWS.findAllClientesAsync();
                return ToViewModels(respuesta.@return);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<ClienteViewModel?> FindById(int id) {
            try {
                findClienteByIdResponse respuesta = await clienteWS.findClienteByIdAsync(id);
                clienteDTO? dto = respuesta.@return;
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Insert(ClienteViewModel cliente) {
            try {
                insertClienteResponse respuesta = await clienteWS.insertClienteAsync(ToDto(cliente));
                cliente.Id = respuesta.@return;
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Update(ClienteViewModel cliente) {
            try {
                await clienteWS.updateClienteAsync(ToDto(cliente));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Delete(int id) {
            try {
                await clienteWS.deleteClienteAsync(id);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<List<ClienteViewModel>> FilterByName(string nombre) {
            try {
                filterClientesByNameResponse respuesta = await clienteWS.filterClientesByNameAsync(nombre);
                return ToViewModels(respuesta.@return);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task<ClienteViewModel?> FindByCuenta(string userName) {
            try {
                findClienteByCuentaResponse respuesta = await clienteWS.findClienteByCuentaAsync(userName);
                clienteDTO? dto = respuesta.@return;
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public async Task Registrar(RegistroClienteViewModel registro) {
            clienteDTO dto = new() {
                dni = registro.Dni,
                nombre = registro.Nombre,
                apellidoPaterno = registro.ApellidoPaterno,
                genero = registro.Genero.ToString(),
                fechaNacimiento = registro.FechaNacimiento!.Value.ToString(FormatoFecha, CultureInfo.InvariantCulture),
                categoria = registro.Categoria.ToString(),
                lineaCredito = registro.LineaCredito,
                activo = true
            };

            try {
                await clienteWS.registrarAsync(dto, registro.UserName, registro.Password);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        private List<ClienteViewModel> ToViewModels(clienteDTO[]? dtos) {
            List<ClienteViewModel> clientes = [];
            foreach (clienteDTO dto in dtos ?? []) {
                clientes.Add(ToViewModel(dto));
            }
            return clientes;
        }

        private ClienteViewModel ToViewModel(clienteDTO dto) {
            return new ClienteViewModel {
                Id = dto.id,
                IdCuentaUsuario = dto.cuentaUsuario?.id ?? 0,
                Dni = dto.dni,
                Nombre = dto.nombre,
                ApellidoPaterno = dto.apellidoPaterno,
                Genero = Enum.Parse<Genero>(dto.genero),
                FechaNacimiento = DateTime.ParseExact(dto.fechaNacimiento, FormatoFecha, CultureInfo.InvariantCulture),
                Categoria = Enum.Parse<CategoriaCliente>(dto.categoria),
                LineaCredito = dto.lineaCredito,
                IsActivo = dto.activo
            };
        }

        private clienteDTO ToDto(ClienteViewModel cliente) {
            return new clienteDTO {
                id = cliente.Id,
                cuentaUsuario = cliente.IdCuentaUsuario > 0 ? new() { id = cliente.IdCuentaUsuario } : null,
                dni = cliente.Dni,
                nombre = cliente.Nombre,
                apellidoPaterno = cliente.ApellidoPaterno,
                genero = cliente.Genero.ToString(),
                fechaNacimiento = cliente.FechaNacimiento!.Value.ToString(FormatoFecha, CultureInfo.InvariantCulture),
                categoria = cliente.Categoria.ToString(),
                lineaCredito = cliente.LineaCredito,
                activo = cliente.IsActivo
            };
        }
    }
}
