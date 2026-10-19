using SoftProg.DbManager;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Transacciones {
    public static class TransactionsManager {
        private static readonly ThreadLocal<DbConnection?> Conexion = new();
        private static readonly ThreadLocal<DbTransaction?> Transaccion = new();

        public static void Iniciar() {
            if (Activa()) {
                throw new InvalidOperationException("Ya existe una transaccion activa en este hilo");
            }

            DbConnection conn = DBManager.GetInstance().GetConnection();
            try {
                Transaccion.Value = conn.BeginTransaction();
                Conexion.Value = conn;
            } catch (Exception ex) {
                conn.Dispose();
                throw new Exception("No se pudo iniciar la transaccion", ex);
            }
        }

        public static void Commit() {
            DbTransaction transaccion = GetTransaction();
            try {
                transaccion.Commit();
            } catch (Exception ex) {
                try { transaccion.Rollback(); } catch { }
                throw new Exception("No se pudo confirmar la transaccion", ex);
            } finally {
                Cerrar();
            }
        }

        public static void Rollback() {
            if (!Activa()) {
                return;
            }

            DbTransaction transaccion = GetTransaction();
            try {
                transaccion.Rollback();
            } catch (Exception ex) {
                throw new Exception("No se pudo revertir la transaccion", ex);
            } finally {
                Cerrar();
            }
        }

        public static DbConnection GetConnection() {
            DbConnection? conn = Conexion.Value;
            if (conn == null) {
                throw new InvalidOperationException("No hay una transaccion activa en este hilo");
            }
            return conn;
        }

        public static DbTransaction GetTransaction() {
            DbTransaction? transaccion = Transaccion.Value;
            if (transaccion == null) {
                throw new InvalidOperationException("No hay una transaccion activa en este hilo");
            }
            return transaccion;
        }

        public static bool Activa() {
            return Transaccion.Value != null;
        }

        private static void Cerrar() {
            DbTransaction? transaccion = Transaccion.Value;
            DbConnection? conn = Conexion.Value;
            Transaccion.Value = null;
            Conexion.Value = null;

            transaccion?.Dispose();
            conn?.Dispose();
        }
    }
}
