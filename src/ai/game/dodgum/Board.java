package ai.game.dodgum;

import java.util.ArrayList;
import java.util.List;

public class Board {
    int sizeX = 6;
    int sizeY = 6;
    
    Square2d size = new Square2d(sizeX,sizeY);
    
    Square2d destination = new Square2d(sizeX-1,sizeY-1);
    
    Square2d currentPosition = new Square2d();
    
    List<Square2d> obstacleList = new ArrayList<Square2d>();
    
    Path path = new Path();
    
    public PossibleRouteMatrix compilePossibleRouteMatrix()
    {
        PossibleRouteMatrix prm = new PossibleRouteMatrix();
        for(Square2d obstacle : obstacleList)
        {
            Square2d relativeObstaclePosition = Square2d.builder(obstacle)
                    .subtract(currentPosition)
                    .build();
            prm.setElementRelativeToCenter(relativeObstaclePosition,0);
        }
        if(currentPosition.getX()==0)
        {
            prm.setAllX(0,0);
        }
        if(currentPosition.getX()==sizeX-1)
        {
            prm.setAllX(2,0);
        }
        if(currentPosition.getY()==0)
        {
            prm.setAllY(0,0);
        }
        if(currentPosition.getY()==sizeY-1)
        {
            prm.setAllY(2,0);
        }
        
        return prm;
        
    }
    
    
    public void draw()
    {
        //for(int x = 0; x< sizeX; x++ )
        //{
            System.out.println("cur("+currentPosition+")");
            for(int y = 0; y< sizeY; y++ )
            //for(int y = sizeY-1; y>= 0; y-- )
            {
                System.out.println("-----------");
                for(int x = 0; x< sizeX; x++ )
                {
                    if(currentPosition.getX() == x && currentPosition.getY() == y)
                    {
                        System.out.print("|X");
                    }
                    else
                    {
                        boolean obstacleHere = false;
                        for(Square2d obstacle : obstacleList)
                        {
                            if(obstacle.getX() == x && obstacle.getY() == y)
                            {
                                System.out.print("|O");
                                obstacleHere = true;
                            }
                        }
                        /*if((nextBestMove1 && nextBestMove1X == x && nextBestMove1Y == y)
                                || (nextBestMove2 && nextBestMove2X == x && nextBestMove2Y == y))
                        {
                            System.out.print("|0");
                        }
                        else
                        {
                            System.out.print("| ");
                        }*/
                        if(! obstacleHere)
                        {
                            System.out.print("| "); 
                        }
                        
                    }
                   
                }
                System.out.println("|");
                //System.out.println("| | | | |");
                
            } 
        //}
        System.out.println("-----------");
        System.out.println();
            
    }
    public final static void main(String args[])
    {
        
        Board board = new Board();
        board.addObstacle(2,3);
        board.addObstacle(3,2);
        for(int x = 0; x< board.sizeX; x++ )
        {
            for(int y = 0; y< board.sizeY; y++ )
            {
                board.setCurrentPosition(x,y);
                PossibleRouteMatrix prm = board.compilePossibleRouteMatrix();
                board.draw();
                prm.drawPossibleRoutes();
                prm.drawProbabilities();
                //board.assessNextBestMove();
                
                
            } 
        }
    }
    public void addObstacle(int x, int y) {
        obstacleList.add(new Square2d(x,y));
        
    }
    public void setCurrentPosition(int x, int y) {
       currentPosition.set(x, y);
        
    }
    public boolean noObstacles(Square2d square2d)
    {
        for(Square2d obstacle : obstacleList)
        {
            if(obstacle.equals(square2d))
            {
                return false;
            }
        }
        return true;
    }
    public boolean inBounds(Square2d square2d)
    {
        if(square2d.getX()<0 || square2d.getY()<0 
                || square2d.getX()>=sizeX || square2d.getY()>=sizeY
                )
        {
            return false;
        }
        return true;
    }
    public int getSizeX() {
        return sizeX;
    }
    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }
    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
    public Square2d getDestination() {
        return destination;
    }
    public void setDestination(Square2d destination) {
        this.destination = destination;
    }
    public Square2d getCurrentPosition() {
        return currentPosition;
    }
    public void setCurrentPosition(Square2d currentPosition) {
        this.currentPosition = currentPosition;
    }
    public List<Square2d> getObstacleList() {
        return obstacleList;
    }
    public void setObstacleList(List<Square2d> obstacleList) {
        this.obstacleList = obstacleList;
    }


    public Path getPath() {
        return path;
    }


    public void setPath(Path path) {
        this.path = path;
    }


    public void toggleObstacle(int posX, int posY) {
        Square2d newObstacle = new Square2d(posX,posY);
        boolean found = false;
        for(Square2d obstacle : getObstacleList())
        {
            if(newObstacle.equals(obstacle))
            {
                getObstacleList().remove(obstacle);
                found = true;
                break;
            }
                
        }
        
        if(!found)
        {
            getObstacleList().add(newObstacle);
        }
        
        
    }

   
}
