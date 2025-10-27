<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="CambiarContrasena.aspx.cs" Inherits="PUCP.SoftProg.Web.CambiarContrasena" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <link href="Content/bootstrap.css" rel="stylesheet" />
    <link href="Content/estilo.css" rel="stylesheet" />
    <link href="Content/Fonts/css/all.css" rel="stylesheet" />

    <script src="Scripts/bootstrap.js"></script>
    <script src="Scripts/bootstrap.bundle.js"></script>
    <script src="Scripts/jquery-3.7.1.js"></script>
    
    <title>Cambiar Contraseña</title>
</head>
<body>
    <form id="form1" runat="server">
        <asp:ScriptManager ID="ScriptManager1" runat="server" />

        <div class="container vh-100 d-flex justify-content-center align-items-center">
            <div class="card shadow-lg p-4 rounded-4" style="max-width: 400px; width: 100%;">
                <h1 class="text-center mb-4 text-primary">Cambiar Contraseña</h1>

                <div class="mb-3">
                    <label for="txtContrasenaActual" class="form-label">Contraseña actual</label>
                    <asp:TextBox ID="txtContrasenaActual" runat="server" CssClass="form-control" TextMode="Password" placeholder="Ingresa tu contraseña actual"></asp:TextBox>
                    <asp:RequiredFieldValidator 
                        ID="rfvContrasenaActual" 
                        runat="server" 
                        ControlToValidate="txtContrasenaActual" 
                        ErrorMessage="Debe ingresar su contraseña actual" 
                        CssClass="text-danger small" 
                        Display="Dynamic" />
                </div>

                <div class="mb-3">
                    <label for="txtNuevaContrasena" class="form-label">Nueva contraseña</label>
                    <asp:TextBox ID="txtNuevaContrasena" runat="server" CssClass="form-control" TextMode="Password" placeholder="Ingresa tu nueva contraseña"></asp:TextBox>
                    <asp:RequiredFieldValidator 
                        ID="rfvNuevaContrasena" 
                        runat="server" 
                        ControlToValidate="txtNuevaContrasena" 
                        ErrorMessage="Debe ingresar una nueva contraseña" 
                        CssClass="text-danger small" 
                        Display="Dynamic" />
                </div>

                <div class="mb-3">
                    <label for="txtConfirmarContrasena" class="form-label">Confirmar nueva contraseña</label>
                    <asp:TextBox ID="txtConfirmarContrasena" runat="server" CssClass="form-control" TextMode="Password" placeholder="Repite tu nueva contraseña"></asp:TextBox>
                    <asp:RequiredFieldValidator 
                        ID="rfvConfirmarContrasena" 
                        runat="server" 
                        ControlToValidate="txtConfirmarContrasena" 
                        ErrorMessage="Debe confirmar la nueva contraseña" 
                        CssClass="text-danger small" 
                        Display="Dynamic" />
                    <asp:CompareValidator 
                        ID="cvContrasena" 
                        runat="server" 
                        ControlToValidate="txtConfirmarContrasena" 
                        ControlToCompare="txtNuevaContrasena" 
                        ErrorMessage="Las contraseñas no coinciden" 
                        CssClass="text-danger small" 
                        Display="Dynamic" />
                </div>

                <div class="d-grid mb-3">
                    <asp:Button ID="btnCambiarContrasena" runat="server" CssClass="btn btn-primary" Text="Cambiar Contraseña" OnClick="btnCambiarContrasena_Click" />
                </div>

                <div class="text-center">
                    <a href="<%= ResolveUrl("~/Login.aspx") %>" class="small text-decoration-none">Volver al inicio de sesión</a>
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
