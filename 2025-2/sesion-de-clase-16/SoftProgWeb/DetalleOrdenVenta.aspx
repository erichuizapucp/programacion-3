<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="DetalleOrdenVenta.aspx.cs" Inherits="PUCP.SoftProg.Web.DetalleOrdenVenta" %>
<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Detalle de la Orden de Venta
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="cphScripts" runat="server">
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="cphContenido" runat="server">
    <div class="card border">
        <div class="card-header">
            <h5 class="card-title mb-0">Información de la Orden</h5>
        </div>
        <div class="card-body">
            <div class="mb-3 row">
                <asp:Label ID="lblIdOrden" runat="server" Text="ID Orden:" CssClass="col-sm-2 col-form-label" />
                <div class="col-sm-3">
                    <asp:TextBox ID="txtIdOrden" runat="server" Enabled="false" CssClass="form-control" />
                </div>
            </div>
            <div class="mb-3 row">
                <asp:Label ID="lblCliente" runat="server" Text="Cliente:" CssClass="col-sm-2 col-form-label" />
                <div class="col-sm-5">
                    <asp:TextBox ID="txtCliente" runat="server" Enabled="false" CssClass="form-control" />
                </div>
            </div>
            <div class="mb-3 row">
                <asp:Label ID="lblDniCliente" runat="server" Text="DNI:" CssClass="col-sm-2 col-form-label" />
                <div class="col-sm-3">
                    <asp:TextBox ID="txtDniCliente" runat="server" Enabled="false" CssClass="form-control" />
                </div>
            </div>
        </div>
    </div>
    <div class="card border mt-3">
        <div class="card-header">
            <h5 class="card-title mb-0">Detalle de Productos</h5>
        </div>
        <div class="card-body">
            <asp:GridView ID="gvLineasOrden" runat="server"
                CssClass="table table-striped table-bordered"
                AutoGenerateColumns="False">
                <Columns>
                    <asp:BoundField DataField="Producto.Id" HeaderText="ID Producto" />
                    <asp:BoundField DataField="Producto.Nombre" HeaderText="Nombre" />
                    <asp:BoundField DataField="Producto.Precio" HeaderText="Precio Unitario (S/)" DataFormatString="{0:N2}" />
                    <asp:BoundField DataField="Cantidad" HeaderText="Cantidad" />
                    <asp:BoundField DataField="SubTotal" HeaderText="Subtotal (S/)" DataFormatString="{0:N2}" />
                </Columns>
            </asp:GridView>
        </div>
    </div>
    <div class="card border mt-3">
        <div class="card-body text-end">
            <div class="row mb-2">
                <div class="col-sm-9 text-end">
                    <strong>Subtotal:</strong>
                </div>
                <div class="col-sm-3">
                    <asp:TextBox ID="txtSubtotal" runat="server" Enabled="false" CssClass="form-control text-end" />
                </div>
            </div>
            <div class="row mb-2">
                <div class="col-sm-9 text-end">
                    <strong>IGV (18%):</strong>
                </div>
                <div class="col-sm-3">
                    <asp:TextBox ID="txtIGV" runat="server" Enabled="false" CssClass="form-control text-end" />
                </div>
            </div>
            <div class="row">
                <div class="col-sm-9 text-end">
                    <strong>Total:</strong>
                </div>
                <div class="col-sm-3">
                    <asp:TextBox ID="txtTotal" runat="server" Enabled="false" CssClass="form-control text-end fw-bold" />
                </div>
            </div>
        </div>
    </div>
</asp:Content>
