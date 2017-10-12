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

	private static final long serialVersionUID = 4435596811596503762L;
	private JComboBox<Led> cmbAvaliableLeds;
	private JComboBox<Led> cmbCurrentLeds;
	private UserIlumination userIlumination;
	private JComboBox<Led> cmbLedsAction;

	public UserIluminationUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});
		userIlumination = (UserIlumination) Main.getHouseInstance().getFeatureByType(UserIlumination.class);

		setLayout(null);
		setForClass(UserIlumination.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(12, 220, 438, 120);
		add(panel);
		panel.setLayout(null);

		JLabel lblLed = new JLabel("Led:");
		lblLed.setBounds(6, 35, 26, 16);
		panel.add(lblLed);

		cmbLedsAction = new JComboBox<Led>();
		cmbLedsAction.setBounds(43, 30, 370, 30);
		panel.add(cmbLedsAction);

		JButton btnSwitchOn = new JButton("Switch On");
		btnSwitchOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Led led = (Led)cmbLedsAction.getSelectedItem();
				if(led!=null){
					String[]args = {String.valueOf(led.getPin()),"1"};
					userIlumination.proceedActions(args);
					cmbAvaliableLeds.repaint();
					cmbCurrentLeds.repaint();
					cmbLedsAction.repaint();
				}
			}
		});
		btnSwitchOn.setBounds(43, 70, 110, 25);
		panel.add(btnSwitchOn);

		JButton btnSwitchOff = new JButton("Switch Off");
		btnSwitchOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Led led = (Led)cmbLedsAction.getSelectedItem();
				if(led!=null){
					String[]args = {String.valueOf(led.getPin()),"0"};
					userIlumination.proceedActions(args);
					cmbAvaliableLeds.repaint();
					cmbCurrentLeds.repaint();
					cmbLedsAction.repaint();
				}
			}
		});
		btnSwitchOff.setBounds(300, 70, 110, 25);
		panel.add(btnSwitchOff);
		
		JButton btnStateChange = new JButton("State Change");
		btnStateChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Led led = (Led)cmbLedsAction.getSelectedItem();
				if(led!=null){
					String[]args = {String.valueOf(led.getPin()),"-1"};
					userIlumination.proceedActions(args);
					cmbAvaliableLeds.repaint();
					cmbCurrentLeds.repaint();
					cmbLedsAction.repaint();
				}
			}
		});
		btnStateChange.setBounds(167, 70, 110, 25);
		panel.add(btnStateChange);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Feature Management", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel_1.setLayout(null);
		panel_1.setBounds(12, 18, 438, 171);
		add(panel_1);

		JLabel lblLedPin = new JLabel("Avaliable Leds:");
		lblLedPin.setBounds(6, 35, 101, 16);
		panel_1.add(lblLedPin);
		cmbAvaliableLeds = new JComboBox<Led>();
		updateAvaliableCombo();
		cmbAvaliableLeds.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Led led = (Led) cmbAvaliableLeds.getSelectedItem();
				if(!userIlumination.getLeds().contains(led)){
					userIlumination.getLeds().add(led);
					updateCurrentCombo();
				}
			}
		});
		cmbAvaliableLeds.setBounds(103, 30, 315, 30);
		panel_1.add(cmbAvaliableLeds);

		JLabel lblCurrentLeds = new JLabel("Current Leds:");
		lblCurrentLeds.setBounds(6, 103, 101, 16);
		panel_1.add(lblCurrentLeds);

		cmbCurrentLeds = new JComboBox<Led>();
		updateCurrentCombo();
		cmbCurrentLeds.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Led led = (Led) cmbCurrentLeds.getSelectedItem();
				led.deactivate();
				userIlumination.getLeds().remove(led);
				updateCurrentCombo();
			}
		});
		cmbCurrentLeds.setBounds(103, 95, 315, 30);
		panel_1.add(cmbCurrentLeds);

		JLabel lblWhen = new JLabel("Clicking in a current led combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 135, 412, 16);
		panel_1.add(lblWhen);
	}

	private void updateAvaliableCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(Led.class);
		Led[] leds= new Led[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			leds[i] = (Led) hardware;
			i++;
		}
		cmbAvaliableLeds.setModel(new DefaultComboBoxModel<Led>(leds));
	}

	private void updateCurrentCombo() {
		if(userIlumination.getLeds().size()>0){
			Led[] leds= new Led[userIlumination.getLeds().size()];
			int i=0;
			for (Led led : userIlumination.getLeds()) {
				leds[i] = led;
				i++;
			}
			cmbCurrentLeds.setModel(new DefaultComboBoxModel<Led>(leds));
			cmbLedsAction.setModel(new DefaultComboBoxModel<Led>(leds));
		}else{
			cmbCurrentLeds.setModel(new DefaultComboBoxModel<Led>());
			cmbLedsAction.setModel(new DefaultComboBoxModel<Led>());
		}
	}

}
