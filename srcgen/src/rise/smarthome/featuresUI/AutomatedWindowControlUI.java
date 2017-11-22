              
package rise.smarthome.featuresUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import rise.smarthome.features.AutomatedWindowControl;
import rise.smarthome.gui.Main;
import rise.smarthome.model.devices.AutomaticWindow;
import rise.smarthome.model.devices.Hardware;
import rise.smarthome.model.devices.TemperatureSensor;

public class AutomatedWindowControlUI extends FeatureUIBase {

	private static final long serialVersionUID = 4435596811596503762L;
	private JComboBox<AutomaticWindow> cmbAvaliableAutomaticWindow;
	private AutomatedWindowControl automatedwindowcontrol;
    private JComboBox<AutomaticWindow> cmbCurrentAutomaticWindows;
	private JComboBox<TemperatureSensor> cmbSensor;
	private JToggleButton tglActivateFeature;

	public AutomatedWindowControlUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateAutomatedWindowControlToAutomateCombo();
				updateTemperatureSensorCombo();
			}
		});
		automatedwindowcontrol = (AutomatedWindowControl) Main.getHouseInstance().getFeatureByType(AutomatedWindowControl.class);
		setForClass(AutomatedWindowControl.class);
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(12, 260, 438, 120);
		add(panel);
		panel.setLayout(null);
		
		tglActivateFeature = new JToggleButton("Start Feature");
		tglActivateFeature.setBounds(125, 56, 161, 29);
		tglActivateFeature.addActionListener(new ActionListener() {
                    
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tglActivateFeature.isSelected()){
					tglActivateFeature.setText("Stop Feature");
					automatedwindowcontrol.setActive(true);
				}else{
					tglActivateFeature.setText("Start Feature");
					automatedwindowcontrol.setActive(false);
				}
			}
		});
		panel.add(tglActivateFeature);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Feature Management", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel_1.setLayout(null);
		panel_1.setBounds(12, 22, 438, 220);
		add(panel_1);

		JLabel lblAutomaticWindowPin = new JLabel("Avaliable Automatic Windows:");
		lblAutomaticWindowPin.setBounds(6, 90, 220, 16);
		panel_1.add(lblAutomaticWindowPin);
		cmbAvaliableAutomaticWindow = new JComboBox<AutomaticWindow>();
		updateAvaliableCombo();
		cmbAvaliableAutomaticWindow.addActionListener(new ActionListener() {
                    
			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticWindow actuator = (AutomaticWindow) cmbAvaliableAutomaticWindow.getSelectedItem();
				if(!automatedwindowcontrol.getAutomaticWindows().contains(actuator)){
					automatedwindowcontrol.getAutomaticWindows().add(actuator);
					updateAutomatedWindowControlToAutomateCombo();
				}
			}
		});
		cmbAvaliableAutomaticWindow.setBounds(210, 85, 210, 30);
		panel_1.add(cmbAvaliableAutomaticWindow);

		JLabel lblCurrentAutomaticWindow = new JLabel("Automatic AutomaticWindow to Automate:");
		lblCurrentAutomaticWindow.setBounds(6, 143, 220, 16);
		panel_1.add(lblCurrentAutomaticWindow);

		cmbCurrentAutomaticWindows = new JComboBox<AutomaticWindow>();
		updateAutomatedWindowControlToAutomateCombo();
		cmbCurrentAutomaticWindows.addActionListener(new ActionListener() {
                    
			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticWindow actuator = (AutomaticWindow) cmbCurrentAutomaticWindows.getSelectedItem();
				automatedwindowcontrol.getAutomaticWindows().remove(actuator);
				updateAutomatedWindowControlToAutomateCombo();
			}
		});
		cmbCurrentAutomaticWindows.setBounds(245, 135, 175, 30);
		panel_1.add(cmbCurrentAutomaticWindows);

		JLabel lblWhen = new JLabel("Clicking in a current AutomaticWindow combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 180, 412, 16);
		panel_1.add(lblWhen);
		
		JLabel lblAvaliableSensors = new JLabel("Avaliable Sensors:");
		lblAvaliableSensors.setBounds(6, 40, 114, 16);
		panel_1.add(lblAvaliableSensors);
		
		cmbSensor = new JComboBox<TemperatureSensor>();
		cmbSensor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TemperatureSensor temperatureSensor = (TemperatureSensor) cmbSensor.getSelectedItem();
				automatedwindowcontrol.setTemperatureSensor(temperatureSensor);
			}
		});
		cmbSensor.setBounds(125, 36, 293, 27);
		panel_1.add(cmbSensor);
	}
	
	private void updateAvaliableCombo() {
		ArrayList<AutomaticWindow> actuators = automatedwindowcontrol.getAutomaticWindows();
		AutomaticWindow[] actuatorsArray= new AutomaticWindow[actuators.size()];
		int i=0;
		for (AutomaticWindow actuator : actuators) {
			actuatorsArray[i] = (AutomaticWindow) actuator;
			i++;
		}
		cmbAvaliableAutomaticWindow.setModel(new DefaultComboBoxModel<AutomaticWindow>(actuatorsArray));
	}

	private void updateTemperatureSensorCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(TemperatureSensor.class);
		TemperatureSensor[] temperatureSensors= new TemperatureSensor[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			temperatureSensors[i] = (TemperatureSensor) hardware;
			i++;
		}
		cmbSensor.setModel(new DefaultComboBoxModel<TemperatureSensor>(temperatureSensors));
	}

	private void updateAutomatedWindowControlToAutomateCombo() {
		ArrayList<AutomaticWindow> actuators = automatedwindowcontrol.getAutomaticWindows();
		AutomaticWindow[] actuatorsArray= new AutomaticWindow[actuators.size()];
		int i=0;
		for (AutomaticWindow actuator : actuators) {
			actuatorsArray[i] = (AutomaticWindow) actuator;
			i++;
		}
		cmbCurrentAutomaticWindows.setModel(new DefaultComboBoxModel<AutomaticWindow>(actuatorsArray));
	}
}
