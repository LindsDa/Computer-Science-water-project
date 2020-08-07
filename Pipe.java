import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Random;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
/**
 * Makes the individual objects making up the linked lists of the pipe network
 */
public class Pipe extends JFrame
{
    public String pipeType;
    //The string that determines what type of pipe the object will be. The options are Sink, Source, leftTurn, rightTurn, Linear, leftJoin, rightJoin, leftSplit and rightSplit.
    //This string is used in determining the position and orientation and flowrate of the next pipe, as well as other things

    public int Orientation = 0;
    //This integer determines the rotation of the image. It goes from 0 to 3, with 0 representing the pipes pointing right, and each successive number rotating it 90 degrees.

    public Pipe nextPipe1;
    //The main next pipe in the linked list. For all pipes but splits, the only variable that holds the next object in the linked list
    public Pipe nextPipe2;
    //For splits, which can have 2 pipes coming out of them, they use both nextPipe1 and nextPipe2 to hold the next items in the linked list. 
    public int maxFlowRate;
    //records the pipes maximum flow rate, the upper limit for how much water flow it can handle before sending an error message
    public int flowRate;
    //records the rate of water flow inside the pipe. If it is greater than maxFlowRate, an error message occurs. 
    int pipeChain;
    //Pipes are added/removed according to pipeChain. A pipeChain is a chain of pipes from a startPipe (either a source or a split) to an endPipe (the final pipe in a pipeChain). This records what pipeChain the pipe is within.
    int x;
    int y;
    //Holds the x and y coordinates of the pipe, which are used to determine the placement of the pipe on the GUI depending on their x/y coordinates. 
    //x and y are 0 at the top left and increase as you move right/down

    /**
     * Constructor function for class Pipe, for all pipeTypes except for sources.
     */
    public Pipe(String pipeType, int maxFlowRate)
    {
        this.pipeType = pipeType;
        this.maxFlowRate = maxFlowRate;
    }

    /**
     * Constructor function for pipes of pipeType Source
     */
    public Pipe(String pipeType, int maxFlowRate, int pipeChain){
        if(pipeType.equals("Source")){
            this.pipeType = pipeType;
            this.maxFlowRate = maxFlowRate;
            this.pipeChain = pipeChain;
        }else{
            System.out.println("You cannot use this constructor function on anything other than a source");
        }
    }

    /**
     * A constructor function for creating a variable-less pipe
     * Only used to create pipes to then access the MakeADialog function (under the pipe class, often used under Network)
     */
    public Pipe(){
    }

    /**
     * A function that adds the next pipe in the linked list for all pipe types but splits and joins
     * It sets the value of nextPipe1, it sets the flow rate of the next pipe, and it sets the x and y position and orientation of the next pipe.
     */
    public void setNextPipe(Pipe nextPipe1, int pipeChain){
        nextPipe1.pipeChain = pipeChain;
        if(nextPipe1.equals("Source") != true && this.pipeType.equals("Sink") != true){
            this.nextPipe1 = nextPipe1;
            this.setFlow();
            this.setPosition();
        }else if(nextPipe1.equals("Source")){
            System.out.println("You cannot make a source a next pipe");
        }else if(this.pipeType.equals("Sink") == true){
            System.out.println("You cannot make a sink have a next pipe");
        }
    }

    /**
     * A second verison for adding into joins
     * It is separated from the regular setNextPipe by taking in an irrelevant integer.
     * It sets the value of nextPipe1, and it sets the flow rate of the join, but it does not change the position/orientation of the join.
     */
    public void setNextPipe(Pipe nextPipe1, int pipeChain, int irrelevant){
        nextPipe1.pipeChain = pipeChain;
        if(nextPipe1.equals("Source") != true && this.pipeType.equals("Sink") != true){
            this.nextPipe1 = nextPipe1;
            this.setFlow();
        }else if(nextPipe1.equals("Source")){
            System.out.println("You cannot make a source a next pipe");
        }else if(this.pipeType.equals("Sink") == true){
            System.out.println("You cannot make a sink have a next pipe");
        }
    }

    /**
     * A third version for adding onto the nextPipe2 of splits.
     * Does everything the same, except for nextPipe2.
     */
    public void setNextPipe(Pipe nextPipe2, int pipeChain, String Irrelevant){
        nextPipe2.pipeChain = pipeChain;
        this.nextPipe2 = nextPipe2;
        this.setFlow();
        this.setPosition();
    }

    /**
     * An old version of setNextPipe, no longer used
     * It required you to know both nextPipe1 and nextPipe2 at the same time for a split.
     * It is no longer used because now both following pipes are set independently
     */
    public void setNextPipe(Pipe nextPipe1, Pipe nextPipe2, int pipeChain1, int pipeChain2){
        nextPipe1.pipeChain = pipeChain1;
        nextPipe2.pipeChain = pipeChain2;
        if((nextPipe1.equals("Source") != true && nextPipe2.equals("Source") != true) && (this.pipeType.equals("leftSplit") == true || this.pipeType.equals("rightSplit") == true)){
            this.nextPipe1 = nextPipe1;
            this.nextPipe2 = nextPipe2;
            this.setFlow();
            this.setPosition();
        }
    }

    /**
     * Returns the value of pipeType
     */
    public String returnPipeType(){
        return this.pipeType;   
    }

    /**
     * Sets the flow rate of the newly added pipe, and sends an input dialog warning if the flowrate is greater than the value of maxFlowRate
     * It sets the value of flowrate depending on the type of pipe (eg splits halve flow rate, joins compound flow rate)
     * Is called under setNextPipe
     */
    public void setFlow(){
        switch(this.pipeType){
            case "Source": 
            if(this.maxFlowRate + nextPipe1.flowRate <= nextPipe1.maxFlowRate){
                this.flowRate = this.maxFlowRate;
                nextPipe1.flowRate = nextPipe1.flowRate + this.maxFlowRate;
            }else{
                MakeADialog("Pipe Overflow!", "The flow rate of the pipe is greater than its maximum flow rate \n The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain, 50, 50);
            }
            break;
            case "Sink": 
            break;
            case "Linear":
            if(nextPipe1.flowRate + this.flowRate <= nextPipe1.maxFlowRate){
                nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate;
            }else{
                MakeADialog("Pipe Overflow!", "The flow rate of the pipe is greater than its maximum flow rate \n The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain, 50, 50);
            }
            break;
            case "leftTurn":
            if(nextPipe1.flowRate + this.flowRate <= nextPipe1.maxFlowRate){
                nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate;
            }else{
                MakeADialog("Pipe Overflow!", "The flow rate of the pipe is greater than its maximum flow rate \n The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain, 50, 50);
            }
            break;
            case "rightTurn":
            if(nextPipe1.flowRate + this.flowRate <= nextPipe1.maxFlowRate){
                nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate;
            }else{
                MakeADialog("Pipe Overflow!", "The flow rate of the pipe is greater than its maximum flow rate \n The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain, 50, 50);
            }
            break;
            case "leftSplit": 
            if(nextPipe1 != null){
                if(nextPipe1.flowRate + this.flowRate/2 <= nextPipe1.maxFlowRate){
                    nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate/2;
                }else{
                    MakeADialog("Pipe Overflow!", "The flow rate of the pipe is greater than its maximum flow rate \n The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain, 50, 50);
                }
            }
            if(nextPipe2 != null){
                if(nextPipe2.flowRate + this.flowRate/2 <= nextPipe2.maxFlowRate){
                    nextPipe2.flowRate = nextPipe2.flowRate + this.flowRate/2;
                }else{
                    MakeADialog("Pipe Overflow!", "The flow rate of the pipe is greater than its maximum flow rate \n The error occured at the " + nextPipe2.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe2.pipeChain, 50, 50);
                }
            }
            break;
            case "rightSplit": 
            if(nextPipe1 != null){
                if(nextPipe1.flowRate + this.flowRate/2 <= nextPipe1.maxFlowRate){
                    nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate/2;
                }else{
                    MakeADialog("Pipe Overflow!", "The flow rate of the pipe is greater than its maximum flow rate \n The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain, 50, 50);
                }
            }
            if(nextPipe2 != null){
                if(nextPipe2.flowRate + this.flowRate/2 <= nextPipe2.maxFlowRate){
                    nextPipe2.flowRate = nextPipe2.flowRate + this.flowRate/2;
                }else{
                    MakeADialog("Pipe Overflow!", "The flow rate of the pipe is greater than its maximum flow rate \n The error occured at the " + nextPipe2.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe2.pipeChain, 50, 50);
                }
            }
            break;
            case "leftJoin": 
            if(nextPipe1.flowRate + this.flowRate <= nextPipe1.maxFlowRate){
                nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate;
            }else{
                MakeADialog("Pipe Overflow!", "The flow rate of the pipe is greater than its maximum flow rate \n The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain, 50, 50);
            }
            break;
            case "rightJoin": 
            if(nextPipe1.flowRate + this.flowRate <= nextPipe1.maxFlowRate){
                nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate;
            }else{
                MakeADialog("Pipe Overflow!", "The flow rate of the pipe is greater than its maximum flow rate \n The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain, 50, 50);
            }
            //Note: this only works if you only use nextpipe on this once for each pipe going in. 
            break;
            default: System.out.println("That is not one of the options. How...?");
        }
    }

    /**
     * This sets the x and y position and orientation of the newly added pipe
     * This is called under setNextPipe
     * It determines x, y, and orientation of the added pipe depending on the x, y, and orientation and pipeType of the current pipe 
     */
    public void setPosition(){
        switch(this.pipeType){
            case "Source": 
            if(this.Orientation == 0){
                this.nextPipe1.x = this.x + 1;
                this.nextPipe1.y = this.y;
            }else if(this.Orientation == 1){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y - 1;
            }else if(this.Orientation == 2){
                this.nextPipe1.x = this.x - 1;
                this.nextPipe1.y = this.y;
            }else if(this.Orientation == 3){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y + 1;
            }
            this.nextPipe1.Orientation = this.Orientation;
            break;
            case "Sink":
            break;
            case "Linear":
            if(this.Orientation == 0){
                this.nextPipe1.x = this.x + 1;
                this.nextPipe1.y = this.y;
            }else if(this.Orientation == 1){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y + 1;
            }else if(this.Orientation == 2){
                this.nextPipe1.x = this.x - 1;
                this.nextPipe1.y = this.y;
            }else if(this.Orientation == 3){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y - 1;
            }
            this.nextPipe1.Orientation = this.Orientation;
            break;
            case "leftTurn":
            if(this.Orientation == 0){
                this.nextPipe1.x = this.x ;
                this.nextPipe1.y = this.y - 1;
            }else if(this.Orientation == 1){
                this.nextPipe1.x = this.x + 1;
                this.nextPipe1.y = this.y;
            }else if(this.Orientation == 2){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y + 1;
            }else if(this.Orientation == 3){
                this.nextPipe1.x = this.x - 1;
                this.nextPipe1.y = this.y;
            }
            this.nextPipe1.Orientation = (this.Orientation + 3)%4;
            break;
            case "rightTurn":
            if(this.Orientation == 0){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y + 1;
            }else if(this.Orientation == 1){
                this.nextPipe1.x = this.x - 1;
                this.nextPipe1.y = this.y;
            }else if(this.Orientation == 2){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y - 1;
            }else if(this.Orientation == 3){
                this.nextPipe1.x = this.x + 1;
                this.nextPipe1.y = this.y;
            }
            this.nextPipe1.Orientation = (this.Orientation + 1)%4;
            break;
            case "leftJoin":
            if(this.Orientation == 0){
                this.nextPipe1.x = this.x + 1;
                this.nextPipe1.y = this.y;
            }else if(this.Orientation == 1){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y + 1;
            }else if(this.Orientation == 2){
                this.nextPipe1.x = this.x - 1;
                this.nextPipe1.y = this.y;
            }else if(this.Orientation == 3){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y - 1;
            }
            this.nextPipe1.Orientation = this.Orientation;
            break;
            case "rightJoin":
            if(this.Orientation == 0){
                this.nextPipe1.x = this.x + 1;
                this.nextPipe1.y = this.y;
            }else if(this.Orientation == 1){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y + 1;
            }else if(this.Orientation == 2){
                this.nextPipe1.x = this.x - 1;
                this.nextPipe1.y = this.y;
            }else if(this.Orientation == 3){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y - 1;
            }
            this.nextPipe1.Orientation = this.Orientation;
            break;
            case "leftSplit":
            if(this.nextPipe1 != null){
                if(this.Orientation == 0){
                    this.nextPipe1.x = this.x + 1;
                    this.nextPipe1.y = this.y;
                }else if(this.Orientation == 1){
                    this.nextPipe1.x = this.x;
                    this.nextPipe1.y = this.y + 1;
                }else if(this.Orientation == 2){
                    this.nextPipe1.x = this.x - 1;
                    this.nextPipe1.y = this.y;
                }else if(this.Orientation == 3){
                    this.nextPipe1.x = this.x;
                    this.nextPipe1.y = this.y - 1;
                }
                this.nextPipe1.Orientation = this.Orientation;
            }
            if(this.nextPipe2 != null){
                if(this.Orientation == 0){
                    this.nextPipe2.x = this.x;
                    this.nextPipe2.y = this.y - 1;
                }else if(this.Orientation == 1){
                    this.nextPipe2.x = this.x + 1;
                    this.nextPipe2.y = this.y;
                }else if(this.Orientation == 2){
                    this.nextPipe2.x = this.x;
                    this.nextPipe2.y = this.y + 1;
                }else if(this.Orientation == 3){
                    this.nextPipe2.x = this.x - 1;
                    this.nextPipe2.y = this.y;
                }
                this.nextPipe2.Orientation = (this.Orientation - 1)%4;
            }
            break;
            case "rightSplit":
            if(this.nextPipe1 != null){
                if(this.Orientation == 0){
                    this.nextPipe1.x = this.x + 1;
                    this.nextPipe1.y = this.y;
                }else if(this.Orientation == 1){
                    this.nextPipe1.x = this.x;
                    this.nextPipe1.y = this.y + 1;
                }else if(this.Orientation == 2){
                    this.nextPipe1.x = this.x - 1;
                    this.nextPipe1.y = this.y;
                }else if(this.Orientation == 3){
                    this.nextPipe1.x = this.x;
                    this.nextPipe1.y = this.y - 1;
                }
                this.nextPipe1.Orientation = this.Orientation;
            }
            if(this.nextPipe2 != null){
                if(this.Orientation == 0){
                    this.nextPipe2.x = this.x;
                    this.nextPipe2.y = this.y + 1;
                }else if(this.Orientation == 1){
                    this.nextPipe2.x = this.x - 1;
                    this.nextPipe2.y = this.y;
                }else if(this.Orientation == 2){
                    this.nextPipe2.x = this.x;
                    this.nextPipe2.y = this.y - 1;
                }else if(this.Orientation == 3){
                    this.nextPipe2.x = this.x + 1;
                    this.nextPipe2.y = this.y;
                }
                this.nextPipe2.Orientation = (this.Orientation + 1)%4;

            }
            break;
        }
    }

    /**
     * Makes a dialog box depending on the strings passed in
     * Used for the introduction/instructions and for various error messages
     */
    void MakeADialog(String Title, String String1, int x, int y){
        JDialog DialogBox3 = new JDialog(this);
        String parts[] = String1.split("\n");
        String largestPart = parts[0];
        String Part;
        int n = 0;
        while(n < parts.length){
            Part = parts[n];
            if(Part.length() > largestPart.length()){
                largestPart = Part;
            }
            n = n + 1;
        }
        DialogBox3.setBounds(x, y, (largestPart.length())*7, ((parts.length)*20 + 70));
        DialogBox3.toFront();
        DialogBox3.setTitle(Title);
        TextArea Text3= new TextArea(String1);
        DialogBox3.add(Text3);
        DialogBox3.setVisible(true);
    }
}