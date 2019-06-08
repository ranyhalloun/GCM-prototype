package commands;

public enum CommandType
{
    ConnectionCommand(1),
    RegisterCommand(2),
    SigninCommand(3),
	SearchMapCommand(4);
    
    private int CommandTypeValue;

    CommandType(int value) {
         this.CommandTypeValue = value;
    }

    public int getCommandTypeValue() {
         return this.CommandTypeValue;
    }

    public String getCommandTypeName() {
         return this.name();
    }
}
