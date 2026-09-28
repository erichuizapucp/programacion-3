using SoftProg.DbManager;
using SoftProg.Modelo.RRHH;
using System.Data;
using System.Data.Common;

namespace SoftProg.AccessoDatos.DAO.Impl {
    public class EmpleadoDAOImpl : PersonaDAOImpl<Empleado>, IEmpleadoDAO {
        public List<Empleado> FindAll() {
            string sql =
                """
                SELECT id, id_area, id_cuenta_usuario, dni, nombre, apellido_paterno, 
                    genero, fecha_nacimiento, cargo, sueldo, activo 
                FROM empleado
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            using DbDataReader reader = cmd.ExecuteReader();

            List<Empleado> empleados = [];
            while (reader.Read()) {
                empleados.Add(Mapear(reader, new Empleado()));
            }

            return empleados;
        }

        public Empleado? FindById(int id) {
            string sql =
                """
                SELECT id, id_area, id_cuenta_usuario, dni, nombre, apellido_paterno, 
                    genero, fecha_nacimiento, cargo, sueldo, activo 
                FROM empleado
                WHERE
                    id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id", id);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Empleado()) : null;
        }

        public Empleado? FindByDni(string dni) {
            string sql =
                """
                SELECT id, id_area, id_cuenta_usuario, dni, nombre, apellido_paterno, 
                    genero, fecha_nacimiento, cargo, sueldo, activo 
                FROM empleado
                WHERE
                    dni = @dni
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroCadena("dni", dni);
            using DbDataReader reader = cmd.ExecuteReader();

            return reader.Read() ? Mapear(reader, new Empleado()) : null;
        }

        public void Insert(Empleado empleado) {
            string sql =
                """
                INSERT INTO empleado(id_area, id_cuenta_usuario, dni, nombre, apellido_paterno, 
                    genero, fecha_nacimiento, cargo, sueldo, activo)
                VALUES (@id_area, @id_cuenta_usuario, @dni, @nombre, @apellido_paterno, 
                    @genero, @fecha_nacimiento, @cargo, @sueldo, @activo);
                SELECT LAST_INSERT_ID();
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id_area", empleado.Area.Id);
            cmd.AgregarParametroEntero("id_cuenta_usuario", empleado.CuentaUsuario?.Id);
            cmd.AgregarParametroCadena("dni", empleado.Dni);
            cmd.AgregarParametroCadena("nombre", empleado.Nombre);
            cmd.AgregarParametroCadena("apellido_paterno", empleado.ApellidoPaterno);
            cmd.AgregarParametroCadena("genero", empleado.Genero.ToString());
            cmd.AgregarParametroFecha("fecha_nacimiento", empleado.FechaNacimiento);
            cmd.AgregarParametroCadena("cargo", empleado.Cargo.ToString());
            cmd.AgregarParametroDouble("sueldo", empleado.Sueldo);
            cmd.AgregarParametroBoolean("activo", empleado.IsActivo);

            object? resultado = cmd.ExecuteScalar();
            if (resultado == null || resultado == DBNull.Value) {
                throw new Exception("No se pudo insertar el empleado");
            }

            empleado.Id = Convert.ToInt32(resultado);
        }

        public void Update(Empleado empleado) {
            string sql =
                """
                UPDATE empleado
                SET
                    id_area = @id_area,
                    id_cuenta_usuario = @id_cuenta_usuario,
                    dni = @dni,
                    nombre = @nombre,
                    apellido_paterno = @apellido_paterno,
                    genero = @genero,
                    fecha_nacimiento = @fecha_nacimiento,
                    cargo = @cargo,
                    sueldo = @sueldo,
                    activo = @activo
                WHERE
                    id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id_area", empleado.Area.Id);
            cmd.AgregarParametroEntero("id_cuenta_usuario", empleado.CuentaUsuario?.Id);
            cmd.AgregarParametroCadena("dni", empleado.Dni);
            cmd.AgregarParametroCadena("nombre", empleado.Nombre);
            cmd.AgregarParametroCadena("apellido_paterno", empleado.ApellidoPaterno);
            cmd.AgregarParametroCadena("genero", empleado.Genero.ToString());
            cmd.AgregarParametroFecha("fecha_nacimiento", empleado.FechaNacimiento);
            cmd.AgregarParametroCadena("cargo", empleado.Cargo.ToString());
            cmd.AgregarParametroDouble("sueldo", empleado.Sueldo);
            cmd.AgregarParametroBoolean("activo", empleado.IsActivo);
            cmd.AgregarParametroEntero("id", empleado.Id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo actualizar el empleado");
            }
        }

        public void Delete(int id) {
            string sql =
                """
                DELETE FROM empleado WHERE id = @id
                """;

            using DbConnection conn = DBManager.GetInstance().GetConnection();
            using DbCommand cmd = conn.CreateCommand();
            cmd.CommandText = sql;
            cmd.AgregarParametroEntero("id", id);

            if (cmd.ExecuteNonQuery() == 0) {
                throw new Exception("No se pudo eliminar el empleado");
            }
        }

        protected override Empleado Mapear(DbDataReader reader, Empleado empleado) {
            base.Mapear(reader, empleado);
            empleado.Cargo = Enum.Parse<Cargo>(reader.GetString("cargo"));
            empleado.Sueldo = reader.GetDouble("sueldo");
            empleado.Area = new AreaDAOImpl().FindById(reader.GetInt32("id_area"))!;
            return empleado;
        }
    }
}
