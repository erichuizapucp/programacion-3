using SoftProg.AccessoDatos.DAO;
using SoftProg.AccessoDatos.DAO.Impl;
using SoftProg.Modelo.RRHH;

public class Program {
    public static void Main(string[] args) {
        IAreaDAO areaDAO = new AreaDAOImpl();
        List<Area> areas = areaDAO.FindAll();

        //areas.ForEach(area => { 
        //    Console.WriteLine(area);
        //});

        foreach (Area area in areas) {
            Console.WriteLine(area);
        }

        //Area nuevaArea = new Area {
        //    Nombre = "Nueva Area", 
        //    IsActivo = true,
        //};
        //areaDAO.Insert(nuevaArea);

        //Console.WriteLine();
        //areas = areaDAO.FindAll();
        //areas.ForEach(area => {
        //    Console.WriteLine(area);
        //});

        //areaDAO.Delete(9);

    }
}
