function createPoint2d(x,y)
{
	var p = new Point2d();
	p.x = x;
	p.y = y;
	return p;
}
function clonePoint2d(p1)
{
	var p = new Point2d();
	p.x = p1.x;
	p.y = p1.y;
	return p;
}
function Point2d()
{
	this.x = 0;
	this.y = 0;
}

Point2d.prototype = {
	add : function(point)
	{
		this.x += point.x;
		this.y += point.y;
	},
	add2 : function(px,py)
	{
		this.x += px;
		this.y += py;
	},
	subtract : function(point)
	{
		this.x -= point.x;
		this.y -= point.y;
	},
	scale : function(scale)
	{
		this.x *= scale;
		this.y *= scale;
	},
}