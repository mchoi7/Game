import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Scene {
    private List<Actor> actors = new ArrayList<>();
    private List<Block> blocks = new ArrayList<>();

    public Scene() {
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void update() {
        actors.forEach(Updatable::update);
        actors.forEach(actor -> actor.process(blocks));
    }

    public void render(Graphics g) {
        actors.forEach(actor -> actor.render(g));
        blocks.forEach(block -> block.render(g));
    }
}
