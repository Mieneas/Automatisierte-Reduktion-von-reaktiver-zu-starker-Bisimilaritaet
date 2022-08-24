package SystemComponents;

public class Action {
    private final String actionName;
    private int priority;

    public Action(String name){
        this.actionName = name;
        this.setPriority();
    }

    /***
     * @return process name.
     */
    public String getName() {
        return this.actionName;
    }

    /***
     * @return process priority.
     */
    public int getPriority() {
        return this.priority;
    }

    /***
     * time action has priority 1
     * normal action has priority 2
     * tau action has priority 3
     */
    public void setPriority(){
        if(this.actionName.equals("tau"))//t = tau
            this.priority = 3;
        else if(this.actionName.equals("time"))
            this.priority = 1;
        else
            this.priority = 2;
    }
}
