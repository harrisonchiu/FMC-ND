import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.lang.Runnable;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Point;
import java.awt.Font;
import java.awt.Polygon;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import java.util.Arrays;

public class Window extends JPanel {
    // Constants
    public static final int WINDOW_WIDTH = 700;
    public static final int WINDOW_HEIGHT = 1050;
    public static final Color BACKGROUND_COLOR = Color.BLACK;

    // Position of keys (in pixels)
    private int mapKeyX = 35;
    private int keysY = 20;
    private int mapPlanKeysGap = 10;
    private int planMenuKeysGap = 25;

    private JFrame window;

    private NDButton mapKey;
    private NDButton planKey;
    private NDButton menuKey;
    private Menu menu;

    private int[] menuXPos;
    private int[] menuYPos;


    // Constructor for GUI components and window behaviour
    public Window() {
        // Create the buttons
        mapKey = new NDButton(mapKeyX, keysY, "MAP", 0, "images/keysUnselected.png", "images/keysSelected.png");

        planKey = new NDButton(mapKey.getSize()[0] + mapKey.getX() + mapPlanKeysGap, keysY, "PLAN", 0, 
                                "images/keysUnselected.png", "images/keysSelected.png");

        menuKey = new NDButton(planKey.getSize()[0] + planKey.getX() + planMenuKeysGap, keysY, "MENU", 0, 
                                "images/menuKeyUnselected.png", "images/menuKeySelected.png");

        menu = new Menu(menuKey);

        listeners();

        // Create the window itself and draw all the GUI components to it
        window = new JFrame("Route Visualizer");
        window.add(this);
        window.pack();

        // Window behaviours
        window.setFocusable(true);
        window.setVisible(true);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void listeners() {
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (mapKey.isMouseHover(e.getPoint())) {
                    mapKey.select();
                    planKey.unselect();
                    menu.setMapMode();
                }
                if (planKey.isMouseHover(e.getPoint())) {
                    planKey.select();
                    mapKey.unselect();
                    menu.setPlanMode();
                }
                if (menuKey.isMouseHover(e.getPoint())) {
                    menuKey.toggle();
                    menu.toggleVisibility();
                }
                repaint();
            }
        });
    }

    // Draw on the window
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        super.paintComponent(g2);
        setBackground(BACKGROUND_COLOR);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("Sans-Serif", Font.PLAIN, 18));

        // Draw the keys
        g2.drawImage(mapKey.getImage(), mapKey.getX(), mapKey.getY(), null);
        mapKey.drawStringCenter(g2);

        g2.drawImage(planKey.getImage(), planKey.getX(), planKey.getY(), null);
        planKey.drawStringCenter(g2);

        g2.drawImage(menuKey.getImage(), menuKey.getX(), menuKey.getY(), null);
        menuKey.drawStringCenter(g2);

        if (menu.getVisibility()) {
            menu.drawMenu(g2);
        }
    }

    // Runner
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() { 
                new Window();
            }
        });
    }
}
