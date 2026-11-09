using System.Security.Claims;

namespace SoftProg.Web.Seguridad {
    public static class Permisos {
        public static readonly IReadOnlyDictionary<string, string[]> RolesPorPermiso = new Dictionary<string, string[]> {
            ["Resumen"] = ["Administrador"],
            ["Productos"] = ["Administrador", "ENCARGADO_ALMACEN"],
            ["Clientes"] = ["Administrador"],
            ["OrdenesVenta"] = ["Administrador", "VENDEDOR_SENIOR", "EJECUTIVO_COMERCIAL"],
            ["Areas"] = ["Administrador"],
            ["Empleados"] = ["Administrador", "ANALISTA_RRHH"],
            ["CuentasUsuario"] = ["Administrador"],
            ["MisOrdenes"] = ["Cliente"],
            ["PerfilCliente"] = ["Cliente"],
            ["PerfilEmpleado"] = ["VENDEDOR_SENIOR", "EJECUTIVO_COMERCIAL", "ANALISTA_RRHH", "ENCARGADO_ALMACEN", "CONTADOR", "TECNICO"]
        };

        public static readonly IReadOnlyDictionary<string, List<OpcionMenu>> Menu = new Dictionary<string, List<OpcionMenu>> {
            ["Almacén"] = [
                new OpcionMenu { Texto = "Productos", Href = "productos", Permiso = "Productos" }
            ],
            ["Ventas"] = [
                new OpcionMenu { Texto = "Clientes", Href = "clientes", Permiso = "Clientes" },
                new OpcionMenu { Texto = "Órdenes de venta", Href = "ordenes-venta", Permiso = "OrdenesVenta" }
            ],
            ["RRHH"] = [
                new OpcionMenu { Texto = "Áreas", Href = "areas", Permiso = "Areas" },
                new OpcionMenu { Texto = "Empleados", Href = "empleados", Permiso = "Empleados" }
            ],
            ["Seguridad"] = [
                new OpcionMenu { Texto = "Cuentas de usuario", Href = "cuentas-usuario", Permiso = "CuentasUsuario" }
            ],
            ["Mis compras"] = [
                new OpcionMenu { Texto = "Mis órdenes", Href = "mis-ordenes", Permiso = "MisOrdenes" }
            ]
        };

        public static readonly List<OpcionMenu> MenuCuenta = [
            new OpcionMenu { Texto = "Mi perfil", Href = "mi-perfil", Permiso = "PerfilCliente" },
            new OpcionMenu { Texto = "Mi perfil", Href = "mi-perfil-empleado", Permiso = "PerfilEmpleado" }
        ];

        private static bool TienePermiso(ClaimsPrincipal usuario, string permiso) {
            string[] rolesPermitidos = RolesPorPermiso[permiso];
            foreach (string rol in rolesPermitidos) {
                if (usuario.IsInRole(rol)) {
                    return true;
                }
            }
            return false;
        }

        public static List<OpcionMenu> OpcionesDelUsuario(List<OpcionMenu> opciones, ClaimsPrincipal usuario) {
            List<OpcionMenu> opcionesPermitidas = [];
            foreach (OpcionMenu opcion in opciones) {
                if (TienePermiso(usuario, opcion.Permiso)) {
                    opcionesPermitidas.Add(opcion);
                }
            }
            return opcionesPermitidas;
        }

        public static Dictionary<string, List<OpcionMenu>> MenuDelUsuario(ClaimsPrincipal usuario) {
            Dictionary<string, List<OpcionMenu>> menu = [];
            foreach (KeyValuePair<string, List<OpcionMenu>> grupo in Menu) {
                List<OpcionMenu> opciones = OpcionesDelUsuario(grupo.Value, usuario);
                if (opciones.Count > 0) {
                    menu[grupo.Key] = opciones;
                }
            }
            return menu;
        }
    }
}
