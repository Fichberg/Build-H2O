public class Atom implements Runnable
{
	private int number;
	private char type;
	private Monitor monitor;

	public Atom(int number, char type, Monitor monitor)
	{
		this.number = number;
		this.type = type;
		this.monitor = monitor;
	}

	public void run()
	{
		while(BuildH2O.get_water_molecules() != monitor.get_water_molecules())
		{
			try {
				if(type == 'H') { monitor.hydrogen_arrive(); release_msg(); }
				else { monitor.oxygen_arrive(); release_msg(); }
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}

		monitor.release_all();
		
		try{ done(); } catch(InterruptedException e) { e.printStackTrace(); }
	}

	private void release_msg() { System.out.println("Atom " + type + " number "+ number +" has been released. (molecules formed so far: "+monitor.get_water_molecules()+")"); }
	
	private void done() throws InterruptedException 
	{
		try{
			if(type == 'H' && number == 1) { 
				Thread.sleep(200);
				System.out.println("Water molecules formed : " + monitor.get_water_molecules()); 
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	} 
}