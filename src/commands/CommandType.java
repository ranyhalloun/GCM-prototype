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
    GetMapInfoFromIDCommand(18),
    UpdateDBAfterDeclineCommand(19),
    UpdateDBAfterAcceptCommand(20),
    GetNewExternalMapsCommand(21),
    CheckCityExistanceCommand(22),
    InsertNewCityCommand(23),
    GetOldPricesCommand(24),
    SendNewPricesCommand(25),
    GetPricesCommand(26),
    UpdatePricesAfterAcceptCommand(27),
    UpdatePricesAfterDeclineCommand(28),
    GetCustomersCitiesCommand(29),
    GetCityReportCommand(30),
    CheckCustomerCommand(31),
    GetPurchasesCommand(32),
    GetViewsCommand(33),
	GetDownloadsCommand(34),
	UpdateDBAfterPurchasingCommand(35),
	CheckSubscriptionCommand(36),
	GetExpirationDateCommand(37),
	GetManagerNotifCommand(38),
	GetNewVersionsCommand(39),
	AddNewAttractionToMapCommand(40),
    EditAttractionInMapCommand(41),
    RemoveAttractionFromMapCommand(42),
    IncrementNumViewOfMapCommand(43),
    IncrementNumDownloadsOfMapCommand(44),
    SendRenewalEmailCommand(45),
    SendNewVersionCommand(46),
    SendNewPricesRequestEmailCommand(47),
    SendNewPricesDecisionEmailCommand(48),
    SendNewEditedMapsEmailCommand(49),
    SendEditedMapsDecisionCommand(50),
    CheckOneTimePurchaseCommand(51);
    
    
    
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
