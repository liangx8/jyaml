package bid.rcgreed.yaml;

import java.util.HashMap;
import java.util.Map;

public class YMap implements YamlBase {
    final private Map<YamlBase, YamlBase> map = new HashMap<>();

    public void putInt(YamlBase key, int val) {
        map.put(key, YamlBase.fromInt(val));
    }

    public void putString(YamlBase key, String val) {
        map.put(key, YamlBase.fromString(val));
    }
    public void putLong(YamlBase key, long val) {
        map.put(key, YamlBase.fromLong(val));
    }

    public void put(YamlBase key, YamlBase value) {
        map.put(key, value);
    }

    @Override
    public void dump(final YamlWriter print) throws YamlException {
        var keys=map.keySet();
        for(var k:keys){
            print.carry();
            print.renderIndent();
            k.dump(print);
            print.colon();
            map.get(k).dump(print);
        }
    }

}
