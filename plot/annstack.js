function AnnStack()
{
	this.annStack = [];
	this.initialLayer;
	this.finalLayer;
	this.testAgainst;
	this.error;
}
function AnnStack()
{
	this.annStack = [];
}
var sigmoid = function(val){
    return (1.0/(1.0+Math.exp(-val)));
    };
var sigmoidDeriv = function(val){
        return val *(1-val);
        };
/*var randomizer = function(val){
	return 1.0-(random.nextDouble()*2);;
            };*/
        
AnnStack.prototype =
{
	
	feedForward : function()
	{
		var previousLayer = this.initialLayer;
		//console.log("sl layer : " + previousLayer.checkSum());

        for(var i = 0 ; i < this.annStack.length; i++)
        {
            previousLayer = this.annStack[i].feedForward(previousLayer);
            //console.log("sl layer : " + previousLayer.checkSum());
           
        }
        this.finalLayer = previousLayer;
        return this.finalLayer;
	},
	backPropagate : function()
    {
		var stack = this.annStack;
		var previousTestAgainst= this.testAgainst;
        previousTestAgainst.subtract(stack[stack.length-1].layer);
        //console.log("sl layer : " + previousTestAgainst.checkSum());
        for(var i = stack.length-1 ; i>= 0; --i)
    	{
    	
    	
            var annStackItem = stack[i];
            var delta =  annStackItem.backPropagate(previousTestAgainst);
            previousTestAgainst = delta.dot(annStackItem.synapse.transpose());
            //console.log("sl layer : " + previousTestAgainst.checkSum());
            
        }
        
        var previouslayer = this.initialLayer;
        for(var i = 0 ; i < stack.length; i++)
        {
        	var annStackItem = stack[i];
            annStackItem.updateSynapse(previouslayer);
            previouslayer = annStackItem.layer;
        }
    },
    addStackItem : function()
    {
    	var asi = new AnnStackItem();
    	this.annStack.push(asi);
    	return asi;
    },
    addStackItem3 : function(synX,synY,random)
    {
    	var asi = new AnnStackItem();
    	this.annStack.push(asi);
    	asi.synapse= createRandomMatrix2d(synX,synY,random);
    	return asi;
    }
   
	
}


function AnnStackItem()
{
	this.layer = null;
	this.synapse = null;
	this.delta = null;
	
}
AnnStackItem.prototype =
{
	feedForward : function(previousLayer)
	{
		this.layer = createMatrix2d();
		this.layer.dot2(previousLayer,this.synapse);
		//console.log("layer.dot2(previousLayer,this.synapse)"+this.synapse.checkSum()+" " + this.layer.checkSum());
		this.layer.operation(sigmoid);
        return this.layer;
	},
	backPropagate : function( testAgainst)
	{
		this.error = createMatrix2d().setMatrix2d(testAgainst);
        //error.subtract(layer);
                  
        var l2_deriv = createMatrix2d()
        	.setMatrix2d(this.layer)
        	.operation(sigmoidDeriv);
        this.delta = createMatrix2d().setMatrix2d(this.error);
        this.delta.mult1(l2_deriv);
        
        return this.delta;
	},
	updateSynapse : function(previousLayer)
	{
		this.synapse.add( previousLayer.transpose().dot(this.delta));
	}
	
}