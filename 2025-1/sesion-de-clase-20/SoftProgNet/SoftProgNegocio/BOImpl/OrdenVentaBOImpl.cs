using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Config;
using PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Ventas;
using PUCP.Edu.Pe.SoftProg.Negocio.BO;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.Logistica.Ventas;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.Logistica.Ventas;

namespace PUCP.Edu.Pe.SoftProg.Negocio.BOImpl {
    public class OrdenVentaBOImpl : IOrdenVentaBO {
        private readonly IOrdenVentaDAO ordenVentaDAO;
        private readonly ILineaOrdenVentaDAO lineaOrdenVentaDAO;

        public OrdenVentaBOImpl() {
            this.ordenVentaDAO = new OrdenVentaDAOImpl();
            this.lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
        }

        public void Guardar(OrdenVenta ordenVenta, Estado estado) {
            if (estado == Estado.Nuevo) {
                DbTransaction transaction = null;
                using (DbConnection conexion = DBManager.GetInstance().GetConnection()) {
                    try {
                        transaction = conexion.BeginTransaction();

                        int idOrden = this.ordenVentaDAO.Insertar(ordenVenta, conexion, transaction);

                        foreach (LineaOrdenVenta linea in ordenVenta.LineasOrdenVenta) {
                            linea.OrdenVenta = new OrdenVenta {
                                Id = idOrden,
                            };
                            this.lineaOrdenVentaDAO.Insertar(linea, conexion, transaction);
                        }

                        transaction.Commit();
                    }
                    catch (Exception) {
                        transaction.Rollback();
                    }
                }
            }
            else {
                DbTransaction transaction = null;
                using (DbConnection conexion = DBManager.GetInstance().GetConnection()) {
                    try {
                        transaction = conexion.BeginTransaction();

                        this.ordenVentaDAO.Modificar(ordenVenta);

                        foreach (LineaOrdenVenta linea in ordenVenta.LineasOrdenVenta) {
                            this.lineaOrdenVentaDAO.Modificar(linea);
                        }

                        transaction.Commit();
                    }
                    catch (Exception) {
                        transaction.Rollback();
                    }
                }
            }
        }

        public void Eliminar(int id) {
            DbTransaction transaction = null;
            using (DbConnection conexion = DBManager.GetInstance().GetConnection()) {
                try {
                    transaction = conexion.BeginTransaction();

                    OrdenVenta ordenVenta = this.ordenVentaDAO.Buscar(id);

                    foreach (LineaOrdenVenta linea in ordenVenta.LineasOrdenVenta) {
                        this.lineaOrdenVentaDAO.Eliminar(linea.Id);
                    }

                    this.ordenVentaDAO.Eliminar(ordenVenta.Id);

                    transaction.Commit();
                }
                catch (Exception e) {
                    transaction.Rollback();
                }
            }
        }

        public OrdenVenta Obtener(int id) {
            return this.ordenVentaDAO.Buscar(id);
        }

        public List<OrdenVenta> Listar() {
            return this.ordenVentaDAO.Listar();
        }
    }
}
