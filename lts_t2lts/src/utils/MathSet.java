package utils;

import SystemComponents.Action;
import SystemComponents.Environment;
import Systems.OldSystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MathSet {
    private OldSystem oldSystem1;
    private OldSystem oldSystem2;
    private final List<Action> allActions = new LinkedList<>();
    private List<String> allActionsNames = new LinkedList<>();
    private final int actionNumber;

    public MathSet(OldSystem oldSystem1, OldSystem oldSystem2){
        this.oldSystem1 = oldSystem1;
        this.oldSystem2 = oldSystem2;

        this.allActions.addAll(oldSystem1.getActions());
        this.allActionsNames.addAll(oldSystem1.getActionsNames());
        for(Action a : oldSystem2.getActions()){
            if(!allActionsNames.contains(a.getName())){
                allActionsNames.add(a.getName());
                allActions.add(a);
            }
        }
        this.actionNumber = allActions.size();
    }

    public List<Environment> getSubSets(){
        ArrayList<Integer> nums = new ArrayList<>();
        for(int i = 0; i < this.actionNumber; i++)
            nums.add(i);
        List<List<Integer>> subsetsList = this.subsets(nums.toArray());

        List<Environment> subSetsAsEnvironments = new LinkedList<>();
        for(List<Integer> subset : subsetsList){
            Environment e = new Environment();
            for(int actionIndex : subset)
                e.addAction(new Action(this.allActions.get(actionIndex).getName()));
            subSetsAsEnvironments.add(e);
        }
        return subSetsAsEnvironments;
    }

    private List<List<Integer>> subsets(Object[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        subsetsHelper(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private void subsetsHelper(List<List<Integer>> list , List<Integer> resultList, Object [] nums, int start){
        list.add(new ArrayList<>(resultList));
        for(int i = start; i < nums.length; i++){
            // add element
            resultList.add((Integer) nums[i]);
            // Explore
            subsetsHelper(list, resultList, nums, i + 1);
            // remove
            resultList.remove(resultList.size() - 1);
        }
    }
}