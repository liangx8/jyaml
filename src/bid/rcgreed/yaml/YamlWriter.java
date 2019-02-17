package bid.rcgreed.yaml;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;


public class YamlWriter {
    final static byte[] COLON=new byte[]{':',' '};
    final static byte[] ARRAY_LEAD=new byte[]{'-',' '};

    final private int rank;
    final private Params params;
    final OutputStream print;

    public YamlWriter(Params p,OutputStream out){
        rank=0;
        params=p;
        print=out;
    }
    private YamlWriter(int ra,Params p,OutputStream pr){
        rank=ra;
        params=p;
        print=pr;
    }
    public YamlWriter promoted(){
        return new YamlWriter(rank + 1,params,print);
    }
    public static class Params{
        public int indent;
    }
    public void renderIndent() throws YamlException{
        try {
            for(int x=0;x<rank *params.indent;x++){
                print.write(' ');
            }
        } catch (IOException e) {
            throw new YamlException(e);
        }
    }
    public void renderLong(long val) throws YamlException{
        try {
            print.write(String.format( "%ld", val).getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new YamlException(e);
        }
    }
    public void renderInt(int val) throws YamlException{
        try {
            print.write(String.format( "%d", val).getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new YamlException(e);
        }
    }
    public void renderString(String val) throws YamlException{
        try {
            print.write(val.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new YamlException(e);
        }
    }
    public void renderBoolean(boolean val) throws YamlException{
        try {
            print.write((val?"true":"false").getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            throw new YamlException(e);
        }
    }

    public void colon() throws YamlException{
        try{
            print.write(COLON);
        } catch(IOException e){
            throw new YamlException(e);
        }
    }
    public void carry() throws YamlException{
        try {
            print.write('\n');
        } catch (IOException e) {
            throw new YamlException(e);
        }
    }
    public void arrayLead() throws YamlException{
        try{
            print.write(ARRAY_LEAD);
        } catch(IOException e){
            throw new YamlException(e);
        }
    }
}