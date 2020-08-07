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
/**
 * The class that makes the network of Pipes
 * It makes the GUI and the menus among other functions
 */
public class Network extends JFrame implements ActionListener, MouseListener
{
    Canvas myGraphic;
    //The graphic that the entire GUI is drawn on
    boolean Run = true;
    //The boolean that determines whether the program continues running (if true) or stops (if not)
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
    //All of the images for the different types of pipe
    Pipe Grid[][] = new Pipe[10][10];
    //The array that stores the pipes in a 2d grid
    Pipe StartPipe[] = new Pipe[10];
    //The array that stores the start to every pipe chain
    Pipe EndPipe[] = new Pipe[10];
    //The array that stores the end to every pipe chain
    int PipeChainNumber = 3;
    //The number that stores how many pipe chains there are, and also how many menus are made
    ImageIcon rotatedImage;
    //An ImageIcon used to rotate the images
    String FileName = "SavedProgram.txt";
    File myFile = new File(FileName);
    //The text file in which programs are saved
    public Network(){
        String Titlename = "WaterNetwork";
        setTitle(Titlename);
        int GUISizeX = 500;
        int GUISizeY = 500;
        this.getContentPane().setPreferredSize(new Dimension(GUISizeX, GUISizeY));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.toFront();
        this.setVisible(true);

        makeMenus();
        JPanel stage = new JPanel();
        stage.setPreferredSize(new Dimension(GUISizeX, GUISizeY));
        Canvas myGraphic = new Canvas();
        stage.add(myGraphic);
        addMouseListener(this);

        try{
            Pipe P = new Pipe();
            P.MakeADialog("Introduction", "Hello and welcome to David Lindsay's computer science project, WaterProject. \n In it, you can model water networks. Please fullscreen the GUI.", 50, 50);
            Thread.sleep(10000);
            P.MakeADialog("Instructions- 1/7", "At the left will be three sources. \n These produce water in the circuit \n You will be asked to enter their flow rates, which determine the flow of attached pipes", 50, 50);
            Thread.sleep(10000);
            P.MakeADialog("Instructions- 2/7", "Up the top are menus. \n Under File are quit, load, and save. \n Quit stops the program \n Load loads previously saved networks \n Save saves the current networks", 50, 50);
            Thread.sleep(10000);
            P.MakeADialog("Instructions- 3/7", "Next to File is PipeChains 1, 2, and 3. \n Pipechains are strings of pipes connected together. \n Under each Pipechain you can add different pipes: \n Linears, turns, splits, joins, and sinks. \n Every pipe is connected from a source, and the pipechain will end at a sink.", 50, 50);
            Thread.sleep(10000);
            P.MakeADialog("Instructions- 4/7", "When adding a pipe, first click on the pipe type you want to add \n In the dialog box that appears, entered the maximum flow rate you want \n (how much water flow it can handle)", 50, 50);
            Thread.sleep(10000);
            P.MakeADialog("Instructions- 5/7", "Splits create a new pipechain- splitting one into two \n To add to the new offshoot pipechain, a new menu will appear \n Joins merge two pipechains \n To do this, place a pipe on the join's intake, it will link automatically", 50, 50);
            Thread.sleep(10000);
            P.MakeADialog("Instructions- 6/7", "You start with 3 sources, which provide the water. \n The flow rates of the following pipes depend on how the water comes from the pipes before \n Splits halve the flow rate, joins add flow rates ", 50, 50);
            Thread.sleep(10000);
            P.MakeADialog("Instructions- 7/7", "To see any information about a pipe, click on it. \n You can only build pipes within a 10 by ten box from the top left of the screen. \n Enjoy the program!", 50, 50);
            //The introduction and instructions to the game, told through a series of dialog boxes.
            
            //version 1, where the flow rates of the sources were hard coded
            // newSource(10,0, 0, 0);
            // newSource(10,1, 0, 1);
            // newSource(10, 2, 0, 2);
            
            //version 2, where the flow rates of the sources are taken in from the user
            newSource(getPositiveInteger("Source one flow rate:"), 0, 0, 0);
            newSource(getPositiveInteger("Source two flow rate:"), 1, 0, 1);
            newSource(getPositiveInteger("Source three flow rate:"), 2, 0, 2);

            //The while loop that continues, repainting the GUI every 1000 miliseconds.
            while(Run == true){
                repaint();
                Thread.sleep(1000);
            }
        } catch(InterruptedException e){}
    }

    /**
     * Gets the user to input a positive integer (and has error code for if they don't)
     */
    public int getPositiveInteger(String Title){
        String Reply;
        boolean Check = false;
        int x = 0;
        while(Check == false){
            Reply = null;
            while(Reply == null){
                InD Input = new InD(Title);
                Input.setLocationRelativeTo(this);
                Input.setVisible(true);
                Reply = Input.getText();
                if(Reply == null){
                    Pipe P = new Pipe();
                    P.MakeADialog("Error Box", "You need to enter a value", 50, 50);
                }
            }
            try{
                x = Integer.parseInt(Reply);
                if(x >= 0){
                    Check = true;
                }else{
                    Pipe P = new Pipe();
                    P.MakeADialog("Error Box", "You need to enter a positive integer", 50, 50);   
                }
            }catch(NumberFormatException ex){ 
                Pipe P = new Pipe();
                P.MakeADialog("Error Box", "You need to enter an integer", 50, 50); 
            }
        }
        return x;
    }

    //Version 1, hard coded making all of the menus and items.
    // public void makeMenus(){
    // JMenuBar Bar = new JMenuBar();
    // this.setJMenuBar(Bar);
    // JMenu Menu1 = new JMenu("File");
    // Bar.add(Menu1);
    // JMenuItem Item1 = new JMenuItem("Load");
    // Item1.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item1.addActionListener(this);
    // Menu1.add(Item1);
    // JMenuItem Item2 = new JMenuItem("Save");
    // Item2.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item2.addActionListener(this);
    // Menu1.add(Item2);
    // JMenuItem Item3 = new JMenuItem("Close");
    // Item3.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item3.addActionListener(this);
    // Menu1.add(Item3);

    // JMenu Menu2 = new JMenu("PipeChain 1");
    // Bar.add(Menu2);
    // JMenuItem Item4 = new JMenuItem("Add Pipe 1");
    // Item4.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item4.addActionListener(this);
    // Menu2.add(Item4);
    // JMenuItem Item5 = new JMenuItem("Add Sink 1");
    // Item5.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item5.addActionListener(this);
    // Menu2.add(Item5);
    // JMenuItem Item10 = new JMenuItem("Add Left Turn 1");
    // Item10.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item10.addActionListener(this);
    // Menu2.add(Item10);
    // JMenuItem Item11 = new JMenuItem("Add Right Turn 1");
    // Item11.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item11.addActionListener(this);
    // Menu2.add(Item11);
    // JMenuItem Item12 = new JMenuItem("Add Left Split 1");
    // Item12.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item12.addActionListener(this);
    // Menu2.add(Item12);
    // JMenuItem Item26 = new JMenuItem("Add Right Split 1");
    // Item26.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item26.addActionListener(this);
    // Menu2.add(Item26);
    // JMenuItem Item13 = new JMenuItem("Add Left Join 1");
    // Item13.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item13.addActionListener(this);
    // Menu2.add(Item13);
    // JMenuItem Item27 = new JMenuItem("Add Right Join 1");
    // Item27.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item27.addActionListener(this);
    // Menu2.add(Item27);
    // JMenuItem Item28 = new JMenuItem("Remove End Pipe 1");
    // Item28.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item28.addActionListener(this);
    // Menu2.add(Item28);

    // JMenu Menu3 = new JMenu("PipeChain 2");
    // Bar.add(Menu3);
    // JMenuItem Item6 = new JMenuItem("Add Pipe 2");
    // Item6.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item6.addActionListener(this);
    // Menu3.add(Item6);
    // JMenuItem Item7 = new JMenuItem("Add Sink 2");
    // Item7.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item7.addActionListener(this);
    // Menu3.add(Item7);
    // JMenuItem Item14 = new JMenuItem("Add Left Turn 2");
    // Item14.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item14.addActionListener(this);
    // Menu3.add(Item14);
    // JMenuItem Item15 = new JMenuItem("Add Right Turn 2");
    // Item15.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item15.addActionListener(this);
    // Menu3.add(Item15);
    // JMenuItem Item16 = new JMenuItem("Add Left Split 2");
    // Item16.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item16.addActionListener(this);
    // Menu3.add(Item16);
    // JMenuItem Item24 = new JMenuItem("Add Right Split 2");
    // Item24.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item24.addActionListener(this);
    // Menu3.add(Item24);
    // JMenuItem Item17 = new JMenuItem("Add Left Join 2");
    // Item17.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item17.addActionListener(this);
    // Menu3.add(Item17);
    // JMenuItem Item25 = new JMenuItem("Add Right Join 2");
    // Item25.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item25.addActionListener(this);
    // Menu3.add(Item25);
    // JMenuItem Item29 = new JMenuItem("Remove End Pipe 2");
    // Item29.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item29.addActionListener(this);
    // Menu3.add(Item29);

    // JMenu Menu4 = new JMenu("PipeChain 3");
    // Bar.add(Menu4);
    // JMenuItem Item8 = new JMenuItem("Add Pipe 3");
    // Item8.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item8.addActionListener(this);
    // Menu4.add(Item8);
    // JMenuItem Item9 = new JMenuItem("Add Sink 3");
    // Item9.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item9.addActionListener(this);
    // Menu4.add(Item9);
    // JMenuItem Item18 = new JMenuItem("Add Left Turn 3");
    // Item18.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item18.addActionListener(this);
    // Menu4.add(Item18);
    // JMenuItem Item19 = new JMenuItem("Add Right Turn 3");
    // Item19.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item19.addActionListener(this);
    // Menu4.add(Item19);
    // JMenuItem Item20 = new JMenuItem("Add Left Split 3");
    // Item20.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item20.addActionListener(this);
    // Menu4.add(Item20);
    // JMenuItem Item23 = new JMenuItem("Add Right Split 3");
    // Item23.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item23.addActionListener(this);
    // Menu4.add(Item23);
    // JMenuItem Item21 = new JMenuItem("Add Left Join 3");
    // Item21.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item21.addActionListener(this);
    // Menu4.add(Item21);
    // JMenuItem Item22 = new JMenuItem("Add Right Join 3");
    // Item22.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item22.addActionListener(this);
    // Menu4.add(Item22);
    // JMenuItem Item30 = new JMenuItem("Remove End Pipe 3");
    // Item30.setAccelerator(KeyStroke.getKeyStroke("DOWN"));
    // Item30.addActionListener(this);
    // Menu4.add(Item30);
    // }

/**
 * Makes the menus and items in an array, looping depending on the value of pipeChainNumber (more pipe chains, more menus in order to add to them)
 */
    public void makeMenus(){
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

        int m = 1;
        int i = 1;
        JMenu Menus[] = new JMenu[50]; 
        JMenuItem Items[] = new JMenuItem[200];
        while(i != PipeChainNumber + 1){
            Menus[i] = new JMenu("PipeChain " + i);
            Bar.add(Menus[i]);
            Items[m] = new JMenuItem("Add Pipe " + i);
            Items[m].addActionListener(this);
            Menus[i].add(Items[m]);
            m++;
            Items[m] = new JMenuItem("Add Sink " + i);
            Items[m].addActionListener(this);
            Menus[i].add(Items[m]);
            m++;
            Items[m] = new JMenuItem("Add LeftTurn " + i);
            Items[m].addActionListener(this);
            Menus[i].add(Items[m]);
            m++;
            Items[m] = new JMenuItem("Add RightTurn " + i);
            Items[m].addActionListener(this);
            Menus[i].add(Items[m]);
            m++;
            Items[m] = new JMenuItem("Add LeftSplit " + i);
            Items[m].addActionListener(this);
            Menus[i].add(Items[m]);
            m++;
            Items[m] = new JMenuItem("Add RightSplit " + i);
            Items[m].addActionListener(this);
            Menus[i].add(Items[m]);
            m++;
            Items[m] = new JMenuItem("Add LeftJoin " + i);
            Items[m].addActionListener(this);
            Menus[i].add(Items[m]);
            m++;
            Items[m] = new JMenuItem("Add RightJoin " + i);
            Items[m].addActionListener(this);
            Menus[i].add(Items[m]);
            m++;
            Items[m] = new JMenuItem("Remove EndPipe " + i);
            Items[m].addActionListener(this);
            Menus[i].add(Items[m]);
            m++;
            i++;
        }
    }

    /**
     *Special function to make a new source
     */
    public void newSource(int maxFlowRate, int pipeChain, int x, int y){
        if(Grid[x][y] == null){
            Pipe newSource = new Pipe("Source", maxFlowRate, pipeChain);
            StartPipe[pipeChain] = newSource;
            EndPipe[pipeChain] = newSource;
            Grid[x][y] = newSource;
            newSource.x = x;
            newSource.y = y;
        }else{
            System.out.println("There is already a pipe at these coordinates");   
        }
    }

    /**
     * Does all the necessary tasks to create and add a new pipe
     * It does the function setNextPipe, it changes the value of EndPipe, and sets the value of Grid[][] to the new pipe
     */
    public void AddPipe(Pipe nextPipe1, int pipeChain){
        EndPipe[pipeChain].setNextPipe(nextPipe1, pipeChain);
        EndPipe[pipeChain] = nextPipe1;
        if(Grid[nextPipe1.x][nextPipe1.y] == null){
            Grid[nextPipe1.x][nextPipe1.y] = nextPipe1;
        }else{
            //Note: this will never be triggered, as previous functions check for this
        }
    }

    /**
     * For when adding into the offshoot of a join, called under checkForJoin
     */
    public void AddPipe(Pipe nextPipe1, int pipeChain, int irrelevant){
        EndPipe[pipeChain].setNextPipe(nextPipe1, pipeChain, 0);
        EndPipe[pipeChain] = nextPipe1;
        if(Grid[nextPipe1.x][nextPipe1.y] == null){
            Grid[nextPipe1.x][nextPipe1.y] = nextPipe1;
        }else{
        }
    }

    //new version for making the offshoot of a split. Do not have any special needs but did need to differentiate it by requiring a string.
    /**
     * For when adding a pipe onto the offshoot of a split
     */
    public void AddPipe(Pipe nextPipe2, int pipeChain, String Irrelevant){
        if(EndPipe[pipeChain].pipeType.equals("leftSplit") == true || EndPipe[pipeChain].pipeType.equals("rightSplit") == true){
            EndPipe[pipeChain].setNextPipe(nextPipe2, pipeChain, "OffShoot");
            EndPipe[pipeChain] = nextPipe2;
            if(Grid[nextPipe2.x][nextPipe2.y] == null){
                Grid[nextPipe2.x][nextPipe2.y] = nextPipe2;
            }else{
                System.out.println("You cannot put a pipe here, the spot is taken!");
            }
        }else{
            System.out.println("You can only use this method on a split or join");
        }
    }

    //Old version of AddPipe for adding the next pipes onto a split.
    //The method I used to use required all of the nextpipes and pipechains to be known simultaneously beforehand, and so this type of method was not used
    // public void AddPipe(Pipe nextPipe1, Pipe nextPipe2, int pipeChain){
    // if(EndPipe[pipeChain].pipeType.equals("leftSplit") || EndPipe[pipeChain].pipeType.equals("rightSplit")){
    // int x = 0;
    // while(StartPipe[x] != null){
    // x = x + 1;
    // }
    // EndPipe[pipeChain].setNextPipe(nextPipe1, nextPipe2, pipeChain, x);
    // EndPipe[pipeChain] = nextPipe1;
    // StartPipe[x] = nextPipe2;
    // EndPipe[x] = nextPipe2;
    // Grid[nextPipe1.x][nextPipe1.y] = nextPipe1;
    // Grid[nextPipe2.x][nextPipe2.y] = nextPipe2;
    // }else{
    // System.out.println("You cannot split any other pipe than a split");
    // }
    // }

    // An old version for adding in a pipe to a join, again, in the format where you needed to know all pipes beforehand
    // public void AddPipe(Pipe Pipe1,  int pipeChain1, Pipe Pipe2, int pipeChain2, Pipe nextPipe1){
    // if(nextPipe1.pipeType.equals("Join")){
    // Pipe1.setNextPipe(nextPipe1, pipeChain1);
    // Pipe2.setNextPipe(nextPipe1, pipeChain1);
    // EndPipe[pipeChain1] = nextPipe1;
    // EndPipe[pipeChain2] = nextPipe1;
    // Grid[nextPipe1.x][nextPipe1.y] = nextPipe1;
    // }else{
    // System.out.println("You cannot join any other pipe than a join");
    // }
    // }

    // For adding in a split leading to a join. You need to know all pipes leading in and out all at once, making it impractical
    // public void AddPipe(Pipe Pipe1, int pipeChain1, Pipe Pipe2, int pipeChain2, Pipe nextPipe1, Pipe nextPipe2){
    // if((Pipe1.pipeType.equals("leftSplit") || Pipe2.pipeType.equals("leftSplit") || Pipe1.pipeType.equals("rightSplit") || Pipe2.pipeType.equals("rightSplit")) && (nextPipe1.pipeType.equals("Join") || nextPipe2.pipeType.equals("Join"))){
    // Pipe Split;
    // int PipeChainJoin;
    // Pipe notSplit;
    // Pipe Join;
    // Pipe notJoin;
    // if(Pipe1.pipeType.equals("leftSplit") || Pipe1.pipeType.equals("rightSplit")){
    // Split = Pipe1;
    // notSplit = Pipe2;
    // }else{
    // Split = Pipe2;
    // notSplit = Pipe1;
    // }
    // if(nextPipe1.pipeType.equals("Join")){
    // Join = nextPipe1;
    // notJoin = nextPipe2;
    // PipeChainJoin = pipeChain1;
    // }else{
    // Join = nextPipe2;
    // notJoin = nextPipe1;
    // PipeChainJoin = pipeChain2;
    // }
    // Split.setNextPipe(nextPipe1, nextPipe2, pipeChain1, pipeChain2);
    // notSplit.setNextPipe(Join, PipeChainJoin);
    // EndPipe[pipeChain1] = nextPipe1;
    // EndPipe[pipeChain2] = nextPipe2;
    // }else{
    // System.out.println("You can only use this method on a split leading to a join");
    // }
    // }

/**
 * 
 */
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
            case "Close": 
            System.exit(0);
            Run = false;
            break;
        }
        String parts[] = command.split(" ");
        try{
            if(parts.length >= 3){
                if(EndPipe[Integer.parseInt(parts[2]) - 1] != null && StartPipe[Integer.parseInt(parts[2]) - 1] != null){
                    switch(parts[0] + " " + parts[1]){
                        case "Add Pipe":
                        makeNewPipe(Integer.parseInt(parts[2]) - 1, "Linear");
                        break;
                        case "Add Sink":
                        makeNewPipe(Integer.parseInt(parts[2]) - 1, "Sink");
                        break;
                        case "Add LeftTurn":
                        makeNewPipe(Integer.parseInt(parts[2]) - 1, "leftTurn");
                        break;
                        case "Add RightTurn":
                        makeNewPipe(Integer.parseInt(parts[2]) - 1, "rightTurn");
                        break;
                        case "Add LeftSplit":
                        makeNewPipe(Integer.parseInt(parts[2]) - 1, "leftSplit");
                        if(EndPipe[Integer.parseInt(parts[2])-1].pipeType.equals("Sink") != true){
                            PipeChainNumber = PipeChainNumber + 1;
                            StartPipe[PipeChainNumber - 1] = EndPipe[Integer.parseInt(parts[2]) - 1];
                            EndPipe[PipeChainNumber - 1] = EndPipe[Integer.parseInt(parts[2]) - 1];
                            makeMenus();
                        }
                        break;
                        case "Add RightSplit":
                        makeNewPipe(Integer.parseInt(parts[2]) - 1, "rightSplit");
                        if(EndPipe[Integer.parseInt(parts[2])-1].pipeType.equals("Sink") != true){
                            PipeChainNumber = PipeChainNumber + 1;
                            StartPipe[PipeChainNumber - 1] = EndPipe[Integer.parseInt(parts[2]) - 1];
                            EndPipe[PipeChainNumber - 1] = EndPipe[Integer.parseInt(parts[2]) - 1];
                            makeMenus();
                        }
                        break;
                        case "Add LeftJoin":
                        makeNewPipe(Integer.parseInt(parts[2]) - 1, "leftJoin");
                        break;
                        case "Add RightJoin":
                        makeNewPipe(Integer.parseInt(parts[2]) - 1, "rightJoin");
                        break;
                        case "Remove EndPipe":
                        removeEndPipe(Integer.parseInt(parts[2]) - 1);
                        break;
                    }
                }else{
                    Pipe P = new Pipe();
                    P.MakeADialog("Error Box", "You cannot add/remove pipes on a pipechain with it's beginning removed", 50, 50);
                }
            }
        }catch(NumberFormatException e){}
    }

    public void getSourceFlowRates(){
        String Reply;
        InD Input = new InD("Source FlowRate:");
        boolean Check = false;
        Input.setLocationRelativeTo(this);
        Input.setVisible(true);
        Reply = Input.getText();
        while(Check == false){
            try{
                Integer.parseInt(Reply);

            }catch(NumberFormatException ex){ 
                System.out.println("You need to enter an integer"); 
            }

        }
    }

    // public void actionPerformed(ActionEvent Event){
    // Scanner inputStream = new Scanner(System.in);
    // String command = Event.getActionCommand(); //this makes the string Command equal the name of the item which was clicked on (eg if I click Item4, it prints out "Item number 4" in the text box
    // switch(command){
    // case "File":
    // break;
    // case "Load":
    // Load();
    // break;
    // case "Save":
    // Save();
    // break;
    // case "Close": System.exit(0);
    // break;
    // case "Add Pipe 1":
    // makeNewPipe(0, "Linear");
    // break;
    // case "Add Sink 1":
    // makeNewPipe(0, "Sink");
    // break;
    // case "Add Left Turn 1":
    // makeNewPipe(0, "leftTurn");
    // break;
    // case "Add Right Turn 1":
    // makeNewPipe(0, "rightTurn");
    // break;
    // case "Add Left Split 1":
    // makeNewPipe(0, "leftSplit");
    // PipeChainNumber = PipeChainNumber + 1;
    // makeMenus();
    // break;
    // case "Add Right Split 1":
    // makeNewPipe(0, "rightSplit");
    // PipeChainNumber = PipeChainNumber + 1;
    // makeMenus();
    // break;
    // case "Add Left Join 1":
    // break;
    // case "Add Right Join 1":
    // break;
    // case "Remove End Pipe 1":
    // removeEndPipe(0);
    // break;
    // case "Add Pipe 2":
    // makeNewPipe(1, "Linear");
    // break;
    // case "Add Sink 2":
    // makeNewPipe(1, "Sink");
    // break;
    // case "Add Left Turn 2":
    // makeNewPipe(1, "leftTurn");
    // break;
    // case "Add Right Turn 2":
    // makeNewPipe(1, "rightTurn");
    // break;
    // case "Add Left Split 2":
    // break;
    // case "Add Right Split 2":
    // break;
    // case "Add Left Join 2":
    // break;
    // case "Add Right Join 2":
    // break;
    // case "Remove End Pipe 2":
    // removeEndPipe(1);
    // break;
    // case "Add Pipe 3":
    // makeNewPipe(2, "Linear");
    // break;
    // case "Add Sink 3":
    // makeNewPipe(2, "Sink");
    // break;
    // case "Add Left Turn 3":
    // makeNewPipe(2, "leftTurn");
    // break;
    // case "Add Right Turn 3":
    // makeNewPipe(2, "rightTurn");
    // break;
    // case "Add Left Split 3":
    // break;
    // case "Add Right Split 3":
    // break;
    // case "Add Left Join 3":
    // break;
    // case "Add Right Join 3":
    // break;
    // case "Remove End Pipe 3":
    // removeEndPipe(2);
    // break;
    // default: System.out.println("X");
    // }
    // }

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
                Pipe P = new Pipe();
                P.MakeADialog("Error Box", "You failed to load the file", 50, 50);   
            }
        }catch(IOException e){
            Pipe P = new Pipe();
            P.MakeADialog("Error Box", "You failed to load the file", 50, 50);
        }
    }

    //for when one of the menu items is called
    public void makeNewPipe(int pipeChain, String pipeType){ 
        if(EndPipe[pipeChain].pipeType.equals("Sink") != true){
            int Reply = getPositiveInteger("Pipe FlowRate:");
            Pipe newPipe = new Pipe(pipeType, Reply);
            if((StartPipe[pipeChain] == EndPipe[pipeChain]) && (EndPipe[pipeChain].pipeType.equals("leftSplit") || EndPipe[pipeChain].pipeType.equals("rightSplit"))){
                //for splits offshoots
                if(nextPipePosition(EndPipe[pipeChain].x, EndPipe[pipeChain].y, EndPipe[pipeChain].pipeType, EndPipe[pipeChain].Orientation) == null){
                    AddPipe(newPipe, pipeChain, "Split");
                }else{
                    Pipe P = new Pipe();
                    P.MakeADialog("Error Box", "You cannot add a pipe here because there is already a pipe/you are building beyond the 10 by 10 limits of the program", 50, 50);
                }
            }else{
                //for all other pipes
                if(nextPipePosition(EndPipe[pipeChain].x, EndPipe[pipeChain].y, EndPipe[pipeChain].pipeType, EndPipe[pipeChain].Orientation, true) == null){
                    AddPipe(newPipe, pipeChain);
                }else{
                    Pipe P = new Pipe();
                    P.MakeADialog("Error Box", "You cannot add a pipe here because there is already a pipe/you are building beyond the 10 by 10 limits of the program", 50, 50);
                }
            }
        }else{
            Pipe P = new Pipe();
            P.MakeADialog("Error Box", "You cannot add a pipe to a sink", 50, 50);
        }
    }

    //for when you need to load a file
    public void makeNewPipe(int pipeChain, String pipeType, int maxFlowRate){
        Pipe newPipe = new Pipe(pipeType, maxFlowRate);
        if(nextPipePosition(EndPipe[pipeChain].x, EndPipe[pipeChain].y, EndPipe[pipeChain].pipeType, EndPipe[pipeChain].Orientation, true) == null){
            AddPipe(newPipe, pipeChain);
        }else{
            Pipe P = new Pipe();
            P.MakeADialog("Error Box", "You cannot add a pipe here because there is already a pipe/you are building beyond the 10 by 10 limits of the program", 50, 50);
        }
    }

    //version 4
    //removes the end pipe on a pipe chain.
    //note: to work this NEEDS the new pipechain from a split to start on nextPipe2
    //Its update is that it works on both pipechains started by sources and started by splits
    public void removeEndPipe(int pipeChain){
        Pipe currentPipe = findChain2ndToLast(pipeChain);
        int pipeChain2;
        if(currentPipe != EndPipe[pipeChain]){
            if(currentPipe.pipeType.equals("leftSplit") || currentPipe.pipeType.equals("rightSplit")){
                if(currentPipe == StartPipe[pipeChain]){
                    Grid[currentPipe.nextPipe2.x][currentPipe.nextPipe2.y] = null;
                }else{
                    Grid[currentPipe.nextPipe1.x][currentPipe.nextPipe1.y] = null;
                }
            }else{
                Grid[currentPipe.nextPipe1.x][currentPipe.nextPipe1.y] = null;
            }
            if(EndPipe[pipeChain].pipeType.equals("leftSplit") || EndPipe[pipeChain].pipeType.equals("rightSplit")){
                if(EndPipe[pipeChain].nextPipe2 != null){
                    pipeChain2 = EndPipe[pipeChain].nextPipe2.pipeChain;
                    EmptyPipeChain(pipeChain2);
                    StartPipe[pipeChain2] = null;
                    EndPipe[pipeChain2] = null;
                }else{
                    for(int x = 0; x < 10; x++){
                        if(StartPipe[x] == EndPipe[pipeChain]){
                            StartPipe[x] = null;
                            EndPipe[x] = null;
                        }
                    }
                }
            }
            if(EndPipe[pipeChain].pipeType.equals("leftJoin") || EndPipe[pipeChain].pipeType.equals("rightJoin")){
                for(int x = 0; x < 10; x ++){
                    if(x != pipeChain && EndPipe[pipeChain] == EndPipe[x]){
                        if(findChain2ndToLast(x).nextPipe1 == EndPipe[pipeChain]){
                            findChain2ndToLast(x).nextPipe1 = null;
                        }
                        if(findChain2ndToLast(x).nextPipe2 == EndPipe[pipeChain]){
                            findChain2ndToLast(x).nextPipe2 = null;
                        }
                    }
                }
            }
            EndPipe[pipeChain] = currentPipe;
            if(currentPipe.pipeType.equals("leftSplit") || currentPipe.pipeType.equals("rightSplit")){
                if(currentPipe == StartPipe[pipeChain]){
                    currentPipe.nextPipe2 = null;
                }else{
                    currentPipe.nextPipe1 = null;
                }
            }else{
                currentPipe.nextPipe1 = null;
            }
        }else{
            Pipe P = new Pipe();
            P.MakeADialog("Error Box", "A pipeChain cannot remove its own start", 100, 100);  
        }
    }

    //finds the end of a pipeChain
    public Pipe findChain2ndToLast(int pipeChain){
        Pipe currentPipe = StartPipe[pipeChain];
        if(StartPipe[pipeChain].pipeType.equals("Source")){
            while(currentPipe.nextPipe1 != EndPipe[pipeChain]){
                currentPipe = currentPipe.nextPipe1;
            }
        }else if(StartPipe[pipeChain].pipeType.equals("leftSplit") || StartPipe[pipeChain].pipeType.equals("rightSplit")){
            if(currentPipe.nextPipe2 == EndPipe[pipeChain]){
            }else{
                currentPipe = currentPipe.nextPipe2;
                while(currentPipe.nextPipe1 != EndPipe[pipeChain]){
                    currentPipe = currentPipe.nextPipe1;
                }
            }
        }
        return currentPipe;
    }

    //removes all pipes in a pipechain but its start
    //only works on splits (and is only used by removeEndPipe on splits)
    public void EmptyPipeChain(int pipeChain){
        Pipe Original = StartPipe[pipeChain];
        StartPipe[pipeChain] = StartPipe[pipeChain].nextPipe2;
        Original.nextPipe2 = null;
        while(StartPipe[pipeChain] != null){   
            if((StartPipe[pipeChain].pipeType.equals("leftSplit") || StartPipe[pipeChain].pipeType.equals("rightSplit")) && StartPipe[pipeChain].nextPipe2 != null){
                EmptyPipeChain(StartPipe[pipeChain].nextPipe2.pipeChain);
            }
            Grid[StartPipe[pipeChain].x][StartPipe[pipeChain].y] = null;
            Original = StartPipe[pipeChain];
            StartPipe[pipeChain] = StartPipe[pipeChain].nextPipe1;
            Original.nextPipe1 = null;
        }
        EndPipe[pipeChain] = null;
    }
    //VERSION 1
    //removes the end pipe on a pipe chain.
    //note: to work this NEEDS the new pipechain from a split to start on nextPipe2
    //I used this version as it works if the chain is very short, until v4 came along, which was an update of v1
    //This version didn't work on pipechains started by splits
    // public void removeEndPipe(int pipeChain){
    // Pipe currentPipe = StartPipe[pipeChain];
    // if(currentPipe != EndPipe[pipeChain]){
    // while(currentPipe.nextPipe1 != EndPipe[pipeChain]){
    // currentPipe = currentPipe.nextPipe1;
    // }
    // Grid[currentPipe.nextPipe1.x][currentPipe.nextPipe1.y] = null;
    // EndPipe[pipeChain] = currentPipe;
    // currentPipe.nextPipe1 = null;
    // }else{
    // MakeADialog("Error Box", "A pipeChain cannot remove its own start", 100, 100);  
    // }
    // }

    // VERSION 2
    // public void removeEndPipe(int pipeChain){
    // Pipe currentPipe = StartPipe[pipeChain];
    // while(currentPipe.nextPipe1.nextPipe1 != null){
    // currentPipe = currentPipe.nextPipe1;
    // }
    // currentPipe.nextPipe1 = null;
    // }

    // VERSION 3
    // Does have error checking, but does not work if the chain is too short
    // public void removeEndPipe(int pipeChain){
    // Pipe currentPipe = StartPipe[pipeChain];
    // while(currentPipe.nextPipe1.nextPipe1 != null){
    // currentPipe = currentPipe.nextPipe1;
    // }
    // if(currentPipe.nextPipe1 == EndPipe[pipeChain]){
    // currentPipe.nextPipe1 = null;
    // }else{
    // System.out.println("There was an error under removeEndPipe");
    // }
    // }

    public void paint(Graphics G){
        super.paint(G);
        int x = 0;
        int y = 0;
        while(y<10){
            while(x<10){
                if(Grid[x][y] != null){
                    checkforJoin(Grid[x][y]);
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

    public void checkforJoin(Pipe Pipe1){
        if(Pipe1.nextPipe1 == null){
            Pipe Pipe2 = nextPipePosition(Pipe1.x, Pipe1.y, Pipe1.pipeType, Pipe1.Orientation, false);
            if(Pipe2 != null){
                if(Pipe2.pipeType.equals("leftJoin")){
                    if(Pipe2.Orientation == 0){
                        //note: you only need to check the changed value, not both x and y, because you know pipe1 is already pointing at pipe2
                        if(Pipe1.x == Pipe2.x && Pipe1.y == Pipe2.y - 1){
                            AddPipe(Pipe2, Pipe1.pipeChain, 0);
                        }
                    }else if(Pipe2.Orientation == 1){
                        if(Pipe1.x == Pipe2.x + 1 && Pipe1.y == Pipe2.y){
                            AddPipe(Pipe2, Pipe1.pipeChain, 0);
                        }
                    }else if(Pipe2.Orientation == 2){
                        if(Pipe1.x == Pipe2.x && Pipe1.y == Pipe2.y + 1){
                            AddPipe(Pipe2, Pipe1.pipeChain, 0);
                        }
                    }else if(Pipe2.Orientation == 3){
                        if(Pipe1.x == Pipe2.x - 1 && Pipe1.y == Pipe2.y){
                            AddPipe(Pipe2, Pipe1.pipeChain, 0);
                        }
                    }
                }
                if(Pipe2.pipeType.equals("rightJoin")){
                    if(Pipe2.Orientation == 0){
                        if(Pipe1.x == Pipe2.x && Pipe1.y == Pipe2.y + 1){
                            AddPipe(Pipe2, Pipe1.pipeChain, 0);
                        }
                    }else if(Pipe2.Orientation == 1){
                        if(Pipe1.x == Pipe2.x - 1 && Pipe1.y == Pipe2.y){
                            AddPipe(Pipe2, Pipe1.pipeChain, 0);
                        }
                    }else if(Pipe2.Orientation == 2){
                        if(Pipe1.x == Pipe2.x && Pipe1.y == Pipe2.y - 1){
                            AddPipe(Pipe2, Pipe1.pipeChain, 0);
                        }
                    }else if(Pipe2.Orientation == 3){
                        if(Pipe1.x == Pipe2.x + 1 && Pipe1.y == Pipe2.y){
                            AddPipe(Pipe2, Pipe1.pipeChain, 0);
                        }
                    }
                }
            }
        }
    }

    public ImageIcon rotate2(double A, String imageName){
        BufferedImage loadedI;
        try{
            loadedI = ImageIO.read(getClass().getResource(imageName));
            BufferedImage rotatedImageB = rotate(loadedI, A);
            rotatedImage = new ImageIcon(rotatedImageB);
        } catch (Exception e){

        }
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

    //v2: it says the next pipe as well as the other information for testing purposes (testing checkforJoin) and orientation
    public void mouseClicked(MouseEvent e){
        int mouseX = e.getX();
        int mouseY = e.getY();
        int x = Math.round((mouseX)/70);
        int y = Math.round((mouseY - 100)/70);
        String nextPipeType;
        if(Grid[x][y] != null){
            if(Grid[x][y].nextPipe1 != null){
                nextPipeType = Grid[x][y].nextPipe1.pipeType;
            }else{
                nextPipeType = "";
            }
            Pipe P = new Pipe();
            P.MakeADialog("Information Box", "Pipe type = " + Grid[x][y].pipeType + "\n" + "Flow Rate = " + Grid[x][y].flowRate + "/" + Grid[x][y].maxFlowRate + "\n" + "In pipe chain " + Grid[x][y].pipeChain + 1 + "\n" + "At position " + Grid[x][y].x  + ", " + Grid[x][y].y + "\n" + "Orientation: " + Grid[x][y].Orientation + "\n" + "Next Pipe:" + nextPipeType, x, y);
        }      
        //MakeADialog("Hello", mouseX, mouseY);
    }

    //v2: checks if it is within the values for grid. Also has a variable to determine whether you want to display the error message (user-dictated insert pipe) or not (checkforJoin)
    public Pipe nextPipePosition(int EnterX, int EnterY, String pipeType, int Orientation, boolean displayErrorMessage){
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
                x = EnterX;
                y = EnterY + 1;
            }else if(Orientation == 2){
                x = EnterX - 1;
                y = EnterY;
            }else if(Orientation == 3){
                x = EnterX;
                y = EnterY - 1;
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
                y = EnterY + 1;
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
            case "leftJoin":
            if(Orientation == 0){
                x = EnterX + 1;
                y = EnterY;
            }else if(Orientation == 1){
                x =EnterX;
                y = EnterY + 1;
            }else if(Orientation == 2){
                x = EnterX - 1;
                y = EnterY;
            }else if(Orientation == 3){
                x = EnterX;
                y = EnterY - 1;
            }
            break;
            case "rightJoin":
            if(Orientation == 0){
                x = EnterX + 1;
                y = EnterY;
            }else if(Orientation == 1){
                x =EnterX;
                y = EnterY + 1;
            }else if(Orientation == 2){
                x = EnterX - 1;
                y = EnterY;
            }else if(Orientation == 3){
                x = EnterX;
                y = EnterY - 1;
            }
            break;
            case "leftSplit":
            if(Orientation == 0){
                x = EnterX + 1;
                y = EnterY;
            }else if(Orientation == 1){
                x =EnterX;
                y = EnterY + 1;
            }else if(Orientation == 2){
                x = EnterX - 1;
                y = EnterY;
            }else if(Orientation == 3){
                x = EnterX;
                y = EnterY - 1;
            }
            break;
            case "rightSplit":
            if(Orientation == 0){
                x = EnterX + 1;
                y = EnterY;
            }else if(Orientation == 1){
                x =EnterX;
                y = EnterY + 1;
            }else if(Orientation == 2){
                x = EnterX - 1;
                y = EnterY;
            }else if(Orientation == 3){
                x = EnterX;
                y = EnterY - 1;
            }
            break;
        }
        //I added in this block so that if x and y point beyond what grid accepts, 
        if(x >= 0 && x < 10 && y >= 0 && y < 10){
            return Grid[x][y];
        }else{
            Pipe p = new Pipe("Linear", 0);
            return p;
        }
    }

    //v3
    //A special version only for splits offshoots.
    //it was made different from the other by always displaying the error message
    public Pipe nextPipePosition(int EnterX, int EnterY, String pipeType, int Orientation){
        int x = 0;
        int y = 0; 
        switch(pipeType){            
            case "leftSplit":
            if(Orientation == 0){
                x = EnterX;
                y = EnterY - 1;
            }else if(Orientation == 1){
                x = EnterX + 1;
                y = EnterY;
            }else if(Orientation == 2){
                x = EnterX;
                y = EnterY + 1;
            }else if(Orientation == 3){
                x = EnterX - 1;
                y = EnterY;
            }
            break;
            case "rightSplit":
            if(Orientation == 0){
                x = EnterX;
                y = EnterY + 1;
            }else if(Orientation == 1){
                x =EnterX - 1;
                y = EnterY;
            }else if(Orientation == 2){
                x = EnterX;
                y = EnterY - 1;
            }else if(Orientation == 3){
                x = EnterX + 1;
                y = EnterY;
            }
            break;
        }
        //I added in this block so that if x and y point beyond what grid accepts, 
        if(x >= 0 && x < 10 && y >= 0 && y < 10){
            return Grid[x][y];
        }else{
            Pipe p = new Pipe("Linear", 0);
            return p;
        }
    }
}
