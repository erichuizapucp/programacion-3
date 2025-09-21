using System;
using System.Data;
using System.Data.Common;
using PUCP.SoftProg.Modelo.Clientes;
using PUCP.SoftProg.Persistencia.DAO.Clientes;
using PUCP.SoftProg.Modelo;

namespace PUCP.SoftProg.Persistencia.DAOImpl.Clientes {
    public class ClienteDAOImpl : BaseDAOImpl<Cliente>, IClienteDAO {
        protected override DbCommand ComandoCrear(DbConnection conn, Cliente cliente) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarCliente";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idCuentaUsuario", DbType.Int32, cliente.CuentaUsuario.Id);
            this.AgregarParametroEntrada(cmd, "@p_dni", DbType.String, cliente.Dni);
            this.AgregarParametroEntrada(cmd, "@p_nombre", DbType.String, cliente.Nombre);
            this.AgregarParametroEntrada(cmd, "@p_apellidoPaterno", DbType.String, cliente.ApellidoPaterno);
            this.AgregarParametroEntrada(cmd, "@p_genero", DbType.Double, cliente.Genero);
            this.AgregarParametroEntrada(cmd, "@p_fechaNacimiento", DbType.DateTime, cliente.FechaNacimiento);
            this.AgregarParametroEntrada(cmd, "@p_categoria", DbType.String, cliente.Categoria);
            this.AgregarParametroEntrada(cmd, "@p_lineaCredito", DbType.Double, DBNull.Value);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, cliente.IsActive);
            this.AgregarParametroSalida(cmd, "@p_id", DbType.Int32);

            return cmd;
        }

        protected override DbCommand ComandoActualizar(DbConnection conn, Cliente cliente) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarCliente";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idCuentaUsuario", DbType.Int32, cliente.CuentaUsuario.Id);
            this.AgregarParametroEntrada(cmd, "@p_dni", DbType.String, cliente.Dni);
            this.AgregarParametroEntrada(cmd, "@p_nombre", DbType.String, cliente.Nombre);
            this.AgregarParametroEntrada(cmd, "@p_apellidoPaterno", DbType.String, cliente.ApellidoPaterno);
            this.AgregarParametroEntrada(cmd, "@p_genero", DbType.Double, cliente.Genero);
            this.AgregarParametroEntrada(cmd, "@p_fechaNacimiento", DbType.DateTime, cliente.FechaNacimiento);
            this.AgregarParametroEntrada(cmd, "@p_categoria", DbType.String, cliente.Categoria);
            this.AgregarParametroEntrada(cmd, "@p_lineaCredito", DbType.Double, DBNull.Value);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, cliente.IsActive);
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, cliente.Id);

            return cmd;
        }

        protected override DbCommand ComandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarCliente";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeer(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarClientePorId";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeerTodos(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarClientes";
            cmd.CommandType = CommandType.StoredProcedure;
            return cmd;
        }

        protected override Cliente MapearModelo(DbDataReader reader) {
            return new Cliente {
                Id = Convert.ToInt32(reader["id"]),
                Dni = Convert.ToString(reader["dni"]),
                Nombre = Convert.ToString(reader["nombre"]),
                ApellidoPaterno = Convert.ToString(reader["apellidoPaterno"]),
                Genero = (Genero)Enum.Parse(typeof(Genero), Convert.ToString(reader["genero"])),
                FechaNacimiento = Convert.ToDateTime(reader["fechaNacimiento"]),
                Categoria = (CategoriaCliente)Enum.Parse(typeof(CategoriaCliente), 
                    Convert.ToString(reader["categoria"])),
                IsActive = Convert.ToBoolean(reader["activo"])
            };
        }

        protected DbCommand ComandoBuscarPorDni(DbConnection conn, string dni) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarClientePorDni";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_dni", DbType.Int32, dni);

            return cmd;
        }

        public Cliente BuscarPorDni(string dni) {
            return EjecutarComando(conn => {
                using (DbCommand cmd = this.ComandoBuscarPorDni(conn, dni))
                using (DbDataReader reader = cmd.ExecuteReader()) {
                    if (!reader.HasRows) {
                        Console.Error.WriteLine("No se encontro el cliente con dni: " + dni);
                        return default;
                    }

                    reader.Read();
                    return this.MapearModelo(reader);
                }
            });
        }
    }
}
