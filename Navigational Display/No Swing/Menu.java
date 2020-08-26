import java.util.Arrays;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;

import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.Image;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Point;

class Menu {
    private NDButton menuKey;

    private Polygon mapMenu;
    private int[] mapMenuXPos = new int[6];
    private int[] mapMenuYPos = new int[6];

    private Polygon planMenu;
    private int[] planMenuXPos = new int[6];
    private int[] planMenuYPos = new int[6];

    private int menuEdgeX; // X coord of right most edge of the menu
    private int menuEdgeY; // Y coord of top most edge of the menu

    private int[][] menuSize = new int[2][2]; // [ [MAP W, MAP H], [PLAN W, PLAN H] ]
    private int mapMenuWidth;
    private int mapMenuHeight;
    private int planMenuWidth;
    private int planMenuHeight;

    // Selections in the menu
    private String selectionIconDir = "images/selectionUnselected.png";
    private String selectedIconDir = "images/selectionSelected.png";

    private String selectionDiaIconDir = "images/diamondUnselected.png";
    private String selectedDiaIconDir = "images/diamondSelected.png";

    private String selectionDisabledIconDir = "images/selectionDisabled.png";

    private String exitKeyUnselectedIconDir = "images/exitKeyUnselected.png";
    private String exitKeySelectedIconDir = "images/exitKeySelected.png";

    private String separatorDir = "images/separator.png";

    // Different objects since different positions
    // Can use methods to change XY pos and keep same object
    // Could be more efficient; for later

    // Map selections in menu
    private NDButton mapSepVSDWXR;
    private NDButton mapSepTERRTFC;
    private NDButton mapSepDATAVORL;
    private NDButton mapSepVORRFIR;
    private NDButton mapSepExit;
    NDButton mapSelectionVSD;
    NDButton mapSelectionWXR;
    NDButton mapSelectionTERR;
    NDButton mapSelectionTFC;
    NDButton mapSelectionAPT;
    NDButton mapSelectionWPT;
    NDButton mapSelectionSTA;
    NDButton mapSelectionPOS;
    NDButton mapSelectionDATA;
    NDButton mapSelectionVORL;
    NDButton mapSelectionVORR;
    NDButton mapSelectionFIR;
    NDButton mapSelectionAIRSP; 
    NDButton mapExitKey;

    // Plan selections in menu
    private NDButton planSepDATAFIR;
    private NDButton planSepExit;
    NDButton planSelectionAPT;
    NDButton planSelectionWPT;
    NDButton planSelectionSTA;
    NDButton planSelectionDATA;
    NDButton planSelectionFIR;
    NDButton planSelectionAIRSP;
    NDButton planExitKey;

    private boolean mapMode = true;
    private boolean visible = false;

    // Selection positions in the menu
    private int selectionXStart; // X coord of first selection relative to top left menu coords
    private int selectionYStart; // Y coord of first selection relative to top left menu coords
    private int separatorXStart;
    private int eachSelectionGap;
    private int separatorGap;
    private int iconTextGap;
    private int exitKeyXCenter;

    Menu(NDButton parentButton) {
        menuKey = parentButton;

        // All numbers were chosen to resemble the menu in the actual ND
        // Change integers are your discretion

        // Sizes of each menu mode
        planMenuWidth = 130;
        planMenuHeight = 340;
        mapMenuWidth = 130;
        mapMenuHeight = 660;
        createMenuSizes();

        selectionXStart = menuEdgeX - menuKey.getWidth() - 32; // X position of first selection to determine the rest
        selectionYStart = menuEdgeY + 20; // Y position of first selection to determine the rest
        separatorXStart = menuEdgeX - 128; // Horizontal position of separators (left most pref.)
        eachSelectionGap = 40; // Gap between selection and the selection below
        separatorGap = 26; // Gap between separator and the selection below
        iconTextGap = 45; // Gap between the button and text
        exitKeyXCenter = menuEdgeX - 98; // Horizontal position of exit key (center pref.)

        createMapSelections();
        createPlanSelections();
    }

    // Creates the polygons used as the shape of the menu
    private void createMenuSizes() {
        // Find edges of menu
        menuEdgeX = menuKey.getX() + menuKey.getWidth(); // right most edge
        menuEdgeY = menuKey.getY() + menuKey.getHeight(); // top most edge

        // Find the coords of the corners for MAP menu
        mapMenuXPos[0] = menuKey.getX();
        mapMenuXPos[1] = menuEdgeX;
        mapMenuXPos[2] = menuEdgeX;
        mapMenuXPos[3] = menuEdgeX - mapMenuWidth;
        mapMenuXPos[4] = menuEdgeX - mapMenuWidth;
        mapMenuXPos[5] = menuKey.getX() - 10;

        mapMenuYPos[0] = menuEdgeY;
        mapMenuYPos[1] = menuEdgeY;
        mapMenuYPos[2] = menuEdgeY + mapMenuHeight;
        mapMenuYPos[3] = menuEdgeY + mapMenuHeight;
        mapMenuYPos[4] = menuEdgeY + 10;
        mapMenuYPos[5] = menuEdgeY + 10;

        // Find the coords of the corners for PLAN menu
        planMenuXPos[0] = menuKey.getX();
        planMenuXPos[1] = menuEdgeX;
        planMenuXPos[2] = menuEdgeX;
        planMenuXPos[3] = menuEdgeX - planMenuWidth;
        planMenuXPos[4] = menuEdgeX - planMenuWidth;
        planMenuXPos[5] = menuKey.getX() - 10;
        
        planMenuYPos[0] = menuEdgeY;
        planMenuYPos[1] = menuEdgeY;
        planMenuYPos[2] = menuEdgeY + planMenuHeight;
        planMenuYPos[3] = menuEdgeY + planMenuHeight;
        planMenuYPos[4] = menuEdgeY + 10;
        planMenuYPos[5] = menuEdgeY + 10;

        // Create the menus
        mapMenu = new Polygon(mapMenuXPos, mapMenuYPos, mapMenuXPos.length);
        planMenu = new Polygon(planMenuXPos, planMenuYPos, planMenuXPos.length);
    }

    int[][] getMenuSize() {
        menuSize[0][0] = mapMenuWidth;
        menuSize[0][1] = mapMenuHeight;

        menuSize[1][0] = planMenuWidth;
        menuSize[1][1] = planMenuHeight;

        // [ [MAP W, MAP H], [PLAN W, PLAN H] ]
        return menuSize;
    }


    // Whether the menu is visible
    void setVisibility(boolean visibility) {
        visible = visibility;
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


    // Modes of the menu
    void setMapMode() {
        mapMode = true;
    }

    void setPlanMode() {
        mapMode = false;
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


    // Determine whether the mouse is in the hitbox of the popupmenu 
    boolean isMouseHoverMenu(Point mousePoint) {
        if (mapMode) {
            return mapMenu.contains(mousePoint);
        } else {
            return planMenu.contains(mousePoint);
        }
    }


    // Set the Y position of the selection based on its order
    // and how many separators are before (above) that item
    private int setYPos(int position, int previousSeparatorAmount) {
        if (previousSeparatorAmount > 0) {
            return selectionYStart + eachSelectionGap * position - separatorGap * previousSeparatorAmount;
        } else {
            return selectionYStart + eachSelectionGap * position;
        }
    }

    // The selections inside the menu
    private void createMapSelections() {
        mapSelectionVSD = new NDButton(selectionXStart, setYPos(0, 0), "VSD", iconTextGap, selectionIconDir, selectedIconDir);

        mapSepVSDWXR = new NDButton(separatorXStart, setYPos(1, 0), "", 0, separatorDir, separatorDir);

        mapSelectionWXR = new NDButton(selectionXStart, setYPos(2, 1), "WXR", iconTextGap, selectionDiaIconDir, selectedDiaIconDir);
        mapSelectionTERR = new NDButton(selectionXStart, setYPos(3, 1), "TERR", iconTextGap, selectionDiaIconDir, selectedDiaIconDir);

        mapSepTERRTFC = new NDButton(separatorXStart, setYPos(4, 1), "", 0, separatorDir, separatorDir);

        mapSelectionTFC = new NDButton(selectionXStart, setYPos(5, 2), "TFC", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionAPT = new NDButton(selectionXStart, setYPos(6, 2), "APT", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionWPT = new NDButton(selectionXStart, setYPos(7, 2), "WPT", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionSTA = new NDButton(selectionXStart, setYPos(8, 2), "STA", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionPOS = new NDButton(selectionXStart, setYPos(9, 2), "POS", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionDATA = new NDButton(selectionXStart, setYPos(10, 2), "DATA", iconTextGap, selectionIconDir, selectedIconDir);

        mapSepDATAVORL = new NDButton(separatorXStart, setYPos(11, 2), "", 0, separatorDir, separatorDir);

        mapSelectionVORL = new NDButton(selectionXStart, setYPos(12, 3), "VOR L", iconTextGap, selectionIconDir, selectedIconDir);
        mapSelectionVORR = new NDButton(selectionXStart, setYPos(13, 3), "VOR R", iconTextGap, selectionIconDir, selectedIconDir);

        mapSepVORRFIR = new NDButton(separatorXStart, setYPos(14, 3), "", 0, separatorDir, separatorDir);

        mapSelectionFIR = new NDButton(selectionXStart, setYPos(15, 4), "FIR", iconTextGap, selectionDisabledIconDir, selectionDisabledIconDir);
        mapSelectionAIRSP = new NDButton(selectionXStart, setYPos(16, 4), "AIRSP", iconTextGap, selectionDisabledIconDir, selectionDisabledIconDir);
        
        mapSepExit = new NDButton(separatorXStart, setYPos(17, 4), "", 0, separatorDir, separatorDir);

        mapExitKey = new NDButton(exitKeyXCenter, setYPos(18, 5), "EXIT", 0, exitKeyUnselectedIconDir, exitKeySelectedIconDir);
    }

    private void createPlanSelections() {
        planSelectionAPT = new NDButton(selectionXStart, setYPos(0, 0), "APT", iconTextGap, selectionIconDir, selectedIconDir);
        planSelectionWPT = new NDButton(selectionXStart, setYPos(1, 0), "WPT", iconTextGap, selectionIconDir, selectedIconDir);
        planSelectionSTA = new NDButton(selectionXStart, setYPos(2, 0), "STA", iconTextGap, selectionIconDir, selectedIconDir);
        planSelectionDATA = new NDButton(selectionXStart, setYPos(3, 0), "DATA", iconTextGap, selectionIconDir, selectedIconDir);

        planSepDATAFIR = new NDButton(separatorXStart, setYPos(4, 0), "", 0, separatorDir, separatorDir);

        planSelectionFIR = new NDButton(selectionXStart, setYPos(5, 1), "FIR", iconTextGap, selectionDisabledIconDir, selectionDisabledIconDir);
        planSelectionAIRSP = new NDButton(selectionXStart, setYPos(6, 1), "AIRSP", iconTextGap, selectionDisabledIconDir, selectionDisabledIconDir);

        planSepExit = new NDButton(separatorXStart, setYPos(7, 1), "", 0, separatorDir, separatorDir);
        
        planExitKey = new NDButton(exitKeyXCenter, setYPos(8, 2), "EXIT", 0, exitKeyUnselectedIconDir, exitKeySelectedIconDir);
    }

    private void drawMapSelections(Graphics2D g) {
        // Draw the selections in the menu in order
        mapSelectionVSD.drawButton(g, false);

        g.drawImage(mapSepVSDWXR.getImage(), mapSepVSDWXR.getX(), mapSepVSDWXR.getY(), null);

        mapSelectionWXR.drawButton(g, false);

        mapSelectionTERR.drawButton(g, false);

        g.drawImage(mapSepTERRTFC.getImage(), mapSepTERRTFC.getX(), mapSepTERRTFC.getY(), null);

        mapSelectionTFC.drawButton(g, false);

        mapSelectionAPT.drawButton(g, false);

        mapSelectionWPT.drawButton(g, false);

        mapSelectionSTA.drawButton(g, false);

        mapSelectionPOS.drawButton(g, false);

        g.drawImage(mapSepDATAVORL.getImage(), mapSepDATAVORL.getX(), mapSepDATAVORL.getY(), null);
        
        mapSelectionDATA.drawButton(g, false);

        mapSelectionVORL.drawButton(g, false);

        mapSelectionVORR.drawButton(g, false);

        g.drawImage(mapSepVORRFIR.getImage(), mapSepVORRFIR.getX(), mapSepVORRFIR.getY(), null);

        g.drawImage(mapSepExit.getImage(), mapSepExit.getX(), mapSepExit.getY(), null);

        mapExitKey.drawButton(g, true);
    }

    private void drawPlanSelections(Graphics2D g) {
        planSelectionAPT.drawButton(g, false);

        planSelectionWPT.drawButton(g, false);

        planSelectionSTA.drawButton(g, false);

        planSelectionDATA.drawButton(g, false);

        g.drawImage(planSepDATAFIR.getImage(), planSepDATAFIR.getX(), planSepDATAFIR.getY(), null);
        
        g.drawImage(planSepExit.getImage(), planSepExit.getX(), planSepExit.getY(), null);

        planExitKey.drawButton(g, true);
    }


    // Draw the menu itself
    void drawMenu(Graphics2D g) {
        if (mapMode && visible) {
            // Draw the menu background
            g.setColor(Color.BLACK);
            g.fillPolygon(mapMenu);

            // Draw the border of the menu
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(3));
            g.drawPolygon(mapMenu);
            drawMapSelections(g);

            g.drawImage(mapSelectionFIR.getImage(), mapSelectionFIR.getX(), mapSelectionFIR.getY(), null);
            mapSelectionFIR.drawStringDisplaced(g, Color.CYAN);

            g.drawImage(mapSelectionAIRSP.getImage(), mapSelectionAIRSP.getX(), mapSelectionAIRSP.getY(), null);
            mapSelectionAIRSP.drawStringDisplaced(g, Color.CYAN);
        } 
        else if (!mapMode && visible) {
            // Draw the menu background
            g.setColor(Color.BLACK);
            g.fillPolygon(planMenu);

            // Draw the border of the menu
            g.setColor(Color.WHITE);
            g.setStroke(new BasicStroke(3));
            g.drawPolygon(planMenu);
            drawPlanSelections(g);

            g.drawImage(planSelectionFIR.getImage(), planSelectionFIR.getX(), planSelectionFIR.getY(), null);
            planSelectionFIR.drawStringDisplaced(g, Color.CYAN);

            g.drawImage(planSelectionAIRSP.getImage(), planSelectionAIRSP.getX(), planSelectionAIRSP.getY(), null);
            planSelectionAIRSP.drawStringDisplaced(g, Color.CYAN);
        }
    }
}
