package pe.edu.pucp.inf30.softprog.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import pe.edu.pucp.inf30.softprog.config.DBManager;

/**
 *
 * @author eric
 */
@WebServlet(name = "ReporteAreas", urlPatterns = {"/reportes/areas"})
public class ReporteAreas extends HttpServlet {
    private final String NOMBRE_REPORTE = 
            "reportes/Reporte-Areas.jasper";
    
    @Override
    protected void doGet(
            HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/pdf");
        
        try {
            InputStream reporte = 
                    getClass().getClassLoader()
                            .getResourceAsStream(
                                    this.NOMBRE_REPORTE);

            if (reporte == null) {
                throw new FileNotFoundException("No se encontró el archivo 'areas.jasper'");
            }

            Map<String, Object> parametros = 
                    new HashMap<>();
            parametros.put("author", "Juan Perez");

            //  Aqui no hay conexion a base de datos
            // JasperPrint jp = JasperFillManager.fillReport(jr, parametros, new JREmptyDataSource());
            try (Connection conexion = DBManager.getInstance().getConnection()) {
                JasperPrint jp = 
                        JasperFillManager.fillReport(reporte, 
                                parametros, 
                                conexion);
                JasperExportManager.exportReportToPdfStream(
                        jp, response.getOutputStream());
            }
            catch (SQLException | ClassNotFoundException ex) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                               "Error al generar el reporte: " + ex.getMessage());
            }
        }
        catch (IOException | JRException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                               "Error al generar el reporte: " + ex.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Esta servlet genera el reporte de areas.";
    }
}
