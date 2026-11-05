using Microsoft.AspNetCore.WebUtilities;
using SoftProg.Web.DTOs;
using SoftProg.Web.ViewModels;
using System.Globalization;
using System.Reflection;

namespace SoftProg.Web.Servicios.Rest {
    public class ClienteService : IClienteService {
        private string BaseUrlClientes { get; set; }
        private string BaseUrlMiCuenta { get; set; }
        private const string FormatoFecha = "yyyy-MM-dd";

        public ClienteService(IConfiguration configuration) {
            BaseUrlClientes = $"{configuration["RestApi:BaseUrl"]!}/clientes";
            BaseUrlMiCuenta = $"{configuration["RestApi:BaseUrl"]!}/mi-cuenta";
        }

        public async Task Delete(int id) {
            string url = $"{BaseUrlClientes}/{id}";
            await HttpUtils.DoDelete(url);
        }

        public async Task<List<ClienteViewModel>> FindAll() {
            string url = BaseUrlClientes;
            List<ClienteDTO> clientes = await HttpUtils.DoGet<List<ClienteDTO>>(url);
            return ToViewModels(clientes);
        }

        public async Task<List<ClienteViewModel>> FilterByName(string nombre) {
            string url = QueryHelpers.AddQueryString(BaseUrlClientes, new Dictionary<string, string> {
                { "nombre", nombre }
            });
            List<ClienteDTO> clientes = await HttpUtils.DoGet<List<ClienteDTO>>(url);
            return ToViewModels(clientes);
        }

        public async Task<ClienteViewModel?> FindByCuenta(string userName) {
            string url = $"{BaseUrlMiCuenta}/{userName}/cliente";
            ClienteDTO cliente = await HttpUtils.DoGet<ClienteDTO>(url);
            return ToViewModel(cliente);
        }

        public async Task<ClienteViewModel?> FindById(int id) {
            string url = $"{BaseUrlClientes}/{id}";
            ClienteDTO cliente = await HttpUtils.DoGet<ClienteDTO>(url);
            return ToViewModel(cliente);
        }

        public async Task Insert(ClienteViewModel modelo) {
            string url = BaseUrlClientes;
            await HttpUtils.DoPost<ClienteDTO, ClienteDTO>(url, ToDto(modelo));
        }

        public async Task Registrar(RegistroClienteViewModel modelo) {
            string url = BaseUrlMiCuenta;
            await HttpUtils.DoPost<ClienteDTO, ClienteDTO>(url, ToDto(modelo));
        }

        public async Task Update(ClienteViewModel modelo) {
            string url = $"{BaseUrlClientes}/{modelo.Id}";
            await HttpUtils.DoPut<ClienteDTO, ClienteDTO>(url, ToDto(modelo));
        }

        private List<ClienteViewModel> ToViewModels(List<ClienteDTO> dtos) {
            List<ClienteViewModel> clientes = [];
            foreach (ClienteDTO dto in dtos) {
                clientes.Add(ToViewModel(dto));
            }
            return clientes;
        }

        private ClienteViewModel ToViewModel(ClienteDTO dto) {
            return new ClienteViewModel {
                Id = dto.Id,
                IdCuentaUsuario = dto.CuentaUsuario?.Id ?? 0,
                Dni = dto.Dni,
                Nombre = dto.Nombre,
                ApellidoPaterno = dto.ApellidoPaterno,
                Genero = Enum.Parse<Genero>(dto.Genero),
                FechaNacimiento = DateTime.ParseExact(dto.FechaNacimiento, FormatoFecha, CultureInfo.InvariantCulture),
                Categoria = Enum.Parse<CategoriaCliente>(dto.Categoria),
                LineaCredito = dto.LineaCredito,
                IsActivo = dto.Activo
            };
        }

        private ClienteDTO ToDto(ClienteViewModel cliente) {
            return new ClienteDTO {
                Id = cliente.Id,
                CuentaUsuario = cliente.IdCuentaUsuario > 0 ? new() { Id = cliente.IdCuentaUsuario } : null,
                Dni = cliente.Dni,
                Nombre = cliente.Nombre,
                ApellidoPaterno = cliente.ApellidoPaterno,
                Genero = cliente.Genero.ToString(),
                FechaNacimiento = cliente.FechaNacimiento!.Value.ToString(FormatoFecha, CultureInfo.InvariantCulture),
                Categoria = cliente.Categoria.ToString(),
                LineaCredito = cliente.LineaCredito,
                Activo = cliente.IsActivo
            };
        }

        private ClienteDTO ToDto(RegistroClienteViewModel cliente) {
            return new ClienteDTO {
                Dni = cliente.Dni,
                Nombre = cliente.Nombre,
                ApellidoPaterno = cliente.ApellidoPaterno,
                Genero = cliente.Genero.ToString(),
                FechaNacimiento = cliente.FechaNacimiento!.Value.ToString(FormatoFecha, CultureInfo.InvariantCulture),
                Categoria = cliente.Categoria.ToString(),
                LineaCredito = cliente.LineaCredito
            };
        }
    }
}
