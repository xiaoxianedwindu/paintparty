//杜孝顯_104403016_資管3A
import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.JPanel;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.BasicStroke;

public class PaintArea extends JPanel
{
	private final ArrayList<Shape> shapes=new ArrayList<>();
	private Color ForegroundColor=Color.BLACK;
	private Color BackgroundColor=Color.WHITE;
	private int shapeindex=1;
	private Point mousepressed=new Point();
	
	private Point mousereleased=new Point();
	
	private int brushsize=1;
	private boolean fill=false;
	
	private boolean eraser=false;
	public PaintArea(){
			addMouseMotionListener(
					new MouseMotionAdapter(){
						@Override 
						public void mouseDragged(MouseEvent event){
							if(shapeindex==1){
								if(!eraser){
									shapes.add(new Shape(event.getPoint(),brushsize,ForegroundColor,eraser));
									repaint();
								}
								else if(eraser){
									shapes.add(new Shape(event.getPoint(),brushsize,BackgroundColor,eraser));
									repaint();
								}
							}
								
						}
					}
					);
					addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent event){
							mousepressed=event.getPoint();
							
						}						
						@Override
						public void mouseReleased(MouseEvent event){
							mousereleased=event.getPoint();
							if(shapeindex==2){
								shapes.add(new Shape(mousepressed,mousereleased,brushsize,ForegroundColor,eraser));
								repaint();
							}
							else if(shapeindex<=5&&shapeindex>=3){
								shapes.add(new Shape(mousepressed,mousereleased,brushsize,shapeindex,fill,ForegroundColor,eraser));
								repaint();
							}					
							}
						
							
					}
								
					);
		
	}
	
	public void reset(){
		shapes.clear();
		Point pointreset1=new Point();
		Point pointreset2=new Point();
		pointreset1.setLocation(0,0);
		pointreset2.setLocation(0,0);
//		Shape resetpanel=new Shape(pointreset1,pointreset2,brushsize,shapeindex,fill,ForegroundColor,eraser);
		repaint();
		
	}
	
	public void eraser(boolean e){
		eraser=e;
	}
	
	public void setForegroundColor(Color c){
		ForegroundColor=c;
	}
	
	public void setBackgroundColor(Color c){
		System.out.println("Backgroundchang");
		BackgroundColor=c;
	}
	public void setShape(int shape){
		shapeindex=shape;
	}
	
	public void setBrush(int size){
		brushsize=size;
	}
	
	public void setFilled(boolean filled){
		fill=filled;
	}
	
	
	public void previousStep (){
		shapes.remove(shapes.size()-1);
		repaint();
	}
	
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		
		for(Shape shape:shapes){
			System.out.println(shape.getShape());
			if(shape.getShape()==1){
				if(shape.getEraser()){
					g2d.setColor(BackgroundColor);
					g2d.setStroke(new BasicStroke((float)shape.getSize()*2));
					g2d.fill(new Ellipse2D.Double(shape.getPointa().x,shape.getPointa().y ,shape.getPointb().x-shape.getPointa().x ,shape.getPointb().y-shape.getPointa().y));
				}
				else{
					g2d.setColor(shape.getColor());
					g2d.setStroke(new BasicStroke((float)shape.getSize()*2));
					g2d.fill(new Ellipse2D.Double(shape.getPointa().x,shape.getPointa().y ,shape.getPointb().x-shape.getPointa().x ,shape.getPointb().y-shape.getPointa().y));	
				}
				
			
			}
			else if(shape.getShape()==2){
				if(!shape.getEraser()){
					g2d.setColor(shape.getColor());
					g2d.setStroke(new BasicStroke((float)shape.getSize()*2));
					g2d.draw(new Line2D.Double(shape.getPointa().x,shape.getPointa().y,shape.getPointb().x,shape.getPointb().y));
				}
				else{
				g2d.setColor(BackgroundColor);
				g2d.setStroke(new BasicStroke((float)shape.getSize()*2));
				g2d.draw(new Line2D.Double(shape.getPointa().x,shape.getPointa().y,shape.getPointb().x,shape.getPointb().y));
				}
			}
			else if(shape.getShape()==3){
				if(!shape.getEraser()){
					g2d.setColor(shape.getColor());
					if(shape.getFill()){
						g2d.fill(new Ellipse2D.Double(shape.getPointa().x,shape.getPointa().y ,shape.getPointb().x-shape.getPointa().x ,shape.getPointb().y-shape.getPointa().y));
					}
					else{
						g2d.setStroke(new BasicStroke((float)shape.getSize()*2));
						g2d.draw(new Ellipse2D.Double(shape.getPointa().x,shape.getPointa().y ,shape.getPointb().x-shape.getPointa().x ,shape.getPointb().y-shape.getPointa().y));
					}
				}
				else{
					g2d.setColor(BackgroundColor);
					if(shape.getFill()){
						g2d.fill(new Ellipse2D.Double(shape.getPointa().x,shape.getPointa().y ,shape.getPointb().x-shape.getPointa().x ,shape.getPointb().y-shape.getPointa().y));
					}
					else{
						g2d.setStroke(new BasicStroke((float)shape.getSize()*2));
						g2d.draw(new Ellipse2D.Double(shape.getPointa().x,shape.getPointa().y ,shape.getPointb().x-shape.getPointa().x ,shape.getPointb().y-shape.getPointa().y));
					}
				}
			}
			else if(shape.getShape()==4){
				if(!shape.getEraser()){
					g2d.setColor(shape.getColor());
				}
				else{
					g2d.setColor(BackgroundColor);
				}
				
				if(shape.getFill()){
					g2d.fill(new Rectangle2D.Double(shape.getPointa().x,shape.getPointa().y ,shape.getPointb().x-shape.getPointa().x ,shape.getPointb().y-shape.getPointa().y));
				}
				else{
					g2d.setStroke(new BasicStroke((float)shape.getSize()*2));
					g2d.draw(new Rectangle2D.Double(shape.getPointa().x,shape.getPointa().y ,shape.getPointb().x-shape.getPointa().x ,shape.getPointb().y-shape.getPointa().y));
				}
			}
			else if(shape.getShape()==5){
				if(!shape.getEraser()){
					g2d.setColor(shape.getColor());
				}
				else{
					g2d.setColor(BackgroundColor);
				}
				
				if(shape.getFill()){
					g2d.fill(new RoundRectangle2D.Double(shape.getPointa().x,shape.getPointa().y ,shape.getPointb().x-shape.getPointa().x ,shape.getPointb().y-shape.getPointa().y,20,20));
				}
				else{
					g2d.setStroke(new BasicStroke((float)shape.getSize()*2));
					g2d.draw(new RoundRectangle2D.Double(shape.getPointa().x,shape.getPointa().y ,shape.getPointb().x-shape.getPointa().x ,shape.getPointb().y-shape.getPointa().y,20,20));
				}
			}
				
		}
			
		
	}

}
