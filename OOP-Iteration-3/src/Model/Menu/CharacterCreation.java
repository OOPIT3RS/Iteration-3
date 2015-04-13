package Model.Menu;

import java.util.Observable;

import javax.swing.JComponent;

import View.CharacterCreationView;


public class CharacterCreation extends Observable {
	int selected = 0;
	String[] options = {"Smasher","Summoner","Sneak","Back"};
	CharacterCreationView cv = new CharacterCreationView(options);
	
	public CharacterCreation(){
		addObserver(cv);
	}
	
	public JComponent getView(){
		return cv;
	}
	
	public void MoveUp(){
		selected = Math.abs((selected+1)%4);
		setChanged();
		notifyObservers(options[selected]);
	}
	public void MoveDown(){
		if((--selected) < 0){
			selected = 3;
		}
		setChanged();
		notifyObservers(options[Math.abs(selected)]);
	}
	
	public String getState(){
		return options[Math.abs(selected)];
	}
	
}
