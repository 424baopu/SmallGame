/*
 * 1.�÷����
 * 2.��һ��ͼ��Ԥ��
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Textclient extends Frame{

	int score = 0;
	//����һ��random����
	public static Random r = new Random();
	//��һ���������ͼ��仯����
	int type,changestep;

	//����λ�ñ���x,y
	public static final int x = 300;
	public static final int y = 100;

	//�������ڴ�С����width,length
	public static final int width = 450;
	public static final int height = 520;

	//˫����
	private Image offScreenImage = null;
	private Graphics goffScreen;

	//Shape s = new Shape(96,40,type,this);
	Shape s = null;

	//��unit
	List<unit> us = new ArrayList<unit>();
	private Iterator<unit> Iterator;

	public void lanchar(){
		//����λ��
		this.setLocation(x, y);
		//���ڴ�С
		this.setSize(width, height);
		//����
		this.setTitle("����˹����");
		//���ɵ��ڴ�С
		this.setResizable(false);
		//����
		this.setLayout(null);
		//������ɫ
		this.setBackground(Color.gray);
		//�ɼ���
		this.setVisible(true);

		//�޸�logo
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image logo = tk.getImage("src/logo.PNG");
		this.setIconImage(logo);

		//ʵ����һ��Shape����
		s = new Shape(96,-10-25,type,this);
		type = r.nextInt(7);
		changestep = r.nextInt(4);

		//����ˢ���߳�
		new Thread(new paintThread()).start();
		//��������
		this.addKeyListener(new KeyMonitor());
	}

	//������Ϸ����
	public void paint(Graphics g){

		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3f));

		//Color c=g.getColor();
		//g.drawRect(17,37, 253, 452);
		//g.setColor(c);

		//������
		g.setColor(new Color(105,105,105));
		for(int i=0;i<10;i++){
			for(int j=0;j<18;j++){
				g.drawRect(20+i*unit.SIZE, 40+j*unit.SIZE, unit.SIZE-4, unit.SIZE-4);
				g.fillRect(20+i*unit.SIZE+3, 40+j*unit.SIZE+3, unit.SIZE-9, unit.SIZE-9);
			}
		}

		//�������������״����ɫ
		s.draw(g);
		s.changeStatus();

		//���������us��������unit����
		for(int i=0;i<us.size();i++){
			us.get(i).draw(g);
		}



		//�÷���岿��
		g.clearRect(275,80,100,400);
		//g.clearRect(0, 0, 270, 800);
		g.setColor(Color.black);
		Font f = g.getFont();
		g.setFont(new Font("����",Font.BOLD,20));
		g.drawString("score:", 280, 80);
		g.drawString(""+score, 280, 110);
		g.setFont(f);

		//
		s.drawnext(g,280,120,type,changestep);
	}


	//˫�����������
	public void update(Graphics src){
		if(offScreenImage == null){
			offScreenImage = this.createImage(450,520);//253,452//500,600
			goffScreen = offScreenImage.getGraphics();
		}
		goffScreen.setColor(getBackground());
		goffScreen.fillRect(0, 0, 450, 520);//0,0,270,600

		paint(goffScreen);
		src.drawImage(offScreenImage, 0, 0,null);

	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Textclient text = new Textclient();
		text.lanchar();
		//���ڼ����ر�
		text.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}

		});
	}

	//ˢ����
	private class paintThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				repaint();
				if(s.stoped){
					s = null;
					s = new Shape(96,-10-25,type,Textclient.this);
					type = r.nextInt(7);
					for(int i=0;i<changestep;i++){
						s.rotate();
					}
					changestep = r.nextInt(4);
				}

				if(!s.stoped)s.drop();
				try{
					if(s.speed == false)
						Thread.sleep(500);
					else
						Thread.sleep(5);
				}catch(InterruptedException e){
					e.printStackTrace();

				}

			}
		}
	}

	//�����¼��ڲ���
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			//super.keyPressed(arg0);
			s.keypressed(e);

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			//super.keyReleased(arg0);
			int key = e.getKeyCode();
			switch(key){
				case KeyEvent.VK_DOWN:
					s.keyReleased(e);
			}

		}

	}

	//�ж��Ƿ�����
	public boolean isFull(int height){
		int count = 0;
		for(int i=0;i<us.size();i++){
			unit u = us.get(i);
			if(u.getY() == height){
				count++;
			}
		}
		if(count == 10){
			return true;
		}
		else return false;
	}

	//������ʧ
	public void disappear(int height){
		Iterator it = us.iterator();
		while(it.hasNext()){
			unit u = (unit) it.next();
			if(u.getY() == height){
				it.remove();
			}
		}
		//score += 10;
	}

	//���к�������unit����
	public void reloadunits(int height){
		Iterator it = us.iterator();
		while(it.hasNext()){
			unit u = (unit)it.next();
			if(u.getY() < height){
				u.setY(u.getY()+unit.SIZE);
			}
		}
	}


}

