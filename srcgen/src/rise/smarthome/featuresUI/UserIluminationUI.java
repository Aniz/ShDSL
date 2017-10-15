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
import rise.smarthome.features.UserIlumination;

import rise.smarthome.gui.Main;
import rise.smarthome.model.devices.Hardware;
import rise.smarthome.model.devices.Led;

public class UserIluminationUI extends FeatureUIBase {

    private static final long serialVersionUID = 5575292698449566428L;
	
    private UserIlumination userilumination;
	private JComboBox<Led> cmbAvaliableLed;
	private JComboBox<Led> cmbCurrentLed;
	private JComboBox<Led> cmbLedAction;

	public UserIluminationUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});
		userilumination = (UserIlumination) Main.getHouseInstance().getFeatureByType(UserIlumination.class);

		setLayout(null);
		setForClass(UserIlumination.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel.setBounds(12, 220, 438, 120);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblLed = new JLabel("Led:");
		lblLed.setBounds(6, 35, 26, 16);
		panel.add(lblLed);

		cmbLedAction = new JComboBox<Led>();
		cmbLedAction.setBounds(43, 30, 370, 30);
		panel.add(cmbLedAction);

		JButton btnSwitchOn = new JButton("Switch On");
		btnSwitchOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Led alarm = (Led)cmbLedAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"1"};
					userilumination.proceedActions(args);
					cmbAvaliableLed.repaint();
					cmbCurrentLed.repaint();
					cmbLedAction.repaint();
				}
			}
		});
		btnSwitchOn.setBounds(43, 70, 110, 25);
		panel.add(btnSwitchOn);

		JButton btnSwitchOff = new JButton("Switch Off");
		btnSwitchOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Led alarm = (Led)cmbLedAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"0"};
					userilumination.proceedActions(args);
					cmbAvaliableLed.repaint();
					cmbCurrentLed.repaint();
					cmbLedAction.repaint();
				}
			}
		});
		btnSwitchOff.setBounds(300, 70, 110, 25);
		panel.add(btnSwitchOff);
		
		JButton btnStateChange = new JButton("State Change");
		btnStateChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Led alarm = (Led)cmbLedAction.getSelectedItem();
				if(alarm!=null){
					String[]args = {String.valueOf(alarm.getPin()),"-1"};
					userilumination.proceedActions(args);
					cmbAvaliableLed.repaint();
					cmbCurrentLed.repaint();
					cmbLedAction.repaint();
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

		JLabel lblLedPin = new JLabel("Avaliable Leds:");
		lblLedPin.setBounds(6, 35, 101, 16);
		panel_1.add(lblLedPin);
		cmbAvaliableLed = new JComboBox<Led>();
		updateAvaliableCombo();
		cmbAvaliableLed.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Led alarm = (Led) cmbAvaliableLed.getSelectedItem();
				if(!userilumination.getLeds().contains(alarm)){
					userilumination.getLeds().add(alarm);
					updateCurrentCombo();
				}
			}
		});
		cmbAvaliableLed.setBounds(110, 30, 308, 30);
		panel_1.add(cmbAvaliableLed);

		JLabel lblCurrentAirConditioners = new JLabel("Current Leds:");
		lblCurrentAirConditioners.setBounds(6, 103, 101, 16);
		panel_1.add(lblCurrentAirConditioners);

		cmbCurrentLed = new JComboBox<Led>();
		updateCurrentCombo();
		cmbCurrentLed.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Led alarm = (Led) cmbCurrentLed.getSelectedItem();
				alarm.deactivate();
				userilumination.getLeds().remove(alarm);
				updateCurrentCombo();
			}
		});
		cmbCurrentLed.setBounds(110, 95, 308, 30);
		panel_1.add(cmbCurrentLed);

		JLabel lblWhen = new JLabel("Clicking in a current Led combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 135, 412, 16);
		panel_1.add(lblWhen);
	}

	private void updateAvaliableCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(Led.class);
		Led[] alarms= new Led[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			alarms[i] = (Led) hardware;
			i++;
		}
		cmbAvaliableLed.setModel(new DefaultComboBoxModel<Led>(alarms));
	}

	private void updateCurrentCombo() {
		if(userilumination.getLeds().size()>0){
			Led[] alarms= new Led[userilumination.getLeds().size()];
			int i=0;
			for (Led alarm : userilumination.getLeds()) {
				alarms[i] = alarm;
				i++;
			}
			cmbCurrentLed.setModel(new DefaultComboBoxModel<Led>(alarms));
			cmbLedAction.setModel(new DefaultComboBoxModel<Led>(alarms));
		}else{
			cmbCurrentLed.setModel(new DefaultComboBoxModel<Led>());
			cmbLedAction.setModel(new DefaultComboBoxModel<Led>());
		}
	}

}