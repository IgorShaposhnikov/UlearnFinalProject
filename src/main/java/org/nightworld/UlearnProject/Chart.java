package org.nightworld.UlearnProject;

import java.io.File;
import java.io.IOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.data.category.DefaultCategoryDataset;

public class Chart {

    public static void createBarChart(
        DefaultCategoryDataset dataset,
        String title,
        String categoryAxisLabel,
        String valueAxisLabel,
        String saveUrl
    ) throws IOException {

        var chart = ChartFactory.createBarChart(
            title, categoryAxisLabel, valueAxisLabel, dataset
        );

        ChartUtils.saveChartAsPNG(new File(saveUrl), chart, 1920, 1080);
    }
}
