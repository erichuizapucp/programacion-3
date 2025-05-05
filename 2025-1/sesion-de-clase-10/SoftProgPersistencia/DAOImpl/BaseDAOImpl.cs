using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using PUCP.Edu.Pe.SoftProg.Config;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl {
    public abstract class BaseDAOImpl<T> : ICrud<T> {
        protected abstract SqlCommand CommandoInsertar(
            SqlConnection conn, T modelo);
        protected abstract SqlCommand CommandoModificar(
            SqlConnection conn, T modelo);
        protected abstract SqlCommand CommandoEliminar(
            SqlConnection conn, int id);
        protected abstract SqlCommand CommandoBuscar(
            SqlConnection conn, int id);
        protected abstract SqlCommand CommandoListar(SqlConnection conn);
        protected abstract T mapearModelo(SqlDataReader reader);

        public int Insertar(T modelo) {
            try {
                using (SqlConnection conn = 
                    DBManager.GetInstance().GetConnection())
                using (SqlCommand cmd = this.CommandoInsertar(conn, modelo)) {
                    if (cmd.ExecuteNonQuery() == 0) {
                        Console.Error.WriteLine("El registro no se inserto");
                        return -1;
                    }
                    return (int)cmd.Parameters["p_id"].Value;
                }
            }
            catch (SqlException e) {
                Console.Error.WriteLine("Error SQL Durante la insercion: " + e.Message);
                throw new InvalidOperationException("No se pudo insertar el registro.", e);
            }
            catch (Exception e) { 
                Console.Error.Write("Error inesperado: " + e.Message);
                throw new InvalidOperationException("Error inesperado al insertar el registro.", e);
            }
        }

        public bool Modificar(T modelo) {
            try {
                using (SqlConnection conn = 
                    DBManager.GetInstance().GetConnection())
                using (SqlCommand cmd = this.CommandoModificar(conn, modelo)) {
                    return cmd.ExecuteNonQuery() > 0;
                }
            }
            catch (SqlException e) {
                Console.Error.WriteLine("Error SQL Durante la modificacion: " + e.Message);
                throw new InvalidOperationException("No se pudo modificar el registro.", e);
            }
            catch (Exception e) {
                Console.Error.Write("Error inesperado: " + e.Message);
                throw new InvalidOperationException("Error inesperado al modificar el registro.", e);
            }
        }

        public bool Eliminar(int id) {
            try {
                using (SqlConnection conn = DBManager.GetInstance().GetConnection())
                using (SqlCommand cmd = this.CommandoEliminar(conn, id)) {
                    return cmd.ExecuteNonQuery() > 0;
                }
            }
            catch (SqlException e) {
                Console.Error.WriteLine("Error SQL Durante la eliminacion: " + e.Message);
                throw new InvalidOperationException("No se pudo eliminar el registro.", e);
            }
            catch (Exception e) {
                Console.Error.Write("Error inesperado: " + e.Message);
                throw new InvalidOperationException("Error inesperado al eliminar el registro.", e);
            }
        }

        public T Buscar(int id) {
            try {
                using (SqlConnection conn = DBManager.GetInstance().GetConnection())
                using (SqlCommand cmd = this.CommandoBuscar(conn, id)) 
                using (SqlDataReader reader = cmd.ExecuteReader()) { 
                    if (!reader.HasRows) {
                        Console.Error.WriteLine("No se encontro el registro con id: " + id);
                        return default;
                    }

                    reader.Read();
                    return this.mapearModelo(reader);
                }
            }
            catch (SqlException e) {
                Console.Error.WriteLine("Error SQL Durante la busueda: " + e.Message);
                throw new InvalidOperationException("No se pudo buscar el registro.", e);
            }
            catch (Exception e) {
                Console.Error.Write("Error inesperado: " + e.Message);
                throw new InvalidOperationException("Error inesperado al buscar el registro.", e);
            }
        }

        public List<T> Listar() {
            try {
                using (SqlConnection conn = 
                    DBManager.GetInstance().GetConnection())
                using (SqlCommand cmd = this.CommandoListar(conn))
                using (SqlDataReader reader = cmd.ExecuteReader()) {
                    List<T> modelos = new List<T>();

                    while (reader.Read()) { 
                        modelos.Add(this.mapearModelo(reader));
                    }

                    return modelos;
                }
            }
            catch (SqlException e) {
                Console.Error.WriteLine("Error SQL Durante la busueda: " + e.Message);
                throw new InvalidOperationException("No se pudo buscar el registro.", e);
            }
            catch (Exception e) {
                Console.Error.Write("Error inesperado: " + e.Message);
                throw new InvalidOperationException("Error inesperado al buscar el registro.", e);
            }
        }
    }
}
