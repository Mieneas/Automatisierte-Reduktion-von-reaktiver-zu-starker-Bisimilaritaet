package SystemComponents;

import java.util.LinkedList;
import java.util.List;

public class Environment {
    private final List<Action> environmentActions = new LinkedList<>();
    private final List<String> environmentActionsNames = new LinkedList<>();
    private boolean active = true;

    public Environment(){}

    public void addAction(Action environmentAction){
        if(!this.environmentActionsNames.contains(environmentAction.getName())){
            this.environmentActions.add(environmentAction);
            this.environmentActionsNames.add(environmentAction.getName());
        }
    }

    /***
     * @return Actions names as action1-action2-action3.
     */
    public String getEnvironmentActionsAsString() {
        String result = "";
        for(Action a : this.environmentActions)
                result += a.getName() + "-";

        return result.isEmpty()? "" : result.substring(0, result.length() -1);
    }

    public List<Action> getEnvironmentActions() {
        return this.environmentActions;
    }

    public boolean isActionAllowed(Action checkedAction) {
        return this.environmentActionsNames.contains(checkedAction.getName());
    }

    /**
     * @return False if the environment has an out-action.
     */
    public boolean isActive(){ return this.active; }

    /***
     * tells the Environment that it has an out-Action.
     */
    public void deactivate(){this.active = false;}
}
