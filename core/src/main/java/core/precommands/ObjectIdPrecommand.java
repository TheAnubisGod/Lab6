package core.precommands;

public class ObjectIdPrecommand implements Precommand{
    private Object arg;
    private final String commandName;
    private final String id;
    private boolean fromScript;

    public ObjectIdPrecommand(String name, String id){
        this.commandName = name;
        this.id = id;
    }

    @Override
    public void preprocess(Object objectArg, boolean fromScript) {
        this.arg =  objectArg;
        this.fromScript = fromScript;
    }

    @Override
    public String getCommandName() {
        return this.commandName;
    }

    public Object getArg() {
        return arg;
    }

    @Override
    public boolean isFromScript() {
        return this.fromScript;
    }

    public String getId() {
        return id;
    }
}
