<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="GestionarEmpleados.aspx.cs" Inherits="PUCP.SoftProg.Web.GestionarEmpleados" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestionar Empleados
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="cphScripts" runat="server">
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="cphContenido" runat="server">
    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2>
                    <asp:Label ID="lblTitulo" runat="server" Text="Label"></asp:Label>
                </h2>
            </div>
            <div class="card-body">
                <div class="mb-3 row">
                    <asp:Label id="lblDNI" runat="server" CssClass="col-sm-2 col-form-label" Text="DNI: "></asp:Label>
                    <div class="col-sm-8">
                        <asp:HiddenField ID="hdnIdEmpleado" runat="server" />
                        <asp:TextBox id="txtDNIEmpleado" runat="server" CssClass="form-control"></asp:TextBox>
                    </div>
                </div>
                <div class="mb-3 row">
                    <asp:Label id="lblNombre" runat="server" Text="Nombre: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:TextBox id="txtNombre" runat="server" CssClass="form-control"></asp:TextBox>
                    </div>
                </div>
                <div class="mb-3 row">
                    <asp:Label id="lblApellidoPaterno" runat="server" Text="Apellido Paterno: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:TextBox id="txtApellidoPaterno" runat="server" CssClass="form-control"></asp:TextBox>
                    </div>
                </div>
                <div class="mb-3 row">
                    <asp:Label id="lblArea" runat="server" Text="Área: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:DropDownList id="ddlAreas" runat="server" CssClass="form-control"></asp:DropDownList>
                    </div>
                </div>
                <div class="mb-3 row">
                    <div class="col-sm-2">
                        <asp:Label id="lblGenero" runat="server" Text="Genero: " CssClass="col-form-label"></asp:Label>
                    </div>
                    <div class="col-sm-8">
                        <div class="form-check form-check-inline">
                            <input type="radio" runat="server" id="rbMasculino" class="form-check-input" />
                            <label class="form-check-label" runat="server" for="cphContenido_rbMasculino">Masculino</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input type="radio" runat="server" id="rbFemenino" class="form-check-input" />
                            <label class="form-check-label" runat="server" for="cphContenido_rbFemenino">Femenino</label>
                        </div>
                    </div>
                </div>
                <div class="mb-3 row">
                    <asp:Label runat="server" Text="Fecha Nacimiento: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <input id="dtpFechaNacimiento" class="form-control" type="date" runat="server" Text="Label" />
                    </div>
                </div>
                <div class="mb-3 row">
                    <asp:Label id="lblCargo" runat="server" Text="Cargo: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:DropDownList ID="ddlCargo" runat="server">
                            <asp:ListItem Value="GERENTE">GERENTE</asp:ListItem>
                            <asp:ListItem Value="DIRECTOR">DIRECTOR</asp:ListItem>
                            <asp:ListItem Value="SUBDIRECTOR">SUBDIRECTOR</asp:ListItem>
                            <asp:ListItem Value="ASISTENTE">ASISTENTE</asp:ListItem>
                            <asp:ListItem Value="TECNICO">TECNICO</asp:ListItem>
                        </asp:DropDownList>
                    </div>
                </div>
                <div class="mb-3 row">
                    <asp:Label id="lblSueldo" runat="server" Text="Sueldo: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:TextBox id="txtSueldo" runat="server" CssClass="form-control"></asp:TextBox>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <asp:Button ID="btnGuardar" runat="server" CssClass="float-end btn btn-primary" Text="Guardar" OnClick="GuardarEmpleado" />
            </div>
        </div>
    </div>
</asp:Content>
