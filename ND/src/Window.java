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

    // Position of top keys (in pixels)
    private int mapKeyX = 35;
    private int keysY = 20;
    private int mapPlanKeysGap = 10;
    private int planMenuKeysGap = 25;

    // Position of top keys (in pixels)
    private int mapCtrX = 40;
    private int mapCtrY = 950;
    private int mapCtrGap = 10;

    private JFrame window;

    // Top keys
    private NDButton mapKey;
    private NDButton planKey;
    private NDButton menuKey;
    private Menu menu;

    // Bottom keys
    private NDButton pickWaypointKey;
    private NDButton mapCtrAirplane;
    private NDButton mapCtrDest;
    private NDButton mapCtrCursor;
    private NDButton mapCtrCtrOn;


    // Constructor for GUI components and window behaviour
    public Window() {
        // Create the top three buttons
        mapKey = new NDButton(mapKeyX, keysY, "MAP", 0, "images/keysUnselected.png", "images/keysSelected.png");

        planKey = new NDButton(mapKey.getSize()[0] + mapKey.getX() + mapPlanKeysGap, keysY, "PLAN", 0, 
                                "images/keysUnselected.png", "images/keysSelected.png");

        menuKey = new NDButton(planKey.getSize()[0] + planKey.getX() + planMenuKeysGap, keysY, "MENU", 0, 
                                "images/menuKeyUnselected.png", "images/menuKeySelected.png");

        // Create the menu
        menu = new Menu(menuKey);

        // Create the bottom buttons
        pickWaypointKey = new NDButton(590, mapCtrY, "", 0, 
                                "images/pickWptUnselected.png", "images/pickWptSelected.png");

        mapCtrAirplane = new NDButton(mapCtrX, mapCtrY, "AIRPLANE", 0, 
                                "images/mapCtrKeyUnselected.png", "images/mapCtrKeySelected.png");

        mapCtrDest = new NDButton(mapCtrAirplane.getSize()[0] + mapCtrAirplane.getX() + mapCtrGap, mapCtrY, "DEST", 0,
                                "images/mapCtrKeyUnselected.png", "images/mapCtrKeySelected.png");

        mapCtrCursor = new NDButton(mapCtrDest.getSize()[0] + mapCtrDest.getX() + mapCtrGap, mapCtrY, "CURSOR", 0,
                                "images/mapCtrKeyUnselected.png", "images/mapCtrKeySelected.png");

        mapCtrCtrOn = new NDButton(mapCtrCursor.getSize()[0] + mapCtrCursor.getX() + mapCtrGap, mapCtrY, "CTR ON", 0,
                                "images/mapCtrKeyUnselected.png", "images/mapCtrKeySelected.png");

        // Functionality
        listeners();

        // On-start settings
        mapKey.select();
        menu.setMapMode();
        mapCtrAirplane.setVisibility(false);
        mapCtrDest.setVisibility(false);
        mapCtrCursor.setVisibility(false);
        mapCtrCtrOn.setVisibility(false);
        
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
                // Top main 3 keys: MAP PLAN MENU
                if (mapKey.isMouseHover(e.getPoint())) {
                    mapKey.select();
                    planKey.unselect();
                    menu.setMapMode();

                    mapCtrAirplane.setVisibility(false);
                    mapCtrDest.setVisibility(false);
                    mapCtrCursor.setVisibility(false);
                    mapCtrCtrOn.setVisibility(false);
                }
                if (planKey.isMouseHover(e.getPoint())) {
                    planKey.select();
                    mapKey.unselect();
                    menu.setPlanMode();

                    mapCtrAirplane.setVisibility(true);
                    mapCtrDest.setVisibility(true);
                    mapCtrCursor.setVisibility(true);
                    mapCtrCtrOn.setVisibility(true);
                }
                if (menuKey.isMouseHover(e.getPoint())) {
                    menuKey.toggle();
                    menu.toggleVisibility();
                }

                // Hide popupmenu when clicking elsewhere on screen
                if (!menu.isMouseHoverMenu(e.getPoint()) && !menuKey.isMouseHover(e.getPoint())) {
                    menuKey.unselect();
                    menu.setVisibility(false);
                }

                // Must add popup menu listeners here :(
                if (menu.isMapMode()) {
                    if (menu.mapSelectionVSD.isMouseHover(e.getPoint())) {
                        menu.mapSelectionVSD.toggle();
                    }
                    if (menu.mapSelectionWXR.isMouseHover(e.getPoint())) {
                        menu.mapSelectionWXR.select();
                        menu.mapSelectionTERR.unselect();
                    }
                    if (menu.mapSelectionTERR.isMouseHover(e.getPoint())) {
                        menu.mapSelectionTERR.select();
                        menu.mapSelectionWXR.unselect();
                    }
                    if (menu.mapSelectionTFC.isMouseHover(e.getPoint())) {
                        menu.mapSelectionTFC.toggle();
                    }
                    if (menu.mapSelectionAPT.isMouseHover(e.getPoint())) {
                        menu.mapSelectionAPT.toggle();
                    }
                    if (menu.mapSelectionWPT.isMouseHover(e.getPoint())) {
                        menu.mapSelectionWPT.toggle();
                    }
                    if (menu.mapSelectionSTA.isMouseHover(e.getPoint())) {
                        menu.mapSelectionSTA.toggle();
                    }
                    if (menu.mapSelectionPOS.isMouseHover(e.getPoint())) {
                        menu.mapSelectionPOS.toggle();
                    }
                    if (menu.mapSelectionDATA.isMouseHover(e.getPoint())) {
                        menu.mapSelectionDATA.toggle();
                    }
                    if (menu.mapSelectionVORL.isMouseHover(e.getPoint())) {
                        menu.mapSelectionVORL.toggle();
                    }
                    if (menu.mapSelectionVORR.isMouseHover(e.getPoint())) {
                        menu.mapSelectionVORR.toggle();
                    }
                    if (menu.mapExitKey.isMouseHover(e.getPoint())) {
                        menu.mapExitKey.toggle();
                    }
                }
                else {
                    if (menu.planSelectionAPT.isMouseHover(e.getPoint())) {
                        menu.planSelectionAPT.toggle();
                    }
                    if (menu.planSelectionWPT.isMouseHover(e.getPoint())) {
                        menu.planSelectionWPT.toggle();
                    }
                    if (menu.planSelectionSTA.isMouseHover(e.getPoint())) {
                        menu.planSelectionSTA.toggle();
                    }
                    if (menu.planSelectionDATA.isMouseHover(e.getPoint())) {
                        menu.planSelectionDATA.toggle();
                    }
                    if (menu.planExitKey.isMouseHover(e.getPoint())) {
                        menu.planExitKey.toggle();
                    }
                }

                if (pickWaypointKey.isMouseHover(e.getPoint())) {
                    pickWaypointKey.toggle();
                }

                if (mapCtrAirplane.isMouseHover(e.getPoint()) && !menu.isMapMode()) {
                    mapCtrAirplane.toggle();
                }
                if (mapCtrDest.isMouseHover(e.getPoint()) && !menu.isMapMode()) {
                    mapCtrDest.toggle();
                }
                if (mapCtrCursor.isMouseHover(e.getPoint()) && !menu.isMapMode()) {
                    mapCtrCursor.toggle();
                }
                if (mapCtrCtrOn.isMouseHover(e.getPoint()) && !menu.isMapMode()) {
                    mapCtrCtrOn.toggle();
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

        // Draw the main three keys
        mapKey.drawButton(g2, true);

        planKey.drawButton(g2, true);

        menuKey.drawButton(g2, true);


        // Draw the popupmenu
        menu.drawMenu(g2);


        // Bottom Keys
        pickWaypointKey.drawButton(g2, true);

        mapCtrAirplane.drawButton(g2, true);
        
        mapCtrDest.drawButton(g2, true);

        mapCtrCursor.drawButton(g2, true);

        mapCtrCtrOn.drawButton(g2, true);
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
