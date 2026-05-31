using SoftProgWeb.ViewModels;
using SoftProgModelo.Modelos.Almacen;

namespace SoftProgWeb.Extensiones;

public static class EnumLocalizacionExtensions {
    public static string ToLocalizedText(this UnidadMedidaEnum valor) {
        return valor switch {
            UnidadMedidaEnum.Unidad => "Unidad",
            UnidadMedidaEnum.Kilos => "Kilos",
            UnidadMedidaEnum.Onzas => "Onzas",
            UnidadMedidaEnum.Litros => "Litros",
            _ => valor.ToString()
        };
    }

    public static string ToLocalizedText(this UnidadMedida valor) {
        return valor switch {
            UnidadMedida.UND => "Unidad",
            UnidadMedida.Kilos => "Kilos",
            UnidadMedida.Onzas => "Onzas",
            UnidadMedida.Litros => "Litros",
            _ => valor.ToString()
        };
    }

    public static string ToLocalizedText(this TipoUsuarioEnum valor) {
        return valor switch {
            TipoUsuarioEnum.Cliente => "Cliente",
            TipoUsuarioEnum.Empleado => "Empleado",
            TipoUsuarioEnum.Admin => "Admin",
            _ => valor.ToString()
        };
    }
}