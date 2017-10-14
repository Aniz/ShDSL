package rise.smarthome.featuresUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import rise.smarthome.features.PanicMode;
import rise.smarthome.gui.Main;

public class PanicModeUI extends FeatureUIBase {

	private static final long serialVersionUID = 4435596811596503762L;
	private JToggleButton tglStartStop;
	private PanicMode panicMode;

	public PanicModeUI() {
		panicMode = (PanicMode) Main.getHouseInstance().getFeatureByType(PanicMode.class);

		setLayout(null);
		setForClass(PanicMode.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(6, 77, 438, 90);
		add(panel);
		panel.setLayout(null);

		tglStartStop = new JToggleButton("Start Feature");
		tglStartStop.setBounds(133, 35, 161, 29);
		panel.add(tglStartStop);

		tglStartStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tglStartStop.isSelected()){
					tglStartStop.setText("Stop Feature");
					String commandArray[] = {"1"};
					panicMode.proceedActions(commandArray);
					//panicMode.proceedActions(null);
				}else{
					tglStartStop.setText("Start Feature");
					String commandArray[] = {"0"};
					panicMode.proceedActions(commandArray);
				
				}
			}
		});
	}

}