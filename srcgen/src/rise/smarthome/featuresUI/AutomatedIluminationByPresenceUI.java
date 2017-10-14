              
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

import rise.smarthome.features.AutomatedIluminationByPresence;
import rise.smarthome.gui.Main;
import rise.smarthome.model.devices.Led;
import rise.smarthome.model.devices.Hardware;
import rise.smarthome.model.devices.PresenceSensor;

public class AutomatedIluminationByPresenceUI extends FeatureUIBase {

	private static final long serialVersionUID = 4435596811596503762L;
	private JComboBox<Led> cmbAvaliableLed;
	private AutomatedIluminationByPresence automatediluminationbypresence;
    private JComboBox<Led> cmbCurrentLeds;
	private JComboBox<PresenceSensor> cmbSensor;
	private JToggleButton tglActivateFeature;

	public AutomatedIluminationByPresenceUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateAutomatedIluminationByPresenceToAutomateCombo();
				updatePresenceSensorCombo();
			}
		});
		automatediluminationbypresence = (AutomatedIluminationByPresence) Main.getHouseInstance().getFeatureByType(AutomatedIluminationByPresence.class);
		setForClass(AutomatedIluminationByPresence.class);
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
					automatediluminationbypresence.setActive(true);
				}else{
					tglActivateFeature.setText("Start Feature");
					automatediluminationbypresence.setActive(false);
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
				Led actuador = (Led) cmbAvaliableLed.getSelectedItem();
				if(!automatediluminationbypresence.getLeds().contains(actuador)){
					automatediluminationbypresence.getLeds().add(actuador);
					updateAutomatedIluminationByPresenceToAutomateCombo();
				}
			}
		});
		cmbAvaliableLed.setBounds(210, 85, 210, 30);
		panel_1.add(cmbAvaliableLed);

		JLabel lblCurrentLed = new JLabel("Automatic Led to Automate:");
		lblCurrentLed.setBounds(6, 143, 220, 16);
		panel_1.add(lblCurrentLed);

		cmbCurrentLeds = new JComboBox<Led>();
		updateAutomatedIluminationByPresenceToAutomateCombo();
		cmbCurrentLeds.addActionListener(new ActionListener() {
                    
			public synchronized void actionPerformed(ActionEvent e) {
				Led actuador = (Led) cmbCurrentLeds.getSelectedItem();
				automatediluminationbypresence.getLeds().remove(actuador);
				updateAutomatedIluminationByPresenceToAutomateCombo();
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
		
		cmbSensor = new JComboBox<PresenceSensor>();
		cmbSensor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PresenceSensor temperatureSensor = (PresenceSensor) cmbSensor.getSelectedItem();
				automatediluminationbypresence.setPresenceSensor(temperatureSensor);
			}
		});
		cmbSensor.setBounds(125, 36, 293, 27);
		panel_1.add(cmbSensor);
	}
	
	private void updateAvaliableCombo() {
		ArrayList<Led> actuadors = automatediluminationbypresence.getLeds();
		Led[] actuadorsArray= new Led[actuadors.size()];
		int i=0;
		for (Led actuador : actuadors) {
			actuadorsArray[i] = (Led) actuador;
			i++;
		}
		cmbAvaliableLed.setModel(new DefaultComboBoxModel<Led>(actuadorsArray));
	}

	private void updatePresenceSensorCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(PresenceSensor.class);
		PresenceSensor[] temperatureSensors= new PresenceSensor[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			temperatureSensors[i] = (PresenceSensor) hardware;
			i++;
		}
		cmbSensor.setModel(new DefaultComboBoxModel<PresenceSensor>(temperatureSensors));
	}

	private void updateAutomatedIluminationByPresenceToAutomateCombo() {
		ArrayList<Led> actuadors = automatediluminationbypresence.getLeds();
		Led[] actuadorsArray= new Led[actuadors.size()];
		int i=0;
		for (Led actuador : actuadors) {
			actuadorsArray[i] = (Led) actuador;
			i++;
		}
		cmbCurrentLeds.setModel(new DefaultComboBoxModel<Led>(actuadorsArray));
	}
}
