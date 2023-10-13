package com.example.javafxcumprobability;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import java.io.*;
import java.util.Scanner;
import java.lang.Math;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        //Defining the x an y axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        //Setting labels for the axes
        xAxis.setLabel("Wind Speed Squared");
        yAxis.setLabel("Cumulative Probability");

        //Creating a line chart
        LineChart<String, Number> linechart = new LineChart<String, Number>(xAxis, yAxis);
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Cumulative Probability per Interval");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("OLS");

        //Preparing the data points
        File f = new File("cumProbability.txt");
        Scanner s = new Scanner(f);
        String line;

        // get k
        String [] getK = s.nextLine().split(" ");
        double k = Double.parseDouble(getK[1]);

        series1.getData().add(new XYChart.Data("0.0", Double.parseDouble("1.0")));
        series2.getData().add(new XYChart.Data("0.0", Double.parseDouble("1.0")));

        while(s.hasNextLine()){
            line = s.nextLine();
            String[] xy = line.split(" ");
            series1.getData().add(new XYChart.Data(String.valueOf(xy[0]), Double.parseDouble(xy[1])));
            series2.getData().add(new XYChart.Data(String.valueOf(xy[0]), Math.exp(-k*Double.parseDouble(xy[0]))));
        }
        //Setting the data to Line chart
        linechart.getData().addAll(series1,series2);
        //Creating a stack pane to hold the chart
        StackPane pane = new StackPane(linechart);
        pane.setPadding(new Insets(15, 15, 15, 15));
        pane.setStyle("-fx-background-color: BEIGE");
        //Setting the Scene



        Scene scene = new Scene(pane, 900, 650);
        stage.setTitle("Histogram Analysis");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}