using SoftProg.Web.ViewModels;
using SoftProgWS;
using System.Globalization;
using System.ServiceModel;

namespace SoftProg.Web.Servicios.Soap {
    public class ClienteSoapService : IClienteService {
        private const string FormatoFecha = "yyyy-MM-dd";

        private readonly ClienteWSClient clienteWS;

        public ClienteSoapService(IConfiguration configuration) {
            string baseUrl = configuration["SoapWS:BaseUrl"]!;
            clienteWS = new ClienteWSClient(ClienteWSClient.EndpointConfiguration.ClienteWSPort, $"{baseUrl}/ClienteWS");
        }

        public List<ClienteViewModel> FindAll() {
            try {
                return ToViewModels(clienteWS.findAllClientes());
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public ClienteViewModel? FindById(int id) {
            try {
                clienteDTO? dto = clienteWS.findClienteById(id);
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Insert(ClienteViewModel cliente) {
            try {
                cliente.Id = clienteWS.insertCliente(ToSoap(cliente));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Update(ClienteViewModel cliente) {
            try {
                clienteWS.updateCliente(ToSoap(cliente));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Delete(int id) {
            try {
                clienteWS.deleteCliente(id);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public List<ClienteViewModel> FilterByName(string nombre) {
            try {
                return ToViewModels(clienteWS.filterClientesByName(nombre));
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public ClienteViewModel? FindByCuenta(string userName) {
            try {
                clienteDTO? dto = clienteWS.findClienteByCuenta(userName);
                return dto == null ? null : ToViewModel(dto);
            } catch (FaultException ex) {
                throw new ServicioException(ex.Message, ex);
            }
        }

        public void Registrar(RegistroClienteViewModel registro) {
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
                clienteWS.registrar(dto, registro.UserName, registro.Password);
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
                IdCuentaUsuario = dto.idCuentaUsuario,
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

        private clienteDTO ToSoap(ClienteViewModel cliente) {
            return new clienteDTO {
                id = cliente.Id,
                idCuentaUsuario = cliente.IdCuentaUsuario,
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
