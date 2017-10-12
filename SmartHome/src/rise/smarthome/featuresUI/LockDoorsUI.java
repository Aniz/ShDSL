              
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

    private static final long serialVersionUID = 4435596811596503762L;
	
    private LockDoors lockDoors;
	private JComboBox<AutomaticDoor> cmbAvaliableDoors;
	private JComboBox<AutomaticDoor> cmbCurrentDoors;
	private JComboBox<AutomaticDoor> cmbDoorsAction;

	public LockDoorsUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});
		lockDoors = (LockDoors) Main.getHouseInstance().getFeatureByType(LockDoors.class);

		setLayout(null);
		setForClass(LockDoors.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel.setBounds(12, 220, 438, 120);
		add(panel);
		panel.setLayout(null);

		JLabel lblWindow = new JLabel("Door :");
		lblWindow.setBounds(6, 35, 150, 16);
		panel.add(lblWindow);

		cmbDoorsAction = new JComboBox<AutomaticDoor>();
		cmbDoorsAction.setBounds(108, 30, 300, 30);
		panel.add(cmbDoorsAction);

		JButton btnSwitchOn = new JButton("Switch On");
		btnSwitchOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AutomaticDoor automaticDoor = (AutomaticDoor)cmbDoorsAction.getSelectedItem();
				if(automaticDoor!=null){
					String[]args = {String.valueOf(automaticDoor.getPin()),"1"};
					lockDoors.proceedActions(args);
					cmbAvaliableDoors.repaint();
					cmbCurrentDoors.repaint();
					cmbDoorsAction.repaint();
				}
			}
		});
		btnSwitchOn.setBounds(43, 70, 110, 25);
		panel.add(btnSwitchOn);

		JButton btnSwitchOff = new JButton("Switch Off");
		btnSwitchOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AutomaticDoor automaticWindow = (AutomaticDoor)cmbDoorsAction.getSelectedItem();
				if(automaticWindow!=null){
					String[]args = {String.valueOf(automaticWindow.getPin()),"0"};
					lockDoors.proceedActions(args);
					cmbAvaliableDoors.repaint();
					cmbCurrentDoors.repaint();
					cmbDoorsAction.repaint();
				}
			}
		});
		btnSwitchOff.setBounds(300, 70, 110, 25);
		panel.add(btnSwitchOff);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Feature Management", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel_1.setLayout(null);
		panel_1.setBounds(6, 6, 438, 171);
		add(panel_1);

		JLabel lblAutomaticWindowPin = new JLabel("Avaliable Doors:");
		lblAutomaticWindowPin.setBounds(6, 35, 180, 16);
		panel_1.add(lblAutomaticWindowPin);
		cmbAvaliableDoors = new JComboBox<AutomaticDoor>();
		updateAvaliableCombo();
		cmbAvaliableDoors.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticDoor automaticWindow = (AutomaticDoor) cmbAvaliableDoors.getSelectedItem();
				if(!lockDoors.getautomaticDoors().contains(automaticWindow)){
					lockDoors.getautomaticDoors().add(automaticWindow);
					updateCurrentCombo();
				}
			}
		});
		cmbAvaliableDoors.setBounds(170, 30, 250, 30);
		panel_1.add(cmbAvaliableDoors);

		JLabel lblCurrentWindows = new JLabel("Current Doors:");
		lblCurrentWindows.setBounds(6, 96, 180, 16);
		panel_1.add(lblCurrentWindows);

		cmbCurrentDoors = new JComboBox<AutomaticDoor>();
		updateCurrentCombo();
		cmbCurrentDoors.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticDoor automaticDoor = (AutomaticDoor) cmbCurrentDoors.getSelectedItem();
				automaticDoor.deactivate();
				lockDoors.getautomaticDoors().remove(automaticDoor);
				updateCurrentCombo();
			}
		});
		cmbCurrentDoors.setBounds(170, 95, 250, 30);
		panel_1.add(cmbCurrentDoors);

		JLabel lblWhen = new JLabel("Clicking in a current Window combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 135, 412, 16);
		panel_1.add(lblWhen);
	}

	private void updateAvaliableCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(AutomaticDoor.class);
		AutomaticDoor[] automaticDoors= new AutomaticDoor[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			automaticDoors[i] = (AutomaticDoor) hardware;
			i++;
		}
		cmbAvaliableDoors.setModel(new DefaultComboBoxModel<AutomaticDoor>(automaticDoors));
	}

	private void updateCurrentCombo() {
		if(lockDoors.getautomaticDoors().size()>0){
			AutomaticDoor[] automaticDoors= new AutomaticDoor[lockDoors.getautomaticDoors().size()];
			int i=0;
			for (AutomaticDoor automaticDoor : lockDoors.getautomaticDoors()) {
				automaticDoors[i] = automaticDoor;
				i++;
			}
			cmbCurrentDoors.setModel(new DefaultComboBoxModel<AutomaticDoor>(automaticDoors));
			cmbDoorsAction.setModel(new DefaultComboBoxModel<AutomaticDoor>(automaticDoors));
		}else{
			cmbCurrentDoors.setModel(new DefaultComboBoxModel<AutomaticDoor>());
			cmbDoorsAction.setModel(new DefaultComboBoxModel<AutomaticDoor>());
		}
	}

}
