/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpio_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author federico
 */
public class Net_RPI_IO {

    private static final String setLock = "01";
    private static final String setMultipleLocks = "02";
    private static final String getLock = "03";
    private static final String getLevel = "04";
    private static final String releaseLock = "05";
    private static final String setRly = "06";
    private static final String resetRly = "07";
    private static final String getAnalogSettings = "08";
    private static final String setAnalogSettings = "09";
    private static final String readAnalogChannel = "10";
    private static final String getRelayPort = "11";
    private static final String getInputPort = "12";
    private static final String getInput = "13";
    private static final String getFlagsByte = "14";
    private static final String getFlag ="15";
    private static final String setFlag = "16";
    private static final String resetFlag = "17";
    private static final String getSetpoint = "18";
    private static final String setSetpoint = "19";
    private static final String getSetpoints ="20";
    private static final String getAnalogs = "21";
    private static final String setLedON = "22";
    private static final String setLedOFF = "23";
    private static final String getControlReg = "24";
    private static final String setOutputPort = "25";
    
    private Socket socket = null;
    private String address = null;
    private int slave = 0;
    private int port = 0;

    private BufferedReader bfin = null;
    private InputStreamReader in = null;
    private PrintWriter out = null;

    /**
     * Constructor class
     * @param address String IP address of RPI_IO board "localhost"
     * @param port int PorPI_IO_Server "30000"
     */
    public Net_RPI_IO(String address, int port) {
        this.address = address;
        this.port = port;
    }
    
    /**
     * 
     * @param address String IP address of RPI_IO board "localhost"
     * @param port int port Master Task "30008"
     * @param slave int slave address
     */
    public Net_RPI_IO(String address, int port, int slave){
        this.address = address;
        this.port = port;
        this.slave = slave;
    }
    /**
     * This method locks relay output to requesting task. Also, sets the level
     * of access  of requesting task.
     * @param task int task number 1..255
     * @param level int task access level 1..5
     * @param rly int output relay. 1..8
     * @return "*" if command succeded. "/" if not.
     */
    public String setLock(int task, int level, int rly) {

        String command = task + "," + level + "," + setLock + " " + rly;
        String reply = sendCommand(command);
        return reply;
    }
    
    /**
     * This method is an overloaded version of setLock.
     * @param task int task number requesting lock. 1..255
     * @param level int level. 1..5
     * @param rly int output relay. 1..8
     * @param cnt int number of consecutive relay to lock.
     * @return "*" if command succeded. "/" if not.
     */
    public String setLock(int task, int level, int rly, int cnt) {
        String command = task + "," + level + "," + setMultipleLocks + " " + rly+" "+cnt;
        String reply = sendCommand(command);
        return reply;
    }
    
    /**
     * This method sets ON relay output.
     * @param task int task owner. 1..255
     * @param level int level. 1..5
     * @param rly int relay output to set ON
     * @return "*" if command succeded. "/" if not.
     */
    public String setRly(int task, int level, int rly) {
        String command = task + "," + level + "," + setRly + " " + rly;
        String reply = sendCommand(command);
        return reply;
    }
    
    /**
     * This method sets OFF relay output.
     * @param task int task owner. 1..255
     * @param level int level. 1..5
     * @param rly int relay output to set OFF
     * @return "*" if command succeded. "/" if not.
     */
    public String resetRly(int task, int level, int rly) {
        String command = task + "," + level + "," + resetRly + " " + rly;
        String reply = sendCommand(command);
        return reply;
    }

    /**
     * This method returns the task that owns the relay.
     * @param task int 1..255
     * @param level int 1..5
     * @param rly int output relay.
     * @return String owner.
     */
    public String getLock(int task, int level, int rly) {
        String command = task + "," + level + "," + getLock + " " + rly;
        String reply = sendCommand(command);
        return reply;
    }
    /**
     * This method release the lock on a relay
     * @param task int 1..255
     * @param level int 1..5
     * @param rly int output relay
     * @return 
     */
    public String releaseLock(int task, int level, int rly){
        String command = task + "," + level + "," + releaseLock + " " + rly;
        String reply = sendCommand(command);
        return reply;    
    }
    /**
     * This method returns the task owner level of lock.
     * @param task int 1..255
     * @param level int 1..5
     * @param rly int output relay.
     * @return String level.
     */
    public String getLevel(int task, int level, int rly){
    String command = task + "," + level + "," + getLevel + " " + rly;
        String reply = sendCommand(command);
        return reply;    
    }
    
    /**
     * This method reads the output port byte
     * @param task 1..255
     * @param level 1..5
     * @return String output int equivalent
     */
     public String getRelayPort(int task, int level) {
        String command = task + "," + level + "," + getRelayPort+" "+1;
        String reply = sendCommand(command);
        return reply;
    }
    /**
     * This method reads the input port byte
     * @param task 1..255
     * @param level 1..5
     * @return String input int equivalent
     */
    public String getInputPort(int task, int level) {
        String command = task + "," + level + "," + getInputPort+" "+1;
        String reply = sendCommand(command);
        return reply;
    }
    /**
     * This method reads the status of Input port
     * @param task 1..255
     * @param level 1..5
     * @param input 1..8
     * @return String "true" or "false"
     */
    public String getInput(int task, int level, int input) {
        String command = task + "," + level + "," + getInput+" "+input;
        String reply = sendCommand(command);
        return reply;
    }
    /**
     * This method return the analog settings for channel.
     * @param task 1..255
     * @param level 1..5
     * @param chn 1..8
     * @return String "analog type", "zero cal." ,"span cal."
     */
    public String getAnalogSettings(int task, int level, int chn) {
        String command = task + "," + level + "," + getAnalogSettings+" "+chn;
        String reply = sendCommand(command);
        return reply;
    }
    /**
     * This method sets the analog settings for channel.
     * @param task 1..255
     * @param level 1..5
     * @param chn 1..8
     * @return String "analog type", "zero cal." ,"span cal."
     */
    public String setAnalogSettings(int task, int level, int chn, String data) {
        String command = task + "," + level + "," + setAnalogSettings + " " + chn+" "+data;
        String reply = sendCommand(command);
        return reply;
    }
    /** This method return input value of channel.
     * between 0..5 if 5 volts input type
     * between 0..10 if 10 volts input
     * between 0..20 if 0..20mA
     * @param task 1..255
     * @param level 1..5
     * @param chn 1..8
     * @return String value
     */
    public String readAnalogChannel(int task, int level, int chn){
        String command = task + "," + level + "," + readAnalogChannel + " " + chn;
        String reply = sendCommand(command);
        return reply;
    }
    
    public String readFlagByte(int task, int level){
        String command = task + ","+level+","+getFlagsByte+" "+1;
        String reply = sendCommand(command);
        return reply;
    }
    
    public String setFlag(int task, int level, int flag){
        String command = task + "," + level + "," + setFlag + " " + flag;
        String reply = sendCommand(command);
        return reply;
    }
    
    public String resetFlag(int task, int level, int flag){
        String command = task + "," + level + "," + resetFlag + " " + flag;
        String reply = sendCommand(command);
        return reply;
    }
    
    public String readSetpoint(int task, int level, int setp){
        String command = task + "," + level + "," + getSetpoint + " " + setp;
        String reply = sendCommand(command);
        return reply;   
    }
    
    public String writeSetpoint(int task, int level, int setp, double value){
        String command = task + "," + level + "," + setSetpoint + " " + setp+" "+value;
        String reply = sendCommand(command);
        return reply;       
    }
    
    public String getSetpoints(int task, int level){
        String command = task + ","+level+","+getSetpoints+" "+1;
        String reply = sendCommand(command);
        return reply;
    }
    
    public String getAnalogs(int task, int level){
        String command = task + ","+level+","+getAnalogs+" "+1;
        String reply = sendCommand(command);
        return reply;
    }
    
    public String setLedON(int task, int level) {
        String command = task + "," + level + "," + setLedON + " " + 1;
        String reply = sendCommand(command);
        return reply;
    }
    
    public String setLedOFF(int task, int level){
        String command = task + ","+level+","+setLedOFF+" "+1;
        String reply = sendCommand(command);
        return reply;
    }
    
    public String getControlReg(int task, int level){
        String command = task + ","+level+","+getControlReg+" "+1;
        String reply = sendCommand(command);
        return reply;
    }
    
    public String setOutputPort(int task, int level, int value){
        String command = task + "," + level + "," + setOutputPort + " "+ value;
        String reply = sendCommand(command);
        return reply;
    }
    
    private String sendCommand(String command) {
        
        if(slave != 0){
            command = "1:"+slave+":"+command;
        }
        try {
            int i = 0;
            String resp = "";
            
            do {
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(address, port), 1000);
                    i=10;
                } catch (IOException ex) {
                    Logger.getLogger(Net_RPI_IO.class.getName()).log(Level.SEVERE, null, ex);
                    i++;
                }
            } while (i < 5);
            
            if (i == 10) {
                try {
                    in = new InputStreamReader(socket.getInputStream());
                    //takes input from socket
                    bfin = new BufferedReader(in);
                    // sends output to the socket
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(command);
                    resp = bfin.readLine();
                    bfin.close();
                    out.close();
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(Net_RPI_IO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            socket.close();
            
            return resp;
        } catch (IOException ex) {
            Logger.getLogger(Net_RPI_IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "0";
    }
}
