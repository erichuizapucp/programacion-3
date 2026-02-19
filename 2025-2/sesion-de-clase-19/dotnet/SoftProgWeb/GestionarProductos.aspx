<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="GestionarProductos.aspx.cs" Inherits="PUCP.SoftProg.Web.GestionarProductos" %>
<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestionar Productos
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
                    <asp:Label ID="lblIDProducto" runat="server" Text="Id Producto: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-4">
                        <asp:TextBox ID="txtIDProducto" Enabled="false" runat="server" CssClass="form-control"></asp:TextBox>
                    </div>
                </div>
                <div class="mb-3 row">
                    <asp:Label id="lblNombre" runat="server" Text="Nombre: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:TextBox id="txtNombre" runat="server" CssClass="form-control"></asp:TextBox>
                    </div>
                </div>
                <div class="mb-3 row">
                    <asp:Label id="lblUnidadMedida" runat="server" Text="Unidad de Medida: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:DropDownList ID="ddlUnidadMedida" runat="server">
                            <asp:ListItem Value="Unidad">Unidad</asp:ListItem>
                            <asp:ListItem Value="Kilos">Kilos</asp:ListItem>
                            <asp:ListItem Value="Onzas">Onzas</asp:ListItem>
                            <asp:ListItem Value="Litros">Litros</asp:ListItem>
                        </asp:DropDownList>
                    </div>
                </div>
                <div class="mb-3 row">
                    <asp:Label id="lblPrecio" runat="server" Text="Precio: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:TextBox id="txtPrecio" runat="server" CssClass="form-control"></asp:TextBox>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <asp:Button id="btnGuardar" runat="server" CssClass="float-end btn btn-primary" Text="Guardar" OnClick="GuardarProducto"/>
            </div>
        </div>
    </div>
</asp:Content>
