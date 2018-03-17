import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class unit {

	//出现的位置
	private int x,y;
	//大小
	public static final int SIZE = 25;
	//停止状态
	public boolean stoped = false;
	//下落补偿
	public static final int SPEED =SIZE;
	//颜色
	public Color color;
	//构造函数
	Textclient tc;
	public unit(Textclient tc){
		this.tc = tc;
	}
	public unit(Color c){
		this.color = c;
	}
	public unit(Color c,Textclient tc){
		this.color = c;
		this.tc = tc;
		tc.us.add(this);
	}
	public unit(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	//画出自己的方法
	public void draw(Graphics g){
		//加粗
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3f));

		g.setColor(color);
		g.drawRect(x, y, SIZE-4, SIZE-4);
		g.fillRect(x+3, y+3, SIZE-9, SIZE-9);
	}
	
	//画出下一个图形
	public void drawnext(Graphics g,int x,int y){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3f));

		g.setColor(color);
		g.drawRect(x, y, SIZE-4, SIZE-4);
		g.fillRect(x+3, y+3, SIZE-9, SIZE-9);
	}
	
	//unit对象下落
	public void drop(){
		y += SPEED;
	}
	
	//检测当前状态
	public void changeStatus(){
		if(y+SIZE > 465){
			stoped = true;
		}
		else if(ishit()){
			stoped = true;
		}
		else{
			stoped = false;
		}
	}
	
	public boolean ishit() {
		// TODO Auto-generated method stub
		for(int i=0;i<tc.us.size();i++){
			unit u = tc.us.get(i);
			if(x == u.getX() && y + unit.SIZE == u.getY() && u.stoped){
				return true;
			}
		}
		return false;
	}
	//设置与得到位置变量
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
    public void moveleft() {
		// TODO Auto-generated method stub
    	x -= SIZE;
	}
	public void moveright() {
		// TODO Auto-generated method stub
		x += SIZE;
	}

	

}
