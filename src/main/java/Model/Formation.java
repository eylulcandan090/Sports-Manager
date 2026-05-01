package Model;

import java.util.ArrayList;
import java.util.List;

public class Formation {
    String formationName;
    List<FormationSlot> slots;


    public Formation(String formationName){
        this.formationName=formationName;
        slots=new ArrayList<>();
    }


    public String getFormationName() {
        return formationName;
    }

    public void setFormationName(String formationName) {
        this.formationName = formationName;
    }

    public List<FormationSlot> getSlots() {
        return slots;
    }

    public void setSlots(List<FormationSlot> slots) {
        this.slots = slots;
    }

    public void addSlot(FormationSlot slot) {
        slots.add(slot);
    }

    public void clearSlots() {
        slots.clear();
    }

}
