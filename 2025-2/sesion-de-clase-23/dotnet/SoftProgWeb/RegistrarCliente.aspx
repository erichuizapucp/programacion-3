<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="RegistrarCliente.aspx.cs" Inherits="PUCP.SoftProg.Web.RegistrarCliente" %>

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

    <title>Registrar Cliente</title>
</head>
<body class="bg-light">
    <form id="form1" runat="server">
        
        <asp:ScriptManager ID="ScriptManager1" runat="server" />

        <div class="container vh-100 d-flex justify-content-center align-items-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow-lg p-4">
                    <div class="text-center mb-3">
                        <h4 class="fw-bold">Registro de Cliente</h4>
                        <asp:Label ID="lblPaso" runat="server" CssClass="text-muted small"></asp:Label>
                    </div>
                    <div class="progress mb-3" style="height: 8px;">
                        <div id="progressBar" runat="server" class="progress-bar bg-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="3" style="width: 0%"></div>
                    </div>

                    <asp:Wizard ID="WizardRegistro" runat="server"
                        DisplaySideBar="false"
                        CssClass="w-100"
                        FinishCompleteButtonText="Finalizar"
                        StartNextButtonText="Siguiente"
                        StepNextButtonText="Siguiente"
                        StepPreviousButtonText="Anterior"
                        OnActiveStepChanged="WizardRegistro_ActiveStepChanged" 
                        OnFinishButtonClick="WizardRegistro_FinishButtonClick"
                        OnNextButtonClick="WizardRegistro_NextButtonClick">

                        <WizardSteps>
                            <asp:WizardStep ID="StepCuenta" Title="Cuenta de Usuario" runat="server">
                                <h5 class="mb-3">Cuenta de Usuario</h5>

                                <asp:ValidationSummary ID="ValidationSummary1" runat="server"
                                    CssClass="alert alert-danger"
                                    HeaderText="Por favor corrige los siguientes errores:"
                                    DisplayMode="BulletList" />

                                <div class="mb-3">
                                    <label for="txtUser" class="form-label">Usuario</label>
                                    <asp:TextBox ID="txtUser" runat="server" CssClass="form-control" />
                                    <asp:RequiredFieldValidator ID="rfvUser" runat="server"
                                        ControlToValidate="txtUser"
                                        ErrorMessage="El usuario es obligatorio"
                                        CssClass="text-danger" Display="Dynamic" />
                                </div>

                                <div class="mb-3">
                                    <label for="txtPassword" class="form-label">Contraseña</label>
                                    <asp:TextBox ID="txtPassword" runat="server" CssClass="form-control" TextMode="Password" />
                                    <asp:RequiredFieldValidator ID="rfvPassword" runat="server"
                                        ControlToValidate="txtPassword"
                                        ErrorMessage="La contraseña es obligatoria"
                                        CssClass="text-danger" Display="Dynamic" />
                                </div>
                            </asp:WizardStep>

                            <asp:WizardStep ID="StepDatos" Title="Datos Personales" runat="server">
                                <h5 class="mb-3">Datos Personales</h5>

                                <asp:ValidationSummary ID="ValidationSummary2" runat="server"
                                    CssClass="alert alert-danger"
                                    HeaderText="Por favor corrige los siguientes errores:"
                                    DisplayMode="BulletList" />

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label for="txtDni" class="form-label">DNI</label>
                                        <asp:TextBox ID="txtDni" runat="server" CssClass="form-control" MaxLength="8" />
                                        <asp:RequiredFieldValidator ID="rfvDni" runat="server"
                                            ControlToValidate="txtDni" ErrorMessage="El DNI es obligatorio"
                                            CssClass="text-danger" Display="Dynamic" />
                                        <asp:RegularExpressionValidator 
                                            ID="revDniNumerico" 
                                            runat="server"
                                            ControlToValidate="txtDni"
                                            ValidationExpression="^\d+$" 
                                            ErrorMessage="El DNI solo puede contener números"
                                            CssClass="text-danger" 
                                            Display="Dynamic" 
                                        />
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label for="txtNombre" class="form-label">Nombre</label>
                                        <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" />
                                        <asp:RequiredFieldValidator ID="rfvNombre" runat="server"
                                            ControlToValidate="txtNombre" ErrorMessage="El nombre es obligatorio"
                                            CssClass="text-danger" Display="Dynamic" />
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label for="txtApellidoPaterno" class="form-label">Apellido Paterno</label>
                                    <asp:TextBox ID="txtApellidoPaterno" runat="server" CssClass="form-control" />
                                    <asp:RequiredFieldValidator ID="rfvApellidoPaterno" runat="server"
                                        ControlToValidate="txtApellidoPaterno"
                                        ErrorMessage="El apellido paterno es obligatorio"
                                        CssClass="text-danger" Display="Dynamic" />
                                </div>

                                <div class="mb-3">
                                    <label>Género</label><br />
                                    <asp:RadioButtonList ID="rblGenero" runat="server" RepeatDirection="Horizontal">
                                        <asp:ListItem Text="Masculino" Value="MASCULINO"></asp:ListItem>
                                        <asp:ListItem Text="Femenino" Value="FEMENINO"></asp:ListItem>
                                    </asp:RadioButtonList>
                                </div>

                                <div class="mb-3">
                                    <label for="txtFechaNacimiento" class="form-label">Fecha de nacimiento</label>
                                    <asp:TextBox ID="txtFechaNacimiento" runat="server" CssClass="form-control" TextMode="Date" />
                                    <asp:RequiredFieldValidator ID="rfvFechaNacimiento" runat="server"
                                        ControlToValidate="txtFechaNacimiento"
                                        ErrorMessage="La fecha es obligatoria"
                                        CssClass="text-danger" Display="Dynamic" />
                                </div>
                            </asp:WizardStep>

                            <asp:WizardStep ID="StepDetalles" Title="Detalles del Cliente" runat="server">
                                <h5 class="mb-3">Detalles del Cliente</h5>

                                <asp:ValidationSummary ID="ValidationSummary3" runat="server"
                                    CssClass="alert alert-danger"
                                    HeaderText="Por favor corrige los siguientes errores:"
                                    DisplayMode="BulletList" />

                                <div class="mb-3">
                                    <label for="txtLineaCredito" class="form-label">Línea de crédito</label>
                                    <asp:TextBox ID="txtLineaCredito" runat="server" CssClass="form-control" TextMode="Number" />
                                    <asp:RequiredFieldValidator ID="rfvLineaCredito" runat="server"
                                        ControlToValidate="txtLineaCredito"
                                        ErrorMessage="La línea de crédito es obligatoria"
                                        CssClass="text-danger" Display="Dynamic" />
                                </div>

                                <div class="mb-3">
                                    <label>Categoría</label><br />
                                    <asp:RadioButtonList ID="rblCategoria" runat="server" RepeatDirection="Horizontal">
                                        <asp:ListItem Text="Estandar" Value="ESTANDARD"></asp:ListItem>
                                        <asp:ListItem Text="Premium" Value="PREMIUM"></asp:ListItem>
                                    </asp:RadioButtonList>
                                </div>
                            </asp:WizardStep>

                        </WizardSteps>
                    </asp:Wizard>
                    <asp:Panel ID="pnlMensaje" runat="server" Visible="false" CssClass="alert alert-success text-center mt-3">
                        <h5 class="fw-bold">Registro completado</h5>
                        Su cuenta ha sido creada exitosamente.<br />
                        Será redirigido al inicio de sesión en unos segundos...
                    </asp:Panel>
                </div>
            </div>
        </div>
                
    </form>
</body>
</html>
