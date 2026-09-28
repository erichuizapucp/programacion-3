using SoftProg.DbManager;
using SoftProg.Modelo.Ventas;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl {
    public class ClienteDAOImpl : PersonaDAOImpl<Cliente>, IClienteDAO {
        public List<Cliente> FindAll() {
            string sql =
                """
                SELECT id, id_cuenta_usuario, dni, nombre, apellido_paterno, 
                    genero, fecha_nacimiento, categoria, linea_credito, activo 
                FROM cliente
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            using DbDataReader reader = cmd.ExecuteReader();

            List<Cliente> clientes = [];
            while (reader.Read()) {
                clientes.Add(Mapear(reader, new Cliente()));
            }

            return clientes;
        }

        public Cliente? FindById(int id) {
            string sql =
                """
                SELECT id, id_cuenta_usuario, dni, nombre, apellido_paterno, 
                    genero, fecha_nacimiento, categoria, linea_credito, activo 
                FROM cliente
                WHERE
                    id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id", id);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Cliente()) : null;
        }

        public Cliente? FindByDni(string dni) {
            string sql =
                """
                SELECT id, id_cuenta_usuario, dni, nombre, apellido_paterno, 
                    genero, fecha_nacimiento, categoria, linea_credito, activo 
                FROM cliente
                WHERE
                    dni = @dni
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroCadena("dni", dni);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Cliente()) : null;
        }

        public void Insert(Cliente cliente) {
            string sql =
                """
                INSERT INTO cliente(id_cuenta_usuario, dni, nombre, apellido_paterno, 
                    genero, fecha_nacimiento, categoria, linea_credito, activo)
                VALUES (@id_cuenta_usuario, @dni, @nombre, @apellido_paterno, 
                    @genero, @fecha_nacimiento, @categoria, @linea_credito, @activo);
                SELECT LAST_INSERT_ID();
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id_cuenta_usuario", cliente.CuentaUsuario?.Id);
            cmd.AgregarParametroCadena("dni", cliente.Dni);
            cmd.AgregarParametroCadena("nombre", cliente.Nombre);
            cmd.AgregarParametroCadena("apellido_paterno", cliente.ApellidoPaterno);
            cmd.AgregarParametroCadena("genero", cliente.Genero.ToString());
            cmd.AgregarParametroFecha("fecha_nacimiento", cliente.FechaNacimiento);
            cmd.AgregarParametroCadena("categoria", cliente.Categoria.ToString());
            cmd.AgregarParametroDouble("linea_credito", cliente.LineaCredito);
            cmd.AgregarParametroBoolean("activo", cliente.IsActivo);

            object? resultado = cmd.ExecuteScalar();
            if (resultado == null || resultado == DBNull.Value) {
                throw new Exception("No se pudo insertar el cliente");
            }

            cliente.Id = Convert.ToInt32(resultado);
        }

        public void Update(Cliente cliente) {
            string sql =
                """
                UPDATE cliente
                SET
                    id_cuenta_usuario = @id_cuenta_usuario,
                    dni = @dni,
                    nombre = @nombre,
                    apellido_paterno = @apellido_paterno,
                    genero = @genero,
                    fecha_nacimiento = @fecha_nacimiento,
                    categoria = @categoria,
                    linea_credito = @linea_credito,
                    activo = @activo
                WHERE
                    id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id_cuenta_usuario", cliente.CuentaUsuario?.Id);
            cmd.AgregarParametroCadena("dni", cliente.Dni);
            cmd.AgregarParametroCadena("nombre", cliente.Nombre);
            cmd.AgregarParametroCadena("apellido_paterno", cliente.ApellidoPaterno);
            cmd.AgregarParametroCadena("genero", cliente.Genero.ToString());
            cmd.AgregarParametroFecha("fecha_nacimiento", cliente.FechaNacimiento);
            cmd.AgregarParametroCadena("categoria", cliente.Categoria.ToString());
            cmd.AgregarParametroDouble("linea_credito", cliente.LineaCredito);
            cmd.AgregarParametroBoolean("activo", cliente.IsActivo);
            cmd.AgregarParametroEntero("id", cliente.Id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo actualizar el cliente");
            }
        }

        public void Delete(int id) {
            string sql =
                """
                DELETE FROM cliente WHERE id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id", id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo eliminar el cliente");
            }
        }

        protected override Cliente Mapear(DbDataReader reader, Cliente cliente) {
            base.Mapear(reader, cliente);
            cliente.Categoria = Enum.Parse<CategoriaCliente>(reader.GetString("categoria"));
            cliente.LineaCredito = reader.IsDBNull("linea_credito") ? 0 : reader.GetDouble("linea_credito");
            return cliente;
        }
    }
}
