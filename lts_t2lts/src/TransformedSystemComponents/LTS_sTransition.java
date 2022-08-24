package TransformedSystemComponents;

import SystemComponents.Action;

public class LTS_sTransition {
    private final Action action;
    private final LTS_sProcess successor;

    public LTS_sTransition(Action action, LTS_sProcess successor){
        this.action = action;
        this.successor = successor;
    }

    public Action getAction(){
        return this.action;
    }
    public LTS_sProcess getSuccessor(){
        return this.successor;
    }
}
