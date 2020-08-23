import java.util.Arrays;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.Image;

class Menu {
    private NDButton menuKey;

    private int[] menuXPos = new int[6];
    private int[] menuYPos = new int[6];
    private int menuEdgeX1; // X coord of left most edge of the menu
    private int menuEdgeX2; // X coord of right most edge of the menu
    private int menuEdgeY1; // Y coord of top most edge of the menu
    private int menuEdgeY2; // Y coord of bottom most edge of the menu
    private int menuWidth;
    private int menuHeight;

    // Selections in the menu
    private String selectionIconDir = "images/selectionUnselected.png";
    private String selectedIconDir = "images/selectionSelected.png";

    private String selectionDiaIconDir = "images/diamondUnselected.png";
    private String selectedDiaIconDir = "images/diamondSelected.png";

    private String selectionDisabledIconDir = "images/selectionDisabled.png";

    private String exitKeyUnselectedIconDir = "images/exitKeyUnselected.png";
    private String exitKeySelectedIconDir = "images/exitKeySelected.png";

    // Different objects since different positions
    // Can use methods to change XY pos and keep same object
    // Could be more efficient; for later

    // Map selections in menu
    private NDButton mapSelectionVSD;
    private NDButton mapSelectionWXR;
    private NDButton mapSelectionTERR;
    private NDButton mapSelectionTFC;
    private NDButton mapSelectionAPT;
    private NDButton mapSelectionWPT;
    private NDButton mapSelectionSTA;
    private NDButton mapSelectionPOS;
    private NDButton mapSelectionDATA;
    private NDButton mapSelectionVORL;
    private NDButton mapSelectionVORR;
    private NDButton mapSelectionFIR;
    private NDButton mapSelectionAIRSP; 
    private NDButton mapExitKey;

    // Plan selections in menu
    private NDButton planSelectionAPT;
    private NDButton planSelectionWPT;
    private NDButton planSelectionSTA;
    private NDButton planSelectionDATA;
    private NDButton planSelectionFIR;
    private NDButton planSelectionAIRSP;
    private NDButton planExitKey;

    private boolean mapMode = true;
    private boolean visible = false;

    // Selection positions in the menu
    private int selectionXStart; // X coord of first selection relative to top left menu coords
    private int selectionYStart; // Y coord of first selection relative to top left menu coords
    private int eachSelectionGap;
    private int iconTextGap;

    Menu(NDButton parentButton) {
        menuKey = parentButton;

        setMapMenu();

        // Set the position of the first selection
        // it will determine the rest of the selections' positions
        selectionXStart = menuEdgeX1 + 12;
        selectionYStart = menuEdgeY1 + 20;
        eachSelectionGap = 40;
        iconTextGap = 45;

        createMapSelections();
        createPlanSelections();
    }


    // Modes of the menu
    void setMapMode() {
        mapMode = true;
        setMapMenu();
    }

    void setPlanMode() {
        mapMode = false;
        setPlanMenu();
    }

    void toggleMode() {
        if (mapMode) {
            setPlanMode();
        } else {
            setMapMode();
        }
    }

    boolean isMapMode() {
        return mapMode;
    }

    // Find the X Y coords of the menu edges
    private void findEdges() {
        // Find the X coords of the edges of the menu
        menuEdgeX2 = menuKey.getX() + menuKey.getWidth();
        menuEdgeX1 = menuKey.getX() + menuKey.getWidth() - menuWidth;

        // Find the Y coords of the edges of the menu
        menuEdgeY2 = menuKey.getY() + menuKey.getHeight() + menuHeight;
        menuEdgeY1 = menuKey.getY() + menuKey.getHeight();
    }

    private void setMenuSize() {
        menuXPos[0] = menuKey.getX();
        menuXPos[1] = menuEdgeX2;
        menuXPos[2] = menuEdgeX2;
        menuXPos[3] = menuEdgeX1;
        menuXPos[4] = menuEdgeX1;
        menuXPos[5] = menuKey.getX() - 10;

        menuYPos[0] = menuEdgeY1;
        menuYPos[1] = menuEdgeY1;
        menuYPos[2] = menuEdgeY2;
        menuYPos[3] = menuEdgeY2;
        menuYPos[4] = menuEdgeY1 + 10;
        menuYPos[5] = menuEdgeY1 + 10;
    }


    // Change menu based on mode
    // Change menu size here
    private void setPlanMenu() {
        menuWidth = 127;
        menuHeight = 320;

        findEdges();
        setMenuSize();
    }

    private void setMapMenu() {
        menuWidth = 127;
        menuHeight = 600;

        findEdges();
        setMenuSize();
    }


    // Whether the menu is visible
    void setVisible() {
        visible = true;
    }

    void setNotVisible() {
        visible = false;
    }

    void toggleVisibility() {
        if (visible) {
            visible = false;
        } else {
            visible = true;
        }
    }

    boolean getVisibility() {
        return visible;
    }


    // Set the Y position of the selection based on its order
    private int setYPos(int position) {
        return selectionYStart + eachSelectionGap * position;
    }

    // The selections inside the menu
    private void createMapSelections() {
        mapSelectionVSD = new NDButton(selectionXStart, setYPos(0), "VSD", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionWXR = new NDButton(selectionXStart, setYPos(1), "WXR", iconTextGap, selectionDiaIconDir, selectedDiaIconDir);
        mapSelectionTERR = new NDButton(selectionXStart, setYPos(2), "TERR", iconTextGap, selectionDiaIconDir, selectedDiaIconDir);
        mapSelectionTFC = new NDButton(selectionXStart, setYPos(3), "TFC", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionAPT = new NDButton(selectionXStart, setYPos(4), "APT", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionWPT = new NDButton(selectionXStart, setYPos(5), "WPT", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionSTA = new NDButton(selectionXStart, setYPos(6), "STA", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionPOS = new NDButton(selectionXStart, setYPos(7), "POS", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionDATA = new NDButton(selectionXStart, setYPos(8), "DATA", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionVORL = new NDButton(selectionXStart, setYPos(9), "VOR L", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionVORR = new NDButton(selectionXStart, setYPos(10), "VOR R", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionFIR = new NDButton(selectionXStart, setYPos(11), "FIR", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionAIRSP = new NDButton(selectionXStart, setYPos(12), "AIRSP", iconTextGap, selectionIconDir, selectedIconDir);
        
        mapExitKey = new NDButton(selectionXStart, setYPos(13), "EXIT", 0, exitKeyUnselectedIconDir, exitKeySelectedIconDir);
    }

    private void createPlanSelections() {
        planSelectionAPT = new NDButton(selectionXStart, setYPos(0), "APT", iconTextGap, selectionIconDir, selectedIconDir);
        planSelectionWPT = new NDButton(selectionXStart, setYPos(1), "WPT", iconTextGap, selectionIconDir, selectedIconDir);
        planSelectionSTA = new NDButton(selectionXStart, setYPos(2), "STA", iconTextGap, selectionIconDir, selectedIconDir);
        planSelectionDATA = new NDButton(selectionXStart, setYPos(3), "DATA", iconTextGap, selectionIconDir, selectedIconDir);
        planSelectionFIR = new NDButton(selectionXStart, setYPos(4), "FIR", iconTextGap, selectionIconDir, selectedIconDir);
        planSelectionAIRSP = new NDButton(selectionXStart, setYPos(5), "AIRSP", iconTextGap, selectionIconDir, selectedIconDir);
        
        planExitKey = new NDButton(selectionXStart, setYPos(6), "EXIT", 0, exitKeyUnselectedIconDir, exitKeySelectedIconDir);
    }

    void drawMapMenu(Graphics2D g) {
        // Draw the selections in the menu in order
        g.drawImage(mapSelectionVSD.getImage(), mapSelectionVSD.getX(), mapSelectionVSD.getY(), null);
        mapSelectionVSD.drawStringDisplaced(g);

        g.drawImage(mapSelectionWXR.getImage(), mapSelectionWXR.getX(), mapSelectionWXR.getY(), null);
        mapSelectionWXR.drawStringDisplaced(g);

        g.drawImage(mapSelectionTERR.getImage(), mapSelectionTERR.getX(), mapSelectionTERR.getY(), null);
        mapSelectionTERR.drawStringDisplaced(g);

        g.drawImage(mapSelectionTFC.getImage(), mapSelectionTFC.getX(), mapSelectionTFC.getY(), null);
        mapSelectionTFC.drawStringDisplaced(g);

        g.drawImage(mapSelectionAPT.getImage(), mapSelectionAPT.getX(), mapSelectionAPT.getY(), null);
        mapSelectionAPT.drawStringDisplaced(g);

        g.drawImage(mapSelectionWPT.getImage(), mapSelectionWPT.getX(), mapSelectionWPT.getY(), null);
        mapSelectionWPT.drawStringDisplaced(g);

        g.drawImage(mapSelectionSTA.getImage(), mapSelectionSTA.getX(), mapSelectionSTA.getY(), null);
        mapSelectionSTA.drawStringDisplaced(g);

        g.drawImage(mapSelectionPOS.getImage(), mapSelectionPOS.getX(), mapSelectionPOS.getY(), null);
        mapSelectionPOS.drawStringDisplaced(g);
        
        g.drawImage(mapSelectionDATA.getImage(), mapSelectionDATA.getX(), mapSelectionDATA.getY(), null);
        mapSelectionDATA.drawStringDisplaced(g);

        g.drawImage(mapSelectionVORL.getImage(), mapSelectionVORL.getX(), mapSelectionVORL.getY(), null);
        mapSelectionVORL.drawStringDisplaced(g);

        g.drawImage(mapSelectionVORR.getImage(), mapSelectionVORR.getX(), mapSelectionVORR.getY(), null);
        mapSelectionVORR.drawStringDisplaced(g);
        
        g.drawImage(mapSelectionFIR.getImage(), mapSelectionFIR.getX(), mapSelectionFIR.getY(), null);
        mapSelectionFIR.drawStringDisplaced(g);

        g.drawImage(mapSelectionAIRSP.getImage(), mapSelectionAIRSP.getX(), mapSelectionAIRSP.getY(), null);
        mapSelectionAIRSP.drawStringDisplaced(g);

        g.drawImage(mapExitKey.getImage(), mapExitKey.getX(), mapExitKey.getY(), null);
        mapExitKey.drawStringCenter(g);
    }

    void drawPlanMenu(Graphics2D g) {
        g.drawImage(planSelectionAPT.getImage(), planSelectionAPT.getX(), planSelectionAPT.getY(), null);
        planSelectionAPT.drawStringDisplaced(g);

        g.drawImage(planSelectionWPT.getImage(), planSelectionWPT.getX(), planSelectionWPT.getY(), null);
        planSelectionWPT.drawStringDisplaced(g);

        g.drawImage(planSelectionSTA.getImage(), planSelectionSTA.getX(), planSelectionSTA.getY(), null);
        planSelectionSTA.drawStringDisplaced(g);
        
        g.drawImage(planSelectionDATA.getImage(), planSelectionDATA.getX(), planSelectionDATA.getY(), null);
        planSelectionDATA.drawStringDisplaced(g);
        
        g.drawImage(planSelectionFIR.getImage(), planSelectionFIR.getX(), planSelectionFIR.getY(), null);
        planSelectionFIR.drawStringDisplaced(g);

        g.drawImage(planSelectionAIRSP.getImage(), planSelectionAIRSP.getX(), planSelectionAIRSP.getY(), null);
        planSelectionAIRSP.drawStringDisplaced(g);

        g.drawImage(planExitKey.getImage(), planExitKey.getX(), planExitKey.getY(), null);
        planExitKey.drawStringCenter(g);
    }

    // Draw the menu
    void drawMenu(Graphics2D g) {
        // Draw the menu background
        g.setColor(Color.BLACK);
        g.fillPolygon(menuXPos, menuYPos, menuXPos.length);

        // Draw the border of the menu
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(3));
        g.drawPolygon(menuXPos, menuYPos, menuXPos.length);

        if (mapMode) {
            drawMapMenu(g);
        } else {
            drawPlanMenu(g);
        }
    }
}
