package bid.rcgreed.yaml;

import java.util.ArrayList;
import java.util.List;

public class YDict implements YamlBase {
    private static class YPair {
        public YamlBase key;
        public YamlBase val;
    }

    private static YPair newPair(YamlBase key, YamlBase value) {
        var pair = new YPair();
        pair.key = key;
        pair.val = value;
        return pair;
    }

    final private List<YPair> data = new ArrayList<>();

    public void addInt(YamlBase k, int val) {
        data.add(newPair(k, YamlBase.fromInt(val)));
    }
    public void addString(YamlBase k,String str){
        data.add(newPair(k,YamlBase.fromString(str)));
    }
    public void add(YamlBase k,YamlBase v){
        data.add(newPair(k, v));
    }

    @Override
    public void dump(YamlWriter print) throws YamlException{
        for(var it : data){
            print.carry();
            print.renderIndent();
            it.key.dump(print.promoted());
            print.colon();
            it.val.dump(print.promoted());
        }
    }

}