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
import rise.smarthome.features.UserAirConditionerControl;

import rise.smarthome.gui.Main;
import rise.smarthome.model.devices.Hardware;
import rise.smarthome.model.devices.AirConditioner;

public class UserAirConditionerControlUI extends FeatureUIBase {

    private static final long serialVersionUID = 5575292698449566428L;
	
    private UserAirConditionerControl userairconditionercontrol;
	private JComboBox<AirConditioner> cmbAvaliableAirConditioner;
	private JComboBox<AirConditioner> cmbCurrentAirConditioner;
	private JComboBox<AirConditioner> cmbAirConditionerAction;

	public UserAirConditionerControlUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});
		userairconditionercontrol = (UserAirConditionerControl) Main.getHouseInstance().getFeatureByType(UserAirConditionerControl.class);

		setLayout(null);
		setForClass(UserAirConditionerControl.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel.setBounds(12, 220, 438, 120);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblLed = new JLabel("Led:");
		lblLed.setBounds(6, 35, 26, 16);
		panel.add(lblLed);

		cmbAirConditionerAction = new JComboBox<AirConditioner>();
		cmbAirConditionerAction.setBounds(43, 30, 370, 30);
		panel.add(cmbAirConditionerAction);

		JButton btnSwitchOn = new JButton("Switch On");
		btnSwitchOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AirConditioner alarm = (AirConditioner)cmbAirConditionerAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"1"};
					userairconditionercontrol.proceedActions(args);
					cmbAvaliableAirConditioner.repaint();
					cmbCurrentAirConditioner.repaint();
					cmbAirConditionerAction.repaint();
				}
			}
		});
		btnSwitchOn.setBounds(43, 70, 110, 25);
		panel.add(btnSwitchOn);

		JButton btnSwitchOff = new JButton("Switch Off");
		btnSwitchOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AirConditioner alarm = (AirConditioner)cmbAirConditionerAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"0"};
					userairconditionercontrol.proceedActions(args);
					cmbAvaliableAirConditioner.repaint();
					cmbCurrentAirConditioner.repaint();
					cmbAirConditionerAction.repaint();
				}
			}
		});
		btnSwitchOff.setBounds(300, 70, 110, 25);
		panel.add(btnSwitchOff);
		
		JButton btnStateChange = new JButton("State Change");
		btnStateChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AirConditioner alarm = (AirConditioner)cmbAirConditionerAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"-1"};
					userairconditionercontrol.proceedActions(args);
					cmbAvaliableAirConditioner.repaint();
					cmbCurrentAirConditioner.repaint();
					cmbAirConditionerAction.repaint();
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

		JLabel lblAirConditionerPin = new JLabel("Avaliable AirConditioners:");
		lblAirConditionerPin.setBounds(6, 35, 101, 16);
		panel_1.add(lblAirConditionerPin);
		cmbAvaliableAirConditioner = new JComboBox<AirConditioner>();
		updateAvaliableCombo();
		cmbAvaliableAirConditioner.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AirConditioner alarm = (AirConditioner) cmbAvaliableAirConditioner.getSelectedItem();
				if(!userairconditionercontrol.getAirConditioners().contains(alarm)){
					userairconditionercontrol.getAirConditioners().add(alarm);
					updateCurrentCombo();
				}
			}
		});
		cmbAvaliableAirConditioner.setBounds(110, 30, 308, 30);
		panel_1.add(cmbAvaliableAirConditioner);

		JLabel lblCurrentAirConditioners = new JLabel("Current AirConditioners:");
		lblCurrentAirConditioners.setBounds(6, 103, 101, 16);
		panel_1.add(lblCurrentAirConditioners);

		cmbCurrentAirConditioner = new JComboBox<AirConditioner>();
		updateCurrentCombo();
		cmbCurrentAirConditioner.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AirConditioner alarm = (AirConditioner) cmbCurrentAirConditioner.getSelectedItem();
				alarm.deactivate();
				userairconditionercontrol.getAirConditioners().remove(alarm);
				updateCurrentCombo();
			}
		});
		cmbCurrentAirConditioner.setBounds(110, 95, 308, 30);
		panel_1.add(cmbCurrentAirConditioner);

		JLabel lblWhen = new JLabel("Clicking in a current AirConditioner combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 135, 412, 16);
		panel_1.add(lblWhen);
	}

	private void updateAvaliableCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(AirConditioner.class);
		AirConditioner[] alarms= new AirConditioner[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			alarms[i] = (AirConditioner) hardware;
			i++;
		}
		cmbAvaliableAirConditioner.setModel(new DefaultComboBoxModel<AirConditioner>(alarms));
	}

	private void updateCurrentCombo() {
		if(userairconditionercontrol.getAirConditioners().size()>0){
			AirConditioner[] alarms= new AirConditioner[userairconditionercontrol.getAirConditioners().size()];
			int i=0;
			for (AirConditioner alarm : userairconditionercontrol.getAirConditioners()) {
				alarms[i] = alarm;
				i++;
			}
			cmbCurrentAirConditioner.setModel(new DefaultComboBoxModel<AirConditioner>(alarms));
			cmbAirConditionerAction.setModel(new DefaultComboBoxModel<AirConditioner>(alarms));
		}else{
			cmbCurrentAirConditioner.setModel(new DefaultComboBoxModel<AirConditioner>());
			cmbAirConditionerAction.setModel(new DefaultComboBoxModel<AirConditioner>());
		}
	}

}