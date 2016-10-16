package ai.game.dodgum;

import java.util.ArrayList;
import java.util.Random;

import ai.ann.AnnTwoLayer;
import ai.transform.Matrix2d;

public class Learner {
    Random random = new Random(1);
    PossibleRouteMatrix prm;
    int maxSize = 10000;
    
    AnnTwoLayer ann = new AnnTwoLayer();

    ArrayList<PossibleRouteMatrix> possibleRouteMatrixList = new  ArrayList<PossibleRouteMatrix>();
    public Learner(int maxSize) {
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
    
    public void learn()
    {
      
            PossibleRouteMatrixRandom prmr = new PossibleRouteMatrixRandom(maxSize);
            prmr.setup();
            
            
            ann.getSynapse0().setMatrix(initSyn0);
            ann.getSynapse1().setMatrix(initSyn1);
            
            int slugSize = 10;
            int slugCount = 0;
            int numberSlugsProcessed = 0;
            
            double[][] poss = new double[slugSize][9];
            double[][] prob = new double[slugSize][9];
            
            for(PossibleRouteMatrix prm : prmr.getPossibleRouteMatrixList())
            {
                poss[slugCount] = Matrix2d.singleLengthArray(prm.createPossibleRoutesDouble());
                prob[slugCount] = Matrix2d.singleLengthArray(prm.getProbabilities());
                slugCount++;
                if(slugCount>=slugSize)
                {
                    slugCount = 0;
                    executeLearning(ann,poss,prob);
                    poss = new double[slugSize][9];
                    prob = new double[slugSize][9];
                }
                if(numberSlugsProcessed % 10000 == 0)
                {
                    System.out.println("numberSlugsProcessed: "+numberSlugsProcessed++);
                }
                
            }
    }
        
        private void executeLearning(AnnTwoLayer ann, double[][] poss, double[][] prob) {
            double[][] initialX = poss;
            double[][] initialy = prob;
            
            Matrix2d layer0 = new Matrix2d(initialX);
            ann.setLayer0(layer0);
            
            ann.getTestAgainst().setMatrix(initialy);
            
            
            ann.feedForward();
            ann.backPropagate();
            
        }
        
       
        
        double[][] initSyn0 ={{-0.16595599,0.44064899,-0.99977125,-0.39533485,-0.70648822,-0.81532281,-0.62747958,-0.30887855,-0.20646505}
        ,{0.07763347,-0.16161097,0.370439,-0.5910955,0.75623487,-0.94522481,0.34093502,-0.1653904,0.11737966}
        ,{-0.71922612,-0.60379702,0.60148914,0.93652315,-0.37315164,0.38464523,0.7527783,0.78921333,-0.82991158}
        ,{-0.92189043,-0.66033916,0.75628501,-0.80330633,-0.15778475,0.91577906,0.06633057,0.38375423,-0.36896874}
        ,{0.37300186,0.66925134,-0.96342345,0.50028863,0.97772218,0.49633131,-0.43911202,0.57855866,-0.79354799}
        ,{-0.10421295,0.81719101,-0.4127717,-0.42444932,-0.73994286,-0.96126608,0.35767107,-0.57674377,-0.46890668}
        ,{-0.01685368,-0.89327491,0.14823521,-0.70654285,0.17861107,0.39951672,-0.79533114,-0.17188802,0.38880032}
        ,{-0.17164146,-0.90009308,0.07179281,0.32758929,0.02977822,0.88918951,0.17311008,0.80680383,-0.72505059}
        ,{-0.72144731,0.61478258,-0.20464633,-0.66929161,0.85501716,-0.30446828,0.50162421,0.45199597,0.76661218}};
        double[][] initSyn1 ={{0.24734441,0.50188487,-0.30220332,-0.46014422,0.79177244,-0.14381762,0.92968009,0.326883,0.24339144}
        ,{-0.77050805,0.89897852,-0.10017573,0.15677923,-0.18372639,-0.52594604,0.80675904,0.14735897,-0.99425935}
        ,{0.23428983,-0.3467102,0.0541162,0.7718842,-0.28546048,0.8170703,0.24672023,-0.96835751,0.85887447}
        ,{0.38179384,0.9946457,-0.65531898,-0.7257285,0.86519093,0.39363632,-0.86799965,0.51092611,0.50775238}
        ,{0.84604907,0.42304952,-0.75145808,-0.96023973,-0.94757803,-0.94338702,-0.50757786,0.7200559,0.07766213}
        ,{0.10564396,0.68406178,-0.75165337,-0.44163264,0.17151854,0.9391915,0.12206044,-0.96270542,0.60126535}
        ,{-0.53405145,0.61421039,-0.22427871,0.72708371,0.49424329,0.11248047,-0.72708955,-0.88016462,-0.75731309}
        ,{-0.91089624,-0.78501174,-0.54858132,0.42597796,0.11943396,-0.97488804,-0.85605144,0.93455266,0.13620092}
        ,{-0.59341353,-0.49534851,0.48765171,-0.60914104,0.16271785,0.94003998,0.6936576,-0.52030448,-0.01246057}};
        public AnnTwoLayer getAnn() {
            return ann;
        }

        public void setAnn(AnnTwoLayer ann) {
            this.ann = ann;
        }
}
