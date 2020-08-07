import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Random;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
public class Network extends JFrame 
{
    //implements ActionListener, MouseListener
    Canvas myGraphic;
    final String Source = "Source.png";
    ImageIcon SourceIcon = new ImageIcon(Source);
    final String Sink = "Sink.png";
    ImageIcon SinkIcon = new ImageIcon(Sink);
    final String linearPipe = "Pipes2.png";
    ImageIcon PipeIcon = new ImageIcon(linearPipe);
    //Pipe Grid[][] = new Pipe[10][10];
    Pipe StartPipe[] = new Pipe[10];
    Pipe EndPipe[] = new Pipe[10];
    //Keeps track of all of the ends of pipe trails, depending on the sources
    public Network(){
        int x = 500;
        int y = 500;
        Scanner inputStream = new Scanner(System.in);
        String Titlename = "WaterNetwork";
        setTitle(Titlename);
        this.getContentPane().setPreferredSize(new Dimension(x,y));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.toFront();
        this.setVisible(true);

        JMenuBar Bar = new JMenuBar();
        this.setJMenuBar(Bar);
        JMenu Menu1 = new JMenu("Menu1");
        Bar.add(Menu1);
        JMenuItem Item1 = new JMenuItem("Item number 1");
        Item1.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        //Item1.addActionListener(this);
        Menu1.add(Item1);
        //addMouseListener(this); 
        JPanel stage = new JPanel();
        stage.setPreferredSize(new Dimension(x,y));
        Canvas myGraphic = new Canvas();
        stage.add(myGraphic);

        Pipe FirstPipe = new Pipe("Source", 10, 0);
        StartPipe[0]= FirstPipe;
        EndPipe[0] = FirstPipe;
        Pipe SecondPipe = new Pipe("Linear", 10);
        Pipe ThirdPipe = new Pipe("Split", 10);
        Pipe FourthPipe = new Pipe("Sink", 10);
        Pipe FifthPipe = new Pipe("Sink", 10);
        AddPipe(SecondPipe, 0);
        AddPipe(ThirdPipe, 0);
        AddPipe(FourthPipe, FifthPipe, 0);

        // boolean Run = true;
        // try{
        // while(Run == true){
        // repaint();
        // Thread.sleep(1000);
        // }
        // } catch(InterruptedException e){}
    }

    public void AddPipe(Pipe nextPipe1, int pipeChain){
        if(nextPipe1.pipeType.equals("Join") != true && EndPipe[pipeChain].equals("Split") != true){
            EndPipe[pipeChain].setNextPipe(nextPipe1, pipeChain);
            EndPipe[pipeChain] = nextPipe1;
        }else{
            System.out.println("You cannot use this method on a split or join");
        }
    }

    public void AddPipe(Pipe nextPipe1, Pipe nextPipe2, int pipeChain){
        if(EndPipe[pipeChain].pipeType.equals("Split")){
                        int x = 0;
            while(StartPipe[x] != null){
                x = x + 1;
            }
            EndPipe[pipeChain].setNextPipe(nextPipe1, nextPipe2, pipeChain, x);
            EndPipe[pipeChain] = nextPipe1;
            StartPipe[x] = nextPipe2;
            EndPipe[x] = nextPipe2;
        }else{
            System.out.println("You cannot split any other pipe than a split");
        }
    }

    public void AddPipe(Pipe Pipe1, Pipe Pipe2, Pipe nextPipe1, int pipeChain1, int pipeChain2){
        if(nextPipe1.pipeType.equals("Join")){
            Pipe1.setNextPipe(nextPipe1, pipeChain1);
            Pipe2.setNextPipe(nextPipe1, pipeChain1);
            EndPipe[pipeChain1] = nextPipe1;
            EndPipe[pipeChain2] = nextPipe1;
        }else{
            System.out.println("You cannot join any other pipe than a join");
        }
    }
    // public void actionPerformed(ActionEvent Event){
    // Scanner inputStream = new Scanner(System.in);
    // String command = Event.getActionCommand(); //this makes the string Command equal the name of the item which was clicked on (eg if I click Item4, it prints out "Item number 4" in the text box
    // switch(command){
    // case "Item number 1": System.exit(0);
    // break;
    // default: System.out.println("X");
    // }
    // }

    // public void InteractingWithMouse(){
    // JDialog InteractingWithMouse = new JDialog(this);
    // InteractingWithMouse.setBounds(200,200,1,1); //(x spawn coordinates, y spawn coordinates, x size, y size)
    // InteractingWithMouse.toFront();
    // InteractingWithMouse.setTitle("Hello");
    // TextArea Text= new TextArea("Greetings, planet!");
    // InteractingWithMouse.add(Text);
    // InteractingWithMouse.setVisible(true);
    // }

    // void MakeADialog(String String1, int x, int y){
    // JDialog DialogBox3 = new JDialog(this);
    // DialogBox3.setBounds(x,y,10*String1.length(),80);
    // DialogBox3.toFront();
    // DialogBox3.setTitle("Hello");
    // TextArea Text3= new TextArea(String1);
    // DialogBox3.add(Text3);
    // DialogBox3.setVisible(true);
    // }

    // public void paint(Graphics G){
    // super.paint(G);
    // int a = 0; //equivalent to x;
    // int b = 0; //equivalent to y;
    // while(b < 10){
    // while(a < 10){
    // if(Grid[a][b] != null){
    // if(Grid[a][b].returnPipeType() == 1){
    // PipeIcon.paintIcon(this, G, Grid[a][b].returnX()*50, Grid[a][b].returnY()*50);
    // }else if(Grid[a][b].returnPipeType() == 2){
    // }else if(Grid[a][b].returnPipeType() == 3){
    // }else if(Grid[a][b].returnPipeType() == 4){
    // }else if(Grid[a][b].returnPipeType() == 5){
    // }else if(Grid[a][b].returnPipeType() == 6){
    // SourceIcon.paintIcon(this, G, Grid[a][b].returnX()*50, Grid[a][b].returnY()*50);
    // }else if(Grid[a][b].returnPipeType() == 7){
    // SinkIcon.paintIcon(this, G, Grid[a][b].returnX()*50, Grid[a][b].returnY()*50);
    // }
    // }
    // a = a + 1;
    // }
    // a = 0;
    // b = b + 1;
    // }
    // a = 0;
    // b = 0;
    // PipeIcon.paintIcon(this, G, 100,100);
    // }

    // public void mouseExited(MouseEvent e){}

    // public void mouseEntered(MouseEvent e){}

    // public void mouseReleased(MouseEvent e){}

    // public void mousePressed(MouseEvent e){}

    // public void mouseClicked(MouseEvent e){
    // int mouseX = e.getX();
    // int mouseY = e.getY();
    // }
}
