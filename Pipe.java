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
    //"leftTurn"
    //"rightTurn"
    //"leftJoin"
    //"rightJoin"
    //"leftSplit"
    //"rightSplit"
    //"Source"
    //"Sink"
    public int Orientation = 0;
    //0 through 3 for rotation. 0 means or original version, each additional number means rotate 90 degrees right
    public Pipe nextPipe1;
    //main nextPipe
    public Pipe nextPipe2;
    //The second nextPipe for when you are dealing with splits
    //Records the next pipe. It has an option of a second nextPipe for splits.
    public int maxFlowRate;
    //records the pipes maxFlowRate
    public int flowRate;
    //records the actual flowRate

    int pipeChain;
    int x;
    int y;
    //the x and y coordinates

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
            this.setPosition();
        }else if(nextPipe1.equals("Source")){
            System.out.println("You cannot make a source a next pipe");
        }else if(this.pipeType.equals("Sink") == true){
            System.out.println("You cannot make a sink have a next pipe");
        }
    }

    //Set next pipe for a split
    public void setNextPipe(Pipe nextPipe1, Pipe nextPipe2, int pipeChain1, int pipeChain2){
        nextPipe1.pipeChain = pipeChain1;
        nextPipe2.pipeChain = pipeChain2;
        if(nextPipe1.equals("Source") != true && nextPipe2.equals("Source") != true && this.pipeType.equals("Sink") != true){
            this.nextPipe1 = nextPipe1;
            this.nextPipe2 = nextPipe2;
            this.setFlow();
            this.setPosition();
        }
    }

    public String returnPipeType(){
        return this.pipeType;   
    }

    public void setFlow(){
        switch(this.pipeType){
            case "Source": 
            if(this.maxFlowRate + nextPipe1.flowRate <= nextPipe1.maxFlowRate){
                this.flowRate = this.maxFlowRate;
                nextPipe1.flowRate = nextPipe1.flowRate + this.maxFlowRate;
            }else{
                System.out.println("Error! Error! Critical Overflow Alert!");
                System.out.println("The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain);
            }
            break;
            case "Sink": 
            break;
            case "Linear":
            if(nextPipe1.flowRate + this.flowRate <= nextPipe1.maxFlowRate){
                nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate;
            }else{
                System.out.println("Error! Error! Critical Overflow Alert!");
                System.out.println("The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain);
            }
            break;
            case "leftTurn":
            if(nextPipe1.flowRate + this.flowRate <= nextPipe1.maxFlowRate){
                nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate;
            }else{
                System.out.println("Error! Error! Critical Overflow Alert!");
                System.out.println("The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain);
            }
            break;
            case "rightTurn":
            if(nextPipe1.flowRate + this.flowRate <= nextPipe1.maxFlowRate){
                nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate;
            }else{
                System.out.println("Error! Error! Critical Overflow Alert!");
                System.out.println("The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain);
            }
            break;
            case "Split": 
            if(nextPipe1.flowRate + this.flowRate/2 <= nextPipe1.maxFlowRate && nextPipe2.flowRate + this.flowRate/2 <= nextPipe2.maxFlowRate){
                nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate/2;
                nextPipe2.flowRate = nextPipe2.flowRate + this.flowRate/2;
            }else{
                System.out.println("Error! Error! Critical overflow alert!");
                System.out.println("The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain);
            }
            break;
            case "Join": 
            if(nextPipe1.flowRate + this.flowRate <= nextPipe1.maxFlowRate){
                nextPipe1.flowRate = nextPipe1.flowRate + this.flowRate;
            }else{
                System.out.println("Error! Error! Critical overflow alert!");
                System.out.println("The error occured at the " + nextPipe1.pipeType + ",  after the " + this.pipeType + " in pipeChain " + nextPipe1.pipeChain);
            }
            //Note: this only works if you only use nextpipe on this once for each pipe going in. 
            break;
            default: System.out.println("That is not one of the options. How...?");
        }
    }

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
            case "leftTurn":
            if(this.Orientation == 0){
                this.nextPipe1.x = this.x ;
                this.nextPipe1.y = this.y + 1;
            }else if(this.Orientation == 1){
                this.nextPipe1.x = this.x + 1;
                this.nextPipe1.y = this.y;
            }else if(this.Orientation == 2){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y - 1;
            }else if(this.Orientation == 3){
                this.nextPipe1.x = this.x - 1;
                this.nextPipe1.y = this.y;
            }
            this.nextPipe1.Orientation = (this.Orientation + 3)%4;
            break;
            case "rightTurn":
            if(this.Orientation == 0){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y - 1;
            }else if(this.Orientation == 1){
                this.nextPipe1.x = this.x - 1;
                this.nextPipe1.y = this.y;
            }else if(this.Orientation == 2){
                this.nextPipe1.x = this.x;
                this.nextPipe1.y = this.y + 1;
            }else if(this.Orientation == 3){
                this.nextPipe1.x = this.x + 1;
                this.nextPipe1.y = this.y;
            }
            this.nextPipe1.Orientation = (this.Orientation + 1)%4;
            break;
            case "Join":
            if(this.Orientation == 0){

            }else if(this.Orientation == 1){

            }else if(this.Orientation == 2){

            }else if(this.Orientation == 3){

            }
            this.nextPipe1.x = this.x + 1;
            this.nextPipe1.y = this.y;
            this.nextPipe1.Orientation = this.Orientation;
            break;
            case "Split":
            if(this.Orientation == 0){

            }else if(this.Orientation == 1){

            }else if(this.Orientation == 2){

            }else if(this.Orientation == 3){

            }
            this.nextPipe1.x = this.x + 1;
            this.nextPipe2.x = this.x;
            this.nextPipe1.y = this.y;
            this.nextPipe2.y = this.y-1;
            this.nextPipe1.Orientation = this.Orientation;
            this.nextPipe2.Orientation = (this.Orientation + 1)%4;
            break;
        }
    }
}