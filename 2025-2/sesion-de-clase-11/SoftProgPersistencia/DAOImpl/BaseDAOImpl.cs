using PUCP.SoftProg.Db;
using PUCP.SoftProg.Persistencia.DAO;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;

namespace PUCP.SoftProg.Persistencia.DAOImpl {
    public abstract class BaseDAOImpl<T> : IPersistible<T, int> {
        protected abstract DbCommand ComandoCrear(DbConnection conn, T modelo);
        protected abstract DbCommand ComandoActualizar(DbConnection conn, T modelo);
        protected abstract DbCommand ComandoEliminar(DbConnection conn, int id);
        protected abstract DbCommand ComandoLeer(DbConnection conn, int id);
        protected abstract DbCommand ComandoLeerTodos(DbConnection conn);
        protected abstract T MapearModelo(DbDataReader reader);

        protected void AgregarParametroEntrada(DbCommand cmd, string nombre, DbType tipo, object dato) {
            DbParameter param = cmd.CreateParameter();
            param.ParameterName = nombre;
            param.DbType = tipo;
            param.Value = dato;
            param.Direction = ParameterDirection.Input;
            cmd.Parameters.Add(param);
        }

        protected void AgregarParametroSalida(DbCommand cmd, string nombre, DbType tipo) {
            DbParameter param = cmd.CreateParameter();
            param.ParameterName = nombre;
            param.DbType = tipo;
            param.Direction = ParameterDirection.Output;
            cmd.Parameters.Add(param);
        }

        protected R EjecutarComando<R>(Func<DbConnection, R> comando) {
            DBManager dBManager = DBFactoryProvider.GetManager();
            using (DbConnection conn = dBManager.GetConnection()) {
                try {
                    return comando(conn);
                }
                catch (DbException e) {
                    Console.Error.WriteLine("Error SQL: " + e.Message);
                    throw new Exception("Error SQL", e);
                }
                catch (Exception e) {
                    Console.Error.WriteLine("Error inesperado: " + e.Message);
                    throw;
                }
            }
        }

        protected int EjecutarComandoCrear(DbConnection conn, T modelo) {
            using (DbCommand cmd = this.ComandoCrear(conn, modelo)) {
                if (cmd.ExecuteNonQuery() == 0) {
                    Console.Error.WriteLine("El registro no se inserto");
                    return -1;
                }

                return (int)cmd.Parameters["@p_id"].Value;
            }
        }

        protected bool EjecutarComandoActualizar(DbConnection conn, T modelo) {
            using (DbCommand cmd = this.ComandoActualizar(conn, modelo)) {
                return cmd.ExecuteNonQuery() > 0;
            }
        }

        protected bool EjecutarComandoEliminar(DbConnection conn, int id) {
            using (DbCommand cmd = this.ComandoEliminar(conn, id)) {
                return cmd.ExecuteNonQuery() > 0;
            }
        }

        public int Crear(T modelo) {
            return EjecutarComando(conn => EjecutarComandoCrear(conn, modelo));
        }

        
        public bool Actualizar(T modelo) {
            return EjecutarComando(conn => EjecutarComandoActualizar(conn, modelo));
        }

        public bool Eliminar(int id) {
            return EjecutarComando(conn => EjecutarComandoEliminar(conn, id));
        }

        public T Leer(int id) {
            return EjecutarComando(conn => {
                using (DbCommand cmd = this.ComandoLeer(conn, id))
                using (DbDataReader reader = cmd.ExecuteReader()) {

                    if (!reader.HasRows) {
                        Console.Error.WriteLine("No se encontro el registro con id: " + id);
                        return default;
                    }

                    reader.Read();
                    return this.MapearModelo(reader);
                }
            });
        }

        public List<T> LeerTodos() {
            return EjecutarComando(conn => {
                using (DbCommand cmd = this.ComandoLeerTodos(conn))
                using (DbDataReader reader = cmd.ExecuteReader()) {

                    List<T> modelos = new List<T>();

                    while (reader.Read()) {
                        modelos.Add(this.MapearModelo(reader));
                    }

                    return modelos;
                }
            });
        }
    }
}
