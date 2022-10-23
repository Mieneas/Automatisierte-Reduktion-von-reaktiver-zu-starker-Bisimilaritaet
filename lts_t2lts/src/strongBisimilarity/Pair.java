package strongBisimilarity;

import TransformedSystemComponents.LTS_sProcess;

public class Pair {
    private final LTS_sProcess pre;
    private final LTS_sProcess post;

    public Pair(LTS_sProcess pre, LTS_sProcess post){
        this.pre = pre;
        this.post = post;
    }

    public LTS_sProcess getPre(){ return this.pre; }

    public LTS_sProcess getPost(){ return this.post; }

    public String getPairName(){
        return "(" + this.pre.getName().replace("V(", "").replace(")", "") + "," +
                this.post.getName().replace("V(", "").replace(")", "") + ")";
    }

    public String getShowPair(){
        return "(" + this.pre.getName().replace("V(", "").replace(")", "") + "," +
                this.post.getName().replace("V(", "").replace(")", "") + ")";
    }
}
