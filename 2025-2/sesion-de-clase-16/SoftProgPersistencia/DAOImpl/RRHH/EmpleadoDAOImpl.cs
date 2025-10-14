using System;
using System.Data.Common;
using System.Data;
using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Persistencia.DAO.RRHH;
using PUCP.SoftProg.Modelo;

namespace PUCP.SoftProg.Persistencia.DAOImpl.RRHH {
    public class EmpleadoDAOImpl : BaseDAOImpl<Empleado>, IEmpleadoDAO {
        protected override DbCommand ComandoCrear(DbConnection conn, Empleado empleado) {

            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarEmpleado";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idArea", DbType.Int32, empleado.Area.Id);

            if (empleado.CuentaUsuario != null) {
                this.AgregarParametroEntrada(cmd, "@p_idCuentaUsuario", DbType.Int32, 
                    empleado.CuentaUsuario.Id);
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

        protected override DbCommand ComandoActualizar(DbConnection conn, Empleado empleado) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarEmpleado";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idArea", DbType.Int32, empleado.Area.Id);
            if (empleado.CuentaUsuario != null) {
                this.AgregarParametroEntrada(cmd, "@p_idCuentaUsuario", DbType.Int32,
                    empleado.CuentaUsuario.Id);
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
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, empleado.Id);

            return cmd;
        }

        protected override DbCommand ComandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarEmpleado";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeer(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarEmpleadoPorId";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeerTodos(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarEmpleados";
            cmd.CommandType = CommandType.StoredProcedure;
            return cmd;
        }

        protected override Empleado MapearModelo(DbDataReader reader) {
            return new Empleado {
                Id = Convert.ToInt32(reader["id"]),
                Dni = Convert.ToString(reader["dni"]), 
                Nombre = Convert.ToString(reader["nombre"]),
                ApellidoPaterno = Convert.ToString(reader["apellidoPaterno"]), 
                Genero = (Genero)Enum.Parse(typeof(Genero), Convert.ToString(reader["genero"])),
                FechaNacimiento = Convert.ToDateTime(reader["fechaNacimiento"]),
                Cargo = (Cargo)Enum.Parse(typeof(Cargo), Convert.ToString(reader["cargo"])), 
                Sueldo = Convert.ToDouble(reader["sueldo"]), 
                IsActive = (bool)reader["activo"],
                Area = new AreaDAOImpl().Leer(
                    Convert.ToInt32(reader["idArea"]))
            };
        }

        protected DbCommand ComandoBuscarPorDni(DbConnection conn, string dni) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarEmpleadoPorDni";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_dni", DbType.Int32, dni);

            return cmd;
        }

        public Empleado BuscarPorDni(string dni) {
            return EjecutarComando(conn => {
                using (DbCommand cmd = this.ComandoBuscarPorDni(conn, dni)) 
                using (DbDataReader reader = cmd.ExecuteReader()) {
                    if (!reader.HasRows) {
                        Console.Error.WriteLine("No se encontro el empleado con dni: " + dni);
                        return default;
                    }

                    reader.Read();
                    return this.MapearModelo(reader);
                }
            });
        }
    }
}
