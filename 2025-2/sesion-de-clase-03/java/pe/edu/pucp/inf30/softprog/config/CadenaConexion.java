package pe.edu.pucp.inf30.softprog.config;

public class CadenaConexion {
    private String servidor;
    private String schema;
    private int puerto;

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
    
    CadenaConexion(Builder builder) {
        this.servidor = builder.getServidor();
        this.schema = builder.getSchema();
        this.puerto = builder.getPuerto();
    }

    @Override
    public String toString() {
        return String.format(
                "jdbc:mysql://%s:%d/%s?useSSL=false&"
                    + "allowPublicKeyRetrieval=true", 
                this.servidor, 
                this.puerto, 
                this.schema);
    }
    
    public static class Builder {
        private String servidor;
        private String schema;
        private int puerto;
        
        public String getServidor() {
            return this.servidor;
        }
        
        public String getSchema() {
            return this.schema;
        }
        
        public int getPuerto() {
            return this.puerto;
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
        
        public CadenaConexion build() {
            return new CadenaConexion(this);
        }
    }
}
