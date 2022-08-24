package SystemComponents;

public class Transition {

    private final Action action;
    private final Process successor;

    public Transition(Action action, Process successor){
        this.action = action;
        this.successor = successor;
    }

    public Action getAction(){
        return this.action;
    }
    public Process getSuccessor(){
        return this.successor;
    }
}
