using SoftProg.AccessoDatos.DAO.Transacciones;
using SoftProg.DbManager;
using SoftProg.Modelo.Ventas;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl.Ventas {
    public class OrdenVentaDAOImpl : RegistroDAOImpl<OrdenVenta>, IOrdenVentaDAO {
        public List<OrdenVenta> FindAll() {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "listar_ordenes_venta";
            using DbDataReader reader = cmd.ExecuteReader();

            List<OrdenVenta> ordenes = [];
            while (reader.Read()) {
                ordenes.Add(Mapear(reader, new OrdenVenta()));
            }

            return ordenes;
        }

        public OrdenVenta? FindById(int id) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "buscar_orden_venta_por_id";
            cmd.AgregarParametroEntero("p_id", id);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new OrdenVenta()) : null;
        }

        public void Insert(OrdenVenta ordenVenta) {
            DbConnection conn = TransactionsManager.GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.Transaction = TransactionsManager.GetTransaction();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "insertar_orden_venta";
            cmd.AgregarParametroEntero("p_id_cliente", ordenVenta.Cliente?.Id);
            cmd.AgregarParametroEntero("p_id_empleado", ordenVenta.Empleado?.Id);
            cmd.AgregarParametroDouble("p_total", ordenVenta.Total);
            cmd.AgregarParametroBoolean("p_activo", ordenVenta.IsActivo);
            cmd.AgregarParametroSalidaEntero("p_id");

            cmd.ExecuteNonQuery();

            object? resultado = cmd.Parameters["p_id"].Value;
            if (resultado == null || resultado == DBNull.Value) {
                throw new Exception("No se pudo insertar la orden de venta");
            }

            ordenVenta.Id = Convert.ToInt32(resultado);

            ILineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
            lineaOrdenVentaDAO.InsertLineas(ordenVenta.Id, ordenVenta.Lineas);
        }

        public void Update(OrdenVenta ordenVenta) {
            DbConnection conn = TransactionsManager.GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.Transaction = TransactionsManager.GetTransaction();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "modificar_orden_venta";
            cmd.AgregarParametroEntero("p_id_cliente", ordenVenta.Cliente?.Id);
            cmd.AgregarParametroEntero("p_id_empleado", ordenVenta.Empleado?.Id);
            cmd.AgregarParametroDouble("p_total", ordenVenta.Total);
            cmd.AgregarParametroBoolean("p_activo", ordenVenta.IsActivo);
            cmd.AgregarParametroEntero("p_id", ordenVenta.Id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo actualizar la orden de venta");
            }

            ILineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
            lineaOrdenVentaDAO.DeleteLineas(ordenVenta.Id);
            lineaOrdenVentaDAO.InsertLineas(ordenVenta.Id, ordenVenta.Lineas);
        }

        public void Delete(int id) {
            ILineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
            lineaOrdenVentaDAO.DeleteLineas(id);

            DbConnection conn = TransactionsManager.GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.Transaction = TransactionsManager.GetTransaction();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "eliminar_orden_venta";
            cmd.AgregarParametroEntero("p_id", id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo eliminar la orden de venta");
            }
        }

        protected override OrdenVenta Mapear(DbDataReader reader, OrdenVenta ordenVenta) {
            base.Mapear(reader, ordenVenta);
            MapearCliente(reader, ordenVenta);
            MapearEmpleado(reader, ordenVenta);
            ordenVenta.Total = Convert.ToDouble(reader.GetDecimal("total"));
            MapearLineas(reader, ordenVenta);
            return ordenVenta;
        }

        private void MapearCliente(DbDataReader reader, OrdenVenta ordenVenta) {
            if (!reader.IsDBNull("id_cliente")) {
                ordenVenta.Cliente = new ClienteDAOImpl().FindById(reader.GetInt32("id_cliente"));
            }
            else {
                ordenVenta.Cliente = null;
            }
        }

        private void MapearEmpleado(DbDataReader reader, OrdenVenta ordenVenta) {
            if (!reader.IsDBNull("id_empleado")) {
                ordenVenta.Empleado = new EmpleadoDAOImpl().FindById(reader.GetInt32("id_empleado"));
            }
            else {
                ordenVenta.Empleado = null;
            }
        }

        private void MapearLineas(DbDataReader reader, OrdenVenta ordenVenta) {
            ILineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
            ordenVenta.Lineas = lineaOrdenVentaDAO.FindByOrderId(reader.GetInt32("id"));
        }
    }
}
