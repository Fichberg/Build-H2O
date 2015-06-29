import java.util.ArrayList;
import java.util.Random;

public class BuildH2O 
{
	private static int water_molecules;

	public static void main(String[] args)
	{
		int total_atoms, o, h;
		ArrayList<Atom> atoms = new ArrayList<Atom>();

		if(!correct_number_of_arguments(args.length)) use();

		total_atoms = (h = get_int_argument(args[0])) + (o = get_int_argument(args[1]));
		water_molecules = get_int_argument(args[2]);

		if(h < 2) { System.out.println("Insufficient number of Hydrogens."); System.exit(0); }
		if(o < 1) { System.out.println("Insufficient number of Oxygens."); System.exit(0); }

		initiate_atoms(atoms, h, o);

		//Create threads
		for (Atom a : atoms) (new Thread(a)).start();
	}

	private static void initiate_atoms(ArrayList<Atom> atoms, int hydrogens, int oxygens)
	{
		Monitor m = new Monitor(water_molecules);

		for(int i = 0; i < hydrogens; i++)
		{
			Atom c = new Atom(i, 'H', m);
			atoms.add(c);
		}

		for(int i = 0; i < oxygens; i++)
		{
			Atom c = new Atom(i, 'O', m);
			atoms.add(c);
		}
	}

	private static int get_int_argument(String argument)
	{
		if(!is_integer(argument)) return -1;
		return Integer.parseInt(argument);
	}

	//Must be integer number
	private static boolean is_integer(String str)
	{
		try {
			Integer.parseInt(str);
		} catch(NumberFormatException e) {
			return false;
		} catch(NullPointerException e) {
			return false;
		}
		return true;
	}

	private static void use()
	{
		System.out.print("Incorrect number of arguments. Use:\n\n\tjava OneLaneBridge H O W\n\nWhere:\n");
		System.out.print("H is an integer representing the total number of Hydrogens.\n");
		System.out.print("O is an integer representing the total number of Oxygens.\n");
		System.out.print("W is an integer representing the total number of Water molecules that must be done.\n");
		System.exit(0);
	}

	private static boolean correct_number_of_arguments(int length) { return length == 3; }

	public static int get_water_molecules() { return water_molecules; }
}