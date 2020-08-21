package com.kiibos.sharding.jdbc.java.repository;

import java.sql.SQLException;
import java.util.List;

public interface CommonRepository<T,P> {

    void createTableIfNotExists() throws SQLException;

    void dropTable() throws SQLException;

    void truncateTable() throws SQLException;

    P insert(T entity) throws SQLException;

    void delete(P primaryKey) throws SQLException;

    List<T> selectAll() throws SQLException;

}
