import java.awt.Graphics2D;
import java.awt.Graphics;
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

    private int buttonWidth;
    private int buttonHeight;
    private int[] buttonSize = new int[2];

    // Constructor inputs
    private int x;
    private int y;
    private String text;

    // Text variables
    private int textDisplacementHorz;
    private int textWidth;
    private int textCenterHorz;
    private int textCenterVert;


    // Constructor of the button and images
    NDButton(int xPos, int yPos, String name, int textXDisplacement, String unselectedDir, String selectedDir) {
        x = xPos;
        y = yPos;
        text = name;
        textDisplacementHorz = textXDisplacement; // Center of text displacement relative to center

        // Can use ImageIO (needs try, catch)
        URL unselectedImageDir = getClass().getClassLoader().getResource(unselectedDir);
        URL selectedImageDir = getClass().getClassLoader().getResource(selectedDir);

        unselectedIcon = new ImageIcon(unselectedImageDir);
        unselectedImage = unselectedIcon.getImage();

        selectedIcon = new ImageIcon(selectedImageDir);
        selectedImage = selectedIcon.getImage();

        // Initialize the size of the button
        // Assume the size of unselected and selected images are the same
        buttonWidth = unselectedImage.getWidth(null);
        buttonHeight = unselectedImage.getHeight(null);
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
        return buttonWidth;
    }

    int getHeight() {
        return buttonHeight;
    }

    int[] getSize() { // Indirectly sets the size
        // If the center of text is displaced to the left relative to the center of button
        if (textDisplacementHorz < 0) {
            // If the text is sticking out of the button to the left
            if (textCenterHorz < textDisplacementHorz) {
                buttonSize[0] = (buttonWidth - textCenterHorz) + textDisplacementHorz;
            }
            // If the text is still inside the button
            else {
                buttonSize[0] = buttonWidth;
            }
        }

        // If the center of text is displaced to the right relative to the center of button
        else if (textDisplacementHorz > 0) {
            // If the text is sticking out of the button to the right
            if (textCenterHorz + textDisplacementHorz + textWidth > buttonWidth) {
                buttonSize[0] = textCenterHorz + textDisplacementHorz + textWidth;
            }
            // If the text is still inside the button
            else {
                buttonSize[0] = buttonWidth;
            }
        }

        // If no horizontal displacement (center of text is at center of button)
        else if (textDisplacementHorz == 0) {
            buttonSize[0] = buttonWidth;
        }

        buttonSize[1] = buttonHeight;

        return buttonSize;
    }


    // Determine whether the mouse is in the hitbox of the button
    boolean isMouseHover(Point mousePoint) {
        if (mousePoint.getX() >= getX() && mousePoint.getX() <= getX() + getSize()[0] &&
            mousePoint.getY() >= getY() && mousePoint.getY() <= getY() + getSize()[1]) {
            return true;
        }
        return false;
    }


    // Draw the text on the button
    void paint(Graphics2D g) {
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("Sans-Serif", Font.PLAIN, 18));

        textWidth = fm.stringWidth(text);
        textCenterHorz = unselectedImage.getWidth(null) / 2 - textWidth / 2;
        textCenterVert = unselectedImage.getHeight(null) / 2 + fm.getAscent() / 2;
        g.drawString(text, textCenterHorz + x + textDisplacementHorz, textCenterVert + y);
    }
}