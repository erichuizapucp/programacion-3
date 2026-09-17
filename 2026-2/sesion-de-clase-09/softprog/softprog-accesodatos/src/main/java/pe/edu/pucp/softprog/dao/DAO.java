package pe.edu.pucp.softprog.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO <T, ID> {
    List<T> findAll() throws SQLException;
    T findById(ID id) throws SQLException;
    void insert(T modelo) throws SQLException;
    void update(T modelo) throws SQLException;
    void delete(ID id) throws SQLException;
}
