using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Common;
using PUCP.Edu.Pe.SoftProg.Config;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl {
    public abstract class BaseDAOImpl<T> : ICrud<T> {
        protected abstract DbCommand CommandoInsertar(DbConnection conn, T modelo);
        protected abstract DbCommand CommandoInsertar(DbConnection conn, DbTransaction transaction, T modelo);
        protected abstract DbCommand CommandoModificar(DbConnection conn, T modelo);
        protected abstract DbCommand CommandoEliminar(DbConnection conn, int id);
        protected abstract DbCommand CommandoBuscar(DbConnection conn, int id);
        protected abstract DbCommand CommandoListar(DbConnection conn);
        protected abstract T mapearModelo(DbDataReader reader);

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

        public int Insertar(T modelo) {
            try {
                using (DbConnection conn = DBManager.GetInstance().GetConnection())
                using (DbCommand cmd = this.CommandoInsertar(conn, modelo)) {
                    if (cmd.ExecuteNonQuery() == 0) {
                        Console.Error.WriteLine("El registro no se inserto");
                        return -1;
                    }
                    return (int)cmd.Parameters["@p_id"].Value;
                }
            }
            catch (Exception e) {
                Console.Error.WriteLine("Error SQL Durante la insercion: " + e.Message);
                throw new InvalidOperationException("No se pudo insertar el registro.", e);
            }
        }

        public int Insertar(T modelo, DbConnection conn, DbTransaction transaction) {
            try {
                using (DbCommand cmd = this.CommandoInsertar(conn, transaction, modelo)) {
                    if (cmd.ExecuteNonQuery() == 0) {
                        Console.Error.WriteLine("El registro no se inserto");
                        return -1;
                    }
                    return (int)cmd.Parameters["@p_id"].Value;
                }
            }
            catch (Exception e) {
                Console.Error.WriteLine("Error SQL Durante la insercion: " + e.Message);
                throw new InvalidOperationException("No se pudo insertar el registro.", e);
            }
        }

        public bool Modificar(T modelo) {
            try {
                using (DbConnection conn = DBManager.GetInstance().GetConnection())
                using (DbCommand cmd = this.CommandoModificar(conn, modelo)) {
                    return cmd.ExecuteNonQuery() > 0;
                }
            }
            catch (Exception e) {
                Console.Error.WriteLine("Error SQL Durante la modificacion: " + e.Message);
                throw new InvalidOperationException("No se pudo modificar el registro.", e);
            }
        }

        public bool Eliminar(int id) {
            try {
                using (DbConnection conn = DBManager.GetInstance().GetConnection())
                using (DbCommand cmd = this.CommandoEliminar(conn, id)) {
                    return cmd.ExecuteNonQuery() > 0;
                }
            }
            catch (Exception e) {
                Console.Error.WriteLine("Error SQL Durante la eliminacion: " + e.Message);
                throw new InvalidOperationException("No se pudo eliminar el registro.", e);
            }
        }

        public T Buscar(int id) {
            try {
                using (DbConnection conn = DBManager.GetInstance().GetConnection())
                using (DbCommand cmd = this.CommandoBuscar(conn, id)) 
                using (DbDataReader reader = cmd.ExecuteReader()) { 
                    
                    if (!reader.HasRows) {
                        Console.Error.WriteLine("No se encontro el registro con id: " + id);
                        return default;
                    }

                    reader.Read();
                    return this.mapearModelo(reader);
                }
            }
            catch (Exception e) {
                Console.Error.WriteLine("Error SQL Durante la busueda: " + e.Message);
                throw new InvalidOperationException("No se pudo buscar el registro.", e);
            }
        }

        public List<T> Listar() {
            try {
                using (DbConnection conn = DBManager.GetInstance().GetConnection())
                using (DbCommand cmd = this.CommandoListar(conn))
                using (DbDataReader reader = cmd.ExecuteReader()) {
                    List<T> modelos = new List<T>();

                    while (reader.Read()) { 
                        modelos.Add(this.mapearModelo(reader));
                    }

                    return modelos;
                }
            }
            catch (Exception e) {
                Console.Error.WriteLine("Error SQL Durante la busueda: " + e.Message);
                throw new InvalidOperationException("No se pudo buscar el registro.", e);
            }
        }
    }
}
