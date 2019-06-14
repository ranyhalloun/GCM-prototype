package commands;

public enum CommandType
{
    ConnectionCommand(1),
    RegisterCommand(2),
    SigninCommand(3),
    SearchMapCommand(4),
    InsertMapCommand(5),
    GetCustomerInfoCommand(6),
    EditCustomerInfoCommand(7),
    RequestApprovalCommand(8),
    GetCitiesQueueCommand(9),
    GetCityToursCommand(10),
    RemoveAttractionFromTourCommand(11),
    GetAttractionsOfCityCommand(12),
    AddAttractionToTourCommand(13),
    RemoveTourFromCityToursCommand(14),
    AddTourToCityCommand(15),
    SearchCityCommand(16),
    GetTourInfoFromIDCommand(17),
    GetMapInfoFromIDCommand(18);
    
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
