<%@ Page Title="" Language="C#" MasterPageFile="~/Plantilla.Master" AutoEventWireup="true" CodeBehind="Areas.aspx.cs" Inherits="SoftProgWeb.Areas" %>
<asp:Content ID="cHead" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="cContent" ContentPlaceHolderID="cphContent" runat="server">
    <div class="mb-3">
        <asp:Label ID="lblId" CssClass="form-label" runat="server" AssociatedControlID="txtId">Area Id</asp:Label>
        <asp:TextBox ID="txtId" CssClass="form-control" runat="server" ReadOnly="true"></asp:TextBox>
    </div>
    <div class="mb-3">
        <asp:Label ID="lblNombre" CssClass="form-label" runat="server" 
            AssociatedControlID="txtNombre">Nombre</asp:Label>
        <asp:TextBox ID="txtNombre" CssClass="form-control" runat="server"></asp:TextBox>
    </div>
    <div class="mb-3 form-check">
        <asp:CheckBox ID="chkActivo" CssClass="form-check-input" runat="server" />
        <asp:Label ID="lblActivo" CssClass="form-check-input" runat="server" AssociatedControlID="chkActivo">Activo?</asp:Label>
    </div>
    <asp:Button ID="btnSubmit" CssClass="btn btn-primary" Text="Submit" runat="server" />
</asp:Content>
