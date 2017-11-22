              
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

import rise.smarthome.features.{{data.feature.name}};
import rise.smarthome.gui.Main;
import rise.smarthome.model.devices.{{data.feature.actuator.name}};
import rise.smarthome.model.devices.Hardware;
import rise.smarthome.model.devices.{{data.feature.sensor.name}};

public class {{data.feature.name}}UI extends FeatureUIBase {

	private static final long serialVersionUID = 4435596811596503762L;
	private JComboBox<{{data.feature.actuator.name}}> cmbAvaliable{{data.feature.actuator.name}};
	private {{data.feature.name}} {{data.feature.name|lower}};
    private JComboBox<{{data.feature.actuator.name}}> cmbCurrent{{data.feature.actuator.name}}s;
	private JComboBox<{{data.feature.sensor.name}}> cmbSensor;
	private JToggleButton tglActivateFeature;

	public {{data.feature.name}}UI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				update{{data.feature.name}}ToAutomateCombo();
				update{{data.feature.sensor.name}}Combo();
			}
		});
		{{data.feature.name|lower}} = ({{data.feature.name}}) Main.getHouseInstance().getFeatureByType({{data.feature.name}}.class);
		setForClass({{data.feature.name}}.class);
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
					{{data.feature.name|lower}}.setActive(true);
				}else{
					tglActivateFeature.setText("Start Feature");
					{{data.feature.name|lower}}.setActive(false);
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
		cmbAvaliable{{data.feature.actuator.name}} = new JComboBox<{{data.feature.actuator.name}}>();
		updateAvaliableCombo();
		cmbAvaliable{{data.feature.actuator.name}}.addActionListener(new ActionListener() {
                    
			public synchronized void actionPerformed(ActionEvent e) {
				{{data.feature.actuator.name}} actuator = ({{data.feature.actuator.name}}) cmbAvaliable{{data.feature.actuator.name}}.getSelectedItem();
				if(!{{data.feature.name|lower}}.get{{data.feature.actuator.name}}s().contains(actuator)){
					{{data.feature.name|lower}}.get{{data.feature.actuator.name}}s().add(actuator);
					update{{data.feature.name}}ToAutomateCombo();
				}
			}
		});
		cmbAvaliable{{data.feature.actuator.name}}.setBounds(210, 85, 210, 30);
		panel_1.add(cmbAvaliable{{data.feature.actuator.name}});

		JLabel lblCurrent{{data.feature.actuator.name}} = new JLabel("Automatic {{data.feature.actuator.name}} to Automate:");
		lblCurrent{{data.feature.actuator.name}}.setBounds(6, 143, 220, 16);
		panel_1.add(lblCurrent{{data.feature.actuator.name}});

		cmbCurrent{{data.feature.actuator.name}}s = new JComboBox<{{data.feature.actuator.name}}>();
		update{{data.feature.name}}ToAutomateCombo();
		cmbCurrent{{data.feature.actuator.name}}s.addActionListener(new ActionListener() {
                    
			public synchronized void actionPerformed(ActionEvent e) {
				{{data.feature.actuator.name}} actuator = ({{data.feature.actuator.name}}) cmbCurrent{{data.feature.actuator.name}}s.getSelectedItem();
				{{data.feature.name|lower}}.get{{data.feature.actuator.name}}s().remove(actuator);
				update{{data.feature.name}}ToAutomateCombo();
			}
		});
		cmbCurrent{{data.feature.actuator.name}}s.setBounds(245, 135, 175, 30);
		panel_1.add(cmbCurrent{{data.feature.actuator.name}}s);

		JLabel lblWhen = new JLabel("Clicking in a current {{data.feature.actuator.name}} combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 180, 412, 16);
		panel_1.add(lblWhen);
		
		JLabel lblAvaliableSensors = new JLabel("Avaliable Sensors:");
		lblAvaliableSensors.setBounds(6, 40, 114, 16);
		panel_1.add(lblAvaliableSensors);
		
		cmbSensor = new JComboBox<{{data.feature.sensor.name}}>();
		cmbSensor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				{{data.feature.sensor.name}} temperatureSensor = ({{data.feature.sensor.name}}) cmbSensor.getSelectedItem();
				{{data.feature.name|lower}}.set{{data.feature.sensor.name}}(temperatureSensor);
			}
		});
		cmbSensor.setBounds(125, 36, 293, 27);
		panel_1.add(cmbSensor);
	}
	
	private void updateAvaliableCombo() {
		ArrayList<{{data.feature.actuator.name}}> actuators = {{data.feature.name|lower}}.get{{data.feature.actuator.name}}s();
		{{data.feature.actuator.name}}[] actuatorsArray= new {{data.feature.actuator.name}}[actuators.size()];
		int i=0;
		for ({{data.feature.actuator.name}} actuator : actuators) {
			actuatorsArray[i] = ({{data.feature.actuator.name}}) actuator;
			i++;
		}
		cmbAvaliable{{data.feature.actuator.name}}.setModel(new DefaultComboBoxModel<{{data.feature.actuator.name}}>(actuatorsArray));
	}

	private void update{{data.feature.sensor.name}}Combo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType({{data.feature.sensor.name}}.class);
		{{data.feature.sensor.name}}[] temperatureSensors= new {{data.feature.sensor.name}}[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			temperatureSensors[i] = ({{data.feature.sensor.name}}) hardware;
			i++;
		}
		cmbSensor.setModel(new DefaultComboBoxModel<{{data.feature.sensor.name}}>(temperatureSensors));
	}

	private void update{{data.feature.name}}ToAutomateCombo() {
		ArrayList<{{data.feature.actuator.name}}> actuators = {{data.feature.name|lower}}.get{{data.feature.actuator.name}}s();
		{{data.feature.actuator.name}}[] actuatorsArray= new {{data.feature.actuator.name}}[actuators.size()];
		int i=0;
		for ({{data.feature.actuator.name}} actuator : actuators) {
			actuatorsArray[i] = ({{data.feature.actuator.name}}) actuator;
			i++;
		}
		cmbCurrent{{data.feature.actuator.name}}s.setModel(new DefaultComboBoxModel<{{data.feature.actuator.name}}>(actuatorsArray));
	}
}

