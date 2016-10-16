package ai.game.dodgum;

public class Square2d {
    private int x;
    private int y;
    public Square2d() {
        super();
    }
    public Square2d(int x, int y) {
        super();
        set(x,y);
    }
    public Square2d(Square2d square2d) {
        x = square2d.x;
        y = square2d.y;
    }
    public void add(Square2d square2d) {
        x += square2d.x;
        y += square2d.y;
        
    }
    public void subtract(Square2d square2d) {
        x -= square2d.x;
        y -= square2d.y;
        
    }
    public void set(Square2d square2d) {
        x = square2d.x;
        y = square2d.y;
        
    }
    
    public static Builder builder(Square2d square2d) {
        return new Builder(square2d);
    }
    public static class Builder
    {
        Square2d square2d = new Square2d();
        public Builder(Square2d square2d) {
            this.square2d.set(square2d);
        }
        public Builder add(Square2d square2d)
        {
            this.square2d.add(square2d);
            return this;
        }
        public Builder subtract(Square2d square2d)
        {
            this.square2d.subtract(square2d);
            return this;
        }
        public Square2d build()
        {
            return square2d;
        }
        
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public String toString()
    {
        return x+","+y;
    }
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
        
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Square2d other = (Square2d) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
    
}
