package ai.game.dodgum;

import java.util.ArrayList;
import java.util.List;

public class Path {
    List<Square2d> pathList = new ArrayList<Square2d>();
    public Path()
    {
        
    }
    public void addPathSegment(int x, int y)
    {
        pathList.add(new Square2d(x,y));
    }
    public List<Square2d> getPathList() {
        return pathList;
    }
    public void setPathList(List<Square2d> pathList) {
        this.pathList = pathList;
    }

}
