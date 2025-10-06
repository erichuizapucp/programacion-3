using System;
using System.Collections.Generic;
using System.Data.Common;
using PUCP.SoftProg.Db;
using PUCP.SoftProg.Modelo;
using PUCP.SoftProg.Modelo.Ventas;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Persistencia.DAO.Ventas;
using PUCP.SoftProg.Persistencia.DAOImpl.Ventas;

namespace PUCP.SoftProg.Negocio.BOImpl {
    public class OrdenVentaBOImpl : IOrdenVentaBO {
        private readonly IOrdenVentaDAO ordenVentaDAO;
        private readonly ILineaOrdenVentaDAO lineaOrdenVentaDAO;

        public OrdenVentaBOImpl() {
            this.ordenVentaDAO = new OrdenVentaDAOImpl();
            this.lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
        }

        public void Guardar(OrdenVenta ordenVenta, Estado estado) {
            using (DbConnection conexion = DBFactoryProvider.GetManager().GetConnection()) {
                conexion.Open();
                DbTransaction transaction = conexion.BeginTransaction();
                try {
                    switch (estado) {
                        case Estado.Nuevo:
                            int idOrden = this.ordenVentaDAO.Crear(ordenVenta, transaction);
                            ordenVenta.Id = idOrden;

                            foreach (LineaOrdenVenta linea in ordenVenta.LineasOrdenVenta) {
                                linea.OrdenVenta = new OrdenVenta { Id = idOrden };
                                this.lineaOrdenVentaDAO.Crear(linea, transaction);
                            }
                            break;

                        case Estado.Modificado:
                            this.ordenVentaDAO.Actualizar(ordenVenta, transaction);

                            foreach (LineaOrdenVenta linea in ordenVenta.LineasOrdenVenta) {
                                if (linea.Id == 0) {
                                    linea.OrdenVenta = new OrdenVenta { Id = ordenVenta.Id };
                                    this.lineaOrdenVentaDAO.Crear(linea, transaction);
                                }
                                else {
                                    this.lineaOrdenVentaDAO.Actualizar(linea, transaction);
                                }
                            }
                            break;
                    }

                    transaction.Commit();
                }
                catch (Exception ex) {
                    Console.Error.WriteLine("Error al guardar la OrdenVenta: " + ex.Message);
                    transaction.Rollback();
                    throw;
                }
            }
        }

        public void Eliminar(int id) {
            using (DbConnection conexion = DBFactoryProvider.GetManager().GetConnection()) {
                conexion.Open();
                DbTransaction transaction = conexion.BeginTransaction();
                try {
                    List<LineaOrdenVenta> lineas = this.lineaOrdenVentaDAO.LeerTodosPorOrden(id, transaction);
                    foreach (LineaOrdenVenta linea in lineas) {
                        this.lineaOrdenVentaDAO.Eliminar(linea.Id, transaction);
                    }

                    if (!this.ordenVentaDAO.Eliminar(id, transaction)) {
                        throw new Exception("La OrdenVenta no se pudo eliminar.");
                    }

                    transaction.Commit();
                }
                catch (Exception ex) {
                    Console.Error.WriteLine("Error al eliminar la OrdenVenta: " + ex.Message);
                    transaction.Rollback();
                    throw;
                }
            }
        }

        public OrdenVenta Obtener(int id) {
            return this.ordenVentaDAO.Leer(id);
        }

        public List<OrdenVenta> Listar() {
            return this.ordenVentaDAO.LeerTodos();
        }
    }
}
