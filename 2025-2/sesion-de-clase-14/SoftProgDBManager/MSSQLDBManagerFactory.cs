namespace PUCP.SoftProg.Db {
    public class MSSQLDBManagerFactory : DBManagerFactory {
        public override DBManager CrearDBManager(string host, int puerto, string esquema,
                                                 string usuario, string password) {
            return MSSQLDBManager.GetInstance(host, puerto, esquema, usuario,
                                              password);
        }
    }
}
