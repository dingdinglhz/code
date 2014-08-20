package hz.lin.sparkiMapper;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class JSSCTest {
   
    public static void main(String[] args) {
    	SerialPort serialPort = new SerialPort("COM5");
        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(9600, 8, 1, 0);//Set params.
            //byte[] buffer = serialPort.readBytes(10);//Read 10 bytes from serial port
            //System.out.println(new String(buffer));
            for(int i=0;i<10;){
            	String s=serialPort.readString();
            	if(s!=null){
            	System.out.println(s);
            	System.out.println("----Str #"+i);
            	i++;
            	}
            }
            serialPort.closePort();//Close serial port
            
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
}