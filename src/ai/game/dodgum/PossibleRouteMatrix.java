package ai.game.dodgum;

import java.text.DecimalFormat;

public class PossibleRouteMatrix {
    int sizeX = 3;
    int sizeY = 3;
    
    //int[][] possibleRoutes = new int[sizeX][sizeY];
    int[][] possibleRoutes = {  {1,2,3},
                                {2,0,4},
                                {3,4,5},
    };
    double[][] probabilities = new double[sizeX][sizeY];
    public PossibleRouteMatrix()  
    {
        initialise();
    }
    public void initialise()
    {
        /*possibleRoutes[0][0] = 1;
        possibleRoutes[0][0] = 1;
        possibleRoutes[0][0] = 1;
        possibleRoutes[0][0] = 1;
        possibleRoutes[0][0] = 1;
        possibleRoutes[0][0] = 1;
        possibleRoutes[0][0] = 1;
        possibleRoutes[0][0] = 1;
        possibleRoutes[0][0] = 1;
        possibleRoutes[0][0] = 1;*/
        
    }
    public double[][] createPossibleRoutesDouble()
    {
      double[][] possibleRoutesDouble = new double[sizeX][sizeY];
      for(int x = 0; x< sizeX; x++ )
      {   
          for(int y = sizeY-1; y>= 0; y-- )
          {
              if(possibleRoutes[x][y]>0)
              {
                  possibleRoutesDouble[x][y] = 1;
              }
              
          }
      }
      return possibleRoutesDouble;
    }

    public void setElementRelativeToCenter(Square2d relativeObstaclePosition, int i) {
        if(relativeObstaclePosition.getX()<=1 
                && relativeObstaclePosition.getX()>=-1
                && relativeObstaclePosition.getY()<=1 
                && relativeObstaclePosition.getY()>=-1
                )
        {
            setElementRelativeToCenter(relativeObstaclePosition.getX(), relativeObstaclePosition.getY(), i);
        }
        
    }
    public void setElementRelativeToCenter(int x, int y, int i) {
        int px = x + 1;
        int py = y + 1;
           
        possibleRoutes[px][py] = i;
        
    }
    public void setAllX(int pX, int value) {
        for(int y=0;y<sizeY;y++)
        {
            possibleRoutes[pX][y]=value;
        }
        
    }
    public void setAllY(int pY, int value) {
        for(int x=0;x<sizeX;x++)
        {
            possibleRoutes[x][pY]=value;
        }
        
    }
    public void drawPossibleRoutes()
    {
        for(int y = 0; y< sizeY; y++ )
            //for(int y = sizeY-1; y>= 0; y-- )
            {
                System.out.println("-----------");
                for(int x = 0; x< sizeX; x++ )
                {
                    System.out.print("|"+possibleRoutes[x][y]);
                    /*if(possibleRoutes[x][y]==0)
                    {
                        System.out.print("| ");
                    }
                    else
                    {
                        System.out.print("|X");
                        
                    }*/
                   
                }
                System.out.println("|");                
            } 

        System.out.println("-----------");
        System.out.println();
            
    }
    
    public void drawProbabilities()
    {
        DecimalFormat df = new DecimalFormat("0.#");
        for(int y = 0; y< sizeY; y++ )
           // for(int y = sizeY-1; y>= 0; y-- )
            {
                System.out.println("-----------");
                for(int x = 0; x< sizeX; x++ )
                {
                    if(probabilities[x][y]==0)
                    {
                        System.out.print("| - ");
                    }
                    else
                    {
                        System.out.print("|"+df.format(probabilities[x][y]));
                        
                    }
                   
                }
                System.out.println("|");
                
                
            } 
      
        System.out.println("-----------");
        System.out.println();
            
    }
    public boolean build()
    {
        int instances = 0;
        int max = 0;
        for(int x = 0; x< sizeX; x++ )
        {   
            for(int y = sizeY-1; y>= 0; y-- )
            {
                if(possibleRoutes[x][y] == max)
                {
                    instances++;
                }
                if(possibleRoutes[x][y] > max)
                {
                    instances = 1;
                    max = possibleRoutes[x][y];
                }
               
            }
        }
        
        double probability = 1.0/(double)instances;
        for(int x = 0; x< sizeX; x++ )
        {   
            for(int y = sizeY-1; y>= 0; y-- )
            {
                if(possibleRoutes[x][y] == max)
                {
                    probabilities[x][y] = probability;
                }
            }
        }
        return instances == sizeX*sizeY ?  false :  true;
        
            
            
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
    public int[][] getPossibleRoutes() {
        return possibleRoutes;
    }
    public void setPossibleRoutes(int[][] possibleRoutes) {
        this.possibleRoutes = possibleRoutes;
    }
    public double[][] getProbabilities() {
        return probabilities;
    }
    public void setProbabilities(double[][] probabilities) {
        this.probabilities = probabilities;
    }
}
