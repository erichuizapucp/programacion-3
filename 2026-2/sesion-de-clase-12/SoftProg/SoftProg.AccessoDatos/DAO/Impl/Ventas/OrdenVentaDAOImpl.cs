using SoftProg.AccessoDatos.DAO.Transacciones;
using SoftProg.DbManager;
using SoftProg.Modelo.Ventas;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl.Ventas {
    public class OrdenVentaDAOImpl : RegistroDAOImpl<OrdenVenta>, IOrdenVentaDAO {
        public List<OrdenVenta> FindAll() {
            string sql =
                """
                SELECT id, id_cliente, id_empleado, total, activo FROM orden_venta
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            using DbDataReader reader = cmd.ExecuteReader();

            List<OrdenVenta> ordenes = [];
            while (reader.Read()) {
                ordenes.Add(Mapear(reader, new OrdenVenta()));
            }

            return ordenes;
        }

        public OrdenVenta? FindById(int id) {
            string sql =
                """
                SELECT id, id_cliente, id_empleado, total, activo
                FROM orden_venta
                WHERE
                    id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id", id);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new OrdenVenta()) : null;
        }

        public void Insert(OrdenVenta ordenVenta) {
            string sql =
                """
                INSERT INTO orden_venta(id_cliente, id_empleado, total, activo)
                VALUES (@id_cliente, @id_empleado, @total, @activo);
                SELECT LAST_INSERT_ID();
                """;

            DbConnection conn = TransactionsManager.GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.Transaction = TransactionsManager.GetTransaction();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id_cliente", ordenVenta.Cliente?.Id);
            cmd.AgregarParametroEntero("id_empleado", ordenVenta.Empleado?.Id);
            cmd.AgregarParametroDouble("total", ordenVenta.Total);
            cmd.AgregarParametroBoolean("activo", ordenVenta.IsActivo);

            object? resultado = cmd.ExecuteScalar();
            if (resultado == null || resultado == DBNull.Value) {
                throw new Exception("No se pudo insertar la orden de venta");
            }

            ordenVenta.Id = Convert.ToInt32(resultado);

            ILineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
            lineaOrdenVentaDAO.InsertLineas(ordenVenta.Id, ordenVenta.Lineas);
        }

        public void Update(OrdenVenta ordenVenta) {
            string sql =
                """
                UPDATE orden_venta
                SET
                    id_cliente = @id_cliente,
                    id_empleado = @id_empleado,
                    total = @total,
                    activo = @activo
                WHERE
                    id = @id
                """;

            DbConnection conn = TransactionsManager.GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.Transaction = TransactionsManager.GetTransaction();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id_cliente", ordenVenta.Cliente?.Id);
            cmd.AgregarParametroEntero("id_empleado", ordenVenta.Empleado?.Id);
            cmd.AgregarParametroDouble("total", ordenVenta.Total);
            cmd.AgregarParametroBoolean("activo", ordenVenta.IsActivo);
            cmd.AgregarParametroEntero("id", ordenVenta.Id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo actualizar la orden de venta");
            }

            ILineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
            lineaOrdenVentaDAO.DeleteLineas(ordenVenta.Id);
            lineaOrdenVentaDAO.InsertLineas(ordenVenta.Id, ordenVenta.Lineas);
        }

        public void Delete(int id) {
            string sql =
                """
                DELETE FROM orden_venta WHERE id = @id
                """;

            ILineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
            lineaOrdenVentaDAO.DeleteLineas(id);

            DbConnection conn = TransactionsManager.GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.Transaction = TransactionsManager.GetTransaction();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id", id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo eliminar la orden de venta");
            }
        }

        protected override OrdenVenta Mapear(DbDataReader reader, OrdenVenta ordenVenta) {
            base.Mapear(reader, ordenVenta);
            MapearCliente(reader, ordenVenta);
            MapearEmpleado(reader, ordenVenta);
            ordenVenta.Total = reader.GetDouble("total");
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
