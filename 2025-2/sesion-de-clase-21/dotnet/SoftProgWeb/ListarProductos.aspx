<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="ListarProductos.aspx.cs" Inherits="PUCP.SoftProg.Web.ListarProductos" %>
<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Listar Productos
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="cphScripts" runat="server">
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="cphContenido" runat="server">
    <div class="container">
        <div class="container row">
            <div class="row align-items-center">
                <div class="col text-end p-3">
                    <asp:LinkButton 
                        ID="lbRegistrar" 
                        CssClass="btn btn-success" 
                        runat="server" 
                        OnClick="lbRegistrar_Click" 
                        Text="<i class='fa-solid fa-plus pe-2'></i> Registrar Producto" />
                </div>
            </div>
        </div>
        <div class="container row">
            <asp:GridView ID="gvProductos" runat="server" AutoGenerateColumns="false"
                CssClass="table table-hover table-responsive table-striped"
                PageSize="5" AllowPaging="true" OnPageIndexChanging="gvProductos_PageIndexChanging">
                <Columns>
                    <asp:BoundField HeaderText="Id Producto" DataField="id" />
                    <asp:BoundField HeaderText="Nombre del Producto" DataField="nombre" />
                    <asp:BoundField HeaderText="Unidad de Medida" DataField="unidadMedida" />
                    <asp:BoundField HeaderText="Precio" DataField="precio" DataFormatString="{0:C2}" HtmlEncode="false" />
                    <asp:TemplateField>
                        <ItemTemplate>
                            <asp:LinkButton runat="server" Text="<i class='fa-solid fa-edit'></i>" CssClass="btn" OnClick="lbModificar_Click" CommandArgument='<%# Eval("id") %>' />
                            <asp:LinkButton runat="server" Text="<i class='fa-solid fa-trash'></i>" CssClass="btn" OnClick="lbEliminar_Click" CommandArgument='<%# Eval("id") %>' />
                        </ItemTemplate>
                    </asp:TemplateField>
                </Columns>
            </asp:GridView>
        </div>
    </div>
</asp:Content>
