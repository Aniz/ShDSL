              
package {{systemName|lower}}.smarthome.featuresUI;

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

import {{systemName|lower}}.smarthome.features.{{data.feature.name}};
import {{systemName|lower}}.smarthome.gui.Main;
import {{systemName|lower}}.smarthome.model.devices.{{data.feature.actuador.name}};
import {{systemName|lower}}.smarthome.model.devices.Hardware;
import {{systemName|lower}}.smarthome.model.devices.{{data.feature.sensor.name}};

public class {{data.feature.name}}UI extends FeatureUIBase {

	private static final long serialVersionUID = {{data.feature.serial}};
	private JComboBox<{{data.feature.actuador.name}}> cmbAvaliable{{data.feature.actuador.name}};
	private {{data.feature.name}} {{data.feature.name|lower}};
    private JComboBox<{{data.feature.actuador.name}}> cmbCurrent{{data.feature.actuador.name}}s;
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
		cmbAvaliable{{data.feature.actuador.name}} = new JComboBox<{{data.feature.actuador.name}}>();
		updateAvaliableCombo();
		cmbAvaliable{{data.feature.actuador.name}}.addActionListener(new ActionListener() {
                    
			public synchronized void actionPerformed(ActionEvent e) {
				{{data.feature.actuador.name}} actuador = ({{data.feature.actuador.name}}) cmbAvaliable{{data.feature.actuador.name}}.getSelectedItem();
				if(!{{data.feature.name|lower}}.get{{data.feature.actuador.name}}s().contains(actuador)){
					{{data.feature.name|lower}}.get{{data.feature.actuador.name}}s().add(actuador);
					update{{data.feature.name}}ToAutomateCombo();
				}
			}
		});
		cmbAvaliable{{data.feature.actuador.name}}.setBounds(210, 85, 210, 30);
		panel_1.add(cmbAvaliable{{data.feature.actuador.name}});

		JLabel lblCurrent{{data.feature.actuador.name}} = new JLabel("Automatic {{data.feature.actuador.name}} to Automate:");
		lblCurrent{{data.feature.actuador.name}}.setBounds(6, 143, 220, 16);
		panel_1.add(lblCurrent{{data.feature.actuador.name}});

		cmbCurrent{{data.feature.actuador.name}}s = new JComboBox<{{data.feature.actuador.name}}>();
		update{{data.feature.name}}ToAutomateCombo();
		cmbCurrent{{data.feature.actuador.name}}s.addActionListener(new ActionListener() {
                    
			public synchronized void actionPerformed(ActionEvent e) {
				{{data.feature.actuador.name}} actuador = ({{data.feature.actuador.name}}) cmbCurrent{{data.feature.actuador.name}}s.getSelectedItem();
				{{data.feature.name|lower}}.get{{data.feature.actuador.name}}s().remove(actuador);
				update{{data.feature.name}}ToAutomateCombo();
			}
		});
		cmbCurrent{{data.feature.actuador.name}}s.setBounds(245, 135, 175, 30);
		panel_1.add(cmbCurrent{{data.feature.actuador.name}}s);

		JLabel lblWhen = new JLabel("Clicking in a current {{data.feature.actuador.name}} combo item you remove them to the feature.");
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
		ArrayList<{{data.feature.actuador.name}}> actuadors = {{data.feature.name|lower}}.get{{data.feature.actuador.name}}s();
		{{data.feature.actuador.name}}[] actuadorsArray= new {{data.feature.actuador.name}}[actuadors.size()];
		int i=0;
		for ({{data.feature.actuador.name}} actuador : actuadors) {
			actuadorsArray[i] = ({{data.feature.actuador.name}}) actuador;
			i++;
		}
		cmbAvaliable{{data.feature.actuador.name}}.setModel(new DefaultComboBoxModel<{{data.feature.actuador.name}}>(actuadorsArray));
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
		ArrayList<{{data.feature.actuador.name}}> actuadors = {{data.feature.name|lower}}.get{{data.feature.actuador.name}}s();
		{{data.feature.actuador.name}}[] actuadorsArray= new {{data.feature.actuador.name}}[actuadors.size()];
		int i=0;
		for ({{data.feature.actuador.name}} actuador : actuadors) {
			actuadorsArray[i] = ({{data.feature.actuador.name}}) actuador;
			i++;
		}
		cmbCurrent{{data.feature.actuador.name}}s.setModel(new DefaultComboBoxModel<{{data.feature.actuador.name}}>(actuadorsArray));
	}
}

