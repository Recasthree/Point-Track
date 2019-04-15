import java.util.Scanner;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
 
public class ElevationPlot extends Application {
 
    public void start(Stage stage) {
    	
    	//read the file
    	Track walkTrack = new Track();
    	System.out.println("Please input the name of the file you want to make the chart: ");
    	Scanner input = new Scanner(System.in);
    	String fileName = input.nextLine();
    	try {
    		walkTrack.readFile(fileName);
    	} catch ( FileNotFoundException e ) {
    		e.printStackTrace();
    		System.exit(1);
    	}
    	input.close();		//close the input
    	
    	//define the title
        stage.setTitle("Elevation Plot");
        
        //define the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Elevation (m)");
        yAxis.setLabel("Distance (m)");
        
        //create the chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis, yAxis);
        //define the title
        lineChart.setTitle("Elevation Plot");
        
        //define a series
        XYChart.Series series = new XYChart.Series();
        series.setName("walk.csv");
        
        Point p = new Point();
        
        //populating the series with data
        series.getData().add( new XYChart.Data( 0, walkTrack.get(0).getElevation() ) );
        for( int i = 1; i < walkTrack.size(); i++ ) {
        	p = walkTrack.get(i);
        	series.getData().add( new XYChart.Data( p.greatCircleDistance(walkTrack.get(0), p), p.getElevation() ) );
        }
        lineChart.setCreateSymbols(false);
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
        
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}