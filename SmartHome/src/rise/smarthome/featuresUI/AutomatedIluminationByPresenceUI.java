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
import rise.smarthome.model.devices.Hardware;
import rise.smarthome.model.devices.Led;
import rise.smarthome.model.devices.PresenceSensor;

public class AutomatedIluminationByPresenceUI extends FeatureUIBase {

	private static final long serialVersionUID = 4435596811596503762L;
	private JComboBox<Led> cmbAvaliableLeds;
	private JComboBox<Led> cmbCurrentLeds;
	private AutomatedIluminationByPresence automatedIluminationByPresence;
	private JComboBox<PresenceSensor> cmbSensor;
	private JToggleButton tglActivateFeature;

	public AutomatedIluminationByPresenceUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateLedsToAutomateCombo();
				updateLighSensorCombo();
			}
		});
		automatedIluminationByPresence = (AutomatedIluminationByPresence) Main.getHouseInstance().getFeatureByType(AutomatedIluminationByPresence.class);
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
					automatedIluminationByPresence.setActive(true);
				}else{
					tglActivateFeature.setText("Start Feature");
					automatedIluminationByPresence.setActive(false);
				}
			}
		});
		panel.add(tglActivateFeature);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Feature Management", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel_1.setLayout(null);
		panel_1.setBounds(12, 22, 438, 220);
		add(panel_1);

		JLabel lblLedPin = new JLabel("Avaliable Leds:");
		lblLedPin.setBounds(6, 90, 101, 16);
		panel_1.add(lblLedPin);
		cmbAvaliableLeds = new JComboBox<Led>();
		updateAvaliableCombo();
		cmbAvaliableLeds.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Led led = (Led) cmbAvaliableLeds.getSelectedItem();
				if(!automatedIluminationByPresence.getLedsToAutomate().contains(led)){
					automatedIluminationByPresence.getLedsToAutomate().add(led);
					updateLedsToAutomateCombo();
				}
			}
		});
		cmbAvaliableLeds.setBounds(125, 85, 293, 30);
		panel_1.add(cmbAvaliableLeds);

		JLabel lblCurrentLeds = new JLabel("Leds to Automate:");
		lblCurrentLeds.setBounds(6, 143, 138, 16);
		panel_1.add(lblCurrentLeds);

		cmbCurrentLeds = new JComboBox<Led>();
		updateLedsToAutomateCombo();
		cmbCurrentLeds.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Led led = (Led) cmbCurrentLeds.getSelectedItem();
				automatedIluminationByPresence.getLedsToAutomate().remove(led);
				updateLedsToAutomateCombo();
			}
		});
		cmbCurrentLeds.setBounds(125, 135, 293, 30);
		panel_1.add(cmbCurrentLeds);

		JLabel lblWhen = new JLabel("Clicking in a current led combo item you remove them to the feature.");
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
				PresenceSensor presenceSensor = (PresenceSensor) cmbSensor.getSelectedItem();
				automatedIluminationByPresence.setPresenceSensor(presenceSensor);
			}
		});
		cmbSensor.setBounds(125, 36, 293, 27);
		panel_1.add(cmbSensor);
	}
	
	private void updateAvaliableCombo() {
		ArrayList<Led> leds = automatedIluminationByPresence.getLeds();
		Led[] ledArray= new Led[leds.size()];
		int i=0;
		for (Led led : leds) {
			ledArray[i] = (Led) led;
			i++;
		}
		cmbAvaliableLeds.setModel(new DefaultComboBoxModel<Led>(ledArray));
	}

	private void updateLighSensorCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(PresenceSensor.class);
		PresenceSensor[] presenceSensors= new PresenceSensor[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			presenceSensors[i] = (PresenceSensor) hardware;
			i++;
		}
		cmbSensor.setModel(new DefaultComboBoxModel<PresenceSensor>(presenceSensors));
	}

	private void updateLedsToAutomateCombo() {
		ArrayList<Led> leds = automatedIluminationByPresence.getLedsToAutomate();
		Led[] ledArray= new Led[leds.size()];
		int i=0;
		for (Led led : leds) {
			ledArray[i] = (Led) led;
			i++;
		}
		cmbCurrentLeds.setModel(new DefaultComboBoxModel<Led>(ledArray));
	}
}
