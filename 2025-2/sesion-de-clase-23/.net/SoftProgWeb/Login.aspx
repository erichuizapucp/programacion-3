<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="PUCP.SoftProg.Web.Login" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
   
    <link href="Content/bootstrap.css" rel="stylesheet" />
    <link href="Content/estilo.css" rel="stylesheet" />
    <link href="Content/Fonts/css/all.css" rel="stylesheet" />

    <script src="Scripts/bootstrap.js"></script>
    <script src="Scripts/bootstrap.bundle.js"></script>
    <script src="Scripts/jquery-3.7.1.js"></script>
    
    <title>Pagina de Login</title>
</head>
<body>
    <form id="form1" runat="server">
        <asp:ScriptManager ID="ScriptManager1" runat="server" />

        <div class="container vh-100 d-flex justify-content-center align-items-center">
            <div class="card shadow-lg p-4 rounded-4" style="max-width: 400px; width: 100%;">
                <h1 class="text-center mb-4 text-primary">Iniciar Sesión</h1>

                <div class="mb-3">
                    <label class="form-label">Tipo de Usuario</label>
                    <div class="d-flex justify-content-around">
                        <asp:RadioButtonList ID="rblTipoUsuario" runat="server" RepeatDirection="Horizontal" CssClass="form-check-inline">
                            <asp:ListItem Value="CLIENTE" Text="Cliente" Selected="True"></asp:ListItem>
                            <asp:ListItem Value="EMPLEADO" Text="Empleado"></asp:ListItem>
                        </asp:RadioButtonList>
                    </div>
                    <asp:RequiredFieldValidator 
                        ID="rfvTipoUsuario" 
                        runat="server" 
                        ControlToValidate="rblTipoUsuario" 
                        ErrorMessage="Debe seleccionar un tipo de usuario" 
                        CssClass="text-danger small" 
                        Display="Dynamic" />
                </div>

                <div class="mb-3">
                    <label for="txtCuenta" class="form-label">Cuenta</label>
                    <asp:TextBox ID="txtCuenta" runat="server" CssClass="form-control" placeholder="Ingresa tu cuenta"></asp:TextBox>
                    <asp:RequiredFieldValidator 
                        ID="rfvCuenta" 
                        runat="server" 
                        ControlToValidate="txtCuenta" 
                        ErrorMessage="La cuenta es obligatoria" 
                        CssClass="text-danger small" 
                        Display="Dynamic" />
                </div>

                <div class="mb-3">
                    <label for="txtPassword" class="form-label">Contraseña</label>
                    <asp:TextBox ID="txtPassword" runat="server" CssClass="form-control" TextMode="Password" placeholder="Ingresa tu contraseña"></asp:TextBox>
                    <asp:RequiredFieldValidator 
                        ID="rfvPassword" 
                        runat="server" 
                        ControlToValidate="txtPassword" 
                        ErrorMessage="La contraseña es obligatoria" 
                        CssClass="text-danger small" 
                        Display="Dynamic" />
                </div>

                <asp:CustomValidator 
                    ID="cvLoginError" 
                    runat="server" 
                    Display="None" 
                    EnableClientScript="false" />

                <div class="d-grid mb-3">
                    <asp:Button ID="btnLogin" runat="server" CssClass="btn btn-primary" Text="Iniciar Sesión" OnClick="btnLogin_Click" />
                </div>

                <div class="text-center">
                    <a href="<%= ResolveUrl("~/RegistrarCliente.aspx") %>" class="d-block mb-1 small text-decoration-none">¿No tienes cuenta? Regístrate</a>
                    <a href="<%= ResolveUrl("~/RecuperarContrasena.aspx") %>" class="small text-decoration-none">¿Olvidaste tu contraseña?</a>
                </div>

                <asp:ValidationSummary 
                    ID="ValidationSummary1" 
                    runat="server" 
                    CssClass="text-danger mt-3 small" 
                    HeaderText="Por favor corrige los siguientes errores:" 
                    DisplayMode="BulletList" />
            </div>
        </div>
    </form>
</body>
</html>
