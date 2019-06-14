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
    UpdateDBAfterDeclineCommand(14),
    UpdateDBAfterAcceptCommand(15),
    GetNewExternalMapsCommand(16),
    CheckCityExistanceCommand(17),
    InsertNewCityCommand(18),
    GetOldPricesCommand(19),
    SendNewPricesCommand(20),
    GetPricesCommand(21),
    UpdatePricesAfterAcceptCommand(22),
    UpdatePricesAfterDeclineCommand(23);
    
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
