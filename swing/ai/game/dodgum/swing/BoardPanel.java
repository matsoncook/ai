package ai.game.dodgum.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import ai.ann.AnnTwoLayer;
import ai.game.dodgum.Board;
import ai.game.dodgum.Game;
import ai.game.dodgum.Square2d;


public class BoardPanel extends JPanel {
    Board board;
    int squareSize = 25;
    AnnTwoLayer ann;
    Game game = new Game();
    public BoardPanel() {
        game.learn();
        board = game.getBoard();

        /*Learner learner = new Learner(200000);
        learner.learn();*/
        ann = game.getAnn();
        
        mouseListeners();
        // set a preferred size for the custom panel.
        setPreferredSize(new Dimension(420,420));
        
     
        
        game.createPath();
        
        
    }

 

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        fillSquare(g,board.getDestination(),Color.magenta);
        fillSquare(g,board.getCurrentPosition(),Color.blue);
        for(Square2d obstacle : board.getObstacleList())
        {
            fillSquare(g,obstacle,Color.red);

        }
        g.setColor(Color.black);
        for(int x =0; x< board.getSizeX();x++)
        {
            for(int y =0; y< board.getSizeY();y++)
            {
                
                g.drawRect(x*squareSize, y*squareSize, squareSize, squareSize);
                //g.drawString("S"+x+","+y, x*squareSize+10,  y*squareSize+20);
            }
        }
        g.setColor(Color.magenta);
        int count = 0;
        Square2d prevSegment = null;
        for(Square2d pathSegment : board.getPath().getPathList())                
        {
            if(prevSegment!=null)
            {
                g.drawLine(prevSegment.getX()*squareSize + squareSize/2, 
                        prevSegment.getY()*squareSize + squareSize/2, 
                        pathSegment.getX()*squareSize + squareSize/2, 
                        pathSegment.getY()*squareSize + squareSize/2);
                g.fillOval(pathSegment.getX()*squareSize + squareSize/2, pathSegment.getY()*squareSize + squareSize/2, 10, 10);
            }
            
            prevSegment = pathSegment;
        }
    }

    private void fillSquare(Graphics g, Square2d square2d, Color colour) {
        g.setColor(colour);
        g.fillRect(square2d.getX()*squareSize, square2d.getY()*squareSize, squareSize, squareSize);

        
    }
    public void mouseListeners()
    {
        this.addMouseListener(new MouseAdapter() {
           

            @Override
            public void mousePressed(MouseEvent e) {

                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int posX = e.getX()/squareSize;
                int posY = e.getY()/squareSize;
                board.toggleObstacle(posX,posY);
                
                game.createPath();
             
                repaint();
            }
        });
    }
}
