package rise.smarthome.featuresUI;

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
import rise.smarthome.features.AlarmAgainstRobbery;

import rise.smarthome.gui.Main;
import rise.smarthome.model.devices.Hardware;
import rise.smarthome.model.devices.Alarm;

public class AlarmAgainstRobberyUI extends FeatureUIBase {

    private static final long serialVersionUID = 5575292698449566428L;
	
    private AlarmAgainstRobbery alarmagainstrobbery;
	private JComboBox<Alarm> cmbAvaliableAlarm;
	private JComboBox<Alarm> cmbCurrentAlarm;
	private JComboBox<Alarm> cmbAlarmAction;

	public AlarmAgainstRobberyUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});
		alarmagainstrobbery = (AlarmAgainstRobbery) Main.getHouseInstance().getFeatureByType(AlarmAgainstRobbery.class);

		setLayout(null);
		setForClass(AlarmAgainstRobbery.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel.setBounds(12, 220, 438, 120);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblLed = new JLabel("Led:");
		lblLed.setBounds(6, 35, 26, 16);
		panel.add(lblLed);

		cmbAlarmAction = new JComboBox<Alarm>();
		cmbAlarmAction.setBounds(43, 30, 370, 30);
		panel.add(cmbAlarmAction);

		JButton btnSwitchOn = new JButton("Switch On");
		btnSwitchOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Alarm alarm = (Alarm)cmbAlarmAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"1"};
					alarmagainstrobbery.proceedActions(args);
					cmbAvaliableAlarm.repaint();
					cmbCurrentAlarm.repaint();
					cmbAlarmAction.repaint();
				}
			}
		});
		btnSwitchOn.setBounds(43, 70, 110, 25);
		panel.add(btnSwitchOn);

		JButton btnSwitchOff = new JButton("Switch Off");
		btnSwitchOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Alarm alarm = (Alarm)cmbAlarmAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"0"};
					alarmagainstrobbery.proceedActions(args);
					cmbAvaliableAlarm.repaint();
					cmbCurrentAlarm.repaint();
					cmbAlarmAction.repaint();
				}
			}
		});
		btnSwitchOff.setBounds(300, 70, 110, 25);
		panel.add(btnSwitchOff);
		
		JButton btnStateChange = new JButton("State Change");
		btnStateChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Alarm alarm = (Alarm)cmbAlarmAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"-1"};
					alarmagainstrobbery.proceedActions(args);
					cmbAvaliableAlarm.repaint();
					cmbCurrentAlarm.repaint();
					cmbAlarmAction.repaint();
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

		JLabel lblAlarmPin = new JLabel("Avaliable Alarms:");
		lblAlarmPin.setBounds(6, 35, 101, 16);
		panel_1.add(lblAlarmPin);
		cmbAvaliableAlarm = new JComboBox<Alarm>();
		updateAvaliableCombo();
		cmbAvaliableAlarm.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Alarm alarm = (Alarm) cmbAvaliableAlarm.getSelectedItem();
				if(!alarmagainstrobbery.getAlarms().contains(alarm)){
					alarmagainstrobbery.getAlarms().add(alarm);
					updateCurrentCombo();
				}
			}
		});
		cmbAvaliableAlarm.setBounds(110, 30, 308, 30);
		panel_1.add(cmbAvaliableAlarm);

		JLabel lblCurrentAirConditioners = new JLabel("Current Alarms:");
		lblCurrentAirConditioners.setBounds(6, 103, 101, 16);
		panel_1.add(lblCurrentAirConditioners);

		cmbCurrentAlarm = new JComboBox<Alarm>();
		updateCurrentCombo();
		cmbCurrentAlarm.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Alarm alarm = (Alarm) cmbCurrentAlarm.getSelectedItem();
				alarm.deactivate();
				alarmagainstrobbery.getAlarms().remove(alarm);
				updateCurrentCombo();
			}
		});
		cmbCurrentAlarm.setBounds(110, 95, 308, 30);
		panel_1.add(cmbCurrentAlarm);

		JLabel lblWhen = new JLabel("Clicking in a current Alarm combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 135, 412, 16);
		panel_1.add(lblWhen);
	}

	private void updateAvaliableCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(Alarm.class);
		Alarm[] alarms= new Alarm[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			alarms[i] = (Alarm) hardware;
			i++;
		}
		cmbAvaliableAlarm.setModel(new DefaultComboBoxModel<Alarm>(alarms));
	}

	private void updateCurrentCombo() {
		if(alarmagainstrobbery.getAlarms().size()>0){
			Alarm[] alarms= new Alarm[alarmagainstrobbery.getAlarms().size()];
			int i=0;
			for (Alarm alarm : alarmagainstrobbery.getAlarms()) {
				alarms[i] = alarm;
				i++;
			}
			cmbCurrentAlarm.setModel(new DefaultComboBoxModel<Alarm>(alarms));
			cmbAlarmAction.setModel(new DefaultComboBoxModel<Alarm>(alarms));
		}else{
			cmbCurrentAlarm.setModel(new DefaultComboBoxModel<Alarm>());
			cmbAlarmAction.setModel(new DefaultComboBoxModel<Alarm>());
		}
	}

}