using SoftProg.AccessoDatos.DAO.Transacciones;
using SoftProg.DbManager;
using SoftProg.Modelo.Ventas;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl.Ventas {
    internal class LineaOrdenVentaDAOImpl : RegistroDAOImpl<LineaOrdenVenta>, ILineaOrdenVentaDAO {
        public void InsertLineas(int idOrden, IReadOnlyList<LineaOrdenVenta> lineasOrdenVenta) {
            string sql =
                """
                INSERT INTO linea_orden_venta(id_orden_venta, id_producto, cantidad, sub_total, activo)
                VALUES (@id_orden_venta, @id_producto, @cantidad, @sub_total, @activo);
                SELECT LAST_INSERT_ID();
                """;

            DbConnection conn = TransactionsManager.GetConnection();

            foreach (LineaOrdenVenta lineaOrdenVenta in lineasOrdenVenta) {
                using DbCommand cmd = conn.CreateCommand();
                cmd.Transaction = TransactionsManager.GetTransaction();
                cmd.CommandText = sql;
                cmd.AgregarParametroEntero("id_orden_venta", idOrden);
                cmd.AgregarParametroEntero("id_producto", lineaOrdenVenta.Producto.Id);
                cmd.AgregarParametroEntero("cantidad", lineaOrdenVenta.Cantidad);
                cmd.AgregarParametroDouble("sub_total", lineaOrdenVenta.SubTotal);
                cmd.AgregarParametroBoolean("activo", true);

                object? resultado = cmd.ExecuteScalar();
                if (resultado == null || resultado == DBNull.Value) {
                    throw new Exception("No se pudo insertar la linea de la orden de venta");
                }

                lineaOrdenVenta.Id = Convert.ToInt32(resultado);
            }
        }

        public void DeleteLineas(int idOrden) {
            string sql =
                """
                DELETE FROM linea_orden_venta WHERE id_orden_venta = @id_orden_venta
                """;

            DbConnection conn = TransactionsManager.GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.Transaction = TransactionsManager.GetTransaction();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id_orden_venta", idOrden);
            cmd.ExecuteNonQuery();
        }

        public List<LineaOrdenVenta> FindByOrderId(int idOrden) {
            string sql =
                """
                SELECT id, id_orden_venta, id_producto, cantidad, sub_total, activo
                FROM linea_orden_venta
                WHERE
                    id_orden_venta = @id_orden_venta
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id_orden_venta", idOrden);
            using DbDataReader reader = cmd.ExecuteReader();

            List<LineaOrdenVenta> lineasOrdenVenta = [];
            while (reader.Read()) {
                lineasOrdenVenta.Add(Mapear(reader, new LineaOrdenVenta()));
            }

            return lineasOrdenVenta;
        }

        protected override LineaOrdenVenta Mapear(DbDataReader reader, LineaOrdenVenta lineaOrdenVenta) {
            base.Mapear(reader, lineaOrdenVenta);
            lineaOrdenVenta.Producto = new ProductoDAOImpl().FindById(reader.GetInt32("id_producto"))!;
            lineaOrdenVenta.Cantidad = reader.GetInt32("cantidad");
            lineaOrdenVenta.SubTotal = reader.GetDouble("sub_total");
            return lineaOrdenVenta;
        }
    }
}
