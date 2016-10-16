package ai.game.dodgum;

import ai.ann.AnnTwoLayer;
import ai.transform.Matrix2d;

public class Game {
    
    Learner learner;
    AnnTwoLayer ann;
    Board board = new Board();
   
    public void learn()
    {
        learner = new Learner(200000);
        learner.learn();
        ann = learner.getAnn();
    }
    
    public void createPath() {
        Path path = board.getPath();
        path.getPathList().clear();
        board.getCurrentPosition().set(0, 0);
        path.getPathList().add(board.getCurrentPosition());
        int maxPathSegmentCount = board.getSizeX()*3;
        
        while(!board.getCurrentPosition().equals(board.getDestination()) )
        {
            if(path.getPathList().size()<maxPathSegmentCount)
            {
                double[][] poss = new double[1][9];
                poss[0] = Matrix2d.singleLengthArray(board.compilePossibleRouteMatrix().createPossibleRoutesDouble());
                //System.out.println(""+new Matrix2d(poss));
                ann.setLayer0(new Matrix2d(poss));
                ann.feedForward();
                ann.getLayer2();
                double[][] prob = Matrix2d.twoDArray(ann.getLayer2().getMatrix()[0], 3, 3);
                //PossibleRouteMatrix p = new PossibleRouteMatrix();
                Square2d newcurr = new Square2d(board.getCurrentPosition());
                newcurr.add(nextMoveIs(prob));
                board.setCurrentPosition(newcurr);
                path.getPathList().add(newcurr);
            }
            else
            {
                break;
            
            }
        }
    }
    public Square2d nextMoveIs(double[][] matrix) {
        
        double max =0;
        int maxX = -1;
        int maxY = -1;
        for(int x = 0; x < matrix.length;x++)
        {
            for(int y = 0; y < matrix[0].length;y++)
            {
                if(matrix[x][y]>max)
                {
                    max = matrix[x][y];
                    maxX = x;
                    maxY = y;
                }
            }
        }
        return new Square2d(maxX-1,maxY-1);
    }
    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public AnnTwoLayer getAnn() {
        return ann;
    }

    public void setAnn(AnnTwoLayer ann) {
        this.ann = ann;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
