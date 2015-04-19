package Model.Items;

import Model.Entity.Ability.Ability;
import View.Model.MapObjectView;

public class OneHandedWeaponItem extends SmasherWeaponItem {

    public OneHandedWeaponItem(int statchange, String name, MapObjectView mov) {
        super(AttackSpeed.MEDIUM, statchange, name, mov);
    }

    @Override
    public boolean isTwoHanded() {
        return false;//One handed sword, duh
    }
}
