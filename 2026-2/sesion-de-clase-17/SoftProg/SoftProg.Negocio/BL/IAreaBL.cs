using SoftProg.Modelo.RRHH;

namespace SoftProg.Negocio.BL {
    public interface IAreaBL : IRegistroBL<Area, int> {
        List<Area> FilterByName(string nombre);
    }
}
