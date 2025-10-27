using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using PUCP.SoftProg.Modelo.Ventas;
using PUCP.SoftProg.Persistencia.DAO.Ventas;
using PUCP.SoftProg.Persistencia.DAOImpl.Clientes;
using PUCP.SoftProg.Persistencia.DAOImpl.RRHH;

namespace PUCP.SoftProg.Persistencia.DAOImpl.Ventas {
    public class OrdenVentaDAOImpl : TransactionalBaseDAOImpl<OrdenVenta>, IOrdenVentaDAO {
        protected override DbCommand ComandoCrear(DbConnection conn, OrdenVenta ordenVenta) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarOrdenVenta";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idCliente", DbType.Int32, ordenVenta.Cliente.Id);
            this.AgregarParametroEntrada(cmd, "@p_idEmpleado", DbType.Int32, DBNull.Value);
            this.AgregarParametroEntrada(cmd, "@p_total", DbType.Double, ordenVenta.Total);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, ordenVenta.IsActive);
            this.AgregarParametroSalida(cmd, "@p_id", DbType.Int32);

            return cmd;
        }

        protected override DbCommand ComandoActualizar(DbConnection conn, OrdenVenta ordenVenta) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarOrdenVenta";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idCliente", DbType.Int32, ordenVenta.Cliente.Id);
            this.AgregarParametroEntrada(cmd, "@p_idEmpleado", DbType.String, ordenVenta.Empleado.Id);
            this.AgregarParametroEntrada(cmd, "@p_total", DbType.String, ordenVenta.Total);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, ordenVenta.IsActive);
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, ordenVenta.Id);

            return cmd;
        }

        protected override DbCommand ComandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarOrdenVenta";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeer(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarOrdenVentaPorId";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeerTodos(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarOrdenesVenta";
            cmd.CommandType = CommandType.StoredProcedure;
            return cmd;
        }

        protected override OrdenVenta MapearModelo(DbDataReader reader) {
            int idEmpleado = reader["idEmpleado"] != DBNull.Value ? Convert.ToInt32(reader["idEmpleado"]) : 0;

            return new OrdenVenta {
                Id = Convert.ToInt32(reader["id"]),
                Cliente = new ClienteDAOImpl().Leer(Convert.ToInt32(reader["idCliente"])), 
                Empleado = idEmpleado > 0 ? new EmpleadoDAOImpl().Leer(idEmpleado) : null, 
                Total = Convert.ToInt32(reader["total"]),
                IsActive = Convert.ToBoolean(reader["activo"])
            };
        }

        protected DbCommand ComandoLeerOrdenesPorCuenta(DbConnection conn, string cuenta) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarOrdenesVentaPorCuenta";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_cuenta", DbType.String, cuenta);

            return cmd;
        }

        public List<OrdenVenta> LeerOrdenesPorCuenta(string cuenta) {
            return EjecutarComando(conn => {
                using (DbCommand cmd = this.ComandoLeerOrdenesPorCuenta(conn, cuenta))
                using (DbDataReader reader = cmd.ExecuteReader()) {

                    List<OrdenVenta> modelos = new List<OrdenVenta>();

                    while (reader.Read()) {
                        modelos.Add(this.MapearModelo(reader));
                    }

                    return modelos;
                }
            });
        }
    }
}
