package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.sql.SQLException;


public interface AreaDAO extends DAO<Area, Integer> {
    Area findByName(String nombre) throws SQLException;
}
