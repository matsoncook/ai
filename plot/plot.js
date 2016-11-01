var seed =1;
var aThird = 0.3333333333333333333;
function Random(pSeed)
{
	this.seed = pSeed;
	/*this.count=0;
	this.aList=[];
	this.nextDouble1 = function(){
		
		var x = this.aList[this.count];
		this.count++;
		return x;
	}*/
	this.nextDouble = function(){
		var x = Math.sin(this.seed++)*1000;
		return x - Math.floor(x);
	}
}


function Plot(pplotNumber)
{   
	this.plotNumber = pplotNumber;
    
	this.plotList = [];

    this.answerPoint;
    this.velocityVector;
    this.annPoint;
    
}

Plot.prototype = {
		addPlot : function(point2d)
		{
			this.plotList.push(point2d)
		},
		createAnswerPoint : function()
		{
			this.answerPoint = new Point2d();
			for(var i = 0; i< this.plotNumber; i++)
			{
				this.answerPoint.add(this.plotList[i]);
			}
			this.answerPoint.scale(1.0/this.plotNumber);
			this.answerPoint.add(this.velocityVector);
		}
}

function TestBatch(pquestion,panswer)
{
	this.question = pquestion;
	this.answer = panswer;
}

function PlotGenerator(pslugSize, pplotSize)
{
    this.slugSize = pslugSize;
    this.plotSize = pplotSize;
    this.random = null;
    this.reset();
}
PlotGenerator.prototype = {
		reset : function()
	    {
			 this.random = new Random(1);
	    },
		
	    createRandomPoint : function()
	    {
	    	var x = this.random.nextDouble();
	    	var y = this.random.nextDouble();
	    	var p  = createPoint2d(x,y);
	        return p;
	    },
	    createPlot : function()
	    {
	    	var plot = new Plot(this.plotSize);
	    	
	    	var center = this.createRandomPoint();
	        center.scale(aThird);
	         
	    	
	    	for(var x =0; x < this.plotSize; x++)
	        {
	    		var p = this.createRandomPoint()
	            p.scale(aThird)
	            p.add(center);
	            plot.addPlot(p);
	        }
	    	
	    	plot.velocityVector = this.createRandomPoint();
	    	plot.velocityVector.scale(aThird);
	    	plot.velocityVector.scale(aThird);
	    	plot.createAnswerPoint();
	    	plot.annPoint = new Point2d();
	    	return plot;
	    },
	    populateQuestionArray : function(plot,question)
	    {
           
            for(var j =0; j < this.plotSize; j++)
            {
                var p = plot.plotList[j];
                question[j*2]=p.x;
                question[j*2+1]=p.y;
            }
            question[this.plotSize*2]=plot.velocityVector.x;
            question[this.plotSize*2+1]=plot.velocityVector.y;

	    },
	    createNextTestBatch : function(slugSize,plotSize)
	    {
	    	  var question = create2dArray(slugSize,plotSize*2+2);
	          var answer = create2dArray(slugSize,2);
	          for(var i =0; i < this.slugSize; i++)
	          {
	              var plot = this.createPlot();
	              answer[i][0] = plot.answerPoint.x;
	              answer[i][1] = plot.answerPoint.y;
	              this.populateQuestionArray(plot,question[i]);

	             
	          }
	          return new TestBatch(question,answer);
	    },
	    createQuestionArray : function(plot)
	    {
	    	var question = create2dArray(1,this.plotSize*2+2);
	    	this.populateQuestionArray(plot,question[0]);
	    	return question;
	    },
	    createAnswerArray : function( plot)
	    {
	        var answer = create2dArray(1,2);
	        answer[0][0] = plot.answerPoint.x;
	        answer[0][1] = plot.answerPoint.y;
	        return answer;
	    }
}