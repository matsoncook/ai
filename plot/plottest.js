function PlotTest()
{
	this.iterations = 1000;
	this.plotSize = 4;
	this.testBatchSize = 10;
	this.stackSize = 5;
	
	this.tolerance = 0.005;
	
	this.deviation = 0;
	this.doCallback = function(){}
	
	
	this.learn = function(iterations,testBatchSize,plotSize,stackSize)
	{
		var random = new Random(1);
		var ann = new AnnStack();
		for(var i = 0; i < stackSize-1 ;i++)
	    {
	        ann.addStackItem3(plotSize*2+2, plotSize*2+2,random);
	    }
	   
	    ann.addStackItem3(plotSize*2+2, 2 ,random);
	
	    var pg = new PlotGenerator(testBatchSize,plotSize);
	    
	    for(var i  = 0 ; i<iterations; i++)
	    {
	        var testBatch = pg.createNextTestBatch(testBatchSize,plotSize);
	        ann.initialLayer = createMatrix2dFromArray(testBatch.question);
	        //console.log("ann.initialLayer.checkSum(): "+ann.initialLayer.checkSum());
	        ann.testAgainst = createMatrix2dFromArray(testBatch.answer);
	        //console.log("ann.testAgainst.checkSum(): "+ann.testAgainst.checkSum());
	        ann.feedForward();

	        //console.log("ann.annStack[0].layer: "+ann.annStack[0].layer.checkSum());
	        //console.log("ann.annStack[0].synapse: "+ann.annStack[0].synapse.checkSum());
	        
	        //console.log("ann.annStack[1].layer: "+ann.annStack[1].layer.checkSum());
	        //console.log("ann.annStack[1].synapse: "+ann.annStack[1].synapse.checkSum());
	        ann.backPropagate();
	        
	        if(iterations%1000 == 0)
	        {
	        	this.doCallback();
	        	
	        }
	        
	    }
	    console.log("ann.annStack[0].synapse: "+ann.annStack[0].synapse.checkSum());
	    console.log("ann.annStack[1].synapse: "+ann.annStack[1].synapse.checkSum());
	    return ann;
	}
    
	this.testAgainst = function(ann,iterations,testBatchSize,plotSize,tolerance)
    {
		var pg = new PlotGenerator(testBatchSize,plotSize);
		var random = new Random(1);
	    
	    var failuresCS =0;
	    var failuresM =0;
	    this.deviation =0;
	    for(var i  = 0; i < iterations*testBatchSize; i++)
	    {
	        var plot = pg.createPlot();
	        ann.initialLayer=createMatrix2dFromArray(pg.createQuestionArray(plot));
	        //ann.initialLayer= ann.initialLayer.transpose();
	        var result = ann.feedForward();
	        
	        var testAgainst = pg.createAnswerArray(plot);
	        this.deviation += (result.getMatrix()[0][0] - testAgainst[0][0]);
	        this.deviation += (result.getMatrix()[0][1] - testAgainst[0][1]);
	        
	        
	        if(!result.equals(result.getMatrix(),testAgainst, tolerance))
	        {
	            failuresM++;
	        }
	    }
	    this.deviation = Math.abs((100*(this.deviation/tolerance)/(iterations*testBatchSize*2))).toFixed(2);
	   /* Console.log("Failures: " +(100*failuresM)/(iterations*testBatchSize) +"%");
	    return (failuresM)/(iterations*slugSize);*/
	    //Number((6.688689).toFixed(1))
	    return Number((100-(100*failuresM)/(iterations*testBatchSize)).toFixed(1));
    }
	this.nextPlot = function(plotGenerator)
	{
		return pg.createPlot();
	}
   

        
}