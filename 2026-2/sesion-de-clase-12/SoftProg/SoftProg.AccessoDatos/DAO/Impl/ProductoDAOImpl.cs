using SoftProg.DbManager;
using SoftProg.Modelo.Almacen;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl {
    public class ProductoDAOImpl : RegistroDAOImpl<Producto>, IProductoDAO {
        public List<Producto> FindAll() {
            string sql =
                """
                SELECT id, nombre, unidad_medida, precio, activo FROM producto
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            using DbDataReader reader = cmd.ExecuteReader();

            List<Producto> productos = [];
            while (reader.Read()) {
                productos.Add(Mapear(reader, new Producto()));
            }

            return productos;
        }

        public Producto? FindById(int id) {
            string sql =
                """
                SELECT id, nombre, unidad_medida, precio, activo
                FROM producto
                WHERE
                    id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id", id);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Producto()) : null;
        }

        public Producto? FindByName(string nombre) {
            string sql =
                """
                SELECT id, nombre, unidad_medida, precio, activo
                FROM producto
                WHERE
                    nombre = @nombre
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroCadena("nombre", nombre);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Producto()) : null;
        }

        public void Insert(Producto producto) {
            string sql =
                """
                INSERT INTO producto(nombre, unidad_medida, precio, activo)
                VALUES (@nombre, @unidad_medida, @precio, @activo);
                SELECT LAST_INSERT_ID();
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroCadena("nombre", producto.Nombre);
            cmd.AgregarParametroCadena("unidad_medida", producto.UnidadMedida.ToString());
            cmd.AgregarParametroDouble("precio", producto.Precio);
            cmd.AgregarParametroBoolean("activo", producto.IsActivo);

            object? resultado = cmd.ExecuteScalar();
            if (resultado == null || resultado == DBNull.Value) {
                throw new Exception("No se pudo insertar el producto");
            }

            producto.Id = Convert.ToInt32(resultado);
        }

        public void Update(Producto producto) {
            string sql =
                """
                UPDATE producto
                SET
                    nombre = @nombre,
                    unidad_medida = @unidad_medida,
                    precio = @precio,
                    activo = @activo
                WHERE
                    id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroCadena("nombre", producto.Nombre);
            cmd.AgregarParametroCadena("unidad_medida", producto.UnidadMedida.ToString());
            cmd.AgregarParametroDouble("precio", producto.Precio);
            cmd.AgregarParametroBoolean("activo", producto.IsActivo);
            cmd.AgregarParametroEntero("id", producto.Id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo actualizar el producto");
            }
        }

        public void Delete(int id) {
            string sql =
                """
                DELETE FROM producto WHERE id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id", id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo eliminar el producto");
            }
        }

        protected override Producto Mapear(DbDataReader reader, Producto producto) {
            base.Mapear(reader, producto);
            producto.Nombre = reader.GetString("nombre");
            producto.UnidadMedida = Enum.Parse<UnidadMedida>(reader.GetString("unidad_medida"));
            producto.Precio = reader.GetDouble("precio");
            return producto;
        }
    }
}
