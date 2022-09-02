package strongBisimilarity;

import TransformedSystemComponents.LTS_sProcess;
import TransformedSystemComponents.LTS_sTransition;

import java.util.LinkedList;
import java.util.List;

public class CheckBisimilarity {

    List<LTS_sProcess> processList1;
    List<LTS_sProcess> processList2;

    public CheckBisimilarity(List<LTS_sProcess> list1, List<LTS_sProcess> list2){
        this.processList1 = list1;
        this.processList2 = list2;
    }

    private boolean areListsEqual(List<Pair> list1, List<Pair> list2){
        if(list1.size() == list2.size()){
            for(Pair p : list1){
                if(!list2.contains(p))
                    return false;
            }
            return true;
        }
        return false;
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
        boolean found = false;

        for(LTS_sTransition t1 : p.getTransitions()){
            for(LTS_sTransition t2 : q.getTransitions()){
                if(t1.getAction().getName().equals(t2.getAction().getName())){
                    found = this.areSuccessorsInR(leftToRight, pairs, t1.getSuccessor(), t2.getSuccessor());
                    if(found) break;
                }
            }
            if(!found) return false;
            found = false;
        }
        return true;
    }

    private boolean oneStepBisimilar(List<Pair> pairs, Pair pair){
        return this.checkSimulation(true, pairs, pair.getPre(), pair.getPost()) &&
                this.checkSimulation(false, pairs, pair.getPost(), pair.getPre());
    }

    private List<Pair> strongBisimilar(List<Pair> paris){
        List<Pair> xStepBisimilar = new LinkedList<>();

        for(Pair pair : paris){
            if(oneStepBisimilar(paris, pair))
                xStepBisimilar.add(pair);
        }
        return xStepBisimilar;
    }

    public List<String> getBisimilarPairs(){
        List<Pair> pairs = new LinkedList<>();
        List<Pair> bisimilarPairs = new LinkedList<>();

        //Build R
        for(LTS_sProcess p : this.processList1){
            for(LTS_sProcess q : this.processList2){
                pairs.add(new Pair(p, q));
            }
        }

        //Checking
        while (true){
            bisimilarPairs.clear();
            bisimilarPairs.addAll(this.strongBisimilar(pairs));
            if(!this.areListsEqual(pairs, bisimilarPairs)){
                pairs.clear();
                pairs.addAll(bisimilarPairs);
            }
            else break;
        }

        List<String> result = new LinkedList<>();
        for(Pair p : bisimilarPairs){
            if(!result.contains(p.getPairName()) && !p.getPairName().contains("}")){
                result.add(p.getShowPair());
            }
        }
        return result;
    }
}
