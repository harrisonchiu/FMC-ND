import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Dimension;


// https://stackoverflow.com/questions/3025320/
// draw-a-jbutton-to-look-like-a-jlabel-or-at-least-without-the-button-edge

class NavigationalDisplayKey extends JButton {
    private Icon nonselectedIcon;
    private Icon selectedIcon;
    private boolean selected = false;
    
    private String keyName;
    private Color keyNameColor = Color.WHITE;
    private int textPosition;

    NavigationalDisplayKey(String label, int position, String nonselectedDir, String selectedDir) {
        nonselectedIcon = new ImageIcon(nonselectedDir);
        selectedIcon = new ImageIcon(selectedDir);
        keyName = label;
        textPosition = position;

        onlyIcon();
        setNonSelectedSize();
        addKeyText();
    }

    private void onlyIcon() {
        // Add icon to button      
        this.setIcon(nonselectedIcon);

        // Remove button border to only show the icon
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setOpaque(false);
        
        // Mouse visually change to show it is clickable
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
    }

    // These are probably not needed?
    private void setSelectedSize() {
        this.setPreferredSize(new Dimension(selectedIcon.getIconWidth(), selectedIcon.getIconHeight()));
    }
    private void setNonSelectedSize() {
        this.setPreferredSize(new Dimension(nonselectedIcon.getIconWidth(), nonselectedIcon.getIconHeight()));
    }

    // Labels the key with text
    private void addKeyText() {
        this.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        this.setForeground(keyNameColor);
        this.setHorizontalTextPosition(textPosition);
        this.setVerticalTextPosition(textPosition);
        
        this.setText(keyName);
    }

    // Change appearance based on whether it is selected/deselected
    void deselected() {
        selected = false;
        this.setIcon(nonselectedIcon);
    }

    void selected() {
        selected = true;
        this.setIcon(selectedIcon);
    }

    void toggle() {
        if (selected) {
            deselected();
        }
        else {
            selected();
        }
    }

    boolean getToggle() {
        return selected;
    }
}