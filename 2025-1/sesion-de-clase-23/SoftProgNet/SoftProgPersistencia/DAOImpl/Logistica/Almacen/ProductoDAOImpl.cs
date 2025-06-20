using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.Clientes;
using PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Almacen;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.Logistica.Almacen;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.Logistica.Almacen {
    public class ProductoDAOImpl : BaseDAOImpl<Producto>, IProductoDAO {
        protected override DbCommand CommandoInsertar(DbConnection conn, Producto producto) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "insertarProducto";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_nombre", DbType.String, producto.Nombre);
            this.AgregarParametroEntrada(cmd, "@p_unidadMedida", DbType.String, producto.UnidadMedida);
            this.AgregarParametroEntrada(cmd, "@p_precio", DbType.Double, producto.Precio);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, producto.IsActive);
            this.AgregarParametroSalida(cmd, "@p_id", DbType.Int32);

            return cmd;
        }

        protected override DbCommand CommandoInsertar(DbConnection conn, DbTransaction transaccion, Producto producto) {
            DbCommand cmd = this.CommandoInsertar(conn, producto);
            cmd.Transaction = transaccion;

            return cmd;
        }

        protected override DbCommand CommandoModificar(DbConnection conn, Producto producto) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "modificarProducto";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_nombre", DbType.String, producto.Nombre);
            this.AgregarParametroEntrada(cmd, "@p_unidadMedida", DbType.String, producto.UnidadMedida);
            this.AgregarParametroEntrada(cmd, "@p_precio", DbType.Double, producto.Precio);
            this.AgregarParametroEntrada(cmd, "@p_activo", DbType.Boolean, producto.IsActive);
            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, producto.Id);

            return cmd;
        }

        protected override DbCommand CommandoEliminar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "eliminarProducto";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoBuscar(DbConnection conn, int id) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "buscarProductoPorId";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;

            this.AgregarParametroEntrada(cmd, "@p_id", DbType.Int32, id);

            return cmd;
        }

        protected override DbCommand CommandoListar(DbConnection conn) {
            DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = "listarProductos";
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            return cmd;
        }

        protected override Producto mapearModelo(DbDataReader reader) {
            return new Producto {
                Id = Convert.ToInt32(reader["id"]),
                Nombre = Convert.ToString(reader["nombre"]),
                UnidadMedida = Convert.ToString(reader["unidadMedida"]), 
                Precio = Convert.ToDouble(reader["precio"]), 
                IsActive = Convert.ToBoolean(reader["activo"])
            };
        }
    }
}
