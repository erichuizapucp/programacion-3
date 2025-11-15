<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="PerfilCliente.aspx.cs" Inherits="PUCP.SoftProg.Web.PerfilCliente" %>
<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Perfil de Cliente
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="cphScripts" runat="server">
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="cphContenido" runat="server">
    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2>
                    <asp:Label ID="lblTitulo" runat="server" Text="Datos del Cliente"></asp:Label>
                </h2>
            </div>
            <div class="card-body">
                
                <asp:ValidationSummary 
                    ID="vsPerfilCliente" 
                    runat="server" 
                    CssClass="alert alert-danger" 
                    HeaderText="¡Atención! Por favor corrige los siguientes errores para actualizar tu perfil:" 
                    DisplayMode="BulletList" />

                <asp:Panel ID="pnlMensaje" runat="server" Visible="false" CssClass="alert alert-success text-center mt-3">
                    <h5 class="fw-bold">¡Perfil Actualizado!</h5>
                    <asp:Label ID="lblResultado" runat="server" Text="Tus datos han sido guardados exitosamente."></asp:Label>
                </asp:Panel>

                <asp:HiddenField ID="hdnIdCliente" runat="server" />
                
                <div class="mb-3 row">
                    <asp:Label ID="lblID" runat="server" CssClass="col-sm-2 col-form-label" Text="ID Registro: "></asp:Label>
                    <div class="col-sm-8">
                        <asp:Label ID="lblIdRegistro" runat="server" CssClass="form-control-plaintext"></asp:Label>
                    </div>
                </div>
                
                <hr />
                <h4>Datos Personales</h4>

                <div class="mb-3 row">
                    <asp:Label ID="lblDNI" runat="server" CssClass="col-sm-2 col-form-label" Text="DNI: "></asp:Label>
                    <div class="col-sm-8">
                        <asp:TextBox ID="txtDNI" runat="server" CssClass="form-control"></asp:TextBox>
                        
                        <asp:RequiredFieldValidator 
                            ID="rfvDNI" 
                            runat="server" 
                            ControlToValidate="txtDNI" 
                            ErrorMessage="El DNI es obligatorio." 
                            CssClass="text-danger small" 
                            Display="Dynamic" 
                            ValidationGroup="GuardarPerfil" />
                            
                        <asp:RegularExpressionValidator
                            ID="revDNI"
                            runat="server"
                            ControlToValidate="txtDNI"
                            ValidationExpression="^\d{8}$" 
                            ErrorMessage="El DNI debe tener 8 dígitos numéricos."
                            CssClass="text-danger small"
                            Display="Dynamic"
                            ValidationGroup="GuardarPerfil" />
                    </div>
                </div>
                
                <div class="mb-3 row">
                    <asp:Label ID="lblNombre" runat="server" Text="Nombre: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control"></asp:TextBox>
                        
                        <asp:RequiredFieldValidator 
                            ID="rfvNombre" 
                            runat="server" 
                            ControlToValidate="txtNombre" 
                            ErrorMessage="El Nombre es obligatorio." 
                            CssClass="text-danger small" 
                            Display="Dynamic" 
                            ValidationGroup="GuardarPerfil" />
                    </div>
                </div>
                
                <div class="mb-3 row">
                    <asp:Label ID="lblApellidoPaterno" runat="server" Text="Apellido Paterno: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:TextBox ID="txtApellidoPaterno" runat="server" CssClass="form-control"></asp:TextBox>
                        
                        <asp:RequiredFieldValidator 
                            ID="rfvApellidoPaterno" 
                            runat="server" 
                            ControlToValidate="txtApellidoPaterno" 
                            ErrorMessage="El Apellido Paterno es obligatorio." 
                            CssClass="text-danger small" 
                            Display="Dynamic" 
                            ValidationGroup="GuardarPerfil" />
                    </div>
                </div>
                
                <div class="mb-3 row">
                    <div class="col-sm-2">
                        <asp:Label ID="lblGenero" runat="server" Text="Género: " CssClass="col-form-label"></asp:Label>
                    </div>
                    <div class="col-sm-8">
                        <asp:RadioButtonList ID="rblGenero" runat="server" RepeatDirection="Horizontal" CssClass="form-check-inline">
                            <asp:ListItem Value="MASCULINO" Text="Masculino"></asp:ListItem>
                            <asp:ListItem Value="FEMENINO" Text="Femenino"></asp:ListItem>
                        </asp:RadioButtonList>
                    </div>
                </div>
                
                <div class="mb-3 row">
                    <asp:Label runat="server" Text="Fecha Nacimiento: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:TextBox 
                            ID="txtFechaNacimiento" 
                            runat="server" 
                            CssClass="form-control" 
                            TextMode="Date" /> 
                        <asp:RequiredFieldValidator 
                            ID="rfvFechaNacimiento" 
                            runat="server" 
                            ControlToValidate="txtFechaNacimiento" 
                            ErrorMessage="La Fecha de Nacimiento es obligatoria." 
                            CssClass="text-danger small" 
                            Display="Dynamic" 
                            ValidationGroup="GuardarPerfil" />
                        </div>
                </div>

                <hr />
                <h4>Datos de Cliente</h4>

                <div class="mb-3 row">
                    <asp:Label ID="lblLineaCredito" runat="server" Text="Línea Crédito: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:TextBox ID="txtLineaCredito" runat="server" CssClass="form-control"></asp:TextBox>
                    </div>
                </div>
                
                <div class="mb-3 row">
                    <asp:Label ID="lblCategoria" runat="server" Text="Categoría: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:DropDownList ID="ddlCategoria" runat="server" CssClass="form-control">
                            <asp:ListItem Value="ESTANDARD" Text="Estandard"></asp:ListItem>
                            <asp:ListItem Value="PREMIUM" Text="Premium"></asp:ListItem>
                        </asp:DropDownList>
                    </div>
                </div>
                
                <div class="mb-3 row">
                    <asp:Label ID="lblCuenta" runat="server" Text="Cuenta Usuario: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:TextBox ID="txtCuentaUsuario" runat="server" CssClass="form-control" Enabled="false"></asp:TextBox>
                    </div>
                </div>

            </div>
            <div class="card-footer">
                <asp:Button ID="btnGuardar" runat="server" CssClass="float-end btn btn-success" Text="Actualizar Perfil" OnClick="btnGuardar_Click" ValidationGroup="GuardarPerfil" />
            </div>
        </div>
    </div>
</asp:Content>
