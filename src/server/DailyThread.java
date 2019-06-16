package server;
import commands.Command;
import commands.CommandType;
import commands.RegisterCommand;
import database.Database;

public class DailyThread {
	
	private Database db;
	public DailyThread()
	{
		this.db = new Database();
	}
	
	public void start()
	{
		while(true)
		{
			try{
				db.removeExpiredSubscriptions();
				db.sendRenewalReminder();
				Thread.sleep(1000*86400);
			}
			catch(Exception e)
			{
				
			}
		}
	}
}
