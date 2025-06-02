using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.Clientes;
using PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Almacen;
using PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Ventas;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.Logistica.Ventas;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.Clientes;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.Logistica.Ventas {
    public class OrdenVentaDAOImpl : BaseDAOImpl<OrdenVenta>, IOrdenVentaDAO {
        protected override DbCommand CommandoInsertar(DbConnection conn, OrdenVenta ordenVenta) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarOrdenVenta";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idCliente", DbType.Int32, ordenVenta.Cliente.Id);
            this.AgregarParametroEntrada(cmd, "@p_idEmpleado", DbType.Int32, DBNull.Value);
            this.AgregarParametroEntrada(cmd, "@p_total", DbType.Double, ordenVenta.Total);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, ordenVenta.IsActive);
            this.AgregarParametroSalida(cmd, "@p_id", DbType.Int32);

            return cmd;
        }

        protected override DbCommand CommandoInsertar(DbConnection conn, DbTransaction transaccion, OrdenVenta ordenVenta) {
            DbCommand cmd = this.CommandoInsertar(conn, ordenVenta);
            cmd.Transaction = transaccion;

            return cmd;
        }

        protected override DbCommand CommandoModificar(DbConnection conn, OrdenVenta ordenVenta) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarOrdenVenta";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idCliente", DbType.Int32, ordenVenta.Cliente.Id);
            this.AgregarParametroEntrada(cmd, "@p_idEmpleado", DbType.String, ordenVenta.Empleado.Id);
            this.AgregarParametroEntrada(cmd, "@p_total", DbType.String, ordenVenta.Total);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, ordenVenta.IsActive);
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, ordenVenta.Id);

            return cmd;
        }

        protected override DbCommand CommandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarOrdenVenta";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoBuscar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarOrdenVentaPorId";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoListar(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarOrdenesVenta";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            return cmd;
        }

        protected override OrdenVenta mapearModelo(DbDataReader reader) {
            return new OrdenVenta {
                Id = Convert.ToInt32(reader["id"]),
                Cliente = new ClienteDAOImpl().Buscar(Convert.ToInt32(reader["idCliente"])), 
                Empleado = new EmpleadoDAOImpl().Buscar(Convert.ToInt32(reader["idEmpleado"])), 
                Total = Convert.ToInt32(reader["total"]),
                IsActive = Convert.ToBoolean(reader["activo"])
            };
        }
    }
}
