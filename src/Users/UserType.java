package Users;


public enum UserType
{
    Anonymous(0),
    Customer(1),
    Worker(2),
    GCMWorker(3),
    GCMManager(4),
    CompanyManager(5);
    
    private int UserTypeValue;

    UserType() {
        this.UserTypeValue = 0;
    }
    UserType(int value) {
         this.UserTypeValue = value;
    }

    public int getUserTypeValue() {
         return this.UserTypeValue;
    }

    public String getUserTypeName() {
         return this.name();
    }
    
}
