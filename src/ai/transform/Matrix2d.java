package ai.transform;

import java.util.Arrays;



public class Matrix2d
{
    public static Matrix2d build()
    {
        return new Matrix2d();
    }
    public static Matrix2d build(int sizeX, int sizeY)
    {
        return new Matrix2d(sizeX,sizeY);
    }
    public static Matrix2d build(Matrix2d matrix2d)
    {
        return new Matrix2d(matrix2d);
    }
    private int sizeX = 1;

    private int sizeY = 1;
    private double[][] matrix = new double[sizeX][sizeY];
    public Matrix2d(int sizeX, int sizeY) {
        super();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        matrix = new double[sizeX][sizeY];
    }
    public Matrix2d() {
        
    }
    public Matrix2d(double[][] layer1) {
        double[][] oldOne = layer1;        
        double[][] newOne = new double[layer1.length][layer1[0].length];
        
        setMatrix(newOne);
        operation2d((x,y,val)->{ return oldOne[x][y]; });
    }
    public Matrix2d(Matrix2d layer1)
    {
        double[][] oldOne = layer1.getMatrix();        
        double[][] newOne = new double[layer1.getSizeX()][layer1.getSizeY()];
        
        setMatrix(newOne);
        operation2d((x,y,val)->{ return oldOne[x][y]; });
        
    }

    public Matrix2d(Matrix2d layer1, Operation operation) {
        this(layer1);
        operation(operation);
    }
    public Matrix2d dot(Matrix2d m1,Matrix2d m2)
    {

        double result[][] = multiply(m1.getMatrix(), m2.getMatrix());
        setMatrix(result);
        return this;
        
    }
    
    //http://introcs.cs.princeton.edu/java/22library/Matrix.java.html
    public static double[][] multiply(double[][] a, double[][] b) {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
        double[][] c = new double[m1][n2];
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }
    public Matrix2d operation(Operation operation)
    {
        for (int i = 0; i < sizeX; i++) { 
            for (int j = 0; j < sizeY; j++) { 
                matrix[i][j] = operation.operation(matrix[i][j]);
            }
            
        }
        
        return this;
    }
    public Matrix2d operation2d(Operation2d operation)
    {
        for (int i = 0; i < sizeX; i++) { 
            for (int j = 0; j < sizeY; j++) { 
                matrix[i][j] = operation.operation(i,j,matrix[i][j]);
            }
            
        }
        
        return this;
    }
    public void set(int x, int y, double value) {
        matrix[x][y]=value;
    }
    public double get(int x, int y) {
        return matrix[x][y];
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
    public double[][] getMatrix() {
        return matrix;
    }
    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
        sizeX = matrix.length;
        sizeY = matrix[0].length;
    }
    
    @FunctionalInterface
    public interface Operation{

        double operation(double value);
        
        
    }
    @FunctionalInterface
    public interface Operation2d{

        double operation(int x,int y,double value);
        
        
    }

    public Matrix2d mult(double d) {
        operation((val)->{return val*d;});
        return this;
        
    }
    public Matrix2d add(double d) {
        operation((val)->{return val+d;});
        return this;
        
    }
    public Matrix2d reciprocal(double d) {
        operation((val)->{return 1.0/val;});
        return this;
        
    }
    
    public String toString()
    {
        String result = "";
        for (int y = 0; y < sizeY; y++) { 
            for (int x = 0; x < sizeX; x++) { 
                result += matrix[x][y] + ",";
            }
            result += "\n";
        }
        return result;
    }
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(matrix);
        result = prime * result + sizeX;
        result = prime * result + sizeY;
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
        Matrix2d other = (Matrix2d) obj;
        /*if (!Arrays.deepEquals(matrix, other.matrix))
            return false;*/
        if (sizeX != other.sizeX)
            return false;
        if (sizeY != other.sizeY)
            return false;
        return  equals(matrix,other.getMatrix(), 0.00001 );
       
    }
    public boolean equals(double[][] a1,double[][] a2, double delta )
    {
        for (int i = 0; i < sizeX; i++) { 
            for (int j = 0; j < sizeY; j++)
            { 
                double res = Math.abs(a1[i][j] - a2[i][j]);
                if(res > delta)
                {
                    //System.out.println(i + " " + j + " " + a1[i][j] + " " + a2[i][j]);
                    return false;
                }
            }
            
        }
        return true;
    }
    public Matrix2d subtract(Matrix2d matrix2d) {
        checkBounds(matrix2d);
        /*for (int i = 0; i < sizeX; i++) { 
            for (int j = 0; j < sizeY; j++) {
                matrix[i][j] -= matrix2d.getMatrix()[i][j];
            }
        }*/
        double[][] m = matrix2d.getMatrix();
        operation2d((x,y,val)->{ return val - m[x][y]; });
        
        return this;
        
    }
    private void checkBounds(Matrix2d layer1)
    {
        if(sizeX != layer1.getSizeX()
                || sizeY != layer1.getSizeY())
        {
            throw new RuntimeException("Matrix bounds ...");
        }
        
    }
    public void mult(Matrix2d matrix2d) {
        checkBounds(matrix2d);
        
        double[][] m = matrix2d.getMatrix();
        operation2d((x,y,val)->{ return val * m[x][y]; });
        
    }
    public Matrix2d transpose() {
        
        return new Matrix2d(transpose(matrix));
    }
    public static double[][] transpose(double[][] a) {
        int m = a.length;
        int n = a[0].length;
        double[][] b = new double[n][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                b[j][i] = a[i][j];
        return b;
    }
    public Matrix2d add(Matrix2d matrix2d) {
        checkBounds(matrix2d);
        
        double[][] m = matrix2d.getMatrix();
        operation2d((x,y,val)->{ return val + m[x][y]; });
        
        return this;
        
    }
    public Matrix2d dot(Matrix2d Matrix2d) {
        double result[][] = multiply(matrix, Matrix2d.getMatrix());
        Matrix2d resultM = new Matrix2d(result);
     
        return resultM;
        //return null;
    }
    
    public static double[] singleLengthArray(double[][] arr) {
        int xLen = arr.length;
        int yLen = arr[0].length;
        
        
        double[] res = new double[xLen*yLen];
        
        
        
        for(int x = 0; x< xLen; x++ )
        {           
            for(int y = 0; y< yLen; y++ )
            {
                res[x*yLen + y] = arr[x][y];
            }
        }
        return res;
        
    }
    public static double[][] twoDArray(double[] arr,int xLen,int yLen) {
        double[][] res = new double[xLen][yLen];//arr[0].length*arr.length];
        for(int y = 0; y< yLen; y++ )            
        {           
            for(int x = 0; x< xLen; x++ )
            {
                res[x][y] = arr[x*yLen + y];
            }
        }
        return res;
        
    }
    public double checkSum()
    {
        double sum = 0;
        for (int i = 0; i < sizeX; i++) { 
            for (int j = 0; j < sizeY; j++)
            { 
                sum += matrix[i][j];
            }
            
        }
        return sum;
    }

}
