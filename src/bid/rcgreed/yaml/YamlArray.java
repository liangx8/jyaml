package bid.rcgreed.yaml;

import java.util.ArrayList;
import java.util.List;

public class YamlArray implements YamlBase {
    private final List<YamlBase> data;
    public YamlArray(){
        data=new ArrayList<>();
    }
    public YamlArray(List<YamlBase> l){
        data=l;
    }
    public void add(YamlBase obj){
        data.add(obj);
    }
    public void addInt(int val){
        data.add(YamlBase.fromInt(val));
    }
    public void addString(String val){
        data.add(YamlBase.fromString(val));
    }
    @Override
    public void dump(YamlWriter writer) throws YamlException {
        for(var yb: data){
            writer.carry();
            writer.renderIndent();
            writer.arrayLead();
            yb.dump(writer.promoted());
        }
    }


}