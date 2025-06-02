using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Almacen;
using PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Ventas;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.Logistica.Ventas;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.Logistica.Almacen;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.Logistica.Ventas {
    public class LineaOrdenVentaDAOImpl : BaseDAOImpl<LineaOrdenVenta>, ILineaOrdenVentaDAO {
        protected override DbCommand CommandoInsertar(DbConnection conn, LineaOrdenVenta lineaOrdenVenta) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarLineaOrdenVenta";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idOrdenVenta", DbType.Int32, lineaOrdenVenta.OrdenVenta.Id);
            this.AgregarParametroEntrada(cmd, "@p_idProducto", DbType.Int32, lineaOrdenVenta.Producto.Id);
            this.AgregarParametroEntrada(cmd, "@p_cantidad", DbType.Int32, lineaOrdenVenta.Cantidad);
            this.AgregarParametroEntrada(cmd, "@p_subTotal", DbType.Double, lineaOrdenVenta.SubTotal);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, lineaOrdenVenta.IsActive);
            this.AgregarParametroSalida(cmd, "@p_id", DbType.Int32);

            return cmd;
        }

        protected override DbCommand CommandoInsertar(DbConnection conn, DbTransaction transaccion, LineaOrdenVenta lineaOrdenVenta) {
            DbCommand cmd = this.CommandoInsertar(conn, lineaOrdenVenta);
            cmd.Transaction = transaccion;

            return cmd;
        }

        protected override DbCommand CommandoModificar(DbConnection conn, LineaOrdenVenta lineaOrdenVenta) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarLineaOrdenVenta";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idOrdenVenta", DbType.Int32, lineaOrdenVenta.OrdenVenta.Id);
            this.AgregarParametroEntrada(cmd, "@p_idProducto", DbType.Int32, lineaOrdenVenta.Producto.Id);
            this.AgregarParametroEntrada(cmd, "@p_cantidad", DbType.Int32, lineaOrdenVenta.Cantidad);
            this.AgregarParametroEntrada(cmd, "@p_subTotal", DbType.Double, lineaOrdenVenta.IsActive);
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, lineaOrdenVenta.Id);

            return cmd;
        }

        protected override DbCommand CommandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarLineaOrdenVenta";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoBuscar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarLineaOrdenVentaPorId";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoListar(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarLineasOrdenVenta";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            return cmd;
        }

        protected override LineaOrdenVenta mapearModelo(DbDataReader reader) {
            return new LineaOrdenVenta {
                Id = Convert.ToInt32(reader["id"]),
                OrdenVenta = new OrdenVentaDAOImpl().Buscar(Convert.ToInt32(reader["idOrdenVenta"])), 
                Producto = new ProductoDAOImpl().Buscar(Convert.ToInt32(reader["idProducto"])), 
                Cantidad = Convert.ToInt32(reader["cantidad"]), 
                SubTotal = Convert.ToDouble(reader["subTotal"]), 
                IsActive = Convert.ToBoolean(reader["activo"])
            };
        }
    }
}
