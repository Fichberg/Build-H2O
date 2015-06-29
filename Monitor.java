import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor
{
	private int hydrogens;
	private int oxygens;
	private int water_molecules;
	private int wanted;
	private final Lock lock;
	private final Condition more_hydrogens;
	private final Condition more_oxygens;

	public Monitor(int water)
	{
		wanted = water;
		hydrogens = 0;
		oxygens = 0;

		lock = new ReentrantLock(true);
		more_hydrogens = lock.newCondition();
		more_oxygens = lock.newCondition();
	}

	public void hydrogen_arrive() throws InterruptedException
	{
		lock.lock();
		try{
			hydrogens++;
			if(hydrogens >= 2 && oxygens >= 1) do_water();
			else more_hydrogens.await();
		} finally {
			lock.unlock();
		}
	}

	public void oxygen_arrive() throws InterruptedException
	{
		lock.lock();
		try{
			oxygens++;
			if(hydrogens >= 2 && oxygens >= 1) do_water();
			else more_oxygens.await();
		} finally {
			lock.unlock();
		}
	}

	private void do_water()
	{
		if(wanted != water_molecules)
		{
			hydrogens -= 2;
			oxygens--;
			water_molecules++;
			more_hydrogens.signal();
			more_hydrogens.signal();
			more_oxygens.signal();
		}
	}

	public void release_all()
	{
		lock.lock();
		try {
			more_hydrogens.signalAll();
			more_oxygens.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public int get_water_molecules() { return water_molecules; }
}