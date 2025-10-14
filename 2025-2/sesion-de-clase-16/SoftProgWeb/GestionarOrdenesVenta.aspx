<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="GestionarOrdenesVenta.aspx.cs" Inherits="PUCP.SoftProg.Web.GestionarOrdenesVenta" %>
<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestionar Ordenes de Venta
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="cphScripts" runat="server">
    <script src="Scripts/SoftProg/gestionarOrdenesVenta.js"></script>
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="cphContenido" runat="server">
    <div class="container">
        <div class="card">
            <div class="card-header">
                <h2>Registrar Orden de Venta</h2>
            </div>
            <div class="card-body">
                <div class="card border">
                    <div class="card-header bg-light">
                        <h5 class="card-title mb-0">Información del Cliente</h5>
                    </div>
                    <div class="card-body">
                        <div class="mb-3 row">
                            <asp:Label ID="lblDNICliente" runat="server" Text="DNI del Cliente:" CssClass="col-sm-2 col-form-label" />
                            <div class="col-sm-3">
                                <asp:TextBox ID="txtDNICliente" runat="server" Enabled="false" CssClass="form-control" />
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <asp:Label ID="lblNombreCliente" runat="server" Text="Nombre del Cliente:" CssClass="col-sm-2 col-form-label" />
                            <div class="col-sm-3">
                                <asp:HiddenField ID="hdnIdCliente" runat="server" />
                                <asp:TextBox ID="txtNombreCliente" runat="server" Enabled="false" CssClass="form-control" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card border">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Detalle de la Orden de Venta</h5>
                    </div>
                    <div class="card-body">
                        <div class="mb-3 row">
                            <asp:Label ID="lblIDProducto" runat="server" Text="ID del Producto:" CssClass="col-sm-2 col-form-label" />
                            <div class="col-sm-3">
                                <asp:TextBox ID="txtIDProducto" runat="server" Enabled="false" CssClass="form-control"/>
                            </div>
                            <asp:LinkButton ID="btnBuscarProducto" runat="server" Text="<i class='fa-solid fa-magnifying-glass'></i>" CssClass="btn btn-primary col-sm-auto" OnClick="btnBuscarProducto_Click" />
                        </div>
                        <div class="mb-3 row">
                            <asp:Label ID="lblNombreProducto" runat="server" Text="Nombre:" CssClass="col-sm-2 col-form-label" />
                            <div class="col-sm-5">
                                <asp:TextBox ID="txtNombreProducto" runat="server" Enabled="false" CssClass="form-control"/>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <asp:Label ID="lblPrecioUnitProducto" runat="server" Text="Precio Unitario:" CssClass="col-sm-2 col-form-label" />
                            <div class="col-sm-3">
                                <asp:TextBox ID="txtPrecioUnitProducto" runat="server" Enabled="false" CssClass="form-control"/>
                            </div>
                        </div>
                        <div class="mb-3 row">
                            <asp:Label ID="lblCantidadUnidades" runat="server" Text="Cantidad:" CssClass="col-sm-2 col-form-label" />
                            <div class="col-sm-3">
                                <asp:TextBox ID="txtCantidadUnidades" runat="server" CssClass="form-control"/>
                            </div>
                            <div class="col-sm-3">
                                <asp:LinkButton ID="lbAgregarLOV" runat="server" CssClass="btn btn-success" Text="<i class='fa-solid fa-plus pe-2'></i> Agregar" OnClick="lbAgregarLOV_Click" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card border mt-4">
                    <div class="card-header bg-light">
                        <h5 class="card-title mb-0">Productos agregados a la orden</h5>
                    </div>
                    <div class="card-body table-responsive" style="max-height: 350px; overflow-y: auto;">
                        <asp:GridView 
                            ID="gvDetallesOrden"
                            runat="server"
                            AutoGenerateColumns="False"
                            CssClass="table table-striped table-hover table-bordered align-middle text-center"
                            EmptyDataText="No se han agregado productos aún."
                            ShowHeaderWhenEmpty="True">
                            <Columns>
                                <asp:BoundField DataField="IdProducto" HeaderText="ID Producto" />
                                <asp:BoundField DataField="NombreProducto" HeaderText="Producto" />
                                <asp:BoundField DataField="PrecioUnitario" HeaderText="Precio Unitario" DataFormatString="{0:C}" />
                                <asp:BoundField DataField="Cantidad" HeaderText="Cantidad" />
                                <asp:BoundField DataField="Subtotal" HeaderText="Subtotal" DataFormatString="{0:C}" />
                                <asp:TemplateField HeaderText="Acciones">
                                    <ItemTemplate>
                                        <asp:LinkButton 
                                            ID="btnEliminarItem" 
                                            runat="server" 
                                            CssClass="btn btn-danger btn-sm" 
                                            Text="<i class='fa-solid fa-trash'></i> Eliminar"
                                            CommandArgument='<%# Eval("IdProducto") %>'
                                            OnClick="btnEliminarItem_Click" />
                                    </ItemTemplate>
                                </asp:TemplateField>
                            </Columns>
                        </asp:GridView>
                    </div>
                    <div class="card-footer text-end">
                        <div class="mt-3 row">
                            <label class="col-sm-2 col-form-label">Subtotal:</label>
                            <div class="col-sm-2">
                                <asp:TextBox ID="txtSubtotal" runat="server" CssClass="form-control" Enabled="false" />
                            </div>

                            <label class="col-sm-1 col-form-label">IGV (18%):</label>
                            <div class="col-sm-2">
                                <asp:TextBox ID="txtIGV" runat="server" CssClass="form-control" Enabled="false" />
                            </div>

                            <label class="col-sm-1 col-form-label">Total:</label>
                            <div class="col-sm-2">
                                <asp:TextBox ID="txtTotal" runat="server" CssClass="form-control fw-bold text-success" Enabled="false" />
                            </div>
                        </div>
                        <asp:LinkButton 
                            ID="btnGuardarOrden" 
                            runat="server" 
                            CssClass="btn btn-primary"
                            Text="<i class='fa-solid fa-floppy-disk pe-2'></i> Guardar Orden"
                            OnClick="btnGuardarOrden_Click" />
                    </div>
                </div>
            </div>
            <div class="card-footer clearfix">
            </div>
        </div>
    </div>
    <div class="modal" id="form-modal-producto">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Búsqueda de Productos</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="container row">
                        <asp:GridView ID="gvProductos" runat="server" PageSize="5" AllowPaging="true" CssClass="table table-hover table-responsive table-striped" OnRowDataBound="gvProductos_RowDataBound">
                            <Columns>
                                <asp:TemplateField>
                                    <ItemTemplate>
                                        <asp:LinkButton runat="server" Text="<i class='fa-solid fa-check ps-2'></i> Seleccionar" CommandArgument='<%# Eval("id") %>' OnClick="lbSeleccionarProducto_Click" />
                                    </ItemTemplate>
                                </asp:TemplateField>
                            </Columns>
                        </asp:GridView>
                    </div>
                </div>
            </div>
        </div>
    </div>
</asp:Content>