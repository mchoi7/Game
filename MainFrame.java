import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final static long FPS = 60, MSPF = 1000/FPS;
    private Scene scene;
    private Controller controller;
    private boolean pause;
    private Player player;
    private Block platform;

    private MainFrame() {
        super("GameEngine");
        setSize(1600, 1600);
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
        Font f = new Font("Courier", Font.BOLD, 36);
        setFont(f);
        initialize();
    }

    private void initialize() {
        scene = new Scene();
        player = new Player(new Point(getWidth()/2, getHeight()/2), new Box(48, 48));
        platform = new Block(new Point(getWidth()/2, getHeight()/2 + 100), new Box(1600, 160));
        scene.addActor(player);
        scene.addBlock(platform);
        controller = new Controller();
        player.setController(controller);
        addKeyListener(controller);
        addMouseListener(new MouseEditor(this));
        createBufferStrategy(3);
        new Thread(this::loop).start();
    }

    private void loop() {
        long startTime, elapsedTime, sleepTime;

        while(!pause) {
            startTime = System.currentTimeMillis();
            update();
            render();
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = MSPF - elapsedTime;
            try { Thread.sleep(sleepTime > 0 ? sleepTime : 0);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void render() {
        Graphics g = getBufferStrategy().getDrawGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawString("State: " + player.getState(), 100, 100);
        g.drawString("Direction: " + player.getDirection(), 100, 140);
        g.drawString("Press: " + player.getPress()[0], 100, 180);
        scene.render(g);
        g.dispose();
        getBufferStrategy().show();
    }

    private void update() {
        scene.update();
        controller.update();
    }

    public Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}