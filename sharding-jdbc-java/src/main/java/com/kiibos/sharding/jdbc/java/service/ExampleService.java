package com.kiibos.sharding.jdbc.java.service;

import java.sql.SQLException;

public interface ExampleService {


    void initEnvironment() throws SQLException;

    void cleanEnvironment() throws SQLException;

    void processSuccess() throws SQLException;

    void processFailure() throws SQLException;

    void printData() throws SQLException;

}
