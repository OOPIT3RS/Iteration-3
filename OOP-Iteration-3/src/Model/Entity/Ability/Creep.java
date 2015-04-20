package Model.Entity.Ability;

import java.util.ArrayList;

import Model.Entity.Entity;
import Model.Entity.NPC;
import Model.Entity.Skill;
import Model.Map.GameMap;
import Model.Map.HexagonalLocation;
import Model.Map.Location;
import Model.Map.Grid.Tile.Tile;
import Utility.RandomlyGenerate;

public class Creep extends SkillAbility{

	private Entity sourceEntity;
	private Skill skill;

	public Creep(Entity sourceEntity, Skill skill) {
		this.sourceEntity = sourceEntity;
		this.skill = skill;
    }

	@Override
	public void execute() {
		double chanceOfSuccess = getSkillLevel()/50;
		double probabilityOfSuccess = RandomlyGenerate.probability();
		
		int radius = 10;
		
		for (HexagonalLocation hex_location : HexagonalLocation.circleNoCenter(sourceEntity.getLocation(), radius)) {
			Entity targetNPC = (NPC)sourceEntity.getGamemap().getEntity(hex_location);
			if (targetNPC != null){
				if (chanceOfSuccess > probabilityOfSuccess){		// success = non-hostile
					((NPC)targetNPC).becomeNonHostile();
				}
			}
		}
		
		/*for(Tile tile : getTargetTiles()){
			Entity targetEntity = (NPC)tile.getEntity();
			if (targetEntity != null){
				if (chanceOfSuccess > probabilityOfSuccess){		// success = non-hostile
					((NPC)targetEntity).becomeNonHostile();
				}
			}
		}*/
	}

	public ArrayList<Tile> getTargetTiles(){
		Location center = (getSourceEntity().getLocation());
		int radius = 5;

		GameMap map = sourceEntity.getGamemap();
		
		ArrayList<Tile> result = new ArrayList<Tile>();
		for (HexagonalLocation location : HexagonalLocation.circle((HexagonalLocation)center ,radius)){
			result.add(map.getTile(location));

		}
		
		return result;
	}
	
	@Override
	public int getSkillLevel() {
		return skill.getCurrentLevel();
	}

	@Override
	protected Entity getSourceEntity() {
		return sourceEntity;
	}

}
