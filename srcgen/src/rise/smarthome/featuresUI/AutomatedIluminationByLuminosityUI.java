              
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

import rise.smarthome.features.AutomatedIluminationByLuminosity;
import rise.smarthome.gui.Main;
import rise.smarthome.model.devices.Led;
import rise.smarthome.model.devices.Hardware;
import rise.smarthome.model.devices.LightSensor;

public class AutomatedIluminationByLuminosityUI extends FeatureUIBase {

	private static final long serialVersionUID = 4435596811596503762L;
	private JComboBox<Led> cmbAvaliableLed;
	private AutomatedIluminationByLuminosity automatediluminationbyluminosity;
    private JComboBox<Led> cmbCurrentLeds;
	private JComboBox<LightSensor> cmbSensor;
	private JToggleButton tglActivateFeature;

	public AutomatedIluminationByLuminosityUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateAutomatedIluminationByLuminosityToAutomateCombo();
				updateLightSensorCombo();
			}
		});
		automatediluminationbyluminosity = (AutomatedIluminationByLuminosity) Main.getHouseInstance().getFeatureByType(AutomatedIluminationByLuminosity.class);
		setForClass(AutomatedIluminationByLuminosity.class);
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
					automatediluminationbyluminosity.setActive(true);
				}else{
					tglActivateFeature.setText("Start Feature");
					automatediluminationbyluminosity.setActive(false);
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
		cmbAvaliableLed = new JComboBox<Led>();
		updateAvaliableCombo();
		cmbAvaliableLed.addActionListener(new ActionListener() {
                    
			public synchronized void actionPerformed(ActionEvent e) {
				Led actuator = (Led) cmbAvaliableLed.getSelectedItem();
				if(!automatediluminationbyluminosity.getLeds().contains(actuator)){
					automatediluminationbyluminosity.getLeds().add(actuator);
					updateAutomatedIluminationByLuminosityToAutomateCombo();
				}
			}
		});
		cmbAvaliableLed.setBounds(210, 85, 210, 30);
		panel_1.add(cmbAvaliableLed);

		JLabel lblCurrentLed = new JLabel("Automatic Led to Automate:");
		lblCurrentLed.setBounds(6, 143, 220, 16);
		panel_1.add(lblCurrentLed);

		cmbCurrentLeds = new JComboBox<Led>();
		updateAutomatedIluminationByLuminosityToAutomateCombo();
		cmbCurrentLeds.addActionListener(new ActionListener() {
                    
			public synchronized void actionPerformed(ActionEvent e) {
				Led actuator = (Led) cmbCurrentLeds.getSelectedItem();
				automatediluminationbyluminosity.getLeds().remove(actuator);
				updateAutomatedIluminationByLuminosityToAutomateCombo();
			}
		});
		cmbCurrentLeds.setBounds(245, 135, 175, 30);
		panel_1.add(cmbCurrentLeds);

		JLabel lblWhen = new JLabel("Clicking in a current Led combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 180, 412, 16);
		panel_1.add(lblWhen);
		
		JLabel lblAvaliableSensors = new JLabel("Avaliable Sensors:");
		lblAvaliableSensors.setBounds(6, 40, 114, 16);
		panel_1.add(lblAvaliableSensors);
		
		cmbSensor = new JComboBox<LightSensor>();
		cmbSensor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LightSensor temperatureSensor = (LightSensor) cmbSensor.getSelectedItem();
				automatediluminationbyluminosity.setLightSensor(temperatureSensor);
			}
		});
		cmbSensor.setBounds(125, 36, 293, 27);
		panel_1.add(cmbSensor);
	}
	
	private void updateAvaliableCombo() {
		ArrayList<Led> actuators = automatediluminationbyluminosity.getLeds();
		Led[] actuatorsArray= new Led[actuators.size()];
		int i=0;
		for (Led actuator : actuators) {
			actuatorsArray[i] = (Led) actuator;
			i++;
		}
		cmbAvaliableLed.setModel(new DefaultComboBoxModel<Led>(actuatorsArray));
	}

	private void updateLightSensorCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(LightSensor.class);
		LightSensor[] temperatureSensors= new LightSensor[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			temperatureSensors[i] = (LightSensor) hardware;
			i++;
		}
		cmbSensor.setModel(new DefaultComboBoxModel<LightSensor>(temperatureSensors));
	}

	private void updateAutomatedIluminationByLuminosityToAutomateCombo() {
		ArrayList<Led> actuators = automatediluminationbyluminosity.getLeds();
		Led[] actuatorsArray= new Led[actuators.size()];
		int i=0;
		for (Led actuator : actuators) {
			actuatorsArray[i] = (Led) actuator;
			i++;
		}
		cmbCurrentLeds.setModel(new DefaultComboBoxModel<Led>(actuatorsArray));
	}
}
