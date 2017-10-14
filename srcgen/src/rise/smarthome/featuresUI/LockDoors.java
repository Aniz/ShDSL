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
import rise.smarthome.features.LockDoors;

import rise.smarthome.gui.Main;
import rise.smarthome.model.devices.Hardware;
import rise.smarthome.model.devices.AutomaticDoor;

public class LockDoorsUI extends FeatureUIBase {

    private static final long serialVersionUID = 5575292698449566428L;
	
    private LockDoors lockdoors;
	private JComboBox<AutomaticDoor> cmbAvaliableAutomaticDoor;
	private JComboBox<AutomaticDoor> cmbCurrentAutomaticDoor;
	private JComboBox<AutomaticDoor> cmbAutomaticDoorAction;

	public LockDoorsUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});
		lockdoors = (LockDoors) Main.getHouseInstance().getFeatureByType(LockDoors.class);

		setLayout(null);
		setForClass(LockDoors.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel.setBounds(12, 220, 438, 120);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblLed = new JLabel("Led:");
		lblLed.setBounds(6, 35, 26, 16);
		panel.add(lblLed);

		cmbAutomaticDoorAction = new JComboBox<AutomaticDoor>();
		cmbAutomaticDoorAction.setBounds(43, 30, 370, 30);
		panel.add(cmbAutomaticDoorAction);

		JButton btnSwitchOn = new JButton("Switch On");
		btnSwitchOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AutomaticDoor alarm = (AutomaticDoor)cmbAutomaticDoorAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"1"};
					lockdoors.proceedActions(args);
					cmbAvaliableAutomaticDoor.repaint();
					cmbCurrentAutomaticDoor.repaint();
					cmbAutomaticDoorAction.repaint();
				}
			}
		});
		btnSwitchOn.setBounds(43, 70, 110, 25);
		panel.add(btnSwitchOn);

		JButton btnSwitchOff = new JButton("Switch Off");
		btnSwitchOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AutomaticDoor alarm = (AutomaticDoor)cmbAutomaticDoorAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"0"};
					lockdoors.proceedActions(args);
					cmbAvaliableAutomaticDoor.repaint();
					cmbCurrentAutomaticDoor.repaint();
					cmbAutomaticDoorAction.repaint();
				}
			}
		});
		btnSwitchOff.setBounds(300, 70, 110, 25);
		panel.add(btnSwitchOff);
		
		JButton btnStateChange = new JButton("State Change");
		btnStateChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AutomaticDoor alarm = (AutomaticDoor)cmbAutomaticDoorAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"-1"};
					lockdoors.proceedActions(args);
					cmbAvaliableAutomaticDoor.repaint();
					cmbCurrentAutomaticDoor.repaint();
					cmbAutomaticDoorAction.repaint();
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

		JLabel lblAutomaticDoorPin = new JLabel("Avaliable AutomaticDoors:");
		lblAutomaticDoorPin.setBounds(6, 35, 101, 16);
		panel_1.add(lblAutomaticDoorPin);
		cmbAvaliableAutomaticDoor = new JComboBox<AutomaticDoor>();
		updateAvaliableCombo();
		cmbAvaliableAutomaticDoor.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticDoor alarm = (AutomaticDoor) cmbAvaliableAutomaticDoor.getSelectedItem();
				if(!lockdoors.getAutomaticDoors().contains(alarm)){
					lockdoors.getAutomaticDoors().add(alarm);
					updateCurrentCombo();
				}
			}
		});
		cmbAvaliableAutomaticDoor.setBounds(110, 30, 308, 30);
		panel_1.add(cmbAvaliableAutomaticDoor);

		JLabel lblCurrentAirConditioners = new JLabel("Current AutomaticDoors:");
		lblCurrentAirConditioners.setBounds(6, 103, 101, 16);
		panel_1.add(lblCurrentAirConditioners);

		cmbCurrentAutomaticDoor = new JComboBox<AutomaticDoor>();
		updateCurrentCombo();
		cmbCurrentAutomaticDoor.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticDoor alarm = (AutomaticDoor) cmbCurrentAutomaticDoor.getSelectedItem();
				alarm.deactivate();
				lockdoors.getAutomaticDoors().remove(alarm);
				updateCurrentCombo();
			}
		});
		cmbCurrentAutomaticDoor.setBounds(110, 95, 308, 30);
		panel_1.add(cmbCurrentAutomaticDoor);

		JLabel lblWhen = new JLabel("Clicking in a current AutomaticDoor combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 135, 412, 16);
		panel_1.add(lblWhen);
	}

	private void updateAvaliableCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(AutomaticDoor.class);
		AutomaticDoor[] alarms= new AutomaticDoor[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			alarms[i] = (AutomaticDoor) hardware;
			i++;
		}
		cmbAvaliableAutomaticDoor.setModel(new DefaultComboBoxModel<AutomaticDoor>(alarms));
	}

	private void updateCurrentCombo() {
		if(lockdoors.getAutomaticDoors().size()>0){
			AutomaticDoor[] alarms= new AutomaticDoor[lockdoors.getAutomaticDoors().size()];
			int i=0;
			for (AutomaticDoor alarm : lockdoors.getAutomaticDoors()) {
				alarms[i] = alarm;
				i++;
			}
			cmbCurrentAutomaticDoor.setModel(new DefaultComboBoxModel<AutomaticDoor>(alarms));
			cmbAutomaticDoorAction.setModel(new DefaultComboBoxModel<AutomaticDoor>(alarms));
		}else{
			cmbCurrentAutomaticDoor.setModel(new DefaultComboBoxModel<AutomaticDoor>());
			cmbAutomaticDoorAction.setModel(new DefaultComboBoxModel<AutomaticDoor>());
		}
	}

}