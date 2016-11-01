//matrix2d = (function(){

function create2dArray(i,j)
{
	var x = new Array(i);
	for (var ii = 0; ii < i; ii++) {
		x[ii] = new Array(j);
		for (var jj = 0; jj < j; jj++) {
			x[ii][jj] = 0.0;
		}
	}
	return x;
}
function createMatrix2d()
{
	return new Matrix2d();
}
function createRandomMatrix2d(x,y,random)
{
	var m = createMatrix2d().setMatrix( create2dArray(x,y));
	var func = function(val){ return 1.0-(random.nextDouble()*2); };
	
	m.operation(func);
	
	return m;
}
function createMatrix2dFromArray(arr)
{
	var ret = createMatrix2d().setMatrix(arr);
	var ret1 = createMatrix2d().setMatrix2d(ret);
	return ret1;
}



function Matrix2d()
{
	this.sizeX = 1;
	this.sizeY = 1;
	this.matrix = null;
	var that = this;
	
	
	
	return this;
	
	
}
Matrix2d.prototype = 
{
	getMatrix : function()
	{
		return this.matrix;
	},
	setMatrix2d : function(matrix2d)
	{
		var oldOne = matrix2d.getMatrix();        
		var newOne = create2dArray(matrix2d.sizeX,matrix2d.sizeY);
	        
		this.setMatrix(newOne);
		
		 var func = function(x,y,val){ return oldOne[x][y]; };
		this.operation2d(func);
		
		return this;
	},
	setMatrix : function(matrix)
	{
		this.matrix = matrix;
		this.sizeX = matrix.length;
		this.sizeY = matrix[0].length;
		return this;
	},

	dot : function(pMatrix2d)
	{
	
		var mat  = this.multiply(this.getMatrix(),pMatrix2d.getMatrix());
		return createMatrix2d().setMatrix(mat);
	},	
	dot2 : function(pMatrix2d1,pMatrix2d2)
	{
		var ret = this.multiply(pMatrix2d1.getMatrix(),pMatrix2d2.getMatrix())
		this.setMatrix(ret);
		return this;
	},
	

	multiply : function (a, b) {
	    var m1 = a.length;
	    var n1 = a[0].length;
	    var m2 = b.length;
	    var n2 = b[0].length;
	    if (n1 != m2) { return null;
	    	//throw new RuntimeException("Illegal matrix dimensions.");
		}
	    var c = create2dArray(m1,n2);
	    for (var i = 0; i < m1; i++)
	        for (var j = 0; j < n2; j++)
	            for (var k = 0; k < n1; k++)
	                c[i][j] += a[i][k] * b[k][j];
	    return c;
	},
	transpose : function  () {
	    var m = this.matrix.length;
	    var n = this.matrix[0].length;
	    var b = create2dArray(n,m);
	    for (var i = 0; i < m; i++)
	        for (var j = 0; j < n; j++)
	            b[j][i] = this.matrix[i][j];
	    return createMatrix2d().setMatrix(b);
	},
	checkSum : function ()
    {
        var sum = 0;
        for (var i = 0; i < this.sizeX; i++) { 
            for (var j = 0; j < this.sizeY; j++)
            { 
                sum += this.matrix[i][j];
            }
            
        }
        return sum;
    },
	operation : function (func)
    {
        var sum = 0;
        for (var i = 0; i < this.sizeX; i++) { 
            for (var j = 0; j < this.sizeY; j++)
            { 
            	this.matrix[i][j] = func(this.matrix[i][j]);
            }
            
        }
        return this;
    },
    operation2d : function (func)
    {
        var sum = 0;
        for (var i = 0; i < this.sizeX; i++) { 
            for (var j = 0; j < this.sizeY; j++)
            { 
                this.matrix[i][j] = func(i,j,this.matrix[i][j]);
            }
            
        }
        return this;
    },
    add : function (matrix2d) {
        
        var m = matrix2d.getMatrix();
        var func = function(x,y,val){ return val + m[x][y]; };
        this.operation2d(func);
        
        return this;
        
    },
    subtract : function (matrix2d) {
        
        var m = matrix2d.getMatrix();
        var func = function(x,y,val){ return val - m[x][y]; };
        this.operation2d(func);
        
        return this;
        
    },
    mult1 : function (matrix2d) {
        
        var m = matrix2d.getMatrix();
        var func = function(x,y,val){ return val * m[x][y]; };
        this.operation2d(func);
        
        return this;
        
    },
    mult : function (multiplier)
    {
    	
        var func = function(val){return val * multiplier;};
        this.operation(func);
    },
    twoDArray : function(arr2d,xLen,yLen) {
        var res = create2dArray(xLen,yLen);//arr[0].length*arr.length];
        for(var y = 0; y< yLen; y++ )            
        {           
            for(var x = 0; x< xLen; x++ )
            {
                res[x][y] = arr[x*yLen + y];
            }
        }
        return res;
        
    },
    equals : function(a1,a2,delta )
    {
        for (var i = 0; i < this.sizeX; i++) { 
            for (var j = 0; j < this.sizeY; j++)
            { 
                var res = Math.abs(a1[i][j] - a2[i][j]);
                if(res > delta)
                {
                    
                    return false;
                }
            }
            
        }
        return true;
    }
}

//})();

