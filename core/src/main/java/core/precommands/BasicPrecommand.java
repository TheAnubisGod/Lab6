package core.precommands;

public class BasicPrecommand implements Precommand {

    private Object arg;
    private final String commandName;
    private boolean fromScript;

    public BasicPrecommand(String name){
        this.commandName = name;
    }

    @Override
    public void preprocess(Object objectArg, boolean fromScript) {
        this.arg = objectArg;
        this.fromScript = fromScript;
    }

    @Override
    public String getCommandName() {
        return this.commandName;
    }

    @Override
    public Object getArg() {
        return this.arg;
    }

    @Override
    public boolean isFromScript() {
        return this.fromScript;
    }
}
