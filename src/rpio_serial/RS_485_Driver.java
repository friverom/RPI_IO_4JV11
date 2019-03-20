/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpio_serial;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.StopBits;
import com.pi4j.io.serial.impl.SerialImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;

/**
 *
 * @author Federico
 */
public class RS_485_Driver extends SerialImpl{
    
    private GpioController gpio = null;
    private GpioPinDigitalOutput tx_en = null;
    private Serial serial = null;
    private SerialConfig config = null;
    private InputStreamReader isr = null;
    private BufferedReader br = null;
    private PrintStream pos = null;
    
    public RS_485_Driver() throws IOException{
        
        //Instantiate GPIO 04 to drive RS-485 TX Enable
        gpio = GpioFactory.getInstance();
        tx_en = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "TX_EN", PinState.HIGH);
        tx_en.setShutdownOptions(true, PinState.HIGH);
        
        //Instantiate Serial port
        serial = SerialFactory.createInstance();
        
        //Configure Serial Port
        config = new SerialConfig();
        config.device("/dev/ttyS0")
                  .baud(Baud._9600)
                  .dataBits(DataBits._8)
                  .parity(Parity.NONE)
                  .stopBits(StopBits._1)
                  .flowControl(FlowControl.NONE);
        
        serial.open(config);
        serial.discardAll();
        isr = new InputStreamReader(serial.getInputStream());
        br = new BufferedReader(isr);
        pos = new PrintStream(serial.getOutputStream());
    }
    
    public void send(String data) throws IOException{
        
        tx_en.low(); //Take RS85 bus
        pos.print(data);
        pos.flush();
        tx_en.high(); //Release bus
        
        
    }
    
    public String receive() throws IOException, ClassNotFoundException, InterruptedException{
            
            int i =0;
            String data="error";
            
            while(i<20){
                if(serial.available()<1){
                    i++;
                    Thread.sleep(100);
                }else{
                    data = br.readLine();
                    i=21;
                }
            }
            
            return data;
        
        
    }
    
}
