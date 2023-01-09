package org.nightworld.UlearnProject.DTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.nightworld.UlearnProject.DTO.models.SportObject;
import org.nightworld.UlearnProject.DTO.models.SubjectOfRussia;
import org.sqlite.SQLiteConfig;

public class SQLManager {

    private static final String dbPath = "jdbc:sqlite:";
    private static final String dbFileName = "SportObject.db";
    private final Connection conn;
    private final Statement statement;

    public SQLManager() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        conn = DriverManager.getConnection(dbPath + dbFileName, config.toProperties());
        statement = conn.createStatement();
        addSportObjectTable();
        addSubjectOfRussia();
    }

    private void addSportObjectTable() throws SQLException {
        var sql = """
            CREATE TABLE IF NOT EXISTS sport_object(
                id integer not null primary key,
                name text not null,
                subject_of_russia_id integer not null,
                full_address text not null,
                registration_date text not null,
                FOREIGN KEY (subject_of_russia_id) REFERENCES subject_of_russia(id)
            )
            """;

        statement.execute(sql);
    }

    private void addSubjectOfRussia() throws SQLException {
        var sql = """
            CREATE TABLE IF NOT EXISTS subject_of_russia(
                 id integer not null primary key,
                 name text not null UNIQUE
            )
            """;

        statement.execute(sql);
    }

    public void addSportObject(SportObject obj) throws SQLException {
        addSubjectOfRussia(obj.getSubjectOfRussia());

        var sql = """
            INSERT or IGNORE INTO sport_object(
                id, name, subject_of_russia_id, full_address, registration_date
            ) VALUES (?,?,?,?,?)
            """;

        var preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, obj.getId());
        preparedStatement.setString(2, obj.getName());
        preparedStatement.setInt(3, obj.getSubjectOfRussia().getId());
        preparedStatement.setString(4, obj.getFullAddress());
        preparedStatement.setString(5, obj.getRegistrationDate().toString());
        preparedStatement.executeUpdate();
    }

    public void addSubjectOfRussia(SubjectOfRussia subjectOfRussia) throws SQLException {
        var sql = """
            INSERT or IGNORE INTO subject_of_russia(
                id, name
            ) VALUES (?,?)
            """;

        var preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, subjectOfRussia.getId());
        preparedStatement.setString(2, subjectOfRussia.getName());
        preparedStatement.executeUpdate();
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        return conn.createStatement().executeQuery(sql);
    }
}
