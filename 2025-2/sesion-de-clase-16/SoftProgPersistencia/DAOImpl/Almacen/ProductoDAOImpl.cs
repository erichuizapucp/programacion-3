using System;
using System.Data;
using System.Data.Common;
using PUCP.SoftProg.Modelo.Almacen;
using PUCP.SoftProg.Persistencia.DAO.Almacen;

namespace PUCP.SoftProg.Persistencia.DAOImpl.Almacen {
    public class ProductoDAOImpl : BaseDAOImpl<Producto>, IProductoDAO {
        protected override DbCommand ComandoCrear(DbConnection conn, Producto producto) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarProducto";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_nombre", 
                DbType.String, producto.Nombre);
            this.AgregarParametroEntrada(cmd, "@p_unidadMedida", 
                DbType.String, producto.UnidadMedida);
            this.AgregarParametroEntrada(cmd, "@p_precio", 
                DbType.Double, producto.Precio);
            this.AgregarParametroEntrada(cmd, "@p_activo", 
                DbType.Boolean, producto.IsActive);
            this.AgregarParametroSalida(cmd, "@p_id", DbType.Int32);

            return cmd;
        }

        protected override DbCommand ComandoActualizar(DbConnection conn, Producto producto) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarProducto";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_nombre", DbType.String, producto.Nombre);
            this.AgregarParametroEntrada(cmd, "@p_unidadMedida", DbType.String, producto.UnidadMedida);
            this.AgregarParametroEntrada(cmd, "@p_precio", DbType.Double, producto.Precio);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, producto.IsActive);
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, producto.Id);

            return cmd;
        }

        protected override DbCommand ComandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarProducto";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeer(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarProductoPorId";
            cmd.CommandType = CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand ComandoLeerTodos(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarProductos";
            cmd.CommandType = CommandType.StoredProcedure;
            return cmd;
        }

        protected override Producto MapearModelo(DbDataReader reader) {
            return new Producto {
                Id = Convert.ToInt32(reader["id"]),
                Nombre = Convert.ToString(reader["nombre"]),
                UnidadMedida = (UnidadMedida)Enum.Parse(
                    typeof(UnidadMedida), 
                    Convert.ToString(reader["unidadMedida"])), 
                Precio = Convert.ToDouble(reader["precio"]), 
                IsActive = Convert.ToBoolean(reader["activo"])
            };
        }
    }
}
