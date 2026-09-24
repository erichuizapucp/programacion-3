namespace SoftProg.Modelo.Seguridad {
    public class CuentaUsuario : Registro {
        public CuentaUsuario() { }

        public CuentaUsuario(CuentaUsuario cuentaUsuario) : base(cuentaUsuario) {
            UserName = cuentaUsuario.UserName;
            Password = cuentaUsuario.Password;
        }

        public string UserName { 
            get;
            set { 
                ArgumentNullException.ThrowIfNullOrEmpty(value);
                field = value;
            }
        }
        public string Password { 
            get;
            set {
                ArgumentNullException.ThrowIfNullOrEmpty(value);
                field = value;
            }
        }
    }
}
