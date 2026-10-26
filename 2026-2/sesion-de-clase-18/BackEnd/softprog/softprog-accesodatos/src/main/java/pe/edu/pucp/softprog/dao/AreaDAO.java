package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.sql.SQLException;
import java.util.List;


public interface AreaDAO extends DAO<Area, Integer> {
    Area findByName(String nombre) throws SQLException;
    List<Area> filterByName(String nombre) throws SQLException;
}
