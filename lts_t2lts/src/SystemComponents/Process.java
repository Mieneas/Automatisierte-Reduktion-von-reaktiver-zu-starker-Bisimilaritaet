package SystemComponents;

import Systems.OldSystem;

import java.util.*;

public class Process {
    private final String name;

    private final List<Action> actions = new LinkedList<>();
    private final List<String> actionsNames = new LinkedList<>();
    private boolean hasTauAction = false;

    private final LinkedList<Transition> transitions = new LinkedList<>();
    private final List<String> transitionsNames = new LinkedList<>();

    //Own time environment
    private final Environment ownEnvironment = new Environment();
    private boolean hasOwnTimeEnvironment = false;



    public Process(String processName) {
        this.name = processName;
    }

    public void addAction(Action action) {
        if(!this.actionsNames.contains(action.getName()) && action.getPriority() == 2){
            this.actions.add(action);
            this.actionsNames.add(action.getName());
        }
    }

    public Action getActionByName(String name) {
        if(this.actionsNames.contains(name)){
            for(Action a : this.actions){
                if(a.getName().equals(name))
                    return a;
            }
        }
        return null;
    }

    /***
     * Add all transitions so that, the list contains time transition at the begin
     * and tau transitions at the last
     * and normal transitions between them.
     * @param currentTransition: that the process has.
     */
    private void addTransitionInOrder(Transition currentTransition){
        if(currentTransition.getAction().getPriority() == 1) {
            this.transitions.addLast(currentTransition);
        } else if(currentTransition.getAction().getPriority() == 3)
            this.transitions.addFirst(currentTransition);
        else{
            int index;
            for(Transition t : transitions){
                if(t.getAction().getName().equals("time")) {
                    index = transitions.indexOf(t);
                    this.transitions.add(index, currentTransition);
                    return;
                }
            }
            this.transitions.addLast(currentTransition);
        }
    }

    /***
     * Add transition name as: "ActionName processName"
     * and add the transition in transitions list
     * and set the Environment of the successor if this process has a time action.
     * @param currentTransition: that the process has.
     */
    public void addTransition(Transition currentTransition){
        if(!this.transitionsNames.contains(currentTransition.getAction().getName() + " " + currentTransition.getSuccessor().getName())){
            this.addTransitionInOrder(currentTransition);
            this.transitionsNames.add(currentTransition.getAction().getName() + " " + currentTransition.getSuccessor().getName());
            if(!this.hasOwnTimeEnvironment && currentTransition.getAction().getName().equals("time")){
                this.hasOwnTimeEnvironment = true;
            }
            if(currentTransition.getAction().getName().equals("tau"))
                this.hasTauAction = true;
        }
    }

    /***
     * create time environment and set hasOwnTimeEnvironment to true.
     */
    public void createEnvironment(OldSystem oldSystem) {
        for(Action a : oldSystem.getActions()) {
            if (!this.actionsNames.contains(a.getName()) && a.getPriority() == 2)
                this.ownEnvironment.addAction(a);
        }
    }

    public String getName(){
        return this.name;
    }
    public boolean isHasTauAction() { return this.hasTauAction; }
    public List<Transition> getTransitions() { return this.transitions; }
    public List<String> getTransitionsNames() { return this.transitionsNames; }
    public Environment getOwnEnvironment(){ return this.ownEnvironment; }
    public boolean isHasOwnTimeEnvironment() { return this.hasOwnTimeEnvironment; }
    public void setNoOwnEnvironment() { this.hasOwnTimeEnvironment = false; }
}
