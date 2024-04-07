package ru.itis.javafxmavenarchetypes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Stack;

public class GrahamAlgorithm extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public static Stack<Point> grahamAlgoritm(Point[] points) {
        grahamFirstStep(points);
        grahamSecondStep(points);
        return grahamThirdStep(points);
    }

    public static void grahamFirstStep(Point[] points) {

        for (int i = 1; i < points.length; ++i) {
            if (points[i].getX() < points[0].getX()) {
                Point temp = points[0];
                points[0] = points[i];
                points[i] = temp;
            }
        }
    }


    public static void grahamSecondStep(Point[] points) {
        quickSort(points, 1, points.length - 1, points[0]);
    }

    public static void quickSort(Point[] points, int low, int high, Point pivot) {
        if (low < high) {
            int pi = partition(points, low, high, pivot);

            quickSort(points, low, pi - 1, pivot);
            quickSort(points, pi + 1, high, pivot);
        }
    }

    public static int partition(Point[] points, int low, int high, Point pivot) {
        Point pivotValue = points[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (rotate(pivot, pivotValue, points[j])) {
                i++;
                Point temp = points[i];
                points[i] = points[j];
                points[j] = temp;
            }
        }
        Point temp = points[i + 1];
        points[i + 1] = points[high];
        points[high] = temp;

        return i + 1;
    }


    public static Stack<Point> grahamThirdStep(Point[] points) {
        Stack<Point> stack = new Stack<>();
        stack.add(points[0]);
        stack.add(points[1]);
        for (int i = 2; i < points.length; ++i) {
            while (rotate(stack.elementAt(stack.size() - 2),
                    stack.elementAt(stack.size() - 1), points[i])) {
                stack.pop();
            }
            stack.push(points[i]);
        }
        return stack;
    }

    public static boolean rotate(Point R, Point b, Point c) {
        return ((b.getX() - R.getX()) * (c.getY()- b.getY()) - (b.getY()- R.getY()) * (c.getX() - b.getX())) < 0;
    }

    @Override
    public void start(Stage primaryStage) {

        Pane pane = new Pane();

        Line xAxis = new Line(0, 700, 1200, 700);
        Line yAxis = new Line(100, 0, 100, 1200);
        pane.getChildren().addAll(xAxis, yAxis);

        Point[] points = new Point[10];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(Math.random() * 400, Math.random() * 400);
        }
        Stack<Point> stack = grahamAlgoritm(points);

        for (Point point : points) {
            pane.getChildren().add(new Circle(point.getX() + 100, point.getY(), 5));
        }

        Point[] stackPoints = stack.toArray(new Point[0]);

        for (int i = 0; i < stackPoints.length - 1; ++i) {
            pane.getChildren().add(new Line(stackPoints[i].getX() + 100, stackPoints[i].getY(), stackPoints[i + 1].getX() + 100, stackPoints[i + 1].getY()));
        }
        pane.getChildren().add(new Line(stackPoints[0].getX() + 100, stackPoints[0].getY(), stackPoints[stackPoints.length - 1].getX() + 100, stackPoints[stackPoints.length - 1].getY()));
        Scene scene = new Scene(pane, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Coordinate System");
        primaryStage.show();
    }

}
