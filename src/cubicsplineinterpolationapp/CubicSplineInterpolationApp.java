package cubicsplineinterpolationapp;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class CubicSplineInterpolationApp extends Application {

    @Override
    public void start(Stage stage) {
        int size = 37;
        int size_n = 6;
        int size_n_a = 7;
        double[] x = new double[size];
        double[] y = new double[size];
        double[] S = new double[size];
        
        x[0] = -2;
        double dx = 0.1;
        for(int i = 1; i < size; i++)
            x[i] = x[i-1] + dx;
        
        stage.setTitle("Cubic Spline Interpolation");
        
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("X");
        yAxis.setLabel("Y");
        
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Cubic spline interpolation of function exp(sinx)");
        
        XYChart.Series series1 = new XYChart.Series();                          
        XYChart.Series series2 = new XYChart.Series();                          
        series1.setName("F(x)");
        series2.setName("Cubic spline interpolation's curve");
        
        ObservableList datas1 = FXCollections.observableArrayList();
        ObservableList datas2 = FXCollections.observableArrayList();
        
        for(int i = 0; i < size; i++){
            y[i] = cos(sin(cos(x[i])));                                      
            datas1.add(new XYChart.Data(x[i], y[i]));
        }
        
      
        final double h = x[6] - x[0];
        
        double alpha = h;
        double beta = 4 * h;
        double gamma = h;
        
        double[] fi = new double [size_n_a];
        
        for(int i = 1; i < size_n_a-1; i++)
            fi[i] = 6 * (((y[(i+1)*6] - y[i*6]) / h) - ((y[i*6] - y[(i-1)*6]) / h));  
        
        double[] p = new double[size_n_a];
        double[] q = new double[size_n_a];
        p[1] = 0;
        q[1] = 0;
        
        for(int i = 1; i < size_n_a-1; i++){
            p[i+1] = -gamma / (beta + alpha * p[i]);
            q[i+1] = (fi[i]- alpha * q[i]) / (beta + alpha * p[i]);
        }
        
        double[] c = new double[size_n_a];
        c[0] = 0;
        c[size_n_a-1] = 0;
        
        for(int i = size_n_a-2; i > 0; i--)
            c[i] = p[i+1] * c[i+1] + q[i+1];
        
        double[] b = new double[size_n_a];
        
        double[] d = new double[size_n_a];
        for(int i = 0; i < size_n_a-1; i++){
            b[i+1] = ((y[i*6] - y[(i+1)*6]) / h) - (0.5 * c[i+1] * h) - (1/6 * (c[i] - c[i+1]) * h);
            d[i+1] = (c[i] - c[i+1]) / h;
        }
        
        for(int i = 0; i < size_n; i++){
            S[i] = y[size_n] + b[1] * (x[size_n] - x[i]) + 0.5 * c[1] * Math.pow(x[size_n] - x[i], 2) + 1/6 * d[1] * Math.pow(x[size_n] - x[i], 3);
            datas2.add(new XYChart.Data(x[i], S[i]));
        }
        
        for(int i = size_n; i < size_n * 2; i++){
            S[i] = y[size_n*2] + b[2] * (x[size_n*2] - x[i]) + 0.5 * c[2] * Math.pow(x[size_n*2] - x[i], 2) + 1/6 * d[2] * Math.pow(x[size_n*2] - x[i], 3);
            datas2.add(new XYChart.Data(x[i], S[i]));
        }
        
        for(int i = size_n*2; i < size_n * 3; i++){
            S[i] = y[size_n*3] + b[3] * (x[size_n*3] - x[i]) + 0.5 * c[3] * Math.pow(x[size_n*3] - x[i], 2) + 1/6 * d[3] * Math.pow(x[size_n*3] - x[i], 3);
            datas2.add(new XYChart.Data(x[i], S[i]));
        }
        
        for(int i = size_n*3; i < size_n * 4; i++){
            S[i] = y[size_n*4] + b[4] * (x[size_n*4] - x[i]) + 0.5 * c[4] * Math.pow(x[size_n*4] - x[i], 2) + 1/6 * d[4] * Math.pow(x[size_n*4] - x[i], 3);
            datas2.add(new XYChart.Data(x[i], S[i]));
        }
    
        for(int i = size_n*4; i < size_n * 5; i++){
            S[i] = y[size_n*5] + b[5] * (x[size_n*5] - x[i]) + 0.5 * c[5] * Math.pow(x[size_n*5] - x[i], 2) + 1/6 * d[5] * Math.pow(x[size_n*5] - x[i], 3);
            datas2.add(new XYChart.Data(x[i], S[i]));
        }
        
        for(int i = size_n*5; i < size_n * 6 + 1; i++){
            S[i] = y[size_n*6] + b[6] * (x[size_n*6] - x[i]) + 0.5 * c[6] * Math.pow(x[size_n*6] - x[i], 2) + 1/6 * d[6] * Math.pow(x[size_n*6] - x[i], 3);
            datas2.add(new XYChart.Data(x[i], S[i]));
        }
        
        double[] Rp = new double[size];
        
        for(int i = 0; i < size; i++){
            Rp[i] = S[i] - y[i];
            System.out.println(Rp[i]);
        }
        
        series1.setData(datas1);
        series2.setData(datas2);
        lineChart.getData().add(series1);
        lineChart.getData().add(series2);
        
        Scene scene  = new Scene(lineChart,800,600);
        
        stage.setScene(scene);
        stage.show();
    
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
