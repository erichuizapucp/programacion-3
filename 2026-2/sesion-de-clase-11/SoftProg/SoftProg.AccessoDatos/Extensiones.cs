using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos {
    internal static class Extensiones {
        public static void AgregarParametroCadena(
            this DbCommand cmd, 
            string nombre, 
            string value) {

            AgregarParametro(cmd, nombre, value, DbType.String, 
                ParameterDirection.Input);
        }

        public static void AgregarParametroEntero(
            this DbCommand cmd, 
            string nombre, 
            int? value) {

            AgregarParametro(cmd, nombre, value, 
                DbType.Int32, ParameterDirection.Input);
        }

        public static void AgregarParametroBoolean(this DbCommand cmd, string nombre, bool value) {
            AgregarParametro(cmd, nombre, value, DbType.Boolean, ParameterDirection.Input);
        }

        public static void AgregarParametroDouble(this DbCommand cmd, string nombre, double value) {
            AgregarParametro(cmd, nombre, value, DbType.Double, ParameterDirection.Input);
        }

        public static void AgregarParametroFecha(this DbCommand cmd, string nombre, DateTime value) {
            AgregarParametro(cmd, nombre, value, DbType.DateTime, ParameterDirection.Input);
        }

        private static void AgregarParametro(DbCommand cmd, 
            string nombre, 
            object? value, 
            DbType dbType, 
            ParameterDirection direccion) {

            DbParameter parameter = cmd.CreateParameter();
            parameter.ParameterName = nombre;
            parameter.Direction = direccion;
            parameter.Value = value ?? DBNull.Value;
            parameter.DbType = dbType;
            cmd.Parameters.Add(parameter);
        }
    }
}
