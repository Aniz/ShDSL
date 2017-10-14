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
import rise.smarthome.features.UserWindowControl;

import rise.smarthome.gui.Main;
import rise.smarthome.model.devices.Hardware;
import rise.smarthome.model.devices.AutomaticWindow;

public class UserWindowControlUI extends FeatureUIBase {

    private static final long serialVersionUID = 5575292698449566428L;
	
    private UserWindowControl userwindowcontrol;
	private JComboBox<AutomaticWindow> cmbAvaliableAutomaticWindow;
	private JComboBox<AutomaticWindow> cmbCurrentAutomaticWindow;
	private JComboBox<AutomaticWindow> cmbAutomaticWindowAction;

	public UserWindowControlUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});
		userwindowcontrol = (UserWindowControl) Main.getHouseInstance().getFeatureByType(UserWindowControl.class);

		setLayout(null);
		setForClass(UserWindowControl.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel.setBounds(12, 220, 438, 120);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblLed = new JLabel("Led:");
		lblLed.setBounds(6, 35, 26, 16);
		panel.add(lblLed);

		cmbAutomaticWindowAction = new JComboBox<AutomaticWindow>();
		cmbAutomaticWindowAction.setBounds(43, 30, 370, 30);
		panel.add(cmbAutomaticWindowAction);

		JButton btnSwitchOn = new JButton("Switch On");
		btnSwitchOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AutomaticWindow alarm = (AutomaticWindow)cmbAutomaticWindowAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"1"};
					userwindowcontrol.proceedActions(args);
					cmbAvaliableAutomaticWindow.repaint();
					cmbCurrentAutomaticWindow.repaint();
					cmbAutomaticWindowAction.repaint();
				}
			}
		});
		btnSwitchOn.setBounds(43, 70, 110, 25);
		panel.add(btnSwitchOn);

		JButton btnSwitchOff = new JButton("Switch Off");
		btnSwitchOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AutomaticWindow alarm = (AutomaticWindow)cmbAutomaticWindowAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"0"};
					userwindowcontrol.proceedActions(args);
					cmbAvaliableAutomaticWindow.repaint();
					cmbCurrentAutomaticWindow.repaint();
					cmbAutomaticWindowAction.repaint();
				}
			}
		});
		btnSwitchOff.setBounds(300, 70, 110, 25);
		panel.add(btnSwitchOff);
		
		JButton btnStateChange = new JButton("State Change");
		btnStateChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AutomaticWindow alarm = (AutomaticWindow)cmbAutomaticWindowAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"-1"};
					userwindowcontrol.proceedActions(args);
					cmbAvaliableAutomaticWindow.repaint();
					cmbCurrentAutomaticWindow.repaint();
					cmbAutomaticWindowAction.repaint();
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

		JLabel lblAutomaticWindowPin = new JLabel("Avaliable AutomaticWindows:");
		lblAutomaticWindowPin.setBounds(6, 35, 101, 16);
		panel_1.add(lblAutomaticWindowPin);
		cmbAvaliableAutomaticWindow = new JComboBox<AutomaticWindow>();
		updateAvaliableCombo();
		cmbAvaliableAutomaticWindow.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticWindow alarm = (AutomaticWindow) cmbAvaliableAutomaticWindow.getSelectedItem();
				if(!userwindowcontrol.getAutomaticWindows().contains(alarm)){
					userwindowcontrol.getAutomaticWindows().add(alarm);
					updateCurrentCombo();
				}
			}
		});
		cmbAvaliableAutomaticWindow.setBounds(110, 30, 308, 30);
		panel_1.add(cmbAvaliableAutomaticWindow);

		JLabel lblCurrentAirConditioners = new JLabel("Current AutomaticWindows:");
		lblCurrentAirConditioners.setBounds(6, 103, 101, 16);
		panel_1.add(lblCurrentAirConditioners);

		cmbCurrentAutomaticWindow = new JComboBox<AutomaticWindow>();
		updateCurrentCombo();
		cmbCurrentAutomaticWindow.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticWindow alarm = (AutomaticWindow) cmbCurrentAutomaticWindow.getSelectedItem();
				alarm.deactivate();
				userwindowcontrol.getAutomaticWindows().remove(alarm);
				updateCurrentCombo();
			}
		});
		cmbCurrentAutomaticWindow.setBounds(110, 95, 308, 30);
		panel_1.add(cmbCurrentAutomaticWindow);

		JLabel lblWhen = new JLabel("Clicking in a current AutomaticWindow combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 135, 412, 16);
		panel_1.add(lblWhen);
	}

	private void updateAvaliableCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(AutomaticWindow.class);
		AutomaticWindow[] alarms= new AutomaticWindow[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			alarms[i] = (AutomaticWindow) hardware;
			i++;
		}
		cmbAvaliableAutomaticWindow.setModel(new DefaultComboBoxModel<AutomaticWindow>(alarms));
	}

	private void updateCurrentCombo() {
		if(userwindowcontrol.getAutomaticWindows().size()>0){
			AutomaticWindow[] alarms= new AutomaticWindow[userwindowcontrol.getAutomaticWindows().size()];
			int i=0;
			for (AutomaticWindow alarm : userwindowcontrol.getAutomaticWindows()) {
				alarms[i] = alarm;
				i++;
			}
			cmbCurrentAutomaticWindow.setModel(new DefaultComboBoxModel<AutomaticWindow>(alarms));
			cmbAutomaticWindowAction.setModel(new DefaultComboBoxModel<AutomaticWindow>(alarms));
		}else{
			cmbCurrentAutomaticWindow.setModel(new DefaultComboBoxModel<AutomaticWindow>());
			cmbAutomaticWindowAction.setModel(new DefaultComboBoxModel<AutomaticWindow>());
		}
	}

}