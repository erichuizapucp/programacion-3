package pe.edu.pucp.inf30.softprog.reportes;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import pe.edu.pucp.inf30.softprog.db.DBFactoryProvider;

/**
 *
 * @author eric
 */
@WebServlet(name = "ReporteOrdenVenta", urlPatterns = {"/reportes/ordenventa"})
public class ReporteOrdenVenta extends HttpServlet {
    private final String NOMBRE_REPORTE = "reportes/Reporte_OrdenVenta.jasper";
    private final String NOMBRE_LOGO = "imagenes/soft-prog-logo.png";
    private final String SUB_REPORTE = "reportes/Reporte_DetalleOrdenVenta.jasper";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/pdf");
        
        InputStream reporte = getClass().getClassLoader().getResourceAsStream(NOMBRE_REPORTE);
        if (reporte == null) {
            throw new FileNotFoundException("No se encontro el reporte: " + NOMBRE_REPORTE);
        }
        
        Map<String, Object> parametros = new HashMap();
        int idOrden = Integer.parseInt(request.getParameter("id"));
        parametros.put("id_orden", idOrden);
        
        InputStream logo = getClass().getClassLoader().getResourceAsStream(NOMBRE_LOGO);
        if (logo == null) {
            throw new FileNotFoundException("No se encontro el logo: " + NOMBRE_LOGO);
        }
        
        Image imagen = ImageIO.read(logo);
        parametros.put("logo", imagen);
        
        parametros.put("subreporte", SUB_REPORTE);
        
        try (Connection conn = DBFactoryProvider.getManager().getConnection()) {
            JasperPrint jp = JasperFillManager.fillReport(reporte, parametros, conn);
            JasperExportManager.exportReportToPdfStream(jp, response.getOutputStream());
        }
        catch (SQLException | ClassNotFoundException | JRException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                    "Error al generar el reporte: " + ex.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para generar el reporte de ordenes de venta";
    }
}
