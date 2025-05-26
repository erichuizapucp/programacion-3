<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="ListarAreas2.aspx.cs" Inherits="PUCP.Edu.Pe.SoftProg.Web.ListarAreas2" %>
<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="cphScripts" runat="server">
</asp:Content>
<asp:Content ID="Content3" runat="server" ContentPlaceHolderID="cphContenido">
    <div class="container">
        <div class="container row">
            <div class="row align-items-center">
                <div class="col text-end p-3">
                    <asp:LinkButton
                        ID="lbRegistrar"
                        CssClass="btn btn-success"
                        runat="server"
                        Text="<i class='fa-solid fa-plus pe-2'></i> Registrar Area" OnClick="lbRegistrar_Click">
                </asp:LinkButton>
                </div>
            </div>
        </div>
        <div class="container row">
            <asp:GridView
                ID="gvAreas"
                runat="server"
                AutoGenerateColumns="false"
                CssClass="table table-hover table-responsive table-striped"
                PageSize="5"
                AllowPaging="true" OnPageIndexChanging="gvAreas_OnPageIndexChanging">

                <Columns>
                    <asp:BoundField HeaderText="Id Area" DataField="Id" />
                    <asp:BoundField HeaderText="Nombre del Area" DataField="Nombre" />
                    <asp:TemplateField>
                        <ItemTemplate>
                            <asp:LinkButton runat="server" Text="<i class='fa-solid fa-edit'></i>" CssClass="btn" ID="btnModificar" OnClick="btnModificar_Click" CommandArgument='<%# Eval("Id") %>' />
                            <asp:LinkButton runat="server" Text="<i class='fa-solid fa-trash'></i>" CssClass="btn" ID="btnEliminar" OnClick="btnEliminar_Click" CommandArgument='<%# Eval("Id") %>' />
                        </ItemTemplate>
                    </asp:TemplateField>
                </Columns>

            </asp:GridView>
        </div>
    </div>
</asp:Content>
