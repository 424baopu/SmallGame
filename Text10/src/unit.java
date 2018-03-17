import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class unit {

	//���ֵ�λ��
	private int x,y;
	//��С
	public static final int SIZE = 25;
	//ֹͣ״̬
	public boolean stoped = false;
	//���䲹��
	public static final int SPEED =SIZE;
	//��ɫ
	public Color color;
	//���캯��
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
	
	//�����Լ��ķ���
	public void draw(Graphics g){
		//�Ӵ�
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3f));

		g.setColor(color);
		g.drawRect(x, y, SIZE-4, SIZE-4);
		g.fillRect(x+3, y+3, SIZE-9, SIZE-9);
	}
	
	//������һ��ͼ��
	public void drawnext(Graphics g,int x,int y){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3f));

		g.setColor(color);
		g.drawRect(x, y, SIZE-4, SIZE-4);
		g.fillRect(x+3, y+3, SIZE-9, SIZE-9);
	}
	
	//unit��������
	public void drop(){
		y += SPEED;
	}
	
	//��⵱ǰ״̬
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
	//������õ�λ�ñ���
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
