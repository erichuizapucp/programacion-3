<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="RecuperarContrasena.aspx.cs" Inherits="PUCP.SoftProg.Web.OlvidoContrasena" %>

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

    <title>Recuperar Contrasena</title>
</head>
<body>
    <form id="form1" runat="server">
        <asp:ScriptManager ID="ScriptManager1" runat="server" />

        <div class="container vh-100 d-flex justify-content-center align-items-center">
            <div class="card shadow-lg p-4 rounded-4" style="max-width: 400px; width: 100%;">
                <h3 class="text-center mb-4">Recuperar Contraseña</h3>

                <!-- Mensaje de confirmación -->
                <asp:Panel ID="pnlMensaje" runat="server" Visible="false" CssClass="alert alert-success text-center">
                    Su nueva contraseña ha sido enviada a su correo electrónico.<br />
                    Será redirigido al inicio de sesión en unos segundos...
                </asp:Panel>

                <asp:Panel ID="pnlFormulario" runat="server">
                    <div class="mb-3">
                        <label for="txtCorreo" class="form-label">Correo electrónico</label>
                        <asp:TextBox ID="txtCorreo" runat="server" CssClass="form-control" placeholder="Ingrese su correo" />
                        <asp:RequiredFieldValidator ID="rfvCorreo" runat="server"
                            ControlToValidate="txtCorreo"
                            ErrorMessage="El correo es obligatorio"
                            CssClass="text-danger" Display="Dynamic" />
                        <asp:RegularExpressionValidator ID="revCorreo" runat="server"
                            ControlToValidate="txtCorreo"
                            ErrorMessage="Formato de correo inválido"
                            CssClass="text-danger"
                            ValidationExpression="^[\w\.\-]+@([\w\-]+\.)+[a-zA-Z]{2,10}$"
                            Display="Dynamic" />
                    </div>

                    <asp:Button ID="btnEnviar" runat="server" Text="Enviar"
                        CssClass="btn btn-primary w-100"
                        OnClick="btnEnviar_Click" />

                    <div class="text-center mt-3">
                        <a href="Login.aspx" class="text-decoration-none">Volver al inicio de sesión</a>
                    </div>

                    <asp:ValidationSummary ID="ValidationSummary1" runat="server"
                        CssClass="alert alert-danger mt-3"
                        HeaderText="Por favor corrija los siguientes errores:" />
                </asp:Panel>
            </div>
        </div>
    </form>
</body>
</html>
