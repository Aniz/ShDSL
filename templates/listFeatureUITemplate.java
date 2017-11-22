package {{systemName|lower}}.smarthome.featuresUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import {{systemName|lower}}.smarthome.features.{{data.feature.name}};

import {{systemName|lower}}.smarthome.gui.Main;
import {{systemName|lower}}.smarthome.model.devices.Hardware;
import {{systemName|lower}}.smarthome.model.devices.{{data.feature.actuator.name}};

public class {{data.feature.name}}UI extends FeatureUIBase {

    private static final long serialVersionUID = 5575292698449566428L;
	
    private {{data.feature.name}} {{data.feature.name|lower}};
	private JComboBox<{{data.feature.actuator.name}}> cmbAvaliable{{data.feature.actuator.name}};
	private JComboBox<{{data.feature.actuator.name}}> cmbCurrent{{data.feature.actuator.name}};
	private JComboBox<{{data.feature.actuator.name}}> cmb{{data.feature.actuator.name}}Action;

	public {{data.feature.name}}UI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});
		{{data.feature.name|lower}} = ({{data.feature.name}}) Main.getHouseInstance().getFeatureByType({{data.feature.name}}.class);

		setLayout(null);
		setForClass({{data.feature.name}}.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel.setBounds(12, 220, 438, 120);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblLed = new JLabel("Led:");
		lblLed.setBounds(6, 35, 26, 16);
		panel.add(lblLed);

		cmb{{data.feature.actuator.name}}Action = new JComboBox<{{data.feature.actuator.name}}>();
		cmb{{data.feature.actuator.name}}Action.setBounds(43, 30, 370, 30);
		panel.add(cmb{{data.feature.actuator.name}}Action);

		JButton btnSwitchOn = new JButton("Switch On");
		btnSwitchOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				{{data.feature.actuator.name}} alarm = ({{data.feature.actuator.name}})cmb{{data.feature.actuator.name}}Action.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"1"};
					{{data.feature.name|lower}}.proceedActions(args);
					cmbAvaliable{{data.feature.actuator.name}}.repaint();
					cmbCurrent{{data.feature.actuator.name}}.repaint();
					cmb{{data.feature.actuator.name}}Action.repaint();
				}
			}
		});
		btnSwitchOn.setBounds(43, 70, 110, 25);
		panel.add(btnSwitchOn);

		JButton btnSwitchOff = new JButton("Switch Off");
		btnSwitchOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				{{data.feature.actuator.name}} alarm = ({{data.feature.actuator.name}})cmb{{data.feature.actuator.name}}Action.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"0"};
					{{data.feature.name|lower}}.proceedActions(args);
					cmbAvaliable{{data.feature.actuator.name}}.repaint();
					cmbCurrent{{data.feature.actuator.name}}.repaint();
					cmb{{data.feature.actuator.name}}Action.repaint();
				}
			}
		});
		btnSwitchOff.setBounds(300, 70, 110, 25);
		panel.add(btnSwitchOff);
		
		JButton btnStateChange = new JButton("State Change");
		btnStateChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				{{data.feature.actuator.name}} alarm = ({{data.feature.actuator.name}})cmb{{data.feature.actuator.name}}Action.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"-1"};
					{{data.feature.name|lower}}.proceedActions(args);
					cmbAvaliable{{data.feature.actuator.name}}.repaint();
					cmbCurrent{{data.feature.actuator.name}}.repaint();
					cmb{{data.feature.actuator.name}}Action.repaint();
				}
			}
		});
		btnStateChange.setBounds(167, 70, 110, 25);
		panel.add(btnStateChange);
                
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Feature Management", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel_1.setLayout(null);
		panel_1.setBounds(12, 18, 438, 171);
		add(panel_1);

		JLabel lbl{{data.feature.actuator.name}}Pin = new JLabel("Avaliable {{data.feature.actuator.name}}s:");
		lbl{{data.feature.actuator.name}}Pin.setBounds(6, 35, 101, 16);
		panel_1.add(lbl{{data.feature.actuator.name}}Pin);
		cmbAvaliable{{data.feature.actuator.name}} = new JComboBox<{{data.feature.actuator.name}}>();
		updateAvaliableCombo();
		cmbAvaliable{{data.feature.actuator.name}}.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				{{data.feature.actuator.name}} alarm = ({{data.feature.actuator.name}}) cmbAvaliable{{data.feature.actuator.name}}.getSelectedItem();
				if(!{{data.feature.name|lower}}.get{{data.feature.actuator.name}}s().contains(alarm)){
					{{data.feature.name|lower}}.get{{data.feature.actuator.name}}s().add(alarm);
					updateCurrentCombo();
				}
			}
		});
		cmbAvaliable{{data.feature.actuator.name}}.setBounds(110, 30, 308, 30);
		panel_1.add(cmbAvaliable{{data.feature.actuator.name}});

		JLabel lblCurrentAirConditioners = new JLabel("Current {{data.feature.actuator.name}}s:");
		lblCurrentAirConditioners.setBounds(6, 103, 101, 16);
		panel_1.add(lblCurrentAirConditioners);

		cmbCurrent{{data.feature.actuator.name}} = new JComboBox<{{data.feature.actuator.name}}>();
		updateCurrentCombo();
		cmbCurrent{{data.feature.actuator.name}}.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				{{data.feature.actuator.name}} alarm = ({{data.feature.actuator.name}}) cmbCurrent{{data.feature.actuator.name}}.getSelectedItem();
				alarm.deactivate();
				{{data.feature.name|lower}}.get{{data.feature.actuator.name}}s().remove(alarm);
				updateCurrentCombo();
			}
		});
		cmbCurrent{{data.feature.actuator.name}}.setBounds(110, 95, 308, 30);
		panel_1.add(cmbCurrent{{data.feature.actuator.name}});

		JLabel lblWhen = new JLabel("Clicking in a current {{data.feature.actuator.name}} combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 135, 412, 16);
		panel_1.add(lblWhen);
	}

	private void updateAvaliableCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType({{data.feature.actuator.name}}.class);
		{{data.feature.actuator.name}}[] alarms= new {{data.feature.actuator.name}}[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			alarms[i] = ({{data.feature.actuator.name}}) hardware;
			i++;
		}
		cmbAvaliable{{data.feature.actuator.name}}.setModel(new DefaultComboBoxModel<{{data.feature.actuator.name}}>(alarms));
	}

	private void updateCurrentCombo() {
		if({{data.feature.name|lower}}.get{{data.feature.actuator.name}}s().size()>0){
			{{data.feature.actuator.name}}[] alarms= new {{data.feature.actuator.name}}[{{data.feature.name|lower}}.get{{data.feature.actuator.name}}s().size()];
			int i=0;
			for ({{data.feature.actuator.name}} alarm : {{data.feature.name|lower}}.get{{data.feature.actuator.name}}s()) {
				alarms[i] = alarm;
				i++;
			}
			cmbCurrent{{data.feature.actuator.name}}.setModel(new DefaultComboBoxModel<{{data.feature.actuator.name}}>(alarms));
			cmb{{data.feature.actuator.name}}Action.setModel(new DefaultComboBoxModel<{{data.feature.actuator.name}}>(alarms));
		}else{
			cmbCurrent{{data.feature.actuator.name}}.setModel(new DefaultComboBoxModel<{{data.feature.actuator.name}}>());
			cmb{{data.feature.actuator.name}}Action.setModel(new DefaultComboBoxModel<{{data.feature.actuator.name}}>());
		}
	}

}