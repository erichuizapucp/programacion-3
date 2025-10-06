namespace PUCP.SoftProg.Db {
    public class MySQLDBManagerFactory : DBManagerFactory {
        public override DBManager CrearDBManager(string host, int puerto, string esquema,
                                                 string usuario, string password) {
            
            return MySQLDBManager.GetInstance(host, puerto, esquema, usuario,
                                              password);
        }
    }
}
