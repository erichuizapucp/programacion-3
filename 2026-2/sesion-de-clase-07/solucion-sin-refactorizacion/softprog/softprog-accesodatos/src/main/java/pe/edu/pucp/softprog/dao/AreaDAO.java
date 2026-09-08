package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.sql.SQLException;
import java.util.List;


public interface AreaDAO {
    List<Area> findAll() throws SQLException;
    Area findById(Integer id) throws SQLException;
    void insert(Area area) throws SQLException;
    void update(Area area) throws SQLException;
    void delete(Integer id) throws SQLException;
}
