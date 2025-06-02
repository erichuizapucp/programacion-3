using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Config;
using PUCP.Edu.Pe.SoftProg.Modelo.Clientes;
using PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Almacen;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.Clientes;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.Clientes {
    public class ClienteDAOImpl : BaseDAOImpl<Cliente>, IClienteDAO {
        protected override DbCommand CommandoInsertar(DbConnection conn, Cliente cliente) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarCliente";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idCuentaUsuario", DbType.Int32, DBNull.Value);
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

        protected override DbCommand CommandoInsertar(DbConnection conn, DbTransaction transaccion, Cliente cliente) {
            DbCommand cmd = this.CommandoInsertar(conn, cliente);
            cmd.Transaction = transaccion;

            return cmd;
        }

        protected override DbCommand CommandoModificar(DbConnection conn, Cliente cliente) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarCliente";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idCuentaUsuario", DbType.Int32, DBNull.Value);
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

        protected override DbCommand CommandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarCliente";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoBuscar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarClientePorId";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoListar(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarClientes";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            return cmd;
        }

        protected override Cliente mapearModelo(DbDataReader reader) {
            return new Cliente {
                Id = Convert.ToInt32(reader["id"]),
                Dni = Convert.ToString(reader["dni"]),
                Nombre = Convert.ToString(reader["nombre"]),
                ApellidoPaterno = Convert.ToString(reader["apellidoPaterno"]),
                Genero = Convert.ToChar(reader["genero"]),
                FechaNacimiento = Convert.ToDateTime(reader["fechaNacimiento"]),
                Categoria = (Categoria)Enum.Parse(typeof(Categoria), Convert.ToString(reader["categoria"])),
                IsActive = Convert.ToBoolean(reader["activo"])
            };
        }

        public Cliente BuscarPorDni(string dni)
        {
            try {
                using (DbConnection conn = DBManager.GetInstance().GetConnection())
                using (DbCommand cmd = this.ComandoBuscarPorDni(conn, dni))
                using (DbDataReader reader = cmd.ExecuteReader()) {
                    if (!reader.HasRows) {
                        Console.Error.WriteLine("No se encontro el registro con DNI: " + dni);
                        return default;
                    }

                    reader.Read();
                    return this.mapearModelo(reader);
                }
            }
            catch (Exception e) {
                Console.Error.WriteLine("Error SQL Durante la busueda: " + e.Message);
                throw new InvalidOperationException("No se pudo buscar el registro.", e);
            }
        }

        private DbCommand ComandoBuscarPorDni(DbConnection conn, string dni)
        {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarClientePorDni";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_dni", DbType.Int32, dni);

            return cmd;
        }
    }
}
