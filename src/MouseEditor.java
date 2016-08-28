import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Math.abs;

public class MouseEditor extends MouseAdapter {
    private MainFrame mainFrame;
    private double mouseX, mouseY;
    public MouseEditor(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        if(SwingUtilities.isRightMouseButton(e))
            mainFrame.getScene().addBlock(new Block(new Point((mouseX + e.getX())/2, (mouseY + e.getY())/2), new Box(abs(mouseX - e.getX()), abs(mouseY - e.getY()))));
    }
}
