//杜孝顯_104403016_資管3A
import java.awt.Point;
import java.awt.Color;


public class Shape {
	private Point pointa=new Point();
	private Point pointb=new Point();
	private Color color=Color.LIGHT_GRAY;
	private int shape=1;
	private int size=1;
	private boolean fill=false;
	private boolean eraser=false;
	
	public Shape(Point point1,int size,Color color,Boolean eraser){
		if(size==1){
			pointa.setLocation(point1);
			pointb.setLocation(point1.x+2, point1.y+2);
			this.size=size;
			this.color=color;
			fill=false;
			this.shape=1;
		}
		else if(size==2){
			pointa.setLocation(point1);;
			pointb.setLocation(point1.x+4, point1.y+4);
			this.size=size;
			this.color=color;
			fill=false;
			shape=1;
		}
		else if(size==3){
			pointa.setLocation(point1);;
			pointb.setLocation(point1.x+6, point1.y+6);
			this.size=size;
			this.color=color;
			fill=false;
			shape=1;
		}
		else if(size==4){
			pointa.setLocation(point1);;
			pointb.setLocation(point1.x+8, point1.y+8);
			this.size=size;
			this.color=color;
			fill=false;
			shape=1;
		}
		else if(size==5){
			pointa.setLocation(point1);;
			pointb.setLocation(point1.x+10, point1.y+10);
			this.size=size;
			this.color=color;
			fill=false;
			shape=1;
		}
		else if(size==6){
			pointa.setLocation(point1);;
			pointb.setLocation(point1.x+12, point1.y+12);
			this.size=size;
			this.color=color;
			fill=false;
			shape=1;
		}
		else if(size==7){
			pointa.setLocation(point1);;
			pointb.setLocation(point1.x+14, point1.y+14);
			this.size=size;
			this.color=color;
			fill=false;
			shape=1;
		}
		else if(size==8){
			pointa.setLocation(point1);;
			pointb.setLocation(point1.x+16, point1.y+16);
			this.size=size;
			this.color=color;
			fill=false;
			shape=1;
		}
		else if(size==9){
			pointa.setLocation(point1);;
			pointb.setLocation(point1.x+18, point1.y+18);
			this.size=size;
			this.color=color;
			fill=false;
			shape=1;
		}
		this.eraser=eraser;
	}
	
	public Shape(Point point1,Point point2,int size,Color color,boolean eraser){
		
			pointa.setLocation(point1);;
			pointb.setLocation(point2);
			this.size=size;
			this.color=color;
			fill=false;
			shape=2;
			this.eraser=eraser;
		
	}
	
	public Shape(Point point1,Point point2,int size,int shape,boolean fill,Color color,boolean eraser)//deal with rectangles,ovals,roundrects
	{
		pointa.setLocation(point1);;
		pointb.setLocation(point2);
		this.size=size;
		this.color=color;
		this.fill=fill;
		this.shape=shape;
		this.eraser=eraser;
	}
	
	public Point getPointa()//return the topleft point
	{
		return pointa;
	}
	
	public Point getPointb()//return the rightbottom point
	{
		return pointb;
	}
	
	public Color getColor()//return the color
	{
		return color;
	}
	
	public int getShape()//return the shape
	{
		return shape;
	}
	
	public int getSize()//return the Paintbrush size
	{
		return size;
	}
	
	public boolean getFill()//return the Filled or not
	{
		return fill;
	}

	public boolean getEraser()
	{
		return eraser;
	}
}
