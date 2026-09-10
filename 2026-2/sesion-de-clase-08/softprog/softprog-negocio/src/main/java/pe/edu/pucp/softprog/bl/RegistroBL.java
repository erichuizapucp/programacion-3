package pe.edu.pucp.softprog.bl;

import java.util.List;

public interface RegistroBL<T, ID> {
    List<T> findAll() throws BLException;
    T findById(ID id) throws BLException;
    void insert(T entidad) throws BLException;
    void update(T entidad) throws BLException;
    void delete(ID id) throws BLException;
}
