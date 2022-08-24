package utils;

import SystemComponents.Process;
import SystemComponents.Transition;
import Systems.Transformer;

public class Rule {

    public static void rule01TauP1toP2(Process p, Transition t){
        Transformer.pre = "V(" + p.getName() + ")";
        Transformer.action = t.getAction().getName();
        Transformer.post = "V(" + t.getSuccessor().getName() + ")";
    }

    public static void rule02P1toEnvP1(Process p, String environment){
        Transformer.pre = "V(" + p.getName() + ")";
        Transformer.action = "E{" + environment + "}";
        Transformer.post = "V{" + environment + "}(" + p.getName() + ")";
    }

    public static void rule03NormalEnvP1toP2(Process p, Transition t, String environment){
        Transformer.pre = "V{" + environment + "}(" + p.getName() + ")";
        Transformer.action = t.getAction().getName();
        Transformer.post = "V(" + t.getSuccessor().getName() + ")";
    }

    public static void rule04TauEnvP1toEnvP2(Process p, Transition t, String environment){
        Transformer.pre = "V{" + environment + "}(" + p.getName() + ")";
        Transformer.action = t.getAction().getName();
        Transformer.post = "V{" + environment + "}(" + t.getSuccessor().getName() + ")";
    }

    public static void rule05TimeEnvP1toP1(Process p, String environment){
        Transformer.pre = "V{" + environment + "}(" + p.getName() + ")";
        Transformer.action = "t_e";
        Transformer.post = "V(" + p.getName() + ")";
    }

    public static void rule06TimeEnvP1toEnvP2(Process p, Transition t, String environment){
        Transformer.pre = "V{" + environment + "}(" + p.getName() + ")";
        Transformer.action = t.getAction().getName();
        Transformer.post = "V{" + environment + "}(" + t.getSuccessor().getName() + ")";
    }
}
