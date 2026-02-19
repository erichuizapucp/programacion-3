using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using PUCP.SoftProg.Modelo.Ventas;
using PUCP.SoftProg.Persistencia.DAO.Ventas;
using PUCP.SoftProg.Persistencia.DAOImpl.Almacen;

namespace PUCP.SoftProg.Persistencia.DAOImpl.Ventas {
    public class LineaOrdenVentaDAOImpl : TransactionalBaseDAOImpl<LineaOrdenVenta>, ILineaOrdenVentaDAO {
        protected override DbCommand ComandoCrear(DbConnection conn, LineaOrdenVenta lineaOrdenVenta) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarLineaOrdenVenta";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idOrdenVenta", DbType.Int32, lineaOrdenVenta.OrdenVenta.Id);
            this.AgregarParametroEntrada(cmd, "@p_idProducto", DbType.Int32, lineaOrdenVenta.Producto.Id);
            this.AgregarParametroEntrada(cmd, "@p_cantidad", DbType.Int32, lineaOrdenVenta.Cantidad);
            this.AgregarParametroEntrada(cmd, "@p_subTotal", DbType.Double, lineaOrdenVenta.SubTotal);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, lineaOrdenVenta.IsActive);
            this.AgregarParametroSalida(cmd, "@p_id", DbType.Int32);

            return cmd;
        }

        protected override DbCommand ComandoActualizar(DbConnection conn, LineaOrdenVenta lineaOrdenVenta) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarLineaOrdenVenta";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idOrdenVenta", DbType.Int32, lineaOrdenVenta.OrdenVenta.Id);
            this.AgregarParametroEntrada(cmd, "@p_idProducto", DbType.Int32, lineaOrdenVenta.Producto.Id);
            this.AgregarParametroEntrada(cmd, "@p_cantidad", DbType.Int32, lineaOrdenVenta.Cantidad);
            this.AgregarParametroEntrada(cmd, "@p_subTotal", DbType.Double, lineaOrdenVenta.IsActive);
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, lineaOrdenVenta.Id);

            return cmd;
        }

        protected override DbCommand ComandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarLineaOrdenVenta";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeer(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarLineaOrdenVentaPorId";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeerTodos(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarLineasOrdenVenta";
            cmd.CommandType = CommandType.StoredProcedure;
            return cmd;
        }

        protected override LineaOrdenVenta MapearModelo(DbDataReader reader) {
            return new LineaOrdenVenta {
                Id = Convert.ToInt32(reader["id"]),
                OrdenVenta = new OrdenVentaDAOImpl().Leer(Convert.ToInt32(reader["idOrdenVenta"])),
                Producto = new ProductoDAOImpl().Leer(Convert.ToInt32(reader["idProducto"])), 
                Cantidad = Convert.ToInt32(reader["cantidad"]), 
                SubTotal = Convert.ToDouble(reader["subTotal"]), 
                IsActive = Convert.ToBoolean(reader["activo"])
            };
        }

        protected DbCommand ComandoLeerTodosPorOrden(DbConnection conn, int idOrden, 
            DbTransaction transaccion = null) {

            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarLineasPorOrdenVenta";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_idOrdenVenta", DbType.Int32, idOrden);

            if (transaccion != null) {
                cmd.Transaction = transaccion;
            }

            return cmd;
        }

        public List<LineaOrdenVenta> LeerTodosPorOrden(int idOrden, DbTransaction transaccion = null) {
            DbConnection conn = transaccion?.Connection;
            if (conn == null) {
                return EjecutarComando(c => LeerTodosPorOrden(idOrden, c));
            }

            using (DbCommand cmd = this.ComandoLeerTodosPorOrden(conn, idOrden, transaccion))
            using (DbDataReader reader = cmd.ExecuteReader()) {
                List<LineaOrdenVenta> modelos = new List<LineaOrdenVenta>();
                while (reader.Read()) {
                    modelos.Add(this.MapearModelo(reader));
                }
                return modelos;
            }
        }

        private List<LineaOrdenVenta> LeerTodosPorOrden(int idOrden, DbConnection conn) {
            using (DbCommand cmd = this.ComandoLeerTodosPorOrden(conn, idOrden))
            using (DbDataReader reader = cmd.ExecuteReader()) {
                List<LineaOrdenVenta> modelos = new List<LineaOrdenVenta>();
                while (reader.Read()) {
                    modelos.Add(this.MapearModelo(reader));
                }
                return modelos;
            }
        }
    }
}
