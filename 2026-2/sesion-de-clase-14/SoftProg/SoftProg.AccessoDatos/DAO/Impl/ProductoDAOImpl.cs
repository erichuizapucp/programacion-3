using SoftProg.DbManager;
using SoftProg.Modelo.Almacen;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl {
    public class ProductoDAOImpl : RegistroDAOImpl<Producto>, IProductoDAO {
        public List<Producto> FindAll() {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "listar_productos";
            using DbDataReader reader = cmd.ExecuteReader();

            List<Producto> productos = [];
            while (reader.Read()) {
                productos.Add(Mapear(reader, new Producto()));
            }

            return productos;
        }

        public Producto? FindById(int id) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "buscar_producto_por_id";
            cmd.AgregarParametroEntero("p_id", id);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Producto()) : null;
        }

        public Producto? FindByName(string nombre) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "buscar_producto_por_nombre";
            cmd.AgregarParametroCadena("p_nombre", nombre);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Producto()) : null;
        }

        public void Insert(Producto producto) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "insertar_producto";
            cmd.AgregarParametroCadena("p_nombre", producto.Nombre);
            cmd.AgregarParametroCadena("p_unidad_medida", producto.UnidadMedida.ToString());
            cmd.AgregarParametroDouble("p_precio", producto.Precio);
            cmd.AgregarParametroBoolean("p_activo", producto.IsActivo);
            cmd.AgregarParametroSalidaEntero("p_id");

            cmd.ExecuteNonQuery();

            object? resultado = cmd.Parameters["p_id"].Value;
            if (resultado == null || resultado == DBNull.Value) {
                throw new Exception("No se pudo insertar el producto");
            }

            producto.Id = Convert.ToInt32(resultado);
        }

        public void Update(Producto producto) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "modificar_producto";
            cmd.AgregarParametroCadena("p_nombre", producto.Nombre);
            cmd.AgregarParametroCadena("p_unidad_medida", producto.UnidadMedida.ToString());
            cmd.AgregarParametroDouble("p_precio", producto.Precio);
            cmd.AgregarParametroBoolean("p_activo", producto.IsActivo);
            cmd.AgregarParametroEntero("p_id", producto.Id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo actualizar el producto");
            }
        }

        public void Delete(int id) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "eliminar_producto";
            cmd.AgregarParametroEntero("p_id", id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo eliminar el producto");
            }
        }

        protected override Producto Mapear(DbDataReader reader, Producto producto) {
            base.Mapear(reader, producto);
            producto.Nombre = reader.GetString("nombre");
            producto.UnidadMedida = Enum.Parse<UnidadMedida>(reader.GetString("unidad_medida"));
            producto.Precio = Convert.ToDouble(reader.GetDecimal("precio"));
            return producto;
        }
    }
}
