package org.nightworld.UlearnProject;

import java.io.IOException;
import java.sql.SQLException;
import org.jfree.data.category.DefaultCategoryDataset;
import org.nightworld.UlearnProject.DTO.CSVReader;
import org.nightworld.UlearnProject.DTO.SQLManager;

public class Main {

    private static SQLManager sqlManager;
    private static final String csvFileName = "Объекты спорта.csv";
    private static final String chartFileName = "chart.png";

    public static void main(String[] args) throws SQLException, IOException {
        var sportObjects = new CSVReader().parse(csvFileName);

        sqlManager = new SQLManager();

        System.out.println("[Preparation] Проверка целостности данных в базе данных...");
        for (var i : sportObjects) {
            sqlManager.addSportObject(i);
        }
        System.out.println("[Preparation] Проверка целостности данных в базе данных завершена!");

        Task1();
        Task2();
        Task3();
    }

    // Создать график с гистограммами, показывающий количество объектов спорта по областям
    // (объединить Москву и Московскую область)
    private static void Task1() throws SQLException, IOException {
        System.out.println("[Task1] Процесс сбора данных и создания гистограммы...");
        var dataset = new DefaultCategoryDataset();

        var rs = sqlManager.executeQuery("""
            SELECT DISTINCT s.name, count(*) OVER (partition by subject_of_russia_id) DESC 
            FROM sport_object
            INNER JOIN subject_of_russia s ON s.id = subject_of_russia_id
            """);

        while (rs.next()) {
            dataset.addValue(rs.getInt(2), rs.getString(1), "");
        }

        Chart.createBarChart(
            dataset,
            "Гистограмма, показывающая количество объектов спорта по областям",
            "Область",
            "Количество объектов",
            chartFileName
        );
        System.out.println("[Task1] Гистограмма успешно создана!");
    }

    // Task 2
    // Вывести среднее количество объектов спорта в регионах в консоль
    private static void Task2() throws SQLException {
        System.out.println("[Task2] Среднее количество объектов спорта в регионах: " +
            sqlManager.executeQuery("SELECT COUNT(*) FROM sport_object").getInt(1)
                / sqlManager.executeQuery("SELECT COUNT(*) FROM subject_of_russia").getInt(1));
    }

    // Task 3
    // Отсортировать регионы по количеству объектов спорта в них и вывести
    // в консоль первые 3 с самым большим количеством
    private static void Task3() throws SQLException {
        System.out.println("[Task3] Регионы с самым большим количеством объектов спорта: ");
        var rs = sqlManager.executeQuery("""
            SELECT DISTINCT s.name FROM sport_object
                INNER JOIN subject_of_russia s ON s.id = subject_of_russia_id
                ORDER BY count(*) OVER (partition by subject_of_russia_id) DESC
                LIMIT 3
            """);
        var i = 1;
        while (rs.next()) {
            System.out.println("  " + i + ") " + rs.getString(1));
            i++;
        }
    }
}
