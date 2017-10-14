package {{systemName|lower}}.smarthome.arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import {{systemName|lower}}.smarthome.model.devices.Actuator;
import {{systemName|lower}}.smarthome.model.devices.Sensor;

public final class ArduinoControl implements SerialPortEventListener {

	private OutputStream serialOut;
	private int rate=9600;
	// private String portName="/dev/cu.usbmodem411";
	private String portName="{{extraData}}";
	private SerialPort serialPort;
	private BufferedReader input;
	private String arduinoOut;
	private static ArduinoControl arduinoControl;

	public static ArduinoControl getInstance(){
		if(arduinoControl==null){
			arduinoControl = new ArduinoControl();
			return arduinoControl;
		}
		return arduinoControl;
	}

	private ArduinoControl (){
		initialize();
	}
	/**
	 * Inicializa a interface serial
	 * @param commPort
	 * @param rate
	 */
	public void initialize() {
		try {
			CommPortIdentifier portId = null;
			Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
			System.out.println( "Trying:");
			while (portId == null && portEnum.hasMoreElements()) {
				CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
				System.out.println( "   port" + currPortId.getName() );
				if ( currPortId.getName().equals(portName) 
						|| currPortId.getName().startsWith(portName)) {

					serialPort = (SerialPort)currPortId.open("Rise SmartHome", 1000);
					portId = currPortId;
					System.out.println( "Connected on port" + currPortId.getName() );
					break;
				}
			}

			if (portId == null || serialPort == null) {
				System.out.println("Oops... Could not connect to Arduino");
			}

			serialPort.setSerialPortParams(rate,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);

			try { Thread.sleep(2000); } catch (InterruptedException ie) {}
		}
		catch ( Exception e ) { 
			e.printStackTrace();
		}
	}

	public int getPinValue(Sensor sensor){
		return getPinValue(sensor.getPin(), sensor.isAnalog()); 
	}
	public int getPinValue(int pin, boolean isAnalog){
		StringBuilder command = new StringBuilder();
		command.append("r");
		command.append((pin>=10?pin:"0"+pin));
		command.append(((isAnalog)?"a":"d"));
		command.append("*");
		sendCommand(command.toString());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(arduinoOut);
	}

	public void activate(Actuator actuator){
		StringBuilder command = new StringBuilder();
		command.append("a");
		command.append((actuator.getPin()>=10?actuator.getPin():"0"+actuator.getPin()));
		command.append(((actuator.isAnalog())?"a":"d"));
		command.append("1");
		command.append("*");
		sendCommand(command.toString());
	}

	public void deactivate(Actuator actuator){
		StringBuilder command = new StringBuilder();
		command.append("a");
		command.append((actuator.getPin()>=10?actuator.getPin():"0"+actuator.getPin()));
		command.append(((actuator.isAnalog())?"a":"d"));
		command.append("0");
		command.append("*");
		sendCommand(command.toString());
	}

	private void sendCommand(String data) {
		try {
			serialOut = serialPort.getOutputStream();
			serialOut.write( data.getBytes() );
			serialOut.close();
		} 
		catch (Exception e) {
			System.err.println(e.toString());
			System.exit(0);
		}
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void close() {
		if ( serialPort != null ) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public synchronized void serialEvent(SerialPortEvent oEvent) {
		try {
			switch (oEvent.getEventType() ) {
			case SerialPortEvent.DATA_AVAILABLE: 
				if ( input == null ) {
					input = new BufferedReader(
							new InputStreamReader(
									serialPort.getInputStream()));
				}
				arduinoOut = input.readLine();
				break;

			default:
				break;
			}
		} 
		catch (Exception e) {
			System.err.println(e.toString());
		}
	}
}
