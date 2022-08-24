package TransformedSystemComponents;

import SystemComponents.Action;

import java.util.LinkedList;
import java.util.List;

public class LTS_sProcess {
    private final String name;
    private final List<LTS_sTransition> transitions = new LinkedList<>();

    public LTS_sProcess(String name){
        this.name = name;
    }

    public void addLTS_sTransition(Action action, LTS_sProcess process){
        this.transitions.add(new LTS_sTransition(action, process));
    }

    public String getName(){ return this.name; }
    public List<LTS_sTransition> getTransitions(){ return this.transitions; }
}
