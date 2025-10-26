package pe.edu.pucp.inf30.softprog.db.utils;

public class CadenaConexion {
    private String servidor;
    private String schema;
    private int puerto;
    private TipoDB tipoDB;

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
    
    public TipoDB getTipoDB() {
        return tipoDB;
    }
    
    public void setTipoDB(TipoDB tipoDB) {
        this.tipoDB = tipoDB;
    }
    
    CadenaConexion(Builder builder) {
        this.servidor = builder.getServidor();
        this.schema = builder.getSchema();
        this.puerto = builder.getPuerto();
        this.tipoDB = builder.getTipoDB();
    }

    @Override
    public String toString() {
        switch (this.tipoDB) {
            case MSSQL -> {
                return String.format("jdbc:sqlserver://%s:%d;databaseName=%s;"
                        + "encrypt=false", servidor, puerto, schema);
            }
            case MySQL -> {
                return String.format("jdbc:mysql://%s:%d/%s?useSSL=false&"
                        + "allowPublicKeyRetrieval=true", servidor, puerto, 
                        schema);
            }
            default -> throw new UnsupportedOperationException("Tipo DB no "
                    + "soportado: " + tipoDB);
        }
    }
    
    public static class Builder {
        private String servidor;
        private String schema;
        private int puerto;
        private TipoDB tipoDB;
        
        public String getServidor() {
            return this.servidor;
        }
        
        public String getSchema() {
            return this.schema;
        }
        
        public int getPuerto() {
            return this.puerto;
        }
        
        public TipoDB getTipoDB() {
            return this.tipoDB;
        }
        
        public Builder servidor(String servidor) {
            this.servidor = servidor;
            return this;
        }
        
        public Builder schema(String schema) {
            this.schema = schema;
            return this;
        }
        
        public Builder puerto(int puerto) {
            this.puerto = puerto;
            return this;
        }
        
        public Builder tipoDB(TipoDB tipoDB) {
            this.tipoDB = tipoDB;
            return this;
        }
        
        public CadenaConexion build() {
            return new CadenaConexion(this);
        }
    }
}
