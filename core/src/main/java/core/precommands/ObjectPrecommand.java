package core.precommands;

import core.interact.UserInteractor;

public class ObjectPrecommand implements Precommand {
    private Object arg;
    private final String commandName;
    private boolean fromScript;

    public ObjectPrecommand(String name){
        this.commandName = name;
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
}
