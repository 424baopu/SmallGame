package shudu02;

import java.awt.BasicStroke;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

public class Test extends JFrame{

    int x = 300;
    int y = 90;
    int width = 800;
    int height = 600;

    int num = 0;

    int count = 1;
    int hole = 30;
    int res;
    JButton[] btnArray = new JButton[9];
    int[] total = new int[10];

    String url="src/bg1.jpg";
    JLabel background = new JLabel();

    Timer userTimeAction;
    int usedTime = 0; // 用时


    Font font = new Font("抱朴", Font.BOLD, 25);

    public Test(){
        super();
        lanchar();
        addmenu();
    }

    private void addmenu() {
        // TODO Auto-generated method stub
        MenuBar menubar;
        Menu game,about;
        MenuItem began,over,choose,background,statistic,line,aboutgame;

        menubar = new MenuBar();
        game = new Menu("菜单");
        began = new MenuItem("开始");
        began.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                beganGame(hole);
            }
        });
        choose = new MenuItem("选择");
        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                addchoose();
            }
        });
        background = new MenuItem("背景");
        background.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                addBack();
            }
        });
        statistic = new MenuItem("222");
        over = new MenuItem("结束游戏");
        over.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                System.exit(0);
            }
        });
        line=new MenuItem("-");
        game.add(began);
        game.add(choose);
        game.add(background);
        //game.add(statistic);
        game.add(line);
        game.add(over);
        menubar.add(game);

        about = new Menu("关于");
        aboutgame = new MenuItem("关于游戏");
        about.add(aboutgame);
        menubar.add(about);

        this.setMenuBar(menubar);

    }

    //??????
    protected void beganGame(int hole) {
        // TODO Auto-generated method stub
        if(num!=0){
            delete();
            userTimeAction.stop();
        }
        addComponent();
        addJbutton(hole);
        num++;
    }

    protected void addchoose() {
        // TODO Auto-generated method stub

        Dialog level = new Dialog(this,"困难程度");
        level.setLayout(null);
        level.setVisible(true);
        level.setBounds(500, 300, 300, 300);
        level.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                level.setVisible(false);
            }
        });

        //?????????
        JRadioButton J1 = new JRadioButton("无脑");// ?????????????????????
        J1.setBounds(30, 40, 120, 20);//???????????????????
        J1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hole = 1;
            }
        });
        JRadioButton J2 = new JRadioButton("简单",true);
        J2.setBounds(30, 80, 120, 20);
        J2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hole = 30;
            }
        });
        JRadioButton J3 = new JRadioButton("一般");
        J3.setBounds(30, 120, 120, 20);
        J3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hole = 40;
            }
        });
        JRadioButton J4 = new JRadioButton("困难");
        J4.setBounds(30, 160, 120, 20);
        J4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hole = 50;
            }
        });
        JRadioButton J5 = new JRadioButton("空白");
        J5.setBounds(30, 200, 120, 20);
        J5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hole = 81;
            }
        });

        ButtonGroup sexRadioButtonGroup = new ButtonGroup();
        sexRadioButtonGroup.add(J1);
        sexRadioButtonGroup.add(J2);
        sexRadioButtonGroup.add(J3);
        sexRadioButtonGroup.add(J4);
        sexRadioButtonGroup.add(J5);
        level.add(J1);
        level.add(J2);
        level.add(J3);
        level.add(J4);
        level.add(J5);

        //??????
        JButton apply = new JButton("确定");
        apply.setBounds(140, 250, 130, 25);
        apply.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                beganGame(hole);
            }

        });
        level.add(apply);

    }

    protected void addBack() {
        Dialog b = new Dialog(this,"背景图片");//?????????????ó?true???????????
        b.setLayout(null);
        b.setVisible(true);
        b.setBounds(400, 200, 400, 400);
        b.setResizable(false);
        b.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                b.setVisible(false);
            }
        });


        JLabel Picture = new JLabel();
        ImageIcon icon = new ImageIcon(url);
        icon.setImage(icon.getImage().getScaledInstance(380,300,Image.SCALE_DEFAULT));
        Picture.setIcon(icon);
        Picture.setBounds(10, 60, 380, 280);

        Choice c = new Choice();

        c.addItem("bg1");
        c.setBounds(10, 30, 380, 10);
        for(int i=1;i<9;i++){
            c.add("bg"+i);
            c.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    url = "src/"+c.getSelectedItem()+".jpg";
                    ImageIcon icon = new ImageIcon(url);
                    icon.setImage(icon.getImage().getScaledInstance(380,300,Image.SCALE_DEFAULT));
                    Picture.setIcon(icon);
                }
            });
        }

        //??????
        JButton apply = new JButton("确认");
        apply.setBounds(330, 360, 60, 25);
        apply.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setbackground(url);
            }

        });

        b.add(c);
        b.add(Picture);
        b.add(apply);

    }

    //???????
    protected void setbackground(String url) {

        //--------- 2. ????ContentPane??? -----------
        remove(background);
        //?????????????JPanel?????????÷???setOpaque()?????????????
        JPanel imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);

        //------------------------ 3. ????/????? -------------------------

        ImageIcon icon = new ImageIcon(url);
        icon.setImage(icon.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));

        //------------------------ 4. ???汳??:??????С ------------------
        background.setIcon(icon);
        background.setBounds(0, 0, width, height);

        //------- 6. ???????????????????????????? ----------------
        this.getLayeredPane().add(background,new Integer(0));//Integer.MIN_VALUE
        repaint();
    }

    protected void delete() {
        // TODO Auto-generated method stub
        this.getContentPane().removeAll();
    }

    private void addJbutton(int hole) {

        res = hole;
        //??????jpanel??????????
        JPanel[] panels = new JPanel[9];
        for(int i =0;i<9;i++){
            panels[i] = new JPanel();
            int x=(i%3)*152,y=(i/3)*152;
            panels[i].setBounds(x+30 , y+57, 148, 148);
            panels[i].setLayout(new GridLayout(3,3));;
            this.add(panels[i]);
        }

        SudokuPuzzleGenerator shudu = new SudokuPuzzleGenerator();
        int[][] randomMatrix = shudu.generatePuzzleMatrix(hole);

        JButton[][] Map = new JButton[9][9];

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){

                if(randomMatrix[i][j]!=0){
                    Map[i][j] = new JButton(String.valueOf(randomMatrix[i][j]));
                    Map[i][j].setBackground(new Color(176,196,222));
                    Map[i][j].setEnabled(false);
                }else{
                    Map[i][j] = new JButton("");
                    Map[i][j].setBackground(new Color(230,230,250));
                    Map[i][j].addActionListener(new ActionListener()
                    {

                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            JButton jbt = (JButton)e.getSource();
                            if(e.getActionCommand()==""){
                                res--;
                                total[count]++;
                            }else{
                                total[Integer.parseInt(e.getActionCommand())]--;
                                total[count]++;
                            }

                            for(int i = 1;i<=9;i++){
                                if(total[i]>=9){
                                    btnArray[i-1].setBackground(new Color(176,196,222));
                                }else{
                                    btnArray[i-1].setBackground(new Color(230,230,250));
                                }
                            }

                            jbt.setText(String.valueOf(count));

                            if(res == 0){
                                // ?????????????
                                userTimeAction.stop();
                                if(check()){
                                    JOptionPane.showMessageDialog(null,"总共用时:"+ usedTime+ "sec");
                                }else{
                                    JOptionPane.showMessageDialog(null, "?成功!");
                                }
                            }
                        }

                        //?????????
                        private boolean check() {

                            int[][] Matrix = new int[9][9];
                            int[] rows = new int[10];
                            int[] cols = new int[10];
                            int[] qual = new int[10];

                            for(int i=0;i<9;i++){
                                rows[i] = 0;cols[i] = 0;qual[i] = 0;
                            }

                            for(int i=0;i<9;i++){
                                for(int j=0;j<9;j++){
                                    Matrix[i][j] = Integer.parseInt(Map[i][j].getActionCommand());
                                }
                            }

                            for(int i = 0;i< 9;i++){
                                for(int j=0;j<9;j++){
                                    rows[Matrix[j][i]]++;
                                    cols[Matrix[i][j]]++;
                                }
                                for(int a=1;a<10;a++){
                                    if(rows[a]>1||cols[a]>1)return false;
                                    rows[a]=0;
                                    cols[a]=0;
                                }
                            }

                            for(int j=0,i=0;j < 9;i+=3,j+=3){
                                for(int k=0;k<3;k++){
                                    qual[Matrix[i][j+k]]++;
                                    qual[Matrix[i+1][j+k]]++;
                                    qual[Matrix[i+2][j+k]]++;
                                }
                                for(int a=1;a<10;a++){
                                    if(qual[a]>1)return false;
                                    qual[a]=0;
                                }

                            }

                            return true;
                        }
                    });

                }

                Map[i][j].setFont(font);
                Map[i][j].setFocusPainted(false);
                panels[i/3*3+j/3].add(Map[i][j]);
            }

        }


    }

    //?????
    @Override
    public void paint(Graphics g){

        super.paint(g);

        g.setColor(Color.white);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5));

        for(int i = 0;i<3;i++){
            for(int j=0;j<3;j++){
                g.drawRect(30+i*152, 100+j*152, 152, 152);
            }
        }

        //???????????????????????
        g.drawRect(550, 100, 210, 60);
        g.drawRect(550, 220, 210 ,210);
        g.drawRect(550, 490, 210, 60);

    }

    private void addComponent() {

        // ????????
        JPanel panel1 = new JPanel();
        addPanelTime(panel1);
        // ?????????
        JPanel panel2 = new JPanel();
        addPanelNum(panel2);
        //???????
        JPanel panel3 = new JPanel();
        addPanelMy(panel3);
        // ?????????????
        this.add(panel1);
        this.add(panel2);
        this.add(panel3);

    }

    private void addPanelTime(JPanel panelComponent) {

        usedTime = 0;
        panelComponent.setBounds(550, 57, 205, 55);
        panelComponent.setLayout(new GridLayout(1,1));
        panelComponent.setBackground(new Color(230,230,250));

        JLabel label = new JLabel("",JLabel.CENTER);
        label.setFont(font);


        userTimeAction = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                usedTime++;
                //System.out.println(usedTime);
                label.setText("计时:" + usedTime+ "sec");
            }
        });
        userTimeAction.start();

        panelComponent.add(label);
    }

    private void addPanelNum(JPanel panelComponent) {
        // TODO Auto-generated method stub
        for(int i = 1;i < 10; i++) total[i] = 0;
        panelComponent.setBounds(550, 176, 207 ,207);
        GridLayout Layout = new GridLayout(3,3);
        panelComponent.setLayout(Layout);

//		JButton[] btnArray = new JButton[9];
        for(int i = 0;i < btnArray.length;i++){
            btnArray[i] = new JButton(String.valueOf(i+1));
            btnArray[i].setBackground(new Color(230,230,250));
            btnArray[i].setFont(font);
            btnArray[i].setFocusPainted(false);
            panelComponent.add(btnArray[i]);
            btnArray[i].addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    count = Integer.parseInt(e.getActionCommand());
                }

            });
        }

    }

    private void addPanelMy(JPanel panelComponent) {
        // TODO Auto-generated method stub
        panelComponent.setBounds(550, 447, 205, 55);
        panelComponent.setBackground(new Color(230,230,250));
        panelComponent.setLayout(new GridLayout(1,1));//new FlowLayout()

        JLabel label = new JLabel("抱朴",JLabel.CENTER);
        label.setFont(font);
        panelComponent.add(label);

    }


    public void lanchar(){

        this.setBounds(x, y, width, height);
        this.setTitle("数独");
        this.setResizable(false);
        this.setLayout(null);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//HIDE_ON_CLOSE

        setbackground("src/bg1.jpg");

        //????????????????д???????????????
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);//?????????????????????
            }
        });

    }


    public static void printMatrix(int[][] randomMatrix) {
        for (int rowNum = 0; rowNum < 9; rowNum++) {
            for (int colNum = 0; colNum < 9; colNum++) {
                System.out.print(randomMatrix[rowNum][colNum] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        Test test = new Test();
        //test.lanchar();
        test.setVisible(true);


    }


}

