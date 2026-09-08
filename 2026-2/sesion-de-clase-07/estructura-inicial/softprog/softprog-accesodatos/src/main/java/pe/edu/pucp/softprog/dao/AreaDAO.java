package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.sql.SQLException;
import java.util.List;

public interface AreaDAO {
    List<Area> findAll() throws SQLException;
    Area findById(int id) throws SQLException;
    void insert(Area area) throws SQLException;
    void update(Area area) throws SQLException;
    void delete(int id) throws SQLException;
}
