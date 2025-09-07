package pe.edu.pucp.inf30.softprog.db;

import java.sql.Connection;
import java.sql.SQLException;
import pe.edu.pucp.inf30.softprog.db.utils.TipoDB;
import pe.edu.pucp.inf30.softprog.db.utils.CadenaConexion;

public abstract class DBManager {
    protected String host;
    protected int puerto;
    protected String esquema;
    protected String usuario;
    protected String password;
    protected TipoDB tipoDB;
    
    protected DBManager() {}
    
    protected DBManager(String host, int puerto, String esquema, String usuario, 
                        String password, TipoDB tipoDB) {
        this.host = host;
        this.puerto = puerto;
        this.esquema = esquema;
        this.usuario = usuario;
        this.password = password;
        this.tipoDB = tipoDB;
    }
    
    public abstract Connection getConnection() throws SQLException, 
                                                      ClassNotFoundException;
    
    protected String cadenaConexion() {
        CadenaConexion cadena = new CadenaConexion.Builder()
                .tipoDB(this.tipoDB)
                .servidor(this.host)
                .puerto(this.puerto)
                .schema(this.esquema)
                .build();
        
        return cadena.toString();
    }
}
