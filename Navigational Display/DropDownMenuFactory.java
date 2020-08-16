import javax.swing.JPopupMenu;
import javax.swing.JButton;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;
import javax.swing.border.Border;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Arrays;

class DropDownMenuFactory extends JPopupMenu {
    private NavigationalDisplayKey SelectionVSD; // MAP
    private NavigationalDisplayKey SelectionWXR; // MAP
    private NavigationalDisplayKey SelectionTERR; // MAP
    private NavigationalDisplayKey SelectionTFC; // MAP
    private NavigationalDisplayKey SelectionAPT; // BOTH
    private NavigationalDisplayKey SelectionWPT; // BOTH
    private NavigationalDisplayKey SelectionSTA; // BOTH
    private NavigationalDisplayKey SelectionPOS; // MAP
    private NavigationalDisplayKey SelectionDATA; // BOTH
    private NavigationalDisplayKey SelectionVORL; // MAP
    private NavigationalDisplayKey SelectionVORR; // MAP
    private NavigationalDisplayKey SelectionFIR; // BOTH
    private NavigationalDisplayKey SelectionAIRSP; // BOTH
    private NavigationalDisplayKey exitKey; // BOTH

    private Border outerWhiteLine = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.WHITE);
    private Border selectionPadding = BorderFactory.createMatteBorder(10, 10, 10, 10, Color.BLACK);

    private int numberOfSelections;
    private NavigationalDisplayKey[] selections;

    DropDownMenuFactory(String[] selectionNames, String[] selectionSelectedIconsDir, String[] selectionNonSelectedIconsDir) {
        // Assumes same size of arrays
        numberOfSelections = selectionNames.length;
        selections = new NavigationalDisplayKey[numberOfSelections];

        for (int i=0; i < numberOfSelections; i++) {
            selections[i] = new NavigationalDisplayKey(selectionNames[i], JButton.CENTER, 
                                                        selectionSelectedIconsDir[i], selectionNonSelectedIconsDir[i]);
        }

        setAppearance();
        addSelections();
        addSelectionListeners();
    }

    private void setAppearance() {
        this.setBackground(Color.BLACK);
        this.setBorder(new CompoundBorder(outerWhiteLine, selectionPadding));
    }

    private void addSelections() {
        for (int i=0; i < numberOfSelections; i++) {
            this.add(selections[i]);
        }
    }

    private void addSelectionListeners() {
        for (int i=0; i < numberOfSelections; i++) {
            final int index = i;
            selections[index].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    selections[index].toggle();
                }
            });
        }
    }
    
}