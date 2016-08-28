import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {
    private int[] keysTimer = new int[256];
    private boolean[] keys = new boolean[256];

    public void update() {
        for(int i = 0; i < keys.length; i++)
            keysTimer[i]--;
    }

    public void keyPressed(KeyEvent e) {
        if(!keys[e.getExtendedKeyCode()]) {
            keysTimer[e.getExtendedKeyCode()] = 0;
            keys[e.getExtendedKeyCode()] = true;
        }
        if(e.getExtendedKeyCode() == 27) System.exit(1);
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getExtendedKeyCode()] = false;
    }

    public boolean getKey(int index) {
        return keys[index];
    }

    public int getKeyTimer(int index) {
        return keysTimer[index];
    }
}
