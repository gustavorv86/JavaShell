
package main;

import frames.LookAndFeel;
import frames.MainFrame;

public class Main {

    public static void main(String[] args) {
        
        LookAndFeel.setLookAndFeel(LookAndFeel.LAF.WINDOWS);
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    
}
