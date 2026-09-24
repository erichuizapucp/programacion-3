using System;
using System.Collections.Generic;
using System.Text;
using SoftProg.Modelo.RRHH;

namespace SoftProg.AccessoDatos.DAO {
    public interface IAreaDAO {
        List<Area> FindAll();
        Area? FindById(int id);
        void Insert(Area area);
        void Update(Area area);
        void Delete(int id);
    }
}
