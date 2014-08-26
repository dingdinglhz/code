package hz.lin.sparkiMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.JPanel;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialReceiver {
	SerialPort serialPort;
	String strBuf;
	MapData data;
	JPanel canvas;
	FileWriter fout;
	Calendar calendar;
	private static final DateFormat df = new SimpleDateFormat("yyMMddHHmmss");
	private File file;
    public static void main(String[] args) {
    	SerialReceiver rec=new SerialReceiver("COM3");
    }
    public void setCanvas(JPanel canvas){
    	this.canvas=canvas;
    }
    public void writeData(String str){
    	//serialPort.writeBytes(str.getBytes());
    	if(serialPort==null){
    		System.out.println("Null serialPort");
    		return;
    	}
    	if(serialPort.isOpened()){
    		try {
    			serialPort.writeString(str);
    		} catch (SerialPortException e) {
    			System.out.print(str);
    		}
    	}else{
    		System.out.println("serialPort not opened");
    	}
    }
    public SerialReceiver(String portName){
    	calendar=Calendar.getInstance();
    	String parentPath=System.getProperty("user.dir");
    	String fileName=File.separator+df.format(calendar.getTime())+".log";
    	try {
    		System.out.println(parentPath+fileName);
    		file = new File(parentPath+fileName);
    		System.out.println("is file:"+file.isFile());
    		/* if (!file.getParentFile().exists()) {
    			   file.getParentFile().mkdirs();
    			  }*/

     		System.out.println("is file:"+file.isFile());
        	//file.mkdirs();
    		file.createNewFile();
    		System.out.println("is file:"+file.isFile());
    		fout=new FileWriter(file);
		} catch (IOException e) {
			 e.printStackTrace();
		}
    	data=new MapData();
    	start(portName);
    }
    public void start(String portName){
        serialPort = new SerialPort(portName); 
        strBuf=new String();
        try {
            serialPort.openPort();//Open port
            serialPort.setParams(9600, 8, 1, 0);//Set params
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR );//Set mask
            serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
    public void stop(){
    	System.out.println("Trying to stop a port");
    	try{
    	serialPort.removeEventListener();
    	serialPort.closePort();
    	if(fout!=null){fout.flush();fout.close();}
    	}
    	catch (SerialPortException ex) {
            System.out.println(ex);
        } catch (IOException e) {
			// TODO Auto-generated catch block
        	System.out.println(e);
		}
    	
    }
    protected void finalize() throws Throwable{
    	stop();
    	super.finalize();
    }
    public void updataData(){
    	if(data==null){
    		return;
    	}
    	
    	String strA[]=strBuf.split("\n");

    	System.out.println("--DEBUG--");
    	for(int i=0;i<strA.length;i++){
    		System.out.println(strA[i]);
    		//System.out.println("*****");
    	}
    	
    	Scanner scan;
    	String command;
    	double tmpA,tmpB,tmpC;
    	for(int i=0;i<strA.length-1;i++){
    		scan=new Scanner(strA[i]);
    		command=scan.next();
    		if(command.equals("A")){
    			tmpA=scan.nextDouble();
    			tmpB=scan.nextDouble()/2; //Remember, the distance is doubled, so it has to be divided by 2!!!!
    			tmpC=scan.nextDouble();
    			data.newDistance(tmpA,tmpB,tmpC);
    		}else if(command.equals("M")){
    			tmpA=scan.nextDouble();
    			data.move(tmpA);
    		}else if(command.equals("T")){
    			tmpA=scan.nextDouble();
    			data.rotateDeg(tmpA);
    		}
    	}
    	strBuf=strBuf.substring(strBuf.lastIndexOf(strA[strA.length-1]));
    	if(canvas!=null){
    		canvas.repaint();
    	}
    	//data.debug();
    }
    /*
     * In this class must implement the method serialEvent, through it we learn about 
     * events that happened to our port. But we will not report on all events but only 
     * those that we put in the mask. In this case the arrival of the data and change the 
     * status lines CTS and DSR
     */
    class SerialPortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR()){//If data is available
            	if(event.getEventValue()>15){
                    try {
                    	String strTmp=serialPort.readString();
                    	//System.out.println(strTmp);
                    	//System.out.println("---------");
                    	if(fout!=null){fout.write(strTmp);}
                    	strBuf+=strTmp;
                    	updataData();
                    }
                    catch (SerialPortException ex) {
                        System.out.println(ex);
                    } catch (IOException e) {
                    	System.out.println(e);
					}
            	}
            }
        }
    }
}