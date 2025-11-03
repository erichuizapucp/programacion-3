<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="ListarOrdenesVenta.aspx.cs" Inherits="PUCP.SoftProg.Web.ListarOrdenesVenta" %>
<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Listado de Órdenes de Venta
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="cphScripts" runat="server">
</asp:Content>
<asp:Content ID="Content3" ContentPlaceHolderID="cphContenido" runat="server">
    <asp:Panel ID="pnlOrdenes" runat="server" CssClass="panel">
        <h4>Órdenes registradas</h4>

        <asp:GridView ID="gvOrdenes" runat="server"
            CssClass="table table-striped table-bordered"
            AutoGenerateColumns="False"
            AllowPaging="True"
            PageSize="10"
            OnPageIndexChanging="gvOrdenes_PageIndexChanging">

            <Columns>
                <asp:BoundField DataField="Id" HeaderText="ID" />
                <asp:TemplateField HeaderText="Cliente">
                    <ItemTemplate>
                        <%# Eval("cliente.nombre") + " " + Eval("cliente.apellidoPaterno") %>
                    </ItemTemplate>
                </asp:TemplateField>
                <asp:BoundField DataField="total" HeaderText="Total (S/.)" DataFormatString="{0:N2}" />
                <asp:CheckBoxField DataField="activo" HeaderText="Activo" />

                <asp:TemplateField HeaderText="Acciones">
                    <ItemTemplate>
                        <asp:HyperLink runat="server"
                            NavigateUrl='<%# "~/DetalleOrdenVenta.aspx?id=" + Eval("id") %>'
                            Text="Ver Detalle" />
                    </ItemTemplate>
                </asp:TemplateField>
            </Columns>

            <PagerStyle CssClass="pager" HorizontalAlign="Center" />
        </asp:GridView>
    </asp:Panel>
</asp:Content>
