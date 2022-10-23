package strongBisimilarity;

import TransformedSystemComponents.LTS_sProcess;

import java.util.LinkedList;
import java.util.List;

public class CheckBisimilarity {

    List<LTS_sProcess> processList1;
    List<LTS_sProcess> processList2;

    public CheckBisimilarity(List<LTS_sProcess> list1, List<LTS_sProcess> list2){
        this.processList1 = list1;
        this.processList2 = list2;
    }

    private boolean areSuccessorsInR(boolean leftToRight, List<Pair> pairs, LTS_sProcess successorOfP, LTS_sProcess successorOfQ){
        for(Pair pair : pairs){
            if((leftToRight && pair.getPre().getName().equals(successorOfP.getName()) && pair.getPost().getName().equals(successorOfQ.getName()))
            ||
               (!leftToRight && pair.getPost().getName().equals(successorOfP.getName()) && pair.getPre().getName().equals(successorOfQ.getName())))
                return true;
        }
        return false;
    }

    private boolean checkSimulation(boolean leftToRight, List<Pair> pairs, LTS_sProcess p, LTS_sProcess q){

        return p.getTransitions().stream().allMatch(t1 ->
                q.getTransitions().stream().anyMatch(t2 ->
                        t1.getAction().getName().equals(t2.getAction().getName())
                    &&
                        this.areSuccessorsInR(leftToRight, pairs, t1.getSuccessor(), t2.getSuccessor())));
    }

    public List<String> getBisimilarPairs(){
        List<Pair> pairs = new LinkedList<>();

        //Build R
        this.processList1.forEach(p -> this.processList2.forEach(q -> pairs.add(new Pair(p, q))));

        //Checking
        int currentSize = pairs.size();
        while (true){
            pairs.removeIf(p ->
                !this.checkSimulation(true, pairs, p.getPre(), p.getPost()) ||
                !this.checkSimulation(false, pairs, p.getPost(), p.getPre())
            );
            if(currentSize == pairs.size()) break;
            currentSize = pairs.size();
        }
        List<String> result = new LinkedList<>();
        pairs.forEach(p -> {
            if(!result.contains(p.getPairName()) && !p.getPairName().contains("}")){
                result.add(p.getShowPair());
            }
        });
        return result;
    }
}
