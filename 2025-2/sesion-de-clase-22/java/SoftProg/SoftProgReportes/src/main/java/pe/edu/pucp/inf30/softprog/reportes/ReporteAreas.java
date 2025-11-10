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
@WebServlet(name = "ReporteAreas", urlPatterns = {"/reportes/areas"})
public class ReporteAreas extends HttpServlet {
    private final String NOMBRE_REPORTE = "reportes/Reporte_Areas.jasper";
    private final String NOMBRE_LOGO = "imagenes/soft-prog-logo.png";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/pdf");
        
        InputStream reporte = getClass().getClassLoader().getResourceAsStream(NOMBRE_REPORTE);
        if (reporte == null) {
            throw new FileNotFoundException("No se encontro el reporte: " + NOMBRE_REPORTE);
        }
        
        Map<String, Object> parametros = new HashMap();
        
        String autor = request.getParameter("autor");
        if (autor == null || autor.isEmpty() || autor.isBlank()) {
            throw new RuntimeException("El parametro: " + autor + ", es requerido.");
        }
        parametros.put("autor", autor);
        
        InputStream logo = getClass().getClassLoader().getResourceAsStream(NOMBRE_LOGO);
        if (logo == null) {
            throw new FileNotFoundException("No se encontro el logo: " + NOMBRE_LOGO);
        }
        
        Image imagen = ImageIO.read(logo);
        parametros.put("logo", imagen);
        
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
        return "Short description";
    }
}