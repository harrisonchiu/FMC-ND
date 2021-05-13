import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import java.util.Arrays;

public class Window extends JPanel {
    // Constants
    // Any need for these to be used outside?
    public static final int WINDOW_WIDTH = 700;
    public static final int WINDOW_HEIGHT = 250;
    public static final Color BACKGROUND_COLOR = Color.BLACK;
    

    private int dx = 0;
    private int dy = 0;

    private Point mousePt;

    private JFrame window;
    private NavigationalDisplayKey mapKey;
    private NavigationalDisplayKey planKey;
    private NavigationalDisplayKey menuKey;

    private DropDownMenuFactory menu;

    // Constructor for GUI components and window behaviour
    public Window() {
        // Create the panel which everything is drawn on
        JPanel keyPanel = new JPanel();
        //keyPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 200));
        keyPanel.setBackground(BACKGROUND_COLOR);

        // Create the MAP Key
        mapKey = new NavigationalDisplayKey("MAP", JButton.CENTER, "images/mapKeysNonSelected.png", "images/mapKeysSelected.png");       
        keyPanel.add(mapKey);
        
        // Create the PLAN Key
        planKey = new NavigationalDisplayKey("PLAN", JButton.CENTER, "images/mapKeysNonSelected.png", "images/mapKeysSelected.png");
        keyPanel.add(planKey);

        // Create the menu key
        menuKey = new NavigationalDisplayKey("MENU", JButton.CENTER, "images/menuKeyNonSelected.png", "images/menuKeySelected.png");
        keyPanel.add(menuKey);

        String[] names = {"EXIT"};
        String[] icons = {"images/exitKeyNonSelected.png"};
        String[] icons1 = {"images/exitKeySelected.png"};
        menu = new DropDownMenuFactory(names, icons, icons1);

        // Add the camera
        mouseListeners();

        // Add functionality to the keys
        keyListeners();

        // Create the window itself and draw all the GUI components to it
        window = new JFrame("Route Visualizer");
        window.add(this);
        window.add(keyPanel, BorderLayout.NORTH);
        window.pack();

        // Window behaviours
        window.setFocusable(true); // for keyboard input
        window.setVisible(true);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Create a camera view controllable by mouse drag
    private void mouseListeners() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                dx += e.getX() - mousePt.x;
                dy += e.getY() - mousePt.y;
                mousePt = e.getPoint();

                repaint();
            }
        });
    }

    // Listeners for keys
    private void keyListeners() {
        // The MAP Key
        mapKey.addMouseListener(new MouseAdapter() { // move listener to a different file?
            public void mouseClicked(MouseEvent e) {
                mapKey.selected();
                planKey.deselected();
            }
        });

        // The PLAN Key
        planKey.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                planKey.selected();
                mapKey.deselected();
            }
        });

        // The MENU Key
        menuKey.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // Must be mouseClicked()
                if (!menuKey.getToggle()) {
                    menuKey.selected();
                    menu.show(e.getComponent(), -9, 65);
                }
            }
        });

        // The popup menu
        menu.addPopupMenuListener(new PopupMenuListener() {
            // Remove MENU Key highlight when popup menu closed 
            // (this is easier than making popup menu only close when MENU is clicked again)
            public void popupMenuCanceled(PopupMenuEvent e) {
                if (menuKey.getToggle()) {
                    menuKey.deselected();
                }
            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { }
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) { }
        });
    }


    // Draw on the window
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        super.paintComponent(g2);
        setBackground(BACKGROUND_COLOR);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Font font = new Font("Sans-Serif", Font.PLAIN, 18);
        g2.setFont(font);
    }

    // Runner
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
}