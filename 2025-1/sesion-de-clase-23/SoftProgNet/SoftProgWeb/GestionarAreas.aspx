<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="GestionarAreas.aspx.cs" Inherits="PUCP.Edu.Pe.SoftProg.Web.GestionarAreas" %>
<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestionar Areas
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
                    <asp:Label ID="lblIDArea" runat="server" Text="Id Area: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-4">
                        <asp:TextBox ID="txtIDArea" Enabled="false" runat="server" CssClass="form-control"></asp:TextBox>
                    </div>
                </div>
                <div class="mb-3 row">
                    <asp:Label ID="lblNombre" runat="server" Text="Nombre: " CssClass="col-sm-2 col-form-label"></asp:Label>
                    <div class="col-sm-8">
                        <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" ></asp:TextBox>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <asp:Button ID="btnGuardar" CssClass="float-end btn btn-primary" runat="server" Text="Guardar" OnClick="btnGuardar_Click" />
            </div>
        </div>
    </div>
</asp:Content>
