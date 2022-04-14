package core.precommands;

import core.interact.UserInteractor;

import java.io.Serializable;

public interface Precommand extends Serializable {
    void preprocess(Object objectArg, boolean fromScript);
    String getCommandName();
    Object getArg();
    boolean isFromScript();
}
