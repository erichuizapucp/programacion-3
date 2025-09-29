<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="index.aspx.cs" Inherits="SoftProgWeb.WebForm1" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <form id="form1" runat="server">
            <div class="mb-3">
                <asp:Label ID="lblEmail" CssClass="form-label" 
                    AssociatedControlID="txtEmail" runat="server">
                    Email address
                </asp:Label>
                <asp:TextBox ID="txtEmail" TextMode="Email" CssClass="form-control" runat="server" />
            </div>
            <div class="mb-3">
                <asp:Label ID="lblPassword" CssClass="form-label" 
                    AssociatedControlID="txtPassword"
                    runat="server">
                    Password
                </asp:Label>
                <asp:TextBox ID="txtPassword" TextMode="Password" runat="server"></asp:TextBox>
            </div>
            <div class="mb-3 form-check">
                <asp:CheckBox ID="chkCheck" CssClass="form-check-input" runat="server" />
                <asp:Label ID="lblChkCheck" CssClass="form-check-label" AssociatedControlID="chkCheck" runat="server">
                    Check me out
                </asp:Label>
            </div>
            <asp:Button ID="btnSubmit" runat="server" CssClass="btn btn-primary" Text="Submit" OnClick="btnSubmit_Click" />
            <br />
            <asp:GridView ID="gvUsuarios" runat="server"></asp:GridView>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>
