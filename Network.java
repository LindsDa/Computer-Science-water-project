import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Random;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Network extends JFrame implements ActionListener, MouseListener
{
    //implements ActionListener,
    Canvas myGraphic;
    final String Source = "Source.png";
    ImageIcon SourceIcon = new ImageIcon(Source);
    final String Sink = "Sink.png";
    ImageIcon SinkIcon = new ImageIcon(Sink);
    final String linearPipe = "Pipes.png";
    ImageIcon PipeIcon = new ImageIcon(linearPipe);
    final String leftTurn = "LeftTurn.png";
    ImageIcon leftTurnIcon = new ImageIcon(leftTurn);
    final String rightTurn = "RightTurn.png";
    ImageIcon rightTurnIcon = new ImageIcon(rightTurn);
    final String leftSplit = "LeftSplit.png";
    ImageIcon leftSplitIcon = new ImageIcon(leftSplit);
    final String rightSplit = "RightSplit.png";
    ImageIcon rightSplitIcon = new ImageIcon(rightSplit);
    final String leftJoin = "LeftJoin.png";
    ImageIcon leftJoinIcon = new ImageIcon(leftJoin);
    final String rightJoin = "RightJoin.png";
    ImageIcon rightJoinIcon = new ImageIcon(rightJoin);
    Pipe Grid[][] = new Pipe[10][10];
    Pipe StartPipe[] = new Pipe[10];
    Pipe EndPipe[] = new Pipe[10];
    ImageIcon rotatedImage;
    String FileName = "SavedProgram.txt";
    File myFile = new File(FileName);
    //Keeps track of all of the ends of pipe trails, depending on the sources
    public Network(){
        Scanner inputStream = new Scanner(System.in);
        String Titlename = "WaterNetwork";
        setTitle(Titlename);
        int x = 500;
        int y = 500;
        this.getContentPane().setPreferredSize(new Dimension(x,y));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.toFront();
        this.setVisible(true);

        JMenuBar Bar = new JMenuBar();
        this.setJMenuBar(Bar);
        JMenu Menu1 = new JMenu("File");
        Bar.add(Menu1);
        JMenuItem Item1 = new JMenuItem("Load");
        Item1.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item1.addActionListener(this);
        Menu1.add(Item1);
        JMenuItem Item2 = new JMenuItem("Save");
        Item2.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item2.addActionListener(this);
        Menu1.add(Item2);
        JMenuItem Item3 = new JMenuItem("Close");
        Item3.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item3.addActionListener(this);
        Menu1.add(Item3);

        JMenu Menu2 = new JMenu("PipeChain 1");
        Bar.add(Menu2);
        JMenuItem Item4 = new JMenuItem("Add Pipe 1");
        Item4.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item4.addActionListener(this);
        Menu2.add(Item4);
        JMenuItem Item5 = new JMenuItem("Add Sink 1");
        Item5.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item5.addActionListener(this);
        Menu2.add(Item5);
        JMenuItem Item10 = new JMenuItem("Add Left Turn 1");
        Item10.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item10.addActionListener(this);
        Menu2.add(Item10);
        JMenuItem Item11 = new JMenuItem("Add Right Turn 1");
        Item11.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item11.addActionListener(this);
        Menu2.add(Item11);
        JMenuItem Item12 = new JMenuItem("Add Left Split 1");
        Item12.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item12.addActionListener(this);
        Menu2.add(Item12);
        JMenuItem Item26 = new JMenuItem("Add Right Split 1");
        Item26.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item26.addActionListener(this);
        Menu2.add(Item26);
        JMenuItem Item13 = new JMenuItem("Add Left Join 1");
        Item13.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item13.addActionListener(this);
        Menu2.add(Item13);
        JMenuItem Item27 = new JMenuItem("Add Right Join 1");
        Item27.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item27.addActionListener(this);
        Menu2.add(Item27);

        JMenu Menu3 = new JMenu("PipeChain 2");
        Bar.add(Menu3);
        JMenuItem Item6 = new JMenuItem("Add Pipe 2");
        Item6.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item6.addActionListener(this);
        Menu3.add(Item6);
        JMenuItem Item7 = new JMenuItem("Add Sink 2");
        Item7.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item7.addActionListener(this);
        Menu3.add(Item7);
        JMenuItem Item14 = new JMenuItem("Add Left Turn 2");
        Item14.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item14.addActionListener(this);
        Menu3.add(Item14);
        JMenuItem Item15 = new JMenuItem("Add Right Turn 2");
        Item15.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item15.addActionListener(this);
        Menu3.add(Item15);
        JMenuItem Item16 = new JMenuItem("Add Left Split 2");
        Item16.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item16.addActionListener(this);
        Menu3.add(Item16);
        JMenuItem Item24 = new JMenuItem("Add Right Split 2");
        Item24.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item24.addActionListener(this);
        Menu3.add(Item24);
        JMenuItem Item17 = new JMenuItem("Add Left Join 2");
        Item17.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item17.addActionListener(this);
        Menu3.add(Item17);
        JMenuItem Item25 = new JMenuItem("Add Right Join 2");
        Item25.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item25.addActionListener(this);
        Menu3.add(Item25);

        JMenu Menu4 = new JMenu("PipeChain 3");
        Bar.add(Menu4);
        JMenuItem Item8 = new JMenuItem("Add Pipe 3");
        Item8.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item8.addActionListener(this);
        Menu4.add(Item8);
        JMenuItem Item9 = new JMenuItem("Add Sink 3");
        Item9.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item9.addActionListener(this);
        Menu4.add(Item9);
        JMenuItem Item18 = new JMenuItem("Add Left Turn 3");
        Item18.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item18.addActionListener(this);
        Menu4.add(Item18);
        JMenuItem Item19 = new JMenuItem("Add Right Turn 3");
        Item19.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item19.addActionListener(this);
        Menu4.add(Item19);
        JMenuItem Item20 = new JMenuItem("Add Left Split 3");
        Item20.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item20.addActionListener(this);
        Menu4.add(Item20);
        JMenuItem Item23 = new JMenuItem("Add Right Split 3");
        Item23.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item23.addActionListener(this);
        Menu4.add(Item23);
        JMenuItem Item21 = new JMenuItem("Add Left Join 3");
        Item21.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item21.addActionListener(this);
        Menu4.add(Item21);
        JMenuItem Item22 = new JMenuItem("Add Right Join 3");
        Item22.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
        Item22.addActionListener(this);
        Menu4.add(Item22);
        //addMouseListener(this); 
        JPanel stage = new JPanel();
        stage.setPreferredSize(new Dimension(x,y));
        Canvas myGraphic = new Canvas();
        stage.add(myGraphic);

        addMouseListener(this);

        boolean Run = true;
        try{
            newSource(10,0, 0, 0);
            newSource(10,1, 0, 1);
            newSource(10, 2, 0, 2);
            while(Run == true){
                repaint();
                Thread.sleep(1000);
            }
        } catch(InterruptedException e){}
    }

    public void newSource(int maxFlowRate, int pipeChain, int x, int y){
        if(Grid[x][y] == null){
            Pipe newSource = new Pipe("Source", maxFlowRate, 0);
            StartPipe[pipeChain] = newSource;
            EndPipe[pipeChain] = newSource;
            Grid[x][y] = newSource;
            newSource.x = x;
            newSource.y = y;
        }else{
            System.out.println("There is already a pipe at these coordinates");   
        }
    }

    public void AddPipe(Pipe nextPipe1, int pipeChain){
        if(nextPipe1.pipeType.equals("Join") != true && EndPipe[pipeChain].equals("leftSplit") != true && EndPipe[pipeChain].equals("rightSplit") != true){
            EndPipe[pipeChain].setNextPipe(nextPipe1, pipeChain);
            EndPipe[pipeChain] = nextPipe1;
            if(Grid[nextPipe1.x][nextPipe1.y] == null){
                Grid[nextPipe1.x][nextPipe1.y] = nextPipe1;
            }else{

            }
        }else{
            System.out.println("You cannot use this method on a split or join");
        }
    }

    //Adding in a next pipe for a split
    public void AddPipe(Pipe nextPipe1, Pipe nextPipe2, int pipeChain){
        if(EndPipe[pipeChain].pipeType.equals("leftSplit") || EndPipe[pipeChain].pipeType.equals("rightSplit")){
            int x = 0;
            while(StartPipe[x] != null){
                x = x + 1;
            }
            EndPipe[pipeChain].setNextPipe(nextPipe1, nextPipe2, pipeChain, x);
            EndPipe[pipeChain] = nextPipe1;
            StartPipe[x] = nextPipe2;
            EndPipe[x] = nextPipe2;
            Grid[nextPipe1.x][nextPipe1.y] = nextPipe1;
            Grid[nextPipe2.x][nextPipe2.y] = nextPipe2;
        }else{
            System.out.println("You cannot split any other pipe than a split");
        }
    }

    //Add a next pipe for a join
    public void AddPipe(Pipe Pipe1,  int pipeChain1, Pipe Pipe2, int pipeChain2, Pipe nextPipe1){
        if(nextPipe1.pipeType.equals("Join")){
            Pipe1.setNextPipe(nextPipe1, pipeChain1);
            Pipe2.setNextPipe(nextPipe1, pipeChain1);
            EndPipe[pipeChain1] = nextPipe1;
            EndPipe[pipeChain2] = nextPipe1;
            Grid[nextPipe1.x][nextPipe1.y] = nextPipe1;
        }else{
            System.out.println("You cannot join any other pipe than a join");
        }
    }

    //Adding in a next pipe for a split leading to a join
    //nextPipe1 has the old pipechain, nextpipe2 has the new one
    public void AddPipe(Pipe Pipe1, int pipeChain1, Pipe Pipe2, int pipeChain2, Pipe nextPipe1, Pipe nextPipe2){
        if((Pipe1.pipeType.equals("leftSplit") || Pipe2.pipeType.equals("leftSplit") || Pipe1.pipeType.equals("rightSplit") || Pipe2.pipeType.equals("rightSplit")) && (nextPipe1.pipeType.equals("Join") || nextPipe2.pipeType.equals("Join"))){
            Pipe Split;
            int PipeChainJoin;
            Pipe notSplit;
            Pipe Join;
            Pipe notJoin;
            if(Pipe1.pipeType.equals("leftSplit") || Pipe1.pipeType.equals("rightSplit")){
                Split = Pipe1;
                notSplit = Pipe2;
            }else{
                Split = Pipe2;
                notSplit = Pipe1;
            }
            if(nextPipe1.pipeType.equals("Join")){
                Join = nextPipe1;
                notJoin = nextPipe2;
                PipeChainJoin = pipeChain1;
            }else{
                Join = nextPipe2;
                notJoin = nextPipe1;
                PipeChainJoin = pipeChain2;
            }
            Split.setNextPipe(nextPipe1, nextPipe2, pipeChain1, pipeChain2);
            notSplit.setNextPipe(Join, PipeChainJoin);
            EndPipe[pipeChain1] = nextPipe1;
            EndPipe[pipeChain2] = nextPipe2;
        }else{
            System.out.println("You can only use this method on a split leading to a join");
        }
    }

    //FIND SOME WAY OF DOING THIS FOR ALL OF THEM!
    public void AddPipe(Pipe Pipe1, int pipeChain1, Pipe Pipe2, int pipeChain2, Pipe nextPipe1, Pipe nextPipe2, Pipe nextPipe3){
        if((Pipe1.pipeType.equals("Split") && Pipe2.pipeType.equals("Split")) && (nextPipe1.pipeType.equals("Join") || nextPipe2.pipeType.equals("Join"))){

        }
    }

    public void actionPerformed(ActionEvent Event){
        Scanner inputStream = new Scanner(System.in);
        String command = Event.getActionCommand(); //this makes the string Command equal the name of the item which was clicked on (eg if I click Item4, it prints out "Item number 4" in the text box
        switch(command){
            case "File":
            break;
            case "Load":
            Load();
            break;
            case "Save":
            Save();
            break;
            case "Close": System.exit(0);
            break;
            case "Add Pipe 1":
            makeNewPipe(0, "Linear");
            break;
            case "Add Sink 1":
            makeNewPipe(0, "Sink");
            break;
            case "Add Left Turn 1":
            makeNewPipe(0, "leftTurn");
            break;
            case "Add Right Turn 1":
            makeNewPipe(0, "rightTurn");
            break;
            case "Add Left Split 1":
            break;
            case "Add Right Split 1":
            break;
            case "Add Left Join 1":
            break;
            case "Add Right Join 1":
            break;
            case "Add Pipe 2":
            makeNewPipe(1, "Linear");
            break;
            case "Add Sink 2":
            makeNewPipe(1, "Sink");
            break;
            case "Add Left Turn 2":
            makeNewPipe(1, "leftTurn");
            break;
            case "Add Right Turn 2":
            makeNewPipe(1, "rightTurn");
            break;
            case "Add Left Split 2":
            break;
            case "Add Right Split 2":
            break;
            case "Add Left Join 2":
            break;
            case "Add Right Join 2":
            break;
            case "Add Pipe 3":
            makeNewPipe(2, "Linear");
            break;
            case "Add Sink 3":
            makeNewPipe(2, "Sink");
            break;
            case "Add Left Turn 3":
            makeNewPipe(2, "leftTurn");
            break;
            case "Add Right Turn 3":
            makeNewPipe(2, "rightTurn");
            break;
            case "Add Left Split 3":
            break;
            case "Add Right Split 3":
            break;
            case "Add Left Join 3":
            break;
            case "Add Right Join 3":
            break;
            default: System.out.println("X");
        }
    }

    //Version 1
    public void Save(){
        try{
            FileWriter Writer = new FileWriter(myFile);
            int x = 0;
            int y = 0;
            while(y < 10){
                while(x < 10){
                    if(Grid[x][y] != null){
                        Writer.write(Grid[x][y].pipeType + " " + Grid[x][y].Orientation + " ");
                        if(Grid[x][y].nextPipe1 != null){
                            Writer.write(Grid[x][y].nextPipe1.x + " " + Grid[x][y].nextPipe1.y + " ");
                        }else{
                            Writer.write(null + " " + null + " ");
                        }
                        if(Grid[x][y].nextPipe2 != null){
                            Writer.write(Grid[x][y].nextPipe2.x + " " + Grid[x][y].nextPipe2.y + " ");   
                        }else{
                            Writer.write(null + " " + null + " ");
                        }
                        Writer.write(Grid[x][y].maxFlowRate + " " + Grid[x][y].pipeChain + " " + Grid[x][y].x + " " + Grid[x][y].y + "\r\n");
                    }
                    x++;
                }
                x = 0;
                y++;
            }
            Writer.flush();
            Writer.close();
        } catch (IOException e){
            System.out.println("The save function failed");
        }
    }

    public void Load(){
        int x = 0;
        int y = 0;
        int w = 0;
        int pipeChain = 0;
        int sourceY;
        int currentY = 0;
        Boolean Run1 = true;
        Boolean Run2 = true;
        //String Parts[] = new String[10][100]
        String pipeArray[][] = new String[10][100];
        //to empty the array
        while(y < 10){
            while(x < 10){
                Grid[x][y] = null;
                x++;
            }
            x = 0;
            y++;
        }
        x = 0;
        y = 0;

        try{
            Scanner FileReader = new Scanner(myFile);
            try{
                while(FileReader.hasNextLine()){
                    String Parts[] = FileReader.nextLine().split(" ");
                    while(x<10){
                        pipeArray[x][y] = Parts[x];
                        x = x + 1;
                    }
                    x = 0;
                    y  = y + 1;
                    //y = number of lines - 1
                }
                w = 0;
                while(w < 100){
                    if(pipeArray[0][w].equals("Source")){
                        newSource(Integer.parseInt(pipeArray[6][w]), pipeChain, Integer.parseInt(pipeArray[8][w]), Integer.parseInt(pipeArray[9][w]));
                        pipeChain++;
                        sourceY = w;
                        y = 0;
                        while(Run2 == true){
                            if(pipeArray[2][sourceY].equals("null") != true && pipeArray[3][sourceY].equals("null") != true){
                                while(y < 100){
                                    if(pipeArray[8][y].equals("null") != true && pipeArray[9][y].equals("null") != true){ 
                                        if(pipeArray[8][y].equals(pipeArray[2][sourceY]) && pipeArray[9][y].equals(pipeArray[3][sourceY])){
                                            currentY = y;
                                            makeNewPipe(pipeChain-1, pipeArray[0][y], Integer.parseInt(pipeArray[6][y]));
                                            y = 100;
                                        }else{
                                            y = y + 1;
                                        }
                                    }else{
                                        y = y + 1;
                                    }
                                }
                                y = 0;
                                sourceY = currentY;
                            }else{
                                Run2 = false;
                            }
                        }
                        w = w + 1;
                    }else{
                        w = w + 1;
                    }
                }
            } catch(NumberFormatException e){
                System.out.println("You failed to load the file");   
            }
        }catch(IOException e){
            System.out.println("You failed to load the file");
        }
    }

    //for when one of the menu items is called
    public void makeNewPipe(int pipeChain, String pipeType){ 
        String Reply;
        InD Input = new InD("Pipe FlowRate:");
        boolean Check = false;
        Input.setLocationRelativeTo(this);
        Input.setVisible(true);
        Reply = Input.getText();
        try{
            Integer.parseInt(Reply);
            Check = true;
        }catch(NumberFormatException ex){ 
            Check = false; 
        }
        if(Check == true){
            if(Integer.parseInt(Reply) >= 0){
                Pipe newPipe = new Pipe(pipeType, Integer.parseInt(Reply));
                if(isGridEmpty(EndPipe[pipeChain].x, EndPipe[pipeChain].y, EndPipe[pipeChain].pipeType, EndPipe[pipeChain].Orientation)){
                    AddPipe(newPipe, pipeChain);
                }else{
                    System.out.println("You cannot add a pipe here because there is already a pipe");
                }
            }else{ 
                System.out.println("You must enter a positive number");
            }
        }else{
            System.out.println("You must enter a number, and it cannot be negative");
        }
    }

    //for when you need to load a file
    public void makeNewPipe(int pipeChain, String pipeType, int maxFlowRate){
        Pipe newPipe = new Pipe(pipeType, maxFlowRate);
        if(isGridEmpty(EndPipe[pipeChain].x, EndPipe[pipeChain].y, EndPipe[pipeChain].pipeType, EndPipe[pipeChain].Orientation)){
            AddPipe(newPipe, pipeChain);
        }else{
            System.out.println("You cannot add a pipe here because there is already a pipe");
        }
    }

    void MakeADialog(String String1, int x, int y){
        JDialog DialogBox3 = new JDialog(this);
        DialogBox3.setBounds(x,y,150, 110);
        DialogBox3.toFront();
        DialogBox3.setTitle("Information Box");
        TextArea Text3= new TextArea(String1);
        DialogBox3.add(Text3);
        DialogBox3.setVisible(true);
    }

    public void paint(Graphics G){
        super.paint(G);
        int x = 0;
        int y = 0;
        while(y<10){
            while(x<10){
                if(Grid[x][y] != null){
                    switch(Grid[x][y].pipeType){
                        case "Linear": 
                        rotate2(90*Grid[x][y].Orientation, linearPipe).paintIcon(this, G, 70*x, 70*(y+1));
                        break;
                        case "Source":
                        rotate2(90*Grid[x][y].Orientation,Source).paintIcon(this, G, 70*x, 70*(y+1));
                        break;
                        case "Sink": 
                        rotate2(90*Grid[x][y].Orientation, Sink).paintIcon(this, G, 70*x, 70*(y+1));
                        break;
                        case "leftTurn": 
                        rotate2(90*Grid[x][y].Orientation, leftTurn).paintIcon(this, G, 70*x, 70*(y+1)); 
                        break;
                        case "rightTurn": 
                        rotate2(90*Grid[x][y].Orientation, rightTurn).paintIcon(this, G, 70*x, 70*(y+1)); 
                        break;
                        case "leftSplit": 
                        rotate2(90*Grid[x][y].Orientation, leftSplit).paintIcon(this, G, 70*x, 70*(y+1)); 
                        break;
                        case "rightSplit": 
                        rotate2(90*Grid[x][y].Orientation, rightSplit).paintIcon(this, G, 70*x, 70*(y+1)); 
                        break;
                        case "leftJoin":
                        rotate2(90*Grid[x][y].Orientation, leftJoin).paintIcon(this, G, 70*x, 70*(y+1)); 
                        break;
                        case "rightJoin":
                        rotate2(90*Grid[x][y].Orientation, rightJoin).paintIcon(this, G, 70*x, 70*(y+1)); 
                        break;
                    }
                }
                x = x + 1;
            }
            x = 0;
            y = y + 1;
        }

        // PipeIcon.paintIcon(this, G, 70, 110);
        // SourceIcon.paintIcon(this, G, 0, 100);
        // SinkIcon.paintIcon(this, G, 140, 100);
        // leftTurnIcon.paintIcon(this, G, 210, 100);
        // rightTurnIcon.paintIcon(this, G, 280, 100);
        // JoinIcon.paintIcon(this, G, 350, 100);
        // SplitIcon.paintIcon(this, G, 420, 100);
    }

    public ImageIcon rotate2(double A, String imageName){
        BufferedImage loadedI;
        try{
            loadedI = ImageIO.read(getClass().getResource(imageName));
            BufferedImage rotatedImageB = rotate(loadedI, A);
            rotatedImage = new ImageIcon(rotatedImageB);
        } catch (Exception e){}
        return rotatedImage;
    }

    public BufferedImage rotate(BufferedImage image, Double degrees){
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.round(image.getWidth()*cos + image.getHeight()*sin);
        int newHeight = (int) Math.round(image.getWidth()*sin + image.getHeight()*cos);
        BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotate.createGraphics();
        int x = (newWidth - image.getWidth())/2;
        int y = (newHeight - image.getHeight())/2;
        AffineTransform at = new AffineTransform();
        at.setToRotation(radians, x+(image.getWidth()/2), y+(image.getHeight()/2));
        at.translate(x, y);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotate;
        //erg/ewtr/grwthrw/
    }

    public void mouseExited(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void mouseReleased(MouseEvent e){}

    public void mousePressed(MouseEvent e){}

    public void mouseClicked(MouseEvent e){
        int mouseX = e.getX();
        int mouseY = e.getY();
        int x = Math.round((mouseX)/70);
        int y = Math.round((mouseY - 100)/70);
        MakeADialog("Pipe type = " + Grid[x][y].pipeType + "\n" + "Flow Rate = " + Grid[x][y].flowRate + "/" + Grid[x][y].maxFlowRate + "\n" + "In pipe chain " + Grid[x][y].pipeChain + "\n" + "At position " + Grid[x][y].x  + ", " + Grid[x][y].y, x, y);
        //MakeADialog("Hello", mouseX, mouseY);
    }

    public boolean isGridEmpty(int EnterX, int EnterY, String pipeType, int Orientation){
        int x = 0;
        int y = 0; 
        switch(pipeType){
            case "Source": 
            if(Orientation == 0){
                x = EnterX + 1;
                y = EnterY;
            }else if(Orientation == 1){
                x = EnterX;
                y = EnterY - 1;
            }else if(Orientation == 2){
                x = EnterX - 1;
                y = EnterY;
            }else if(Orientation == 3){
                x = EnterX;
                y = EnterY + 1;
            }
            break;
            case "Sink":
            break;
            case "Linear":
            if(Orientation == 0){
                x = EnterX + 1;
                y = EnterY;
            }else if(Orientation == 1){
                x =EnterX;
                y = EnterY - 1;
            }else if(Orientation == 2){
                x = EnterX - 1;
                y = EnterY;
            }else if(Orientation == 3){
                x = EnterX;
                y = EnterY + 1;
            }
            break;
            case "leftTurn":
            if(Orientation == 0){
                x = EnterX;
                y = EnterY + 1;
            }else if(Orientation == 1){
                x = EnterX  + 1;
                y = EnterY;
            }else if(Orientation == 2){
                x = EnterX;
                y = EnterY - 1;
            }else if(Orientation == 3){
                x = EnterX - 1;
                y = EnterY;
            }
            break;
            case "rightTurn":
            if(Orientation == 0){
                x = EnterX;
                y = EnterY - 1;
            }else if(Orientation == 1){
                x = EnterX - 1;
                y = EnterY;
            }else if(Orientation == 2){
                x = EnterX + 1;
                y = EnterY;
            }else if(Orientation == 3){
                x = EnterX;
                y = EnterY + 1;
            }
            break;
            case "Join":
            if(Orientation == 0){

            }else if(Orientation == 1){

            }else if(Orientation == 2){

            }else if(Orientation == 3){

            }
            x = EnterX + 1;
            y = EnterY;
            break;
            case "Split":
            if(Orientation == 0){

            }else if(Orientation == 1){

            }else if(Orientation == 2){

            }else if(Orientation == 3){

            }
            x = EnterX + 1;
            y = EnterY;
            break;
        }
        if(Grid[x][y] == null){
            return true;
        }else{
            return false;
        }
    }
}
