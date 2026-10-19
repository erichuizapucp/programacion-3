using SoftProg.Modelo;
using SoftProg.Modelo.RRHH;
using SoftProg.Modelo.Seguridad;
using System.ComponentModel.DataAnnotations;

namespace SoftProg.Web.ViewModels {
    public class EmpleadoViewModel {
        public int Id { get; set; }

        [Required(ErrorMessage = "El DNI es obligatorio")]
        [RegularExpression(@"^\d{8}$", ErrorMessage = "El DNI tiene 8 dígitos")]
        public string Dni { get; set; } = "";

        [Required(ErrorMessage = "El nombre es obligatorio")]
        [StringLength(50, ErrorMessage = "Máximo 50 caracteres")]
        public string Nombre { get; set; } = "";

        [Required(ErrorMessage = "El apellido es obligatorio")]
        [StringLength(50, ErrorMessage = "Máximo 50 caracteres")]
        public string ApellidoPaterno { get; set; } = "";

        public Genero Genero { get; set; }

        [Required(ErrorMessage = "La fecha de nacimiento es obligatoria")]
        public DateTime? FechaNacimiento { get; set; }

        [Range(1, int.MaxValue, ErrorMessage = "Seleccione un área")]
        public int IdArea { get; set; }

        public Cargo Cargo { get; set; }

        [Range(0, 99999999, ErrorMessage = "Debe ser mayor o igual a 0")]
        public double Sueldo { get; set; }

        public bool IsActivo { get; set; } = true;

        public int IdCuentaUsuario { get; set; }

        public EmpleadoViewModel() { }

        public EmpleadoViewModel(Empleado empleado) {
            Id = empleado.Id;
            Dni = empleado.Dni;
            Nombre = empleado.Nombre;
            ApellidoPaterno = empleado.ApellidoPaterno;
            Genero = empleado.Genero;
            FechaNacimiento = empleado.FechaNacimiento;
            IdArea = empleado.Area.Id;
            Cargo = empleado.Cargo;
            Sueldo = empleado.Sueldo;
            IsActivo = empleado.IsActivo;
            IdCuentaUsuario = empleado.CuentaUsuario?.Id ?? 0;
        }

        public Empleado ToModelo(Area area, CuentaUsuario? cuentaUsuario) {
            return new Empleado {
                Id = Id,
                Dni = Dni,
                Nombre = Nombre,
                ApellidoPaterno = ApellidoPaterno,
                Genero = Genero,
                FechaNacimiento = FechaNacimiento!.Value,
                Area = area,
                CuentaUsuario = cuentaUsuario,
                Cargo = Cargo,
                Sueldo = Sueldo,
                IsActivo = IsActivo
            };
        }
    }
}
