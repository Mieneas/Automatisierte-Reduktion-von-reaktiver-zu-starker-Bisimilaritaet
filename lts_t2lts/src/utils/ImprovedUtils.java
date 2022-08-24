package utils;

import SystemComponents.Action;
import SystemComponents.Environment;
import SystemComponents.Process;
import Systems.OldSystem;

import java.util.LinkedList;
import java.util.List;

public class ImprovedUtils {

    private static void getEnvironments(List<Environment> environments, List<String> EnvNames, List<Action> actions, List<Process> processes){

        for(Action a : actions){
            Environment e = new Environment();
            e.addAction(a);
            environments.add(e);
            EnvNames.add(a.getName());
        }

        for(Process p : processes){
            if(p.isHasOwnTimeEnvironment()){
                Environment e = new Environment();
                for(Action a : p.getOwnEnvironment().getEnvironmentActions()){
                    e.addAction(a);
                }
                if(!EnvNames.contains(e.getEnvironmentActionsAsString())){
                    environments.add(e);
                    EnvNames.add(e.getEnvironmentActionsAsString());
                }
            }
        }
    }

    public static List<Environment> getSharedEnv(OldSystem oldSystem1, OldSystem oldSystem2){
        List<Environment> env = new LinkedList<>();
        List<String> envNames = new LinkedList<>();

        ImprovedUtils.getEnvironments(env, envNames, oldSystem1.actions, oldSystem1.getProcesses());
        ImprovedUtils.getEnvironments(env, envNames, oldSystem2.actions, oldSystem2.getProcesses());

        return env;
    }
}
