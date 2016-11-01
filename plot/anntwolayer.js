

function AnnTwoLayer()
{
	this.layer0;
	this.layer1;
	this.layer2;
	this.synapse0 = new Matrix2d();
	this.synapse1 = new Matrix2d();
	this.output;

	this.testAgainst = new Matrix2d();
	
	this.l1_delta;
	this.l2_delta;
	this.l1_error;
	this.l2_error;
}

var sigmoid = function(val){
    return (1.0/(1.0+Math.exp(-val)));
    };
var sigmoidDeriv = function(val){
        return val *(1-val);
        };
            


AnnTwoLayer.prototype = 
{
	feedForward : function()
	{
	    this.layer1 = createMatrix2d()
	    	.dot2(this.layer0, this.synapse0)
	    	.operation(sigmoid);
	    
	    console.log("layer1" + this.layer1.checkSum());
	    
	    this.layer2 = createMatrix2d()
	    	.dot2(this.layer1, this.synapse1)
	    	.operation(sigmoid);
	    
	    console.log("layer2" + this.layer2.checkSum());
	},
	getSynapse0 : function()
	{
		return this.synapse0;
	},
	
	getSynapse1 : function()
	{
		return this.synapse1;
	},
	getLayer2 : function()
	{
		return this.layer2;
	},
	setLayer0 : function(pLayer0)
	{
		this.layer0 = pLayer0;
	},
	getTestAgainst : function()
	{
		return this.testAgainst;
	},

    backPropagate : function()
    {
    	this.l2_error = createMatrix2d().setMatrix2d(this.testAgainst)
        	.subtract(this.layer2);
    	//console.log("l2_error: " + this.l2_error.checkSum());         
        var l2_deriv = createMatrix2d().setMatrix2d(this.layer2)
        	.operation(sigmoidDeriv);
        
        
        this.l2_delta = createMatrix2d().setMatrix2d(this.l2_error);
    
        this.l2_delta.mult1(l2_deriv);
        //console.log("l2_delta: " + this.l2_delta.checkSum());
        
        //console.log("this.synapse1.transpose(): "+this.synapse1.transpose().checkSum());
        this.l1_error = this.l2_delta.dot(this.synapse1.transpose());
        
        //console.log("l1_error: "+this.l1_error.checkSum());
        var l1_deriv = createMatrix2d().setMatrix2d(this.layer1)
        	.operation(sigmoidDeriv);
        this.l1_delta = createMatrix2d().setMatrix2d(this.l1_error)
        	.mult1(l1_deriv);
       // console.log("l1_delta: "+this.l1_delta.checkSum());
       // console.log("l2_delta: " + this.l2_delta.checkSum());
        //console.log("this.layer1.transpose(): "+this.layer1.transpose().checkSum());
        //console.log("this.layer1.transpose().dot(this.l2_delta): "+this.layer1.transpose().dot(this.l2_delta).checkSum());
        this.synapse1.add( this.layer1.transpose().dot(this.l2_delta));
        this.synapse0.add( this.layer0.transpose().dot(this.l1_delta));
       
        //console.log("synapse1: "+this.synapse1.checkSum());
        //console.log("synapse0: "+this.synapse0.checkSum());
    }
}