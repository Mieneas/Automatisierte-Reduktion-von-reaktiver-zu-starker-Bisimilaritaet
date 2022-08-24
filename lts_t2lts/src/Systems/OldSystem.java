package Systems;

import SystemComponents.Action;
import SystemComponents.Process;
import SystemComponents.Transition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class OldSystem {
    private final List<Process> processes = new LinkedList<>();
    private final List<String> processesNames = new LinkedList<>();
    public List<Action> actions = new LinkedList<>();
    public final List<String> actionsNames = new LinkedList<>();
    private final List<String> triples = new LinkedList<>();
    private String rootName = "";

    public OldSystem(String file){
        putSystem(file);
    }

    /***
     * read the transition system with time outs
     * @param file: path of the file that contains the transition system with time outs.
     */
    private void putSystem(String file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String curLine;
            while ((curLine = bufferedReader.readLine()) != null) {
                if(curLine.contains("des")){
                    this.rootName = curLine.split(",")[0].substring(5);
                }
                else {
                    triples.add(curLine);
                }
            }
            bufferedReader.close();
        } catch (IOException E) {
            System.err.println("somme wrong with the file: " + file);
        }
    }

    /***
     *
     * @param transition: read transition from the file.
     * @return cleaned transition (without mCRL syntax)
     */
    private String[] parseTransition(String transition) {
        String[] transitionMembers = transition.split(",");
        transitionMembers[0] = transitionMembers[0].substring(1);
        transitionMembers[1] = transitionMembers[1].substring(1).substring(0, transitionMembers[1].length()-2);
        transitionMembers[2] = transitionMembers[2].substring(0, transitionMembers[2].length() - 1);

        return transitionMembers;
    }

    private void addProcess(Process process) {
        this.processes.add(process);
        this.processesNames.add(process.getName());
    }

    private Process getProcessByName(String name) {
        if(this.processesNames.contains(name)){
            for(Process p : this.processes){
                if(p.getName().equals(name))
                    return p;
            }
        }
        return null;
    }

    private void addActions(Action action){
        if (!actionsNames.contains(action.getName()) && action.getPriority() == 2){
            actionsNames.add(action.getName());
            actions.add(action);
        }
    }

    private void addTransition(Process pre, Action action, Process post) {
        Transition transition = new Transition(action, post);
        pre.addTransition(transition);
    }

    /***
     * make the written transition system as object oriented system.
     */
    public void parseSystem() {
        if (triples.isEmpty() && this.rootName.equals("")) {
            System.err.println("Please enter the system first.");
        } else {
            String[] transition;
            Process pre;
            Action action;
            Process post;

            for (String s : this.triples) {
                transition = this.parseTransition(s);

                pre = getProcessByName(transition[0]);
                if(pre == null) {
                    pre = new Process(transition[0]);
                    addProcess(pre);
                }
                post = getProcessByName(transition[2]);
                if(post == null) {
                    post = new Process(transition[2]);
                    addProcess(post);
                }
                action = pre.getActionByName(transition[1]);
                if(action == null) {
                    action = new Action(transition[1]);
                    pre.addAction(action);
                }
                this.addActions(action);

                this.addTransition(pre, action, post);
            }
            for(Process p : this.processes) {
                if (p.isHasOwnTimeEnvironment() && p.isHasTauAction()) {
                    p.setNoOwnEnvironment();
                    p.getTransitions().removeIf(e -> e.getAction().getPriority() == 1);
                    p.getTransitionsNames().removeIf( s -> s.contains("time"));
                }
                if (p.isHasOwnTimeEnvironment())
                    p.createEnvironment(this);
            }
        }
    }

    public List<Process> getProcesses(){
        return this.processes;
    }
    public List<Action> getActions(){
        return this.actions;
    }
    public List<String> getActionsNames(){
        return this.actionsNames;
    }
    public String getRootName() { return this.rootName; }
}
