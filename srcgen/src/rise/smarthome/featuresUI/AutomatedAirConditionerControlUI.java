              
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

import rise.smarthome.features.AutomatedAirConditionerControl;
import rise.smarthome.gui.Main;
import rise.smarthome.model.devices.AirConditioner;
import rise.smarthome.model.devices.Hardware;
import rise.smarthome.model.devices.TemperatureSensor;

public class AutomatedAirConditionerControlUI extends FeatureUIBase {

	private static final long serialVersionUID = 4435596811596503762L;
	private JComboBox<AirConditioner> cmbAvaliableAirConditioner;
	private AutomatedAirConditionerControl automatedairconditionercontrol;
    private JComboBox<AirConditioner> cmbCurrentAirConditioners;
	private JComboBox<TemperatureSensor> cmbSensor;
	private JToggleButton tglActivateFeature;

	public AutomatedAirConditionerControlUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateAutomatedAirConditionerControlToAutomateCombo();
				updateTemperatureSensorCombo();
			}
		});
		automatedairconditionercontrol = (AutomatedAirConditionerControl) Main.getHouseInstance().getFeatureByType(AutomatedAirConditionerControl.class);
		setForClass(AutomatedAirConditionerControl.class);
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
					automatedairconditionercontrol.setActive(true);
				}else{
					tglActivateFeature.setText("Start Feature");
					automatedairconditionercontrol.setActive(false);
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
		cmbAvaliableAirConditioner = new JComboBox<AirConditioner>();
		updateAvaliableCombo();
		cmbAvaliableAirConditioner.addActionListener(new ActionListener() {
                    
			public synchronized void actionPerformed(ActionEvent e) {
				AirConditioner actuador = (AirConditioner) cmbAvaliableAirConditioner.getSelectedItem();
				if(!automatedairconditionercontrol.getAirConditioners().contains(actuador)){
					automatedairconditionercontrol.getAirConditioners().add(actuador);
					updateAutomatedAirConditionerControlToAutomateCombo();
				}
			}
		});
		cmbAvaliableAirConditioner.setBounds(210, 85, 210, 30);
		panel_1.add(cmbAvaliableAirConditioner);

		JLabel lblCurrentAirConditioner = new JLabel("Automatic AirConditioner to Automate:");
		lblCurrentAirConditioner.setBounds(6, 143, 220, 16);
		panel_1.add(lblCurrentAirConditioner);

		cmbCurrentAirConditioners = new JComboBox<AirConditioner>();
		updateAutomatedAirConditionerControlToAutomateCombo();
		cmbCurrentAirConditioners.addActionListener(new ActionListener() {
                    
			public synchronized void actionPerformed(ActionEvent e) {
				AirConditioner actuador = (AirConditioner) cmbCurrentAirConditioners.getSelectedItem();
				automatedairconditionercontrol.getAirConditioners().remove(actuador);
				updateAutomatedAirConditionerControlToAutomateCombo();
			}
		});
		cmbCurrentAirConditioners.setBounds(245, 135, 175, 30);
		panel_1.add(cmbCurrentAirConditioners);

		JLabel lblWhen = new JLabel("Clicking in a current AirConditioner combo item you remove them to the feature.");
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
				automatedairconditionercontrol.setTemperatureSensor(temperatureSensor);
			}
		});
		cmbSensor.setBounds(125, 36, 293, 27);
		panel_1.add(cmbSensor);
	}
	
	private void updateAvaliableCombo() {
		ArrayList<AirConditioner> actuadors = automatedairconditionercontrol.getAirConditioners();
		AirConditioner[] actuadorsArray= new AirConditioner[actuadors.size()];
		int i=0;
		for (AirConditioner actuador : actuadors) {
			actuadorsArray[i] = (AirConditioner) actuador;
			i++;
		}
		cmbAvaliableAirConditioner.setModel(new DefaultComboBoxModel<AirConditioner>(actuadorsArray));
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

	private void updateAutomatedAirConditionerControlToAutomateCombo() {
		ArrayList<AirConditioner> actuadors = automatedairconditionercontrol.getAirConditioners();
		AirConditioner[] actuadorsArray= new AirConditioner[actuadors.size()];
		int i=0;
		for (AirConditioner actuador : actuadors) {
			actuadorsArray[i] = (AirConditioner) actuador;
			i++;
		}
		cmbCurrentAirConditioners.setModel(new DefaultComboBoxModel<AirConditioner>(actuadorsArray));
	}
}
