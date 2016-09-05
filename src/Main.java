import javafx.print.Collation;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Main extends BasicGame {

    Input input;
    Polygon[] obstacles;
    Circle mouseCircle;
    Line[] rays;
    ArrayList<Line> shapeLines;

    public static void main(String[] args) {
        try{
            AppGameContainer gc;
            gc = new AppGameContainer(new Main("Hackathon Shiz! (Ray Tracer)"));
            gc.setDisplayMode(640, 480, false);
            gc.start();
        }
        catch (SlickException e){
            e.printStackTrace();
        }
    }

    public Main(String game){
        super(game);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        input = gameContainer.getInput();
        obstacles = new Polygon[]{
                new Polygon(new float[]{150, 80, 230, 100, 150, 300, 70, 200}),
                new Polygon(new float[]{490, 150, 510, 220, 350, 200}),
                new Polygon(new float[]{290, 300, 340, 400, 290, 450, 210, 400}),
                new Polygon(new float[]{300, 100, 340, 100, 340, 140, 300, 140})
        };
        rays = new Line[90];
        for(int i = 0; i < rays.length; i++){
            rays[i] = new Line(0,0,0,0);
        }

        shapeLines = new ArrayList<>();
        for(Polygon polygon : obstacles){
            for(Line shapeLine : getLinesFromShape(polygon)){
                shapeLines.add(shapeLine);
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        Vector2f mousePositionVector = new Vector2f(input.getMouseX(), input.getMouseY());
        for(int i = 0; i < rays.length; i++) {
            double angle = i * 4;
            Vector2f destinationVector = new Vector2f(700, 0);
            destinationVector.setTheta(angle);
            destinationVector.add(mousePositionVector);
            rays[i].set(mousePositionVector, destinationVector);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        for(Polygon p : obstacles){
            graphics.draw(p);
        }
        for(Line line : shapeLines){
            graphics.draw(line);
        }


//        for(Line l : rays){
//            graphics.draw(l);
//        }
        mouseCircle = new Circle(input.getMouseX(), input.getMouseY(), 5);
        graphics.fill(mouseCircle);
        graphics.draw(mouseCircle);

    }

    public ArrayList<Line> getLinesFromShape(Shape shapeToCheck){
        ArrayList<Line> lineList = new ArrayList<>();
        ArrayList<Vector2f> vectorLineList = new ArrayList<>();
        float[] pointsFromShape = shapeToCheck.getPoints();

        for(int i = 0; i < shapeToCheck.getPointCount() * 2; i +=2 ){
            lineList.add(new Line(pointsFromShape[i], pointsFromShape[i+1]));
        }
        return lineList;
    }
}
