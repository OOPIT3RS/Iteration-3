package Model.Entity;



import Model.Entity.Ability.*;
import Model.Items.UsableItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public abstract class Occupation implements TakeableItemVisitor {


    private Entity entity;

    private Skill attack;
    private Skill bindwounds;
    private Skill bargain;
    private Skill observation;

    private Ability bindwoundsAbil;
    private Ability observeAbil;
    private Ability attackAbil;

    public Occupation(Entity entity) {
        this.entity = entity;

        //BREAKING TDA fix!!!!
        this.entity.setOccupation(this);
        bindwounds = new Skill(1, 10, "Bind Wounds");
        bargain = new Skill(1, 10, "Bargain");
        observation = new Skill(1, 10, "Observation");
        attack = new Skill(1,10,"Attack");
        bindwoundsAbil = new BindWounds(entity, bindwounds);
        observeAbil = new Observe(entity, observation);
        attackAbil = new Attack(entity, attack);
    }


    protected Entity getEntity() {
        return entity;
    }
	
/*	public HashMap<String,Skill> getSkills(){
		
		HashMap<String, Skill> skills = new HashMap<String, Skill>();
		skills.put("Bind Wounds", bindwounds);
		skills.put("Attack", attack);
		skills.put("Bargain", bargain);
		skills.put("Observation", observation);
		skills.putAll(getSkillsSub());
		return skills;
		
	}*/

    public ArrayList<Skill> getSkillAL() {
        ArrayList<Skill> skills = new ArrayList<Skill>();
        skills.add(bindwounds);
        skills.add(bargain);
        skills.add(observation);
        skills.add(attack);
        skills.addAll(getSubSkills());
        return skills;
    }

    public HashMap<String,Ability> getAbilities() {

        HashMap<String,Ability> abilities = new HashMap<String,Ability>();
        abilities.put("Bind Wounds",bindwoundsAbil);
        abilities.put("Attack",attackAbil);
        abilities.put("Observe",observeAbil);
        if (getAbilitiesSub() != null){
        	abilities.putAll(getAbilitiesSub());
        }
        return abilities;

    }
    
    public Skill getAttack(){
    	return attack;
    }

    protected abstract HashMap<String, Ability> getAbilitiesSub();

    public void printName() {
        System.out.println(this.toString());

    }
    
    public abstract String displayName();

    @Override

    public void visit(UsableItem ui) {
        ui.execute();
    }

    public abstract Collection<? extends Skill> getSubSkills();
}
