package bid.rcgreed.ok;

import bid.rcgreed.yaml.YDict;
import bid.rcgreed.yaml.YMap;
import bid.rcgreed.yaml.YamlArray;
import bid.rcgreed.yaml.YamlBase;
import bid.rcgreed.yaml.YamlException;
import bid.rcgreed.yaml.YamlWriter;

public class Main{
    public static void main(String args[])throws YamlException{
        var dict=new YDict();
        dict.addString(YamlBase.fromInt(100),"handrends");
        dict.addInt(YamlBase.fromString("ten"), 10);
        var array=new YamlArray();
        dict.add(YamlBase.fromString("array"), array);
        array.addInt(100);
        array.addString("thousand");
        var obj=new YMap();
        obj.putString(YamlBase.fromString("msg"), "this is a long text");
        obj.putInt(YamlBase.fromString("key-number"),31415);
        array.add(obj);
        var arr1=new YamlArray();
        array.add(arr1);
        arr1.addInt(2155);
        arr1.addString("push any key to continue...");
        var params=new YamlWriter.Params();
        params.indent=2;
        dict.dump(new YamlWriter(params,System.out));
    }
}