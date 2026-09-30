using SoftProg.DbManager;
using SoftProg.Modelo.Ventas;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl {
    public class ClienteDAOImpl : PersonaDAOImpl<Cliente>, IClienteDAO {
        public List<Cliente> FindAll() {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "listar_clientes";
            using DbDataReader reader = cmd.ExecuteReader();

            List<Cliente> clientes = [];
            while (reader.Read()) {
                clientes.Add(Mapear(reader, new Cliente()));
            }

            return clientes;
        }

        public Cliente? FindById(int id) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "buscar_cliente_por_id";
            cmd.AgregarParametroEntero("p_id", id);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Cliente()) : null;
        }

        public Cliente? FindByDni(string dni) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "buscar_cliente_por_dni";
            cmd.AgregarParametroCadena("p_dni", dni);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Cliente()) : null;
        }

        public void Insert(Cliente cliente) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "insertar_cliente";
            cmd.AgregarParametroEntero("p_id_cuenta_usuario", cliente.CuentaUsuario?.Id);
            cmd.AgregarParametroCadena("p_dni", cliente.Dni);
            cmd.AgregarParametroCadena("p_nombre", cliente.Nombre);
            cmd.AgregarParametroCadena("p_apellido_paterno", cliente.ApellidoPaterno);
            cmd.AgregarParametroCadena("p_genero", cliente.Genero.ToString());
            cmd.AgregarParametroFecha("p_fecha_nacimiento", cliente.FechaNacimiento);
            cmd.AgregarParametroCadena("p_categoria", cliente.Categoria.ToString());
            cmd.AgregarParametroDouble("p_linea_credito", cliente.LineaCredito);
            cmd.AgregarParametroBoolean("p_activo", cliente.IsActivo);
            cmd.AgregarParametroSalidaEntero("p_id");

            cmd.ExecuteNonQuery();

            object? resultado = cmd.Parameters["p_id"].Value;
            if (resultado == null || resultado == DBNull.Value) {
                throw new Exception("No se pudo insertar el cliente");
            }

            cliente.Id = Convert.ToInt32(resultado);
        }

        public void Update(Cliente cliente) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "modificar_cliente";
            cmd.AgregarParametroEntero("p_id_cuenta_usuario", cliente.CuentaUsuario?.Id);
            cmd.AgregarParametroCadena("p_dni", cliente.Dni);
            cmd.AgregarParametroCadena("p_nombre", cliente.Nombre);
            cmd.AgregarParametroCadena("p_apellido_paterno", cliente.ApellidoPaterno);
            cmd.AgregarParametroCadena("p_genero", cliente.Genero.ToString());
            cmd.AgregarParametroFecha("p_fecha_nacimiento", cliente.FechaNacimiento);
            cmd.AgregarParametroCadena("p_categoria", cliente.Categoria.ToString());
            cmd.AgregarParametroDouble("p_linea_credito", cliente.LineaCredito);
            cmd.AgregarParametroBoolean("p_activo", cliente.IsActivo);
            cmd.AgregarParametroEntero("p_id", cliente.Id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo actualizar el cliente");
            }
        }

        public void Delete(int id) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "eliminar_cliente";
            cmd.AgregarParametroEntero("p_id", id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo eliminar el cliente");
            }
        }

        protected override Cliente Mapear(DbDataReader reader, Cliente cliente) {
            base.Mapear(reader, cliente);
            cliente.Categoria = Enum.Parse<CategoriaCliente>(reader.GetString("categoria"));
            cliente.LineaCredito = reader.IsDBNull("linea_credito") ? 0 : Convert.ToDouble(reader.GetDecimal("linea_credito"));
            return cliente;
        }
    }
}
