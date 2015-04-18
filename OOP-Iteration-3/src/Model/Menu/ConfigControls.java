package Model.Menu;

import java.util.Map.Entry;
import java.util.Observable;

import Controller.RunGame;
import View.ConfigControlsView;

public class ConfigControls extends Observable {
	int selected = 0;
	String[] options = new String[RunGame.getKeys().size()];
	ConfigControlsView cv;
	
	public ConfigControls(){
		int i = 0;
		for(Entry<String, Integer> entry: RunGame.getKeys().entrySet()){
   			options[i++] = entry.getKey();
   		 }
		cv = new ConfigControlsView(options);
		addObserver(cv);
	}
	
	public ConfigControlsView getView(){
		return cv;
	}
	
	public void MoveUp(){
		selected = Math.abs((selected+1)%options.length);
		setChanged();
		notifyObservers(options[selected]);
	}
	public void MoveDown(){
		if((--selected) < 0){
			selected = options.length-1;
		}
		setChanged();
		notifyObservers(options[Math.abs(selected)]);
	}
	
	public String getState(){
		return options[Math.abs(selected)];
	}
}
