package {{systemName|lower}}.smarthome.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import {{systemName|lower}}.smarthome.enums.ActuatorEnum;
import {{systemName|lower}}.smarthome.enums.SensorEnum;

{% for device in extraData.devices %}
{% if device.typeDevice != "Hardware" %}
import {{systemName|lower}}.smarthome.model.devices.{{device.name}};
{% endif %}
{% endfor %}

import {{systemName|lower}}.smarthome.model.devices.AirConditioner;
import {{systemName|lower}}.smarthome.model.devices.Alarm;
import {{systemName|lower}}.smarthome.model.devices.AutomaticDoor;
import {{systemName|lower}}.smarthome.model.devices.AutomaticWindow;
import {{systemName|lower}}.smarthome.model.devices.Led;
import {{systemName|lower}}.smarthome.model.devices.LightSensor;
import {{systemName|lower}}.smarthome.model.devices.PresenceSensor;
import {{systemName|lower}}.smarthome.model.devices.TemperatureSensor;

public class HardwareControlTab extends JPanel {

	private static final long serialVersionUID = 5221285834937378979L;
	private JTextField txtSensorPin;
	private JTextField txtActuatorPin;
	private JTextField txtRemovalPin;
	private JCheckBox chkDigitalRemoval;
	private JComboBox<SensorEnum> cmbSensors;
	private JComboBox<ActuatorEnum> cmbActuators;
	private JCheckBox chkDigitalActuator;
	private JCheckBox chkAnalogSensor;

	public HardwareControlTab() {
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Sensors", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(12, 16, 438, 127);
		add(panel);
		panel.setLayout(null);

		JLabel lblFeatureType = new JLabel("Type:");
		lblFeatureType.setBounds(6, 29, 34, 16);
		panel.add(lblFeatureType);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SensorEnum sensorEnum = (SensorEnum) cmbSensors.getSelectedItem(); 
				switch (sensorEnum) {

				{% for k,device in extraData.items() %}
				{% if device["device"].typeDevice == "Sensor" %}
				case {{device["device"].name|splitName|replace(" ","_")|upper}}:
					{{device["device"].name}} {{device["device"].name|lower}} = new {{device["device"].name}}(Integer.parseInt(txtSensorPin.getText()),chkAnalogSensor.isSelected());
					Main.getHouseInstance().addHardware({{device["device"].name|lower}});
					break;
				{% endif %}
				{% endfor %}

				}
				cmbSensors.setSelectedIndex(0);
				txtSensorPin.setText("");
				chkAnalogSensor.setSelected(true);
			}
		});
		btnAdd.setBounds(302, 85, 117, 30);
		panel.add(btnAdd);

		cmbSensors = new JComboBox<SensorEnum>(SensorEnum.values());
		cmbSensors.setBounds(46, 22, 373, 30);
		panel.add(cmbSensors);

		JLabel lblPin = new JLabel("Pin:");
		lblPin.setBounds(6, 85, 28, 16);
		panel.add(lblPin);

		txtSensorPin = new JTextField();
		txtSensorPin.setBounds(46, 85, 44, 28);
		panel.add(txtSensorPin);
		txtSensorPin.setColumns(10);

		chkAnalogSensor = new JCheckBox("Analog");
		chkAnalogSensor.setSelected(true);
		chkAnalogSensor.setBounds(102, 85, 85, 23);
		panel.add(chkAnalogSensor);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Actuators", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel_2.setBounds(12, 155, 438, 127);
		add(panel_2);

		JLabel label = new JLabel("Type:");
		label.setBounds(6, 29, 34, 16);
		panel_2.add(label);

		chkDigitalActuator = new JCheckBox("Digital");
		chkDigitalActuator.setSelected(true);
		chkDigitalActuator.setBounds(102, 85, 85, 23);
		panel_2.add(chkDigitalActuator);

		JButton button = new JButton("Add");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActuatorEnum actuatorEnum = (ActuatorEnum) cmbActuators.getSelectedItem();
				switch (actuatorEnum) {

				{% for k,device in extraData.items() %}
				{% if device["device"].typeDevice == "Actuator" %}
				case {{device["device"].name|splitName|replace(" ","_")|upper}}:
					{{device["device"].name}} {{device["device"].name|lower}} = new {{device["device"].name}}(Integer.parseInt(txtSensorPin.getText()),chkAnalogSensor.isSelected());
					Main.getHouseInstance().addHardware({{device["device"].name|lower}});
					break;
				{% endif %}
				{% endfor %}

				default:
					break;
				}
				cmbActuators.setSelectedIndex(0);
				txtActuatorPin.setText("");
				chkDigitalActuator.setSelected(true);
			}
		});
		button.setBounds(302, 85, 117, 29);
		panel_2.add(button);

		cmbActuators = new JComboBox<ActuatorEnum>(ActuatorEnum.values());
		cmbActuators.setBounds(46, 22, 373, 30);
		panel_2.add(cmbActuators);

		JLabel label_1 = new JLabel("Pin:");
		label_1.setBounds(6, 85, 28, 16);
		panel_2.add(label_1);

		txtActuatorPin = new JTextField();
		txtActuatorPin.setColumns(10);
		txtActuatorPin.setBounds(46, 85, 44, 28);
		panel_2.add(txtActuatorPin);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Hardware Removal", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel_1.setBounds(12, 300, 438, 80);
		add(panel_1);

		chkDigitalRemoval = new JCheckBox("Digital");
		chkDigitalRemoval.setBounds(101, 35, 85, 23);
		chkDigitalRemoval.setSelected(true);
		panel_1.add(chkDigitalRemoval);

		JButton btnRemoveHardware = new JButton("Remove Hardware");
		btnRemoveHardware.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getHouseInstance().
				removeHardware(Integer.parseInt(txtRemovalPin.getText()), !chkDigitalRemoval.isSelected());
				txtRemovalPin.setText("");
				chkDigitalRemoval.setSelected(true);
			}
		});
		btnRemoveHardware.setBounds(276, 30, 142, 29);
		panel_1.add(btnRemoveHardware);

		JLabel label_3 = new JLabel("Pin:");
		label_3.setBounds(6, 35, 28, 16);
		panel_1.add(label_3);

		txtRemovalPin = new JTextField();
		txtRemovalPin.setColumns(10);
		txtRemovalPin.setBounds(46, 85, 44, 28);
		panel_1.add(txtRemovalPin);

	}
}
