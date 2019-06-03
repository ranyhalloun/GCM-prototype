package commands;

import java.io.Serializable;

public class Command implements Serializable {
    
    private final Object command;
    private final CommandType type;

    public Command(final Object command, CommandType type) {
        this.command = command;
        this.type = type;
    }

    public <T> T getCommand(Class<T> clazz) {
        return (T) command;
    }

    public CommandType getType() {
        return type;
    }
}