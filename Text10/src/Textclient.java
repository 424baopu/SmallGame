/*
 * 1.得分面板
 * 2.下一个图形预览
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
	//声明一个random对象
	public static Random r = new Random();
	//下一个对象类型及变化次数
	int type,changestep;

	//声明位置变量x,y
	public static final int x = 300;
	public static final int y = 100;

	//声明窗口大小变量width,length
	public static final int width = 450;
	public static final int height = 520;

	//双缓冲
	private Image offScreenImage = null;
	private Graphics goffScreen;

	//Shape s = new Shape(96,40,type,this);
	Shape s = null;

	//放unit
	List<unit> us = new ArrayList<unit>();
	private Iterator<unit> Iterator;

	public void lanchar(){
		//窗口位置
		this.setLocation(x, y);
		//窗口大小
		this.setSize(width, height);
		//标题
		this.setTitle("俄罗斯方块");
		//不可调节大小
		this.setResizable(false);
		//管理
		this.setLayout(null);
		//背景颜色
		this.setBackground(Color.gray);
		//可见性
		this.setVisible(true);

		//修改logo
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image logo = tk.getImage("src/logo.PNG");
		this.setIconImage(logo);

		//实例化一个Shape对象
		s = new Shape(96,-10-25,type,this);
		type = r.nextInt(7);
		changestep = r.nextInt(4);

		//启动刷新线程
		new Thread(new paintThread()).start();
		//按键监听
		this.addKeyListener(new KeyMonitor());
	}

	//画出游戏区域
	public void paint(Graphics g){

		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3f));

		//Color c=g.getColor();
		//g.drawRect(17,37, 253, 452);
		//g.setColor(c);

		//背景块
		g.setColor(new Color(105,105,105));
		for(int i=0;i<10;i++){
			for(int j=0;j<18;j++){
				g.drawRect(20+i*unit.SIZE, 40+j*unit.SIZE, unit.SIZE-4, unit.SIZE-4);
				g.fillRect(20+i*unit.SIZE+3, 40+j*unit.SIZE+3, unit.SIZE-9, unit.SIZE-9);
			}
		}

		//随机产生方块形状及颜色
		s.draw(g);
		s.changeStatus();

		//画出存放在us里面所有unit对象
		for(int i=0;i<us.size();i++){
			us.get(i).draw(g);
		}



		//得分面板部分
		g.clearRect(275,80,100,400);
		//g.clearRect(0, 0, 270, 800);
		g.setColor(Color.black);
		Font f = g.getFont();
		g.setFont(new Font("宋体",Font.BOLD,20));
		g.drawString("score:", 280, 80);
		g.drawString(""+score, 280, 110);
		g.setFont(f);

		//
		s.drawnext(g,280,120,type,changestep);
	}


	//双缓冲减缓抖动
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
		//窗口监听关闭
		text.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}

		});
	}

	//刷新类
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

	//按键事件内部类
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

	//判断是否满行
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

	//满行消失
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

	//满行后处理其他unit对象
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

