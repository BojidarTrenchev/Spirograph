import javafx.scene.canvas.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.application.*;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.Random;
import java.lang.Math;

public class Spiro extends Application{
	
	private double time = 0;

	private GraphicsContext graphics;
	
	private int windowWidth = 700;
	private int windowHeight = 700;
	
	//Variables which can be adjusted in order to get different spirographs
	private boolean useLine = true;
	private double frameRate = 0.5;
	private double r = 2.2123234234213;
	private double R = 3.152234234;
	private double a = 1.24234;
	private double scale = 30;
	
	private double newX;
	private double newY;
	private double oldX;
	private double oldY;
	
	private Random rand = new Random();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {		
		stage.setScene(new Scene(CreateShape()));
		stage.show();
	}
	
	private Parent CreateShape() {
		Pane root = new Pane();
		root.setPrefSize(windowWidth, windowHeight);
		
		Canvas canvas = new Canvas(windowWidth, windowHeight);
		graphics = canvas.getGraphicsContext2D();
		
		//Timer which is called usually 60 fps
		
		AnimationTimer timer = new AnimationTimer() {
			public void handle(long now) {
				time += frameRate;
				draw();
			}
		};
		
		/*
		for(int i = 0; i < 10000; i++)
		{
			time += frameRate;
			draw();
		}
		*/
		
		timer.start();
		root.getChildren().add(canvas);
		return root;
	}
	
	//Draw one line or point per frame
	private void draw() {		
		Point2D point = calculatePoint();
		
		graphics.setStroke(getRandomColor());
		
		//Get the position of the point or lne
		newX = windowWidth / 2 + point.getX();
		newY = windowHeight / 2 +  point.getY();
		
		//Draw it
		if(oldX != 0 && oldY != 0){
			if(useLine){
				graphics.strokeLine(newX, newY, oldX, oldY);
			}
			else{
				graphics.strokeOval(newX, newY, 3,3 );	
			}
		}
	
		oldX = newX;
		oldY = newY;
	}

	private Point2D calculatePoint() {
        double x = (R+r) * Math.cos(time) - (r + a) * Math.cos(((R+r)/r)*time);
        double y = (R+r) * Math.sin(time) - (r + a) * Math.sin(((R+r)/r)*time);
        
        return new Point2D(x,y).multiply(scale);
	}
	
	private Color getRandomColor() {
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color randomColor = new Color(r, g, b, 1);
		
		return randomColor;
	}
}