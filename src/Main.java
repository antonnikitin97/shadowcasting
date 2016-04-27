import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by anton on 27/04/2016.
 */
public class Main extends BasicGame {

    Input input;
    Polygon[] obstacles;
    Circle mouseCircle;
    Line[] rays;

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
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        Vector2f mouseVector = new Vector2f(input.getMouseX(), input.getMouseY());
        for(int i = 0; i < rays.length; i++) {
            double angle = i * 4;
            Vector2f dest = new Vector2f(700, 0);
            dest.setTheta(angle);
            dest.add(mouseVector);
            rays[i].set(mouseVector, dest);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        for(Polygon p : obstacles){
            graphics.draw(p);
        }
        for(Line l : rays){
            for(Polygon p : obstacles){
                if(l.intersects(p)){
                    graphics.draw(l);
                }
            }
        }
//        for(Line l : rays){
//            graphics.draw(l);
//        }
        mouseCircle = new Circle(input.getMouseX(), input.getMouseY(), 5);
        graphics.fill(mouseCircle);
        graphics.draw(mouseCircle);

    }
}
