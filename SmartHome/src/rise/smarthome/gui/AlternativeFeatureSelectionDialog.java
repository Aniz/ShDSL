package {{systemName|lower}}.smarthome.gui;

import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JDialog;

import {{systemName|lower}}.smarthome.featureModeling.FeatureBase;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AlternativeFeatureSelectionDialog extends JDialog {

	private static final long serialVersionUID = -372725690685370453L;
	
	private FeatureBase selectedFeature;
	private FeatureBase featureBase1;
	private FeatureBase featureBase2;
	
	public AlternativeFeatureSelectionDialog(FeatureBase feature1, FeatureBase feature2) {
		this.featureBase1 = feature1;
		this.featureBase2 = feature2;
		setModal(true);
		setResizable(false);
		setTitle("RiSE Smart Home - Alternative Feature Selection");
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(null);
		
		Label label = new Label("An alternative to the feature you have added");
		label.setBounds(25, 10, 291, 30);
		getContentPane().add(label);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedFeature = featureBase1;
				setVisible(false);
			}
		});
		btnNewButton.setBounds(95, 93, 230, 40);
		getContentPane().add(btnNewButton);
		btnNewButton.setText(feature1.getName());
		btnNewButton.setToolTipText(feature1.getName());
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedFeature = featureBase2;
				setVisible(false);
			}
		});
		button.setBounds(95, 193, 230, 40);
		getContentPane().add(button);
		button.setText(feature2.getName());
		button.setToolTipText(feature2.getName());
		
		Label label_1 = new Label("has been previously added.");
		label_1.setBounds(25, 35, 291, 20);
		getContentPane().add(label_1);
		
		Label label_2 = new Label("Please choose one.");
		label_2.setBounds(25, 60, 291, 20);
		getContentPane().add(label_2);

	}

	public FeatureBase getSelectedFeature() {
		return selectedFeature;
	}
}
