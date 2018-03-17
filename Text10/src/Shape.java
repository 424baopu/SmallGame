import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shape {
	//Textclient ������
	Textclient tc;
	
	int x,y;
	//��������
	int type;
	//�Ƿ�ֹͣ
	public boolean stoped = false;
	//�Ƿ��������
	public boolean speed = false;
	//����ͼ
	int [][][] date = {
			{//0һ����
				{0,0,0,0},
				{1,1,1,1},
				{0,0,0,0},
				{0,0,0,0}
			},
			{//1������
				{1,1},
				{1,1}
			},
			{//2L��
				{0,1,0},
				{0,1,0},
				{0,1,1}
			},
			{//3��L��
				{0,1,0},
				{0,1,0},
				{1,1,0}
			},
			{//4Z��
				{0,0,0},
				{1,1,0},
				{0,1,1}
			},
			{//5��Z��
				{0,0,0},
				{0,1,1},
				{1,1,0}
			},
			{//6Ʒ����
				{0,1,0},
				{1,1,0},
				{0,1,0}
			}
	};
	//���Ͷ�Ӧ��ɫ
	Color [] colors = {
			new Color(255 ,218 ,185),
			new Color(0 ,0 ,0),
			Color.blue,
			Color.MAGENTA,
			Color.green,
			Color.red,
			new Color(176,196 ,222),
	};
	//���Unit��Units����
	List<unit> Units = new ArrayList<unit>();
	
	//����ı���ɫ
	//int colorIndex = tc.r.nextInt(colors.length);
    
	public Shape(int x,int y,int type,Textclient tc){
		this.x = x;
		this.y = y;
		this.type = type;
		this.tc = tc;
		//ʵ�����ĸ����󣬲�ָ��λ��
		for(int i=0;i<4;i++){
		    Units.add(new unit(colors[type],tc));//��������
	    }
		createBytype();
		
	}

	private void createBytype() {
		// TODO Auto-generated method stub
		int count =0;
		for(int i=0;i<date[type].length;i++){
			for(int j=0;j<date[type][i].length;j++){
				if(date[type][i][j]==1){
					Units.get(count).setX(x + j * unit.SIZE);
					Units.get(count).setY(y + i * unit.SIZE);
					count++;
				}
			}
		}
	}
	
	//��ͼ
	public void draw(Graphics g){
		for(int i=0;i<Units.size();i++){
		    Units.get(i).draw(g);
		}
	}
	
	//
	List<unit> UnitsNext= new ArrayList<unit>();
	
	public void drawnext(Graphics g,int x,int y,int type,int change){
		//ʵ�����ĸ����󣬲�ָ��λ��
		for(int i=0;i<4;i++){
		    UnitsNext.add(new unit(colors[type]));//��������
	    }
		int count =0;
		for(int i=0;i<date[type].length;i++){
			for(int j=0;j<date[type][i].length;j++){
				if(date[type][i][j]==1){
					UnitsNext.get(count).setX(x + j * unit.SIZE);
					UnitsNext.get(count).setY(y + i * unit.SIZE);
					count++;
				}
			}
		}
		
		for(int i=0;i<change;i++){
    		//rotate();
			for(int j=0;j<4;j++){
				//unit u = UnitsNext.get(j);
				int old_x = UnitsNext.get(j).getX();
				int old_y = UnitsNext.get(j).getY();
				
				int times_x = (old_x - x)/unit.SIZE;
				int times_y = (old_y - y)/unit.SIZE;
				
				int xx = (date[type].length - 1 - times_y)*unit.SIZE + x;
				int yy = times_x * unit.SIZE + y;
				
				
				UnitsNext.get(j).setX(xx);
				UnitsNext.get(j).setY(yy); 
			}
    	}
    	
		//System.out.println(Units.size());
		for(int i=0;i<UnitsNext.size();i++){
			UnitsNext.get(i).drawnext(g,UnitsNext.get(i).getX(),UnitsNext.get(i).getY());
		}
	}
	
	//���䷽��
	public void drop(){
		y += unit.SIZE;
		for(int i=0;i<Units.size();i++){
		    Units.get(i).drop();
		}
	}
	
	//����Ƿ�ֹͣ
	public void changeStatus(){
		int judge = 0;
		//���������һ��ֹͣ����ֹͣ
		for(int i=0;i<Units.size();i++){
			unit u = Units.get(i);
			u.changeStatus();
			if(u.stoped){
				judge = 1;
				break;
			}
		}
		
		if(judge == 1){
			stoped = true;
			//System.out.println(date[type].length);
			int count = 0;
			for(int t=0;t<date[type].length;t++){
				
				int temp_height = y+t*unit.SIZE;
				if(tc.isFull(temp_height)){
					tc.disappear(temp_height);
					tc.reloadunits(temp_height);
					count++;
				}
			}
			if(count>1)tc.score+=10;
		    tc.score += 10*count;
		
			for(int i=0;i<Units.size();i++){
				Units.get(i).stoped = true;
			}
		}
		else{
			stoped = false;
		}
	}

	public void keypressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT:
			moveleft();
			tc.repaint();
			break;
		case KeyEvent.VK_RIGHT:
			moveright();
			tc.repaint();
			break;
		case KeyEvent.VK_DOWN:
			movedown();
			tc.repaint();
			break;
		case KeyEvent.VK_UP:
			rotatePlus();
			//System.out.println(hasHit());
			tc.repaint();
			break;
		}
		
	}
	
	public void rotate() {
		// TODO Auto-generated method stub
        
		for(int j=0;j<Units.size();j++){
			unit u = Units.get(j);
			int old_x = u.getX();
			int old_y = u.getY();
			
			int times_x = (old_x - x)/unit.SIZE;
			int times_y = (old_y - y)/unit.SIZE;
			
			int xx = (date[type].length - 1 - times_y)*unit.SIZE + x;
			int yy = times_x * unit.SIZE + y;
			
			
			u.setX(xx);
			u.setY(yy); 
		}
		tc.repaint();
	}
	
	public void rotatePlus(){
		if(!hasHit()){
			rotate();
		}
		else{
			
		}
	}

	private boolean hasHit() {
		// TODO Auto-generated method stub
		
		for(int j=0;j<Units.size();j++){
			unit u = Units.get(j);
			int old_x = u.getX();
			int old_y = u.getY();
				
			int times_x = (old_x - x)/unit.SIZE;
			int times_y = (old_y - y)/unit.SIZE;
				
			int xx = (date[type].length - 1 - times_y)*unit.SIZE + x;
			int yy = times_x * unit.SIZE + y;
				
			if(xx < 21 || xx > 246 || yy >= 490){
				return true;
			}
				
			for(int i=0;i<tc.us.size();i++){
				unit un = tc.us.get(i);
				if(un.stoped && un.getX() == xx && un.getY() == yy ){
					return true;
				}
			}
				
		}
		return false;
		
		
	}

	private void movedown() {
		// TODO Auto-generated method stub
		speed = true;
	}

	private void moveleft() {
		// TODO Auto-generated method stub
		
		int b = 1;
		for(int i=0;i<Units.size();i++){
			unit s = Units.get(i);
			int xx = s.getX();
			int yy = s.getY();
			for(int j=0;j<tc.us.size();j++){
			    unit u = tc.us.get(j);
			    if(yy == u.getY() && xx - unit.SIZE == u.getX() && u.stoped){
				    b = 0;
			    }
		    }
		}
		
	    if(stoped || b == 0){
	    	return;
	    }
	    int judge = 1;
		for(int i=0;i<Units.size();i++){
			if(Units.get(i).getX() <= 21){
				judge = 0;
			}
		}
		if(judge == 1){
			for(int i=0;i<Units.size();i++){
				Units.get(i).moveleft();	
			}
			x -= unit.SIZE;
		}
	    
	}

	private void moveright() {
		// TODO Auto-generated method stub
		int b = 1;
		for(int i=0;i<Units.size();i++){
			unit s = Units.get(i);
			int xx = s.getX();
			int yy = s.getY();
			for(int j=0;j<tc.us.size();j++){
			unit u = tc.us.get(j);
			    if(yy == u.getY() && xx + unit.SIZE == u.getX() && u.stoped){
				    b = 0;
			    }
		    }
		}
		if(stoped || b == 0){
	    	return;
	    }
        int judge = 1;
		for(int i=0;i<Units.size();i++){
			if(Units.get(i).getX() > 245){
				judge = 0;
			}
		}
		
		if(judge == 1){
			for(int i=0;i<Units.size();i++){
				Units.get(i).moveright();
			}
			x += unit.SIZE;
		}
		
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		speed = false;
	}

}
