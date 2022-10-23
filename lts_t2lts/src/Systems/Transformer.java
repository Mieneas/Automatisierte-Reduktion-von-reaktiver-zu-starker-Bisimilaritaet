package Systems;

import SystemComponents.Action;
import SystemComponents.Environment;
import SystemComponents.Process;
import SystemComponents.Transition;
import TransformedSystemComponents.LTS_sProcess;
import utils.Rule;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Transformer {
    private final List<String> allNewTransitions = new LinkedList<>();
    private final List<LTS_sProcess> newProcesses = new LinkedList<>();
    private final List<Action> newActions = new LinkedList<>();

    public static String pre;
    public static String action;
    public static String post;

    public Transformer(){}

    private LTS_sProcess getNewProcessByName(String name){
        for(LTS_sProcess p : this.newProcesses){
            if(p.getName().equals(name))
                return p;
        }
        return null;
    }

    private Action getNewActionByName(String name){
        for(Action a : this.newActions){
            if(a.getName().equals(name))
                return a;
        }
        return null;
    }

    public List<LTS_sProcess> getNewProcesses(){ return this.newProcesses; }

    /**
     * Add new process, Action and transitions of the new system
     */
    private void addTransition(){
        String s = "(" + pre + ",\"" + action + "\"," + post + ")";
        if(!this.allNewTransitions.contains(s)) {
            this.allNewTransitions.add(s);

            LTS_sProcess newProcessPre = this.getNewProcessByName(pre);
            if(newProcessPre == null){
                newProcessPre = new LTS_sProcess(pre);
                this.newProcesses.add(newProcessPre);
            }
            LTS_sProcess newProcessPost = this.getNewProcessByName(post);
            if(newProcessPost == null) {
                newProcessPost = new LTS_sProcess(post);
                this.newProcesses.add(newProcessPost);
            }
            Action newAction = this.getNewActionByName(action);
            if(newAction == null){
                newAction = new Action(action);
                this.newActions.add(newAction);
            }
            newProcessPre.addLTS_sTransition(newAction, newProcessPost);
        }
    }

    private void addTransitionByRuleNumber(int rule, Process p, Transition t, String e){
        if(rule == 1){
            Rule.rule01TauP1toP2(p, t);
        }
        else if(rule == 2){
            Rule.rule02P1toEnvP1(p, e);
        }
        else if(rule == 3){
            Rule.rule03NormalEnvP1toP2(p, t, e);
        }
        else if(rule == 4){
            Rule.rule04TauEnvP1toEnvP2(p, t, e);
        }
        else if(rule == 5){
            Rule.rule05TimeEnvP1toP1(p, e);
        }
        else if(rule == 6){
            Rule.rule06TimeEnvP1toEnvP2(p, t, e);
        }
        this.addTransition();
    }

    /***
     * Do t_e and E{...} actions of none deactivated environments
     * @param p: process
     * @param e: environment
     */
    private void actionsOfNoneDeactivatedEnvironmentsOrigin(Process p, Environment e){
        //v{...}(p), t_e, v(p)
        this.addTransitionByRuleNumber(5, p, null, e.getEnvironmentActionsAsString());
        e.deactivate();
    }
    private void checkNoneDeactivatedEnvOrigin(Process p, HashMap<String, List<Environment>> processSharedEnvironment){
        for(Environment e : processSharedEnvironment.get(p.getName())){
            if(e.isActive())
                this.actionsOfNoneDeactivatedEnvironmentsOrigin(p, e);
        }
    }

    /**
     * Put (Process, Environments) in processSharedEnvironment and adds all that transitions.
     * @param p: related process
     */
    private void addSharedEnvironments(List<Environment> allSharedEnvironments, HashMap<String, List<Environment>> processSharedEnvironment, Process p){
        List<Environment> envs = new LinkedList<>();
        for(Environment e : allSharedEnvironments){
            this.addTransitionByRuleNumber(2, p, null, e.getEnvironmentActionsAsString());
            Environment env = new Environment();
            for(Action a : e.getEnvironmentActions())
                env.addAction(a);
            envs.add(env);
        }
        processSharedEnvironment.put(p.getName(), envs);
    }


    /***
     * Create the LTS_s
     * @return: Array_String of transitions.
     */
    private void getLTS_s(List<Process> processes, List<Environment> allSharedEnvironments, HashMap<String, List<Environment>> processSharedEnvironment){
        for(Process p : processes) {
            this.addSharedEnvironments(allSharedEnvironments, processSharedEnvironment, p);
            int transitionsSize = p.getTransitions().size();
            for (Transition t : p.getTransitions()) {
                transitionsSize--;
                for (Environment e : processSharedEnvironment.get(p.getName())) {
                    if (t.getAction().getPriority() == 3) {
                        //Make tau action from process to its successor: v(p), tau, v(p')
                        this.addTransitionByRuleNumber(1, p, t, "");
                        //v{...}(p), tau, v{...}(p')
                        this.addTransitionByRuleNumber(4, p, t, e.getEnvironmentActionsAsString());
                        e.deactivate();
                    } else if (t.getAction().getPriority() == 2 && e.isActionAllowed(t.getAction())) {
                        //Make this action from the environment and deactivate the environment: v{a}(p), a, v(p')
                        this.addTransitionByRuleNumber(3, p, t, e.getEnvironmentActionsAsString());
                        e.deactivate();
                    } else if (t.getAction().getPriority() == 1 && e.isActive()) {//Active because all environment that allow a normal action have no time action (environment is not suitable)
                        //Go back to the process without environment: v{...}(p), t_e, v(p)
                        this.addTransitionByRuleNumber(5, p, null, e.getEnvironmentActionsAsString());
                        //Do the time action: v{...}(p), time, v{...}(p')
                        this.addTransitionByRuleNumber(6, p, t, e.getEnvironmentActionsAsString());
                        if(transitionsSize == 0)
                            e.deactivate();
                    }
                }
            }
            this.checkNoneDeactivatedEnvOrigin(p, processSharedEnvironment);
        }
    }

    /**
     ******************************************************************************************************************
     *                                                 Main method                                                    *
     ******************************************************************************************************************
     */
    public void getSystem(OldSystem oldSystem, List<Environment> sharedEnvironments) {
        HashMap<String, List<Environment>> processSharedEnvironment = new HashMap<>();
        this.getLTS_s(oldSystem.getProcesses(), sharedEnvironments, processSharedEnvironment);
    }
}