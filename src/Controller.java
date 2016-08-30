import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {
    private boolean[] key = new boolean[256];
    private int[] keyPress = new int[256];
    private boolean[] keyDoubleTap = new boolean[256];

    public void update() {
        for(int i = 0; i < key.length; i++) {
            keyPress[i]--;
        }
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getExtendedKeyCode();
        if(keyPress[code] > 0) {
            keyDoubleTap[code] = true;
        }
        if(!key[code])
            keyPress[code] = 10;
        key[code] = true;

        if(code == 27) System.exit(1);
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getExtendedKeyCode();

        keyDoubleTap[code] = false;
        key[code] = false;
    }

    public boolean getKey(int i) {
        return key[i];
    }

    public int getKeyPress(int i) {
        return keyPress[i];
    }

    public boolean getKeyDoubleTap(int i) {
        return keyDoubleTap[i];
    }

    public void setKeyDoubleTap(int i, boolean state) {
        keyDoubleTap[i] = state;
    }
}
