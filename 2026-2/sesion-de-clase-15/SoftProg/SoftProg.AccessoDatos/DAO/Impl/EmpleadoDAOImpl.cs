using SoftProg.DbManager;
using SoftProg.Modelo.RRHH;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl {
    public class EmpleadoDAOImpl : PersonaDAOImpl<Empleado>, IEmpleadoDAO {
        public List<Empleado> FindAll() {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "listar_empleados";
            using DbDataReader reader = cmd.ExecuteReader();

            List<Empleado> empleados = [];
            while (reader.Read()) {
                empleados.Add(Mapear(reader, new Empleado()));
            }

            return empleados;
        }

        public Empleado? FindById(int id) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "buscar_empleado_por_id";
            cmd.AgregarParametroEntero("p_id", id);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Empleado()) : null;
        }

        public Empleado? FindByDni(string dni) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "buscar_empleado_por_dni";
            cmd.AgregarParametroCadena("p_dni", dni);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Empleado()) : null;
        }

        public List<Empleado> FilterByName(string nombre) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "filtrar_empleados_por_nombre";
            cmd.AgregarParametroCadena("p_nombre", nombre);
            using DbDataReader reader = cmd.ExecuteReader();

            List<Empleado> empleados = [];
            while (reader.Read()) {
                empleados.Add(Mapear(reader, new Empleado()));
            }

            return empleados;
        }

        public void Insert(Empleado empleado) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "insertar_empleado";
            cmd.AgregarParametroEntero("p_id_area", empleado.Area.Id);
            cmd.AgregarParametroEntero("p_id_cuenta_usuario", empleado.CuentaUsuario?.Id);
            cmd.AgregarParametroCadena("p_dni", empleado.Dni);
            cmd.AgregarParametroCadena("p_nombre", empleado.Nombre);
            cmd.AgregarParametroCadena("p_apellido_paterno", empleado.ApellidoPaterno);
            cmd.AgregarParametroCadena("p_genero", empleado.Genero.ToString());
            cmd.AgregarParametroFecha("p_fecha_nacimiento", empleado.FechaNacimiento);
            cmd.AgregarParametroCadena("p_cargo", empleado.Cargo.ToString());
            cmd.AgregarParametroDouble("p_sueldo", empleado.Sueldo);
            cmd.AgregarParametroBoolean("p_activo", empleado.IsActivo);
            cmd.AgregarParametroSalidaEntero("p_id");

            cmd.ExecuteNonQuery();

            object? resultado = cmd.Parameters["p_id"].Value;
            if (resultado == null || resultado == DBNull.Value) {
                throw new Exception("No se pudo insertar el empleado");
            }

            empleado.Id = Convert.ToInt32(resultado);
        }

        public void Update(Empleado empleado) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "modificar_empleado";
            cmd.AgregarParametroEntero("p_id_area", empleado.Area.Id);
            cmd.AgregarParametroEntero("p_id_cuenta_usuario", empleado.CuentaUsuario?.Id);
            cmd.AgregarParametroCadena("p_dni", empleado.Dni);
            cmd.AgregarParametroCadena("p_nombre", empleado.Nombre);
            cmd.AgregarParametroCadena("p_apellido_paterno", empleado.ApellidoPaterno);
            cmd.AgregarParametroCadena("p_genero", empleado.Genero.ToString());
            cmd.AgregarParametroFecha("p_fecha_nacimiento", empleado.FechaNacimiento);
            cmd.AgregarParametroCadena("p_cargo", empleado.Cargo.ToString());
            cmd.AgregarParametroDouble("p_sueldo", empleado.Sueldo);
            cmd.AgregarParametroBoolean("p_activo", empleado.IsActivo);
            cmd.AgregarParametroEntero("p_id", empleado.Id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo actualizar el empleado");
            }
        }

        public void Delete(int id) {
            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandType = CommandType.StoredProcedure;
            cmd.CommandText = "eliminar_empleado";
            cmd.AgregarParametroEntero("p_id", id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo eliminar el empleado");
            }
        }

        protected override Empleado Mapear(DbDataReader reader, Empleado empleado) {
            base.Mapear(reader, empleado);
            empleado.Cargo = Enum.Parse<Cargo>(reader.GetString("cargo"));
            empleado.Sueldo = Convert.ToDouble(reader.GetDecimal("sueldo"));
            empleado.Area = new AreaDAOImpl().FindById(reader.GetInt32("id_area"))!;
            return empleado;
        }
    }
}
