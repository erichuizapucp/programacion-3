using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.RRHH;
using PUCP.Edu.Pe.SoftProg.Config;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.RRHH {
    public class EmpleadoDAOImpl : BaseDAOImpl<Empleado>, IEmpleadoDAO {
        protected override DbCommand CommandoInsertar(DbConnection conn, Empleado empleado) {

            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarEmpleado";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idArea", DbType.Int32, empleado.Area.Id);

            if (empleado.CuentaUsuario != null) {
                this.AgregarParametroEntrada(cmd, "@p_idCuentaUsuario", DbType.Int32, empleado.CuentaUsuario.Id);
            }
            else {
                this.AgregarParametroEntrada(cmd, "@p_idCuentaUsuario", DbType.Int32, DBNull.Value);
            }

            this.AgregarParametroEntrada(cmd, "@p_dni", DbType.String, empleado.Dni);

            this.AgregarParametroEntrada(cmd, "@p_nombre", DbType.String, empleado.Nombre);
            this.AgregarParametroEntrada(cmd, "@p_apellidoPaterno", DbType.String, empleado.ApellidoPaterno);
            this.AgregarParametroEntrada(cmd, "@p_genero", DbType.String, empleado.Genero);

            this.AgregarParametroEntrada(cmd, "@p_fechaNacimiento", DbType.DateTime, empleado.FechaNacimiento);
            this.AgregarParametroEntrada(cmd, "@p_cargo", DbType.String, empleado.Cargo);
            this.AgregarParametroEntrada(cmd, "@p_sueldo", DbType.Double, empleado.Sueldo);

            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, empleado.IsActive);
            this.AgregarParametroSalida(cmd, "@p_id", DbType.Int32);

            return cmd;
        }

        protected override DbCommand CommandoInsertar(DbConnection conn, DbTransaction transaccion, Empleado empleado) {
            DbCommand cmd = this.CommandoInsertar(conn, empleado);
            cmd.Transaction = transaccion;
            return cmd;
        }

        protected override DbCommand CommandoModificar(DbConnection conn, Empleado empleado) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarEmpleado";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idArea", DbType.Int32, empleado.Area.Id);
            this.AgregarParametroEntrada(cmd, "@p_idCuentaUsuario", DbType.Int32, DBNull.Value);
            this.AgregarParametroEntrada(cmd, "@p_dni", DbType.String, empleado.Dni);

            this.AgregarParametroEntrada(cmd, "@p_nombre", DbType.String, empleado.Nombre);
            this.AgregarParametroEntrada(cmd, "@p_apellidoPaterno", DbType.String, empleado.ApellidoPaterno);
            this.AgregarParametroEntrada(cmd, "@p_genero", DbType.String, empleado.Genero);

            this.AgregarParametroEntrada(cmd, "@p_fechaNacimiento", DbType.DateTime, empleado.FechaNacimiento);
            this.AgregarParametroEntrada(cmd, "@p_cargo", DbType.String, empleado.Cargo);
            this.AgregarParametroEntrada(cmd, "@p_sueldo", DbType.Double, empleado.Sueldo);

            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, empleado.IsActive);
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, empleado.Id);

            return cmd;
        }

        protected override DbCommand CommandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarEmpleado";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoBuscar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarEmpleadoPorId";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoListar(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarEmpleados";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            return cmd;
        }

        protected override Empleado mapearModelo(DbDataReader reader) {
            return new Empleado {
                Id = Convert.ToInt32(reader["id"]),
                Dni = Convert.ToString(reader["dni"]), 
                Nombre = Convert.ToString(reader["nombre"]),
                ApellidoPaterno = Convert.ToString(reader["apellidoPaterno"]), 
                Genero = Convert.ToChar(reader["genero"]),
                FechaNacimiento = Convert.ToDateTime(reader["fechaNacimiento"]),
                Cargo = Convert.ToString(reader["cargo"]), 
                Sueldo = Convert.ToDouble(reader["sueldo"]), 
                IsActive = (bool)reader["activo"],
                Area = new AreaDAOImpl().Buscar(Convert.ToInt32(reader["idArea"]))
            };
        }

        public List<Empleado> BuscarPorDni(string dni) {
            try {
                using (DbConnection conn = DBManager.GetInstance().GetConnection())
                using (DbCommand cmd = this.ComandoBuscarPorDni(conn, dni))
                using (DbDataReader reader = cmd.ExecuteReader()) {
                    List<Empleado> modelos = new List<Empleado>();

                    while (reader.Read()) {
                        modelos.Add(this.mapearModelo(reader));
                    }

                    return modelos;
                }
            }
            catch (Exception e) {
                Console.Error.WriteLine("Error SQL Durante la busueda: " + e.Message);
                throw new InvalidOperationException("No se pudo buscar el registro.", e);
            }
        }

        private DbCommand ComandoBuscarPorDni(DbConnection conn, string dni) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarEmpleadoPorDni";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_dni", DbType.Int32, dni);

            return cmd;
        }
    }
}
