/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpio_data;

import java.io.Serializable;

/**
 * Class to create an image of RPI_IO board data. 
 * 
 * @author Federico
 */
public class RPI_Data implements Serializable{
    
    private static String rpi_name="";
    private static String rpi_location="";
    private static String rpi_address="";
    private static int hw_inputs=0; //rpi input port status
    private static int hw_outputs=0; //rpi output port status
    private static int sw_inputs=0; //binary inputs to rpi software
    private static int sw_outputs=0; // binary outputs from rpi software
    private static int[] hw_analogs= new int [8]; //rpi analog readings
    private static double[] sw_results= new double [16]; // software results
    private static double[] setpoints= new double[16]; //software setpoints.

    public RPI_Data() {
        //Initialize class instance
        for( int i=0;i<8;i++) {
            this.hw_analogs[i]=0;
            this.sw_results[i]=0.0;
            this.setpoints[i]=0.0;
        }
    }

     public static String get_Rpi_name() {
        return rpi_name;
    }

    public static void set_Rpi_name(String rpi_name) {
        RPI_Data.rpi_name = rpi_name;
    }

    public static String get_Rpi_location() {
        return rpi_location;
    }

    public static void set_Rpi_location(String rpi_location) {
        RPI_Data.rpi_location = rpi_location;
    }

    public static String get_Rpi_address() {
        return rpi_address;
    }

    public static void set_Rpi_address(String rpi_address) {
        RPI_Data.rpi_address = rpi_address;
    }
    
    public void setFlags(int flags){
        this.sw_inputs=flags;
    }
    
    public int getFlags(){
        return sw_inputs;
    }
    
    public boolean getFlag(int flag){
        if((getBit(flag)&sw_inputs)==0){
            return false;
        } else {
            return true;
        }
    }
    
    public void setInputs(int inputs) {
        this.hw_inputs = inputs;
    }
     
    public int getInputs() {
        return hw_inputs;
    }
    
    public boolean getInput(int in){
        
        if((getBit(in)&hw_inputs)==0){
            return false;
        } else {
            return true;
        }
    }
    
    public void setOutputs(int out){
        this.hw_outputs=out;
    }
    public int getOutputs() {
        return hw_outputs;
    }

    public boolean getOutput(int out){
        if((getBit(out)&hw_outputs)==0){
            return false;
        } else {
            return true;
        }
    }
    
    public int[] getAnalogs() {
        return hw_analogs;
    }

    public int getAnalog(int channel){
        if(channel>0 && channel<9){
            return hw_analogs[channel-1];
        } else {
            return 0;
        }
    }
       
    public void setAnalogs(int[] analogs) {
        this.hw_analogs = analogs;
    }
    
    public void setAnalog(int channel,int value){
        if(channel>0 && channel<9){
            this.hw_analogs[channel-1]=value;
        }
    }
    
    public double[] getSetpoints(){
        return setpoints;
    }
    
    public void setSetpoints(double [] setp){
        this.setpoints=setp;
    }
    
    public double getSetpoint(int setp){
        if(setp>0 && setp<16){
            return setpoints[setp-1];
        } else {
            return 0.0;
        }
    }
    
    public double[] getResults(){
        return sw_results;
    }
    
    public void setResults(double [] results){
        this.sw_results=results;
    }
    
    public double getResult(int chn){
        if(chn>0 && chn<16){
            return sw_results[chn-1];
        } else {
            return 0.0;
        }
        
    }
    private int getBit(int rly){
        
        if(rly==1){
            return 1;
        } else {
            return 1<<(rly-1);
        }
        
    }
    
    
    
}
