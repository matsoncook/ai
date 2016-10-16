package ai.game.dodgum.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import ai.game.dodgum.Learner;


public class BoardSwingApp extends JFrame
{
    
    BoardPanel boardPanel;
    public BoardSwingApp()
    {
        super();

        
    }
    public void setup()
    {
        boardPanel = new BoardPanel();
        getContentPane().add(boardPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setMinimumSize(new Dimension(500, 0));
        setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        setVisible(true);
       
        
        
    }
    
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                BoardSwingApp f = new BoardSwingApp();
                f.setup();
                
            }
        });
    }
}
