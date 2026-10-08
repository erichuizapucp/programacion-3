using SoftProg.AccessoDatos.DAO.Transacciones;
using SoftProg.DbManager;
using SoftProg.Modelo.Ventas;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl.Ventas {
    internal class LineaOrdenVentaDAOImpl : RegistroDAOImpl<LineaOrdenVenta>, ILineaOrdenVentaDAO {
        public void InsertLineas(int idOrden, IReadOnlyList<LineaOrdenVenta> lineasOrdenVenta) {
            DbConnection conn = TransactionsManager.GetConnection();

            foreach (LineaOrdenVenta lineaOrdenVenta in lineasOrdenVenta) {
                using DbCommand cmd = conn.CreateCommand();
                cmd.Transaction = TransactionsManager.GetTransaction();
                cmd.CommandType = CommandType.StoredProcedure;
                cmd.CommandText = "insertar_linea_orden_venta";
                cmd.AgregarParametroEntero("p_id_orden_venta", idOrden);
                cmd.AgregarParametroEntero("p_id_producto", lineaOrdenVenta.Producto.Id);
                cmd.AgregarParametroEntero("p_cantidad", lineaOrdenVenta.Cantidad);
                cmd.AgregarParametroDouble("p_sub_total", lineaOrdenVenta.SubTotal);
                cmd.AgregarParametroBoolean("p_activo", true);
                cmd.AgregarParametroSalidaEntero("p_id");

                cmd.ExecuteNonQuery();

                object? resultado = cmd.Parameters["p_id"].Value;
                if (resultado == null || resultado == DBNull.Value) {
                    throw new Exception("No se pudo insertar la linea de la orden de venta");
                }

                lineaOrdenVenta.Id = Convert.ToInt32(resultado);
            }
        }

        public void DeleteLineas(int idOrden) {
            DbConnection conn = TransactionsManager.GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.Transaction = TransactionsManager.GetTransaction();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "eliminar_lineas_por_orden_venta";
            cmd.AgregarParametroEntero("p_id_orden_venta", idOrden);
            cmd.ExecuteNonQuery();
        }

        public List<LineaOrdenVenta> FindByOrderId(int idOrden) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "listar_lineas_por_orden_venta";
            cmd.AgregarParametroEntero("p_id_orden_venta", idOrden);
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
            lineaOrdenVenta.SubTotal = Convert.ToDouble(reader.GetDecimal("sub_total"));
            return lineaOrdenVenta;
        }
    }
}
