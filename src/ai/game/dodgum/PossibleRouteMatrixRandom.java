package ai.game.dodgum;

import java.util.ArrayList;
import java.util.Random;

public class PossibleRouteMatrixRandom
{


    Random random = new Random(1);
    PossibleRouteMatrix prm;
    int maxSize = 10000;

    ArrayList<PossibleRouteMatrix> possibleRouteMatrixList = new  ArrayList<PossibleRouteMatrix>();
    public PossibleRouteMatrixRandom(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean randomNext()
    {
        prm = new PossibleRouteMatrix();
        for(int x = 0; x< prm.getSizeX(); x++ )
        {   
            for(int y = 0; y< prm.getSizeY(); y++ )
            {
                double randomD = random.nextDouble();
                if(randomD<0.5)
                {
                    prm.getPossibleRoutes()[x][y] = 0;
                }
            }
        }
        return prm.build();
        
    }
    
    public void setup()
    {
        
        for(int i = 0; i < maxSize; i++)
        {

            if(randomNext())
            {
                possibleRouteMatrixList.add(prm);

            }
            
                
        }
    }
    
    
    public static void main(String args[])
    {
        PossibleRouteMatrixRandom p = new PossibleRouteMatrixRandom(10000);
        p.setup();
     

        
       
    }
    
    public ArrayList<PossibleRouteMatrix> getPossibleRouteMatrixList() {
        return possibleRouteMatrixList;
    }

    public void setPossibleRouteMatrixList(ArrayList<PossibleRouteMatrix> possibleRouteMatrixList) {
        this.possibleRouteMatrixList = possibleRouteMatrixList;
    }

}
