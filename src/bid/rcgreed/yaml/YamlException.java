package bid.rcgreed.yaml;


public class YamlException extends Throwable{

    private static final long serialVersionUID = -2475467640055137296L;
    public YamlException(Throwable throwable){
        super(throwable);
    }
    public YamlException(String msg){
        super(msg);
    }
    public YamlException(String msg,Throwable throwable){
        super(msg,throwable);
    }
}