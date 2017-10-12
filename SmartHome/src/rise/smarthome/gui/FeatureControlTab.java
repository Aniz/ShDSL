package rise.smarthome.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.featureModeling.MandatoryFeature;
//import rise.smarthome.featureModeling.OptionalFeature;
import rise.smarthome.features.PanicMode;
import rise.smarthome.features.UserIlumination;

public class FeatureControlTab extends JPanel {

	private static final long serialVersionUID = 5221285834937378979L;
	private JComboBox<FeatureBase> cmbAvaliableFeature;
	private JComboBox<FeatureBase> cmbCurrentFeatures;
	private JTextField txtSerialKey;

	public FeatureControlTab() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Add Feature", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(12, 16, 438, 127);
		add(panel);
		panel.setLayout(null);

		JLabel lblFeatureType = new JLabel("Feature:");
		lblFeatureType.setBounds(12, 29, 50, 16);
		panel.add(lblFeatureType);

		cmbAvaliableFeature = new JComboBox<FeatureBase>();
		cmbAvaliableFeature.setBounds(120, 22, 300, 30);
		updateAvaliableCombo();
		panel.add(cmbAvaliableFeature);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
                    //
			public void actionPerformed(ActionEvent e) {
				FeatureBase newFeature = (FeatureBase) cmbAvaliableFeature.getSelectedItem();
				Main.getHouseInstance().addFeature(newFeature);
				updateAvaliableCombo();
				updateCurrentCombo();
				Main.updateFeaturesTabs();
			}
                        //
		});
		btnAdd.setBounds(302, 85, 117, 30);
		panel.add(btnAdd);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Unlock New Feature", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(12, 155, 438, 127);
		add(panel_1);
		panel_1.setLayout(null);

		JLabel lblFeatureKeyCode = new JLabel("Feature Key Code:");
		lblFeatureKeyCode.setBounds(6, 30, 112, 16);
		panel_1.add(lblFeatureKeyCode);

		txtSerialKey = new JTextField();
		txtSerialKey.setBounds(130, 24, 287, 28);
		panel_1.add(txtSerialKey);

		JButton btnValidate = new JButton("Validate");
		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtSerialKey.getText().equals("Panic!")){
					 Main.getHouseInstance().addAvaliableFeature(
							 PanicMode.getInstance((UserIlumination) Main.getHouseInstance().getFeatureByType(UserIlumination.class)));
					 updateAvaliableCombo();
					 JOptionPane.showMessageDialog(null, "The Panic Mode Feature are avaliable now. Enjoy!");
					 txtSerialKey.setText("");
				}
			}
		});
		btnValidate.setBounds(300, 85, 117, 29);
		panel_1.add(btnValidate);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Remove Feature", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel_2.setBounds(12, 300, 438, 100);
		add(panel_2);

		JLabel label = new JLabel("Feature:");
		label.setBounds(6, 29, 57, 16);
		panel_2.add(label);

		cmbCurrentFeatures = new JComboBox<FeatureBase>();
		cmbCurrentFeatures.setBounds(120, 22, 300, 30);
		updateCurrentCombo();
		panel_2.add(cmbCurrentFeatures);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(302, 58, 117, 29);
		btnRemove.addActionListener(new ActionListener() {
                    //
			public void actionPerformed(ActionEvent e) {
				FeatureBase featureBase = (FeatureBase) cmbCurrentFeatures.getSelectedItem();
				Main.getHouseInstance().removeFeature(featureBase);
				updateAvaliableCombo();
				updateCurrentCombo();
				Main.removeFeatureTab(featureBase.getClass());
			}
                        //
		});
		panel_2.add(btnRemove);
	}

	private void updateAvaliableCombo() {
		ArrayList<FeatureBase> featureBaseList = Main.getHouseInstance().getAvaliableFeatures();
		FeatureBase[] features= new FeatureBase[featureBaseList.size()];
		int i=0;
		for (FeatureBase feature : featureBaseList) {
			features[i] = (FeatureBase) feature;
			i++;
		}
		cmbAvaliableFeature.setModel(new DefaultComboBoxModel<FeatureBase>(features));
	}

	private void updateCurrentCombo() {
		ArrayList<FeatureBase> featureBaseList = Main.getHouseInstance().getFeatures();
		int cont=0;
		for (FeatureBase feature : featureBaseList) {
			if(!feature.getClass().isAnnotationPresent(MandatoryFeature.class)){
				cont++;
			//}else if(!feature.getClass().isAnnotationPresent(OptionalFeature.class)){
			//	cont++;
			}
		}
		FeatureBase[] features= new FeatureBase[cont];
		int i=0;
		for (FeatureBase feature : featureBaseList) {
			if(!feature.getClass().isAnnotationPresent(MandatoryFeature.class)){
				features[i] = (FeatureBase) feature;
				i++;
			//}else if(!feature.getClass().isAnnotationPresent(OptionalFeature.class)){
				
			}
		}
		if(i!=0)
			cmbCurrentFeatures.setModel(new DefaultComboBoxModel<FeatureBase>(features));
		else{
			features= new FeatureBase[0];
			cmbCurrentFeatures.setModel(new DefaultComboBoxModel<FeatureBase>(features));
		}
	}

}
