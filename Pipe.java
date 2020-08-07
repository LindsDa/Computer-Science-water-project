import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Random;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
public class Pipe
{
    public String pipeType;
    //Options:
    //"Linear"
    //"Turn"
    //"Join"
    //"Split"
    //"Source"
    //"Sink"
    public int Orientation = 1;
    //1 through 4 for rotation
    public Pipe nextPipe1;
    //main nextPipe
    public Pipe nextPipe2;
    //The second nextPipe for when you are dealing with splits
    //Records the next pipe. It has an option of a second nextPipe for splits.
    public int maxFlowRate;
    //records the pipes maxFlowRate
    public int flowRate;
    //records the actual flowRate
    public Pipe Sources[] = new Pipe[60];
    //records all of the sources a particular pipe is connected to
    public Pipe Sinks[] = new Pipe[60];
    // public int x;
    // public int y;
    //the x and y coordinates
    int pipeChain;
    
    public Pipe(String pipeType, int maxFlowRate)
    //int x, int y, Pipe[][] Grid
    {
        this.pipeType = pipeType;
        this.maxFlowRate = maxFlowRate;
    }
    
    public Pipe(String pipeType, int maxFlowRate, int pipeChain){
        if(pipeType.equals("Source")){
            this.pipeType = pipeType;
            this.maxFlowRate = maxFlowRate;
            this.pipeChain = pipeChain;
        }else{
            System.out.println("You cannot use this constructor function on anything other than a source");
        }
    }
    
    public void setNextPipe(Pipe nextPipe1, int pipeChain){
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
    
    public void setNextPipe(Pipe nextPipe1, Pipe nextPipe2, int pipeChain1, int pipeChain2){
        nextPipe1.pipeChain = pipeChain1;
        nextPipe2.pipeChain = pipeChain2;
        if(nextPipe1.equals("Source") != true && nextPipe2.equals("Source") != true && this.pipeType.equals("Sink") != true){
            this.nextPipe1 = nextPipe1;
            this.nextPipe2 = nextPipe2;
            this.setFlow();
        }
    }

    public String returnPipeType(){
        return this.pipeType;   
    }

    public void setFlow(){
        switch(this.pipeType){
            case "Source": 
            if(this.maxFlowRate <= nextPipe1.maxFlowRate){
                this.flowRate = this.maxFlowRate;
                nextPipe1.flowRate = this.maxFlowRate;
            }else{
                System.out.println("Error! Error! Critical Overflow Alert!");
            }
            break;
            case "Sink": 
            break;
            case "Linear":
            if(this.maxFlowRate <= nextPipe1.maxFlowRate){
                nextPipe1.flowRate = this.flowRate;
            }else{
                System.out.println("Error! Error! Critical Overflow Alert!");
            }
            break;
            case "Turn":
            if(this.maxFlowRate <= nextPipe1.maxFlowRate){
                nextPipe1.flowRate = this.flowRate;
            }else{
                System.out.println("Error! Error! Critical Overflow Alert!");
            }
            break;
            case "Split": 
            if(this.flowRate/2 <= nextPipe1.maxFlowRate && this.flowRate/2 <= nextPipe2.maxFlowRate){
                nextPipe1.flowRate = this.flowRate/2;
                nextPipe2.flowRate = this.flowRate/2;
            }else{
                System.out.println("Error! Error! Critical overflow alert!");
            }
            break;
            case "Join": 
            if(nextPipe1.flowRate + this.flowRate <= nextPipe1.maxFlowRate){
                nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate;
            }else{
                System.out.println("Error! Error! Critical overflow alert!");
            }
            //Note: this only works if you only use nextpipe on this once for each pipe going in. 
            break;
            default: System.out.println("That is not one of the options. How...?");
        }
    }

    // public void Connect(Pipe[][] Grid){
    // switch(pipeType){
    // case "Linear": setNextPipe(Grid[x+1][y]);
    // nextPipe[0].flowRate = this.flowRate;
    // break;
    // case "Turn":
    // break;
    // case "Join":
    // break;
    // case "Split":
    // break;
    // case "Source":setNextPipe(Grid[x+1][y]);
    // nextPipe[0].flowRate = this.maxFlowRate;
    // break;
    // case "Sink": nextPipe = null;
    // break;
    // default: System.out.println("That is an unnaceptable entry for pipeType");
    // }
    // //There is an error where the Grid is empty, so the nextPipe is empty. 
    // }

    // public int returnX(){
    // return this.x;
    // }

    // public int returnY(){
    // return this.y;
    // }

}