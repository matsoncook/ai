package ai.ann;


import ai.transform.Matrix2d;
import ai.transform.Matrix2d.Operation;
/**
 * Artificial Neural Network(Ann)
 * This is a java version of python code from
 * 
 * https://iamtrask.github.io/2015/07/12/basic-python-network/
 * 
 * @author Mark
 *
 */
public class AnnTwoLayer
{
    Operation sigmoid = (val)->{
        return (1.0/(1.0+Math.exp(-val)));
        };
    Operation sigmoidDeriv = (val)->{
            return val *(1-val);
            };
            
    Matrix2d synapse0 = new Matrix2d();
    Matrix2d synapse1 = new Matrix2d();

    Matrix2d output;
    
    Matrix2d testAgainst = new Matrix2d();
    
    

    
    Matrix2d layer0;
    Matrix2d layer1;
    Matrix2d layer2;
    
   
    
    public void setup()
    {
       
    }

    public void feedForward()
    {
        layer1 = new Matrix2d();
        layer1.dot(layer0, synapse0);
        layer1.operation(sigmoid);
        
        layer2 = new Matrix2d();
        layer2.dot(layer1, synapse1);
        layer2.operation(sigmoid);
        
   
    }
    Matrix2d l1_delta;
    Matrix2d l2_delta;
    Matrix2d l1_error;
    Matrix2d l2_error;
    public void backPropagate()
    {
        l2_error = new Matrix2d(testAgainst);
        l2_error.subtract(layer2);
                  
        Matrix2d l2_deriv = new Matrix2d(layer2,sigmoidDeriv);
        l2_delta = new Matrix2d(l2_error);
        l2_delta.mult(l2_deriv);
        
        l1_error = l2_delta.dot(synapse1.transpose());

        Matrix2d l1_deriv = new Matrix2d(layer1);
        l1_deriv.operation(sigmoidDeriv);
        l1_delta = new Matrix2d(l1_error);
        l1_delta.mult(l1_deriv);
        
        synapse1.add( layer1.transpose().dot(l2_delta));
        synapse0.add( layer0.transpose().dot(l1_delta));
        

    }
    public Matrix2d getL1_error() {
        return l1_error;
    }

    public void setL1_error(Matrix2d l1_error) {
        this.l1_error = l1_error;
    }

    public Matrix2d getL1_delta() {
        return l1_delta;
    }

    public void setL1_delta(Matrix2d l1_delta) {
        this.l1_delta = l1_delta;
    }

    public Matrix2d getL2_error() {
        return l2_error;
    }

    public void setL2_error(Matrix2d l2_error) {
        this.l2_error = l2_error;
    }

    public Matrix2d getL2_delta() {
        return l2_delta;
    }

    public void setL2_delta(Matrix2d l2_delta) {
        this.l2_delta = l2_delta;
    }

    public Matrix2d getTestAgainst() {
        return testAgainst;
    }

    public void setTestAgainst(Matrix2d testAgainst) {
        this.testAgainst = testAgainst;
    }

    public Matrix2d getSynapse0() {
        return synapse0;
    }
    public void setSynapse0(Matrix2d synapse0) {
        this.synapse0 = synapse0;
    }
    public Matrix2d getSynapse1() {
        return synapse1;
    }
    public void setSynapse1(Matrix2d synapse1) {
        this.synapse1 = synapse1;
    }
    
    public Matrix2d getOutput() {
        return output;
    }

    public void setOutput(Matrix2d output) {
        this.output = output;
    }

    public Matrix2d getLayer0() {
        return layer0;
    }

    public void setLayer0(Matrix2d layer0) {
        this.layer0 = layer0;
    }

    public Matrix2d getLayer1() {
        return layer1;
    }

    public void setLayer1(Matrix2d layer1) {
        this.layer1 = layer1;
    }

    public Matrix2d getLayer2() {
        return layer2;
    }

    public void setLayer2(Matrix2d layer2) {
        this.layer2 = layer2;
    }
}
