import java.awt.Graphics2D;
import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.Image;

import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Point;

import java.util.Arrays;

class NDButton {
    // Images of the buttons
    private ImageIcon unselectedIcon;
    private final Image unselectedImage;

    private ImageIcon selectedIcon;
    private final Image selectedImage;

    private boolean selected = false;

    private int[] buttonSize = new int[2];

    // Constructor inputs
    private int x;
    private int y;
    private String text;

    // Text variables
    private int textDisplacementHorz;
    private int textCenterHorz;
    private int textCenterVert;

    private boolean visible;

    // Constructor of the button and images
    NDButton(int xPos, int yPos, String name, int textXDisplacement, String unselectedDir, String selectedDir) {
        x = xPos;
        y = yPos;
        text = name;
        textDisplacementHorz = textXDisplacement;

        visible = true;

        // Create the images of the button
        URL unselectedImageDir = getClass().getClassLoader().getResource(unselectedDir);
        URL selectedImageDir = getClass().getClassLoader().getResource(selectedDir);

        unselectedIcon = new ImageIcon(unselectedImageDir);
        unselectedImage = unselectedIcon.getImage();

        selectedIcon = new ImageIcon(selectedImageDir);
        selectedImage = selectedIcon.getImage();
    }


    // Allow the selection of the button in other classes
    void select() {
        selected = true;
    }

    void unselect() {
        selected = false;
    }

    void toggle() {
        if (selected) {
            unselect();
        } else {
            select();
        }
    }

    boolean getToggle() {
        return selected;
    }

    void setToggle(boolean selection) {
        selected = selection;
    }


    // Change the image of the button based on selection
    Image getUnselectedImage() {
        return unselectedImage;
    }

    Image getSelectedImage() {
        return selectedImage;
    }

    Image getImage() {
        if (selected) {
            return selectedImage;
        } else {
            return unselectedImage;
        }
    }


    // Getters for the position of the button
    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int[] getXY() {
        int[] coords = {x, y};
        return coords;
    }


    // Getters for the size of the button
    int getWidth() {
        return unselectedImage.getWidth(null);
    }

    int getHeight() {
        return unselectedImage.getHeight(null);
    }

    int[] getSize() { 
        // Indirectly sets the size
        // Does not include the size of text protruding out
        buttonSize[0] = getWidth();
        buttonSize[1] = getHeight();

        return buttonSize;
    }


    // Whether the button is visible
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

    // Determine whether the mouse is in the hitbox of the button
    // The button hitbox is ONLY the hitbox of the image
    boolean isMouseHover(Point mousePoint) {
        if (mousePoint.getX() >= getX() && mousePoint.getX() <= getX() + getSize()[0] &&
            mousePoint.getY() >= getY() && mousePoint.getY() <= getY() + getSize()[1]) {
            return true;
        }
        return false;
    }


    // Draw the text on the button
    void drawStringCenter(Graphics2D g, Color color) {
        FontMetrics fm = g.getFontMetrics();
        g.setColor(color);
        g.setFont(new Font("Sans-Serif", Font.PLAIN, 18));

        textCenterHorz = unselectedImage.getWidth(null) / 2 - fm.stringWidth(text) / 2;
        textCenterVert = unselectedImage.getHeight(null) / 2 + fm.getAscent() / 2;
        g.drawString(text, x + textCenterHorz, y + textCenterVert);
    }

    // Uses user inputted text horizontal displacement
    // Displaced from the very right of the button
    void drawStringDisplaced(Graphics2D g, Color color) {
        FontMetrics fm = g.getFontMetrics();
        g.setColor(color);
        g.setFont(new Font("Sans-Serif", Font.PLAIN, 18));

        textCenterVert = unselectedImage.getHeight(null) / 2 + fm.getAscent() / 2;
        g.drawString(text, x + textDisplacementHorz, y + textCenterVert);
    }

    void drawButton(Graphics2D g, boolean centered) {
        if (visible) {
            g.drawImage(this.getImage(), this.getX(), this.getY(), null);   

            if (centered) {
                this.drawStringCenter(g, Color.WHITE);
            } else {
                this.drawStringDisplaced(g, Color.WHITE);
            }
        }
    }
}
