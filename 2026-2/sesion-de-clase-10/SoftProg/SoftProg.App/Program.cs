using SoftProg.DbManager;
using System.Data.Common;

public class Program {
    public static void Main(string[] args) {
        using (DbConnection conn = DBManager.GetInstance().GetConnection()) {
            Console.WriteLine("La conexion es satisfactoria.");
        }
    }
}
