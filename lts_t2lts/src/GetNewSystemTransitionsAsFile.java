import SystemComponents.Environment;
import Systems.Transformer;
import Systems.OldSystem;
import TransformedSystemComponents.LTS_sProcess;
import TransformedSystemComponents.LTS_sTransition;
import utils.ImprovedUtils;
import utils.MathSet;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.exit;

public class GetNewSystemTransitionsAsFile {
    HashMap<String, String> newSystem2mCRL2Format1 = new HashMap<>();
    HashMap<String, String> newSystem2mCRL2Format2 = new HashMap<>();
    boolean toUseJar = false;
    String LTS_sFilePath;

    public GetNewSystemTransitionsAsFile(){}

    public void mapProcessesToMCRL2Format(List<LTS_sProcess> processes, HashMap<String, String> newSystem2mCRL2Format){
        int processNames = 0;
        for(LTS_sProcess p : processes){
            newSystem2mCRL2Format.put(p.getName(), Integer.toString(processNames));
            processNames++;
        }
    }

    /**
     *
     *
     * @param processes: new system processes.
     * @param mCRL2Format: true to get processes names as numbers. false to get the original names.
     * @param assuming: true to make all E_x actions the same, false to get original actions names.
     * @return: List of all transitions as Mcrl2 format with numbered names or not.
     */
    public LinkedList<String> getTransitionsAsList(List<LTS_sProcess> processes, boolean mCRL2Format, boolean assuming, HashMap<String, String> newSystem2mCRL2Format){
        LinkedList<String> transitions = new LinkedList<>();

        String transition;
        for(LTS_sProcess p : processes){
            for(LTS_sTransition t : p.getTransitions()){
                transition =
                        "("
                        + (mCRL2Format? newSystem2mCRL2Format.get(p.getName()) : p.getName())
                        + ",\""
                        + (assuming && t.getAction().getName().contains("E{")? "E{...}" : t.getAction().getName())
                        + "\","
                        + (mCRL2Format? newSystem2mCRL2Format.get(t.getSuccessor().getName()) : t.getSuccessor().getName())
                        + ")";
                if(!transitions.contains(transition)) transitions.add(transition);
            }
        }
        return transitions;
    }

    public void getFirstLintOfMCRL2File(LinkedList<String> mCRL2Transitions, String root, int processesNumber){
        String firstLine = "des ("
                + root
                + "," + mCRL2Transitions.size()
                + "," + (processesNumber == 0?1:processesNumber) + ")";
        mCRL2Transitions.addFirst(firstLine);
    }

    public void create_mCRL2File(List<String> fileAsList, String filename) throws IOException {
        //Write LTS_s system
        File resultedFile = new File(this.LTS_sFilePath + filename);
        FileOutputStream fos = new FileOutputStream(resultedFile);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (String s : fileAsList) {
            bw.write(s);
            bw.newLine();
        }
        bw.close();
    }

    //args[0]: file1, args[1]: file2, args[2]: improved, args[3]: all pairs, args[4]: assumption
    public static void main(String[] args){
        if(args.length == 5) {
            String system1 = args[0];
            String system2 = args[1];
            boolean improvedAlgorithmSet = Integer.parseInt(args[2]) == 1;
            boolean allPairsSet = Integer.parseInt(args[3]) == 1;
            boolean setAssumption = Integer.parseInt(args[4]) == 1;

            OldSystem oldSystem1 = new OldSystem(system1);
            OldSystem oldSystem2 = new OldSystem(system2);
            oldSystem1.parseSystem();
            oldSystem2.parseSystem();

            List<Environment> sharedEnvironments = improvedAlgorithmSet
                    ? ImprovedUtils.getSharedEnv(oldSystem1, oldSystem2)
                    : (new MathSet(oldSystem1, oldSystem2)).getSubSets();

            String root1;
            String root2;
            List<LTS_sProcess> newProcesses1;
            List<LTS_sProcess> newProcesses2;

            Transformer transformer1 = new Transformer();
            transformer1.getSystem(oldSystem1, sharedEnvironments);
            Transformer transformer2 = new Transformer();
            transformer2.getSystem(oldSystem2, sharedEnvironments);

            root1 = oldSystem1.getRootName();
            root2 = oldSystem2.getRootName();
            newProcesses1 = transformer1.getNewProcesses();
            newProcesses2 = transformer2.getNewProcesses();

            if(allPairsSet){
            }
            else {


                GetNewSystemTransitionsAsFile newSystemAsFile = new GetNewSystemTransitionsAsFile();
                newSystemAsFile.LTS_sFilePath = newSystemAsFile.toUseJar ? "out/lts_s_files/" : "../out/lts_s_files/";
                newSystemAsFile.mapProcessesToMCRL2Format(newProcesses1, newSystemAsFile.newSystem2mCRL2Format1);
                newSystemAsFile.mapProcessesToMCRL2Format(newProcesses2, newSystemAsFile.newSystem2mCRL2Format2);
                //1
                LinkedList<String> newSystemListAsmCRL2Format1 = newSystemAsFile.getTransitionsAsList(newProcesses1, true, setAssumption, newSystemAsFile.newSystem2mCRL2Format1);
                LinkedList<String> newSystemListAsmCRL2Format2 = newSystemAsFile.getTransitionsAsList(newProcesses2, true, setAssumption, newSystemAsFile.newSystem2mCRL2Format2);
                //2
                newSystemAsFile.getFirstLintOfMCRL2File(newSystemListAsmCRL2Format1, root1, newProcesses1.size());
                newSystemAsFile.getFirstLintOfMCRL2File(newSystemListAsmCRL2Format2, root2, newProcesses2.size());
                //3
                String filename1 = system1.split("/")[newSystemAsFile.toUseJar ? 2 : 3];
                String filename2 = system2.split("/")[newSystemAsFile.toUseJar ? 2 : 3];
                try {
                    newSystemAsFile.create_mCRL2File(newSystemListAsmCRL2Format1, filename1);
                    newSystemAsFile.create_mCRL2File(newSystemListAsmCRL2Format2, filename2);
                } catch (IOException e) {
                    System.err.println("can not create file: " + filename1 + "or" + filename2);
                }
            }
        }
        else{
            System.err.println("Please enter a mcrl2 file and its parameters first!");
            exit(1);
        }
    }
}