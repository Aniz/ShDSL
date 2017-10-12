package rise.smarthome.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import rise.smarthome.arduino.ArduinoControl;
import rise.smarthome.featureModeling.FeatureBase;
import rise.smarthome.features.AlarmAgainstRobbery;
import rise.smarthome.features.AutomatedAirConditionerControl;
import rise.smarthome.features.AutomatedIluminationByLuminosity;
import rise.smarthome.features.AutomatedIluminationByPresence;
import rise.smarthome.features.AutomatedWindowControl;
import rise.smarthome.features.LockDoors;
import rise.smarthome.features.PanicMode;
import rise.smarthome.features.PresenceIlusion;
import rise.smarthome.features.UserAirConditionerControl;
import rise.smarthome.features.UserIlumination;
import rise.smarthome.features.UserWindowControl;
import rise.smarthome.featuresUI.AlarmAgainstRobberyUI;
import rise.smarthome.featuresUI.AutomatedAirConditionerControlUI;
import rise.smarthome.featuresUI.AutomatedIluminationByLuminosityUI;
import rise.smarthome.featuresUI.AutomatedIluminationByPresenceUI;
import rise.smarthome.featuresUI.AutomatedWindowControlUI;
import rise.smarthome.featuresUI.FeatureUIBase;
import rise.smarthome.featuresUI.LockDoorsUI;
import rise.smarthome.featuresUI.PanicModeUI;
import rise.smarthome.featuresUI.PresenceIlusionUI;
import rise.smarthome.featuresUI.UserAirConditionerControlUI;
import rise.smarthome.featuresUI.UserIluminationUI;
import rise.smarthome.featuresUI.UserWindowControlUI;
import rise.smarthome.model.home.HouseFacade;

public class Main extends JFrame {

	private static final long serialVersionUID = -5521585541646467250L;
	private static HouseFacade house;
	private static JPanel contentPane;
	private static JTabbedPane tabbedPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArduinoControl.getInstance();
					house = new HouseFacade();
					Main frame = new Main();
					updateFeaturesTabs();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
        

	public static void updateFeaturesTabs(){
		for (FeatureBase feature : house.getFeatures()) {
			
			if(feature instanceof PresenceIlusion){
				boolean alreadyExist = false;
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
						FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
						if(featureTab.isForClass(PresenceIlusion.class)){
							alreadyExist = true;
							break;
						}
					}
				}
				if(!alreadyExist){
					PresenceIlusionUI presenceIlusionUI = new PresenceIlusionUI();
					tabbedPane.addTab("Presence Illusion", null, presenceIlusionUI);
				}
			}
			
			if(feature instanceof PanicMode){
				boolean alreadyExist = false;
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
						FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
						if(featureTab.isForClass(PanicMode.class)){
							alreadyExist = true;
							break;
						}
					}
				}
				if(!alreadyExist){
					PanicModeUI panicModeUI = new PanicModeUI();
					tabbedPane.addTab("Panic Mode", null, panicModeUI);
				}
			}
			
			if(feature instanceof UserIlumination){
				boolean alreadyExist = false;
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
						FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
						if(featureTab.isForClass(UserIlumination.class)){
							alreadyExist = true;
							break;
						}
					}
				}
				if(!alreadyExist){
					UserIluminationUI userIluminationUI = new UserIluminationUI();
					tabbedPane.addTab("User Illumination", null, userIluminationUI);
				}
			}
			
			if(feature instanceof AutomatedIluminationByLuminosity){
				boolean alreadyExist = false;
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
						FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
						if(featureTab.isForClass(AutomatedIluminationByLuminosity.class)){
							alreadyExist = true;
							break;
						}
					}
				}
				if(!alreadyExist){
					AutomatedIluminationByLuminosityUI automatedIluminationByLuminosityUI = new AutomatedIluminationByLuminosityUI();
					tabbedPane.addTab("Automated Illumination by Luminosity", null, automatedIluminationByLuminosityUI);
				}
			}
			
			if(feature instanceof AutomatedIluminationByPresence){
				boolean alreadyExist = false;
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
						FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
						if(featureTab.isForClass(AutomatedIluminationByPresence.class)){
							alreadyExist = true;
							break;
						}
					}
				}
				if(!alreadyExist){
					AutomatedIluminationByPresenceUI automatedIluminationByPresenceUI = new AutomatedIluminationByPresenceUI();
					tabbedPane.addTab("Automated Illumination by Presence", null, automatedIluminationByPresenceUI);
				}
			}
                        
                       
                        if(feature instanceof AlarmAgainstRobbery){
				boolean alreadyExist = false;
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
						FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
						if(featureTab.isForClass(AlarmAgainstRobbery.class)){
							alreadyExist = true;
							break;
						}
					}
				}
				if(!alreadyExist){
					AlarmAgainstRobberyUI alarmAgainstRobberyUI = new AlarmAgainstRobberyUI();
					tabbedPane.addTab("Alarm Against Robbery", null, alarmAgainstRobberyUI);
				}
			}
                        
                        if(feature instanceof LockDoors){
				boolean alreadyExist = false;
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
						FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
						if(featureTab.isForClass(LockDoors.class)){
							alreadyExist = true;
							break;
						}
					}
				}
				if(!alreadyExist){
					LockDoorsUI lockDoorsUI = new LockDoorsUI();
					tabbedPane.addTab("Lock Doors", null, lockDoorsUI);
				}
			}
                        
                        if(feature instanceof AutomatedAirConditionerControl){
				boolean alreadyExist = false;
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
						FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
						if(featureTab.isForClass(AutomatedAirConditionerControl.class)){
							alreadyExist = true;
							break;
						}
					}
				}
				if(!alreadyExist){
					AutomatedAirConditionerControlUI automatedAirConditionerControlUI = new AutomatedAirConditionerControlUI();
					tabbedPane.addTab("Automated AirConditioner Control", null, automatedAirConditionerControlUI);
				}
			}
                       
                        if(feature instanceof AutomatedWindowControl){
				boolean alreadyExist = false;
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
						FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
						if(featureTab.isForClass(AutomatedWindowControl.class)){
							alreadyExist = true;
							break;
						}
					}
				}
				if(!alreadyExist){
					AutomatedWindowControlUI automatedWindowControlUI = new AutomatedWindowControlUI();
					tabbedPane.addTab("Automated Window Control", null, automatedWindowControlUI);
				}
			}
                       
			
			if(feature instanceof  UserAirConditionerControl){
				boolean alreadyExist = false;
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
						FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
						if(featureTab.isForClass( UserAirConditionerControl.class)){
							alreadyExist = true;
							break;
						}
					}
				}
				
				if(!alreadyExist){
					 UserAirConditionerControlUI mobileDataNetworkUI = new  UserAirConditionerControlUI();
					tabbedPane.addTab("User AirConditioner Control", null, mobileDataNetworkUI);
				}
			}
			
			if(feature instanceof UserWindowControl){
				boolean alreadyExist = false;
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
						FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
						if(featureTab.isForClass(UserWindowControl.class)){
							alreadyExist = true;
							break;
						}
					}
				}
				
				if(!alreadyExist){
					UserWindowControlUI userWindowControlUI = new UserWindowControlUI();
					tabbedPane.addTab("User Window Control", null, userWindowControlUI);
				}
			}
			
		}
	}

	public static HouseFacade getHouseInstance(){
		return house;
	}
	public Main() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SmartHomeRiSE - Mission Control");
		setBounds(100, 100, 500, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 466, 490);
		FeatureControlTab generalTab = new FeatureControlTab();
		tabbedPane.addTab("Feature Control", null, generalTab);
		HardwareControlTab hardwareControlTab = new HardwareControlTab();
		tabbedPane.addTab("Hardware Control", null, hardwareControlTab);
		contentPane.add(tabbedPane);

	}

	public static void removeFeatureTab(Class<? extends FeatureBase> clazz) {
		
            if(clazz.equals(AutomatedIluminationByLuminosity.class)){
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
					FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
					if(featureTab.isForClass(AutomatedIluminationByLuminosity.class)){
						tabbedPane.removeTabAt(i);
					}
				}
			}
		}
                
                if(clazz.equals(AutomatedIluminationByPresence.class)){
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
					FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
					if(featureTab.isForClass(AutomatedIluminationByPresence.class)){
						tabbedPane.removeTabAt(i);
					}
				}
			}
		}
		
		if(clazz.equals(UserAirConditionerControl.class)){
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
					FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
					if(featureTab.isForClass(UserAirConditionerControl.class)){
						tabbedPane.removeTabAt(i);
					}
				}
			}
		}
		
		if(clazz.equals(UserWindowControl.class)){
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
					FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
					if(featureTab.isForClass(UserWindowControl.class)){
						tabbedPane.removeTabAt(i);
					}
				}
			}
		}   
                
                if(clazz.equals(AlarmAgainstRobbery.class)){
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
					FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
					if(featureTab.isForClass(AlarmAgainstRobbery.class)){
						tabbedPane.removeTabAt(i);
					}
				}
			}
		}
                
                if(clazz.equals(PanicMode.class)){
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
					FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
					if(featureTab.isForClass(PanicMode.class)){
						tabbedPane.removeTabAt(i);
					}
				}
			}
		}
           
                
                if(clazz.equals(AutomatedWindowControl.class)){
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
					FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
					if(featureTab.isForClass(AutomatedWindowControl.class)){
						tabbedPane.removeTabAt(i);
					}
				}
			}
		}
               
                if(clazz.equals(AutomatedAirConditionerControl.class)){
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				if(tabbedPane.getComponentAt(i) instanceof FeatureUIBase){
					FeatureUIBase featureTab = (FeatureUIBase) tabbedPane.getComponentAt(i);
					if(featureTab.isForClass(AutomatedAirConditionerControl.class)){
						tabbedPane.removeTabAt(i);
					}
				}
			}
		}
                
		
	}
}
