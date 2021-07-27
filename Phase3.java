import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Phase3 {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		Map<Integer, Employee> tempEmployee = new HashMap<>();
		String eid = "";
		System.out.println("Welcome to Employee Management System (EMS)");
		tempEmployee = load(args[0]);
		if(tempEmployee.isEmpty())
		{
			System.out.println("Database input file "+args[0]+" not found.");
			System.out.println("Creating an empty database.");
		}
		int selection;
		do {
			menu();
			System.out.println("Enter you selection (1..7):");
			selection = input.nextInt();
			while(selection <1 || selection > 7)
			{
				System.out.println("Invalid selection.");
				System.out.println("Enter you selection (1..7):");
				selection = input.nextInt();
			}
			if(selection == 1)
			{
				System.out.println("Enter an employee ID or QUIT to stop:");
				eid = input.next();
				input.nextLine();
				while(!"QUIT".equalsIgnoreCase(eid) && Integer.parseInt(eid) <= 0)
				{
					System.out.println("Invalid ID. Employee ID should be a positive integer number.");
					System.out.println("Enter an Employee ID or QUIT to stop:");
					eid = input.next();
				}
				while(!"QUIT".equalsIgnoreCase(eid) && tempEmployee.containsKey(Integer.parseInt(eid)))
				{
					System.out.println("Employee ID: "+eid+" already exists in the database.");
					System.out.println("Enter an Employee ID or QUIT to stop:");
					eid = input.next();
					while(!"QUIT".equalsIgnoreCase(eid) && Integer.parseInt(eid) <= 0)
					{
						System.out.println("Invalid ID. Employee ID should be a positive integer number.");
						System.out.println("Enter an Employee ID or QUIT to stop:");
						eid = input.next();
					}
				}
				if(!"QUIT".equalsIgnoreCase(eid))
				{
					System.out.println("Enter employee name:");
					String name = input.nextLine();
						
					System.out.println("Enter employee department:");
					String department = input.nextLine();
						
					System.out.println("Enter employee title:");
					String title = input.nextLine();
						
					System.out.println("Enter employee salary:");
					Double salary = input.nextDouble();
					while(salary<=0)
					{
						System.out.println("Invalid salary. Salary should be a positive number.");
						System.out.println("Enter employee salary:");
						salary = input.nextDouble();
					}				
					tempEmployee.put(Integer.parseInt(eid),new Employee(Integer.parseInt(eid),name,department,title, salary));
				}
			}
			else if(selection == 2)
			{
				System.out.println("Enter an Employee ID or QUIT to stop:");
				eid = input.next();
				if("QUIT".equalsIgnoreCase(eid))
					selection = 7;
				else
					findEmployeeByID(tempEmployee, Integer.parseInt(eid));
			}
			else if(selection == 3)
			{
				System.out.println("Enter an Employee name or QUIT to stop:");
				String name = input.next();
				if("QUIT".equalsIgnoreCase(name))
					selection = 7;
				else
					findEmployeeByName(tempEmployee, name);
			}
			else if(selection == 4)
			{
				System.out.println("Enter an Employee ID to delete or QUIT to stop:");
				eid = input.next();
				if(!"QUIT".equalsIgnoreCase(eid))
				{
					while(Integer.parseInt(eid) <= 0)
					{
						System.out.println("Invalid ID. Employee ID should be a positive integer number.");
						System.out.println("Enter an Employee ID to delete or QUIT to stop:");
						eid = input.next();
					}
					if(tempEmployee.containsKey(eid))
					{
						tempEmployee.remove(Integer.parseInt(eid));
					}
					else
						System.out.println("There is no employee with that ID number");
				}
			}
			else if(selection == 5)
			{
				displayStatistics(tempEmployee);
			}
			else if(selection == 6)
			{
				displayAll(tempEmployee);
			}
			else if(selection == 7)
			{
				if(!tempEmployee.isEmpty())
				{
					File file = new File(args[0]);
					save(tempEmployee, file);
				}
			}
		}while(selection != 7);
		System.out.println("Thank you for using Employee Management System (EMS)");
	}
	private static void displayStatistics(Map<Integer, Employee> tempEmployee) {
		// TODO Auto-generated method stub
		Map<String, Integer> departments = new HashMap<>();
		if(!tempEmployee.isEmpty())
		{
			System.out.println("Department Statistics:");
			for(int i : tempEmployee.keySet())
			{
				String department = tempEmployee.get(i).getDepartment();
				if(!departments.containsKey(tempEmployee.get(i).getDepartment()))
				{
					departments.put(tempEmployee.get(i).getDepartment(),countDepartment(tempEmployee,tempEmployee.get(i).getDepartment()));
					if(departments.get(department) == 1)
						System.out.println("\tDepartment: "+department+" - "+departments.get(department) + " employee");
					else
						System.out.println("\tDepartment: "+department+" - "+departments.get(department) + " employees");
					System.out.printf("\t\tMaximum Salary: $%10.2f\n",maxSal(tempEmployee, department));
					System.out.printf("\t\tMinimum Salary: $%10.2f\n",minSal(tempEmployee, department));
					System.out.printf("\t\tAverage Salary: $%10.2f\n",averageSal(tempEmployee, department));
				}
			}
			if(departments.size() == 1)
				System.out.println("There is "+departments.size()+" department in the database.");
			else
				System.out.println("There are "+departments.size()+" departments in the database.");
			if(tempEmployee.size()==1)
				System.out.println("There is "+tempEmployee.size()+" employee in the database.");
			else
				System.out.println("There are "+tempEmployee.size()+" employees in the database.");
		}
		else
		{
			System.out.println("There are no departments in the database.");
			System.out.println("Employee database is empty.");
		}
	}
	private static int countDepartment(Map<Integer, Employee> tempEmployee, String department)
	{
		int count = 0;
		for(int i : tempEmployee.keySet())
		{
			if(tempEmployee.get(i).getDepartment().equalsIgnoreCase(department))
				count ++;
		}
		return count;
	}
	private static double averageSal(Map<Integer, Employee> tempEmployee, String department)
	{
		double total = 0;
		for(int i : tempEmployee.keySet())
		{
			if(tempEmployee.get(i).getDepartment().equalsIgnoreCase(department))
				total += tempEmployee.get(i).getSalary();
		}
		return total/countDepartment(tempEmployee, department);
	}
	private static double maxSal(Map<Integer, Employee> tempEmployee, String department)
	{
		double max = 0;
		for(int i : tempEmployee.keySet())
		{
			if(tempEmployee.get(i).getDepartment().equalsIgnoreCase(department))
				if(tempEmployee.get(i).getSalary() > max)
					max = tempEmployee.get(i).getSalary();
		}
		return max;
	}
	private static double minSal(Map<Integer, Employee> tempEmployee, String department)
	{
		double min = 0;
		for(int i : tempEmployee.keySet())
		{
			if(tempEmployee.get(i).getDepartment().equalsIgnoreCase(department))
			{
				if(min == 0 || tempEmployee.get(i).getSalary() < min)
					min = tempEmployee.get(i).getSalary();
			}
		}
		return min;
	}
	private static void displayAll(Map<Integer, Employee> tempEmployee) {
		// TODO Auto-generated method stub
		if(tempEmployee.isEmpty())
			System.out.println("Employee database is empty.");
		else
		{
			for(int i : tempEmployee.keySet())
			{
				System.out.println("Employee ID: "+i);
				System.out.println("\tName: "+ tempEmployee.get(i).getName());
				System.out.println("\tDepartment: "+ tempEmployee.get(i).getDepartment());
				System.out.println("\tTitle: "+tempEmployee.get(i).getTitle());
				System.out.printf("\tSalary: %,.2f\n", tempEmployee.get(i).getSalary());
			}
			if(tempEmployee.size() == 1)
				System.out.println("There is "+tempEmployee.size()+" employee in the database.");
			else
				System.out.println("There are "+tempEmployee.size()+" employees in the database.");
		}
	}
	private static void findEmployeeByName(Map<Integer, Employee> tempEmployee, String name ) {
		// TODO Auto-generated method stub
		boolean found = false;
		for(int keys : tempEmployee.keySet())
		{
			if(tempEmployee.get(keys).getName().equalsIgnoreCase(name))
			{
				found = true;
				System.out.println("Employee ID: "+keys);
				System.out.println("\tName: "+ tempEmployee.get(keys).getName());
				System.out.println("\tDepartment: "+ tempEmployee.get(keys).getDepartment());
				System.out.println("\tTitle: "+tempEmployee.get(keys).getTitle());
				System.out.printf("\tSalary: %,.2f\n", tempEmployee.get(keys).getSalary());
			}
		}
		if(!found)
			System.out.println("No employee with "+name+" works here");
	}
	private static void findEmployeeByID(Map<Integer, Employee> tempEmployee, int idNum) {
		// TODO Auto-generated method stub
		boolean found = false;
		for(int keys : tempEmployee.keySet())
		{
			if(keys == idNum)
			{
				found = true;
				System.out.println("Employee ID: "+keys);
				System.out.println("\tName: "+ tempEmployee.get(keys).getName());
				System.out.println("\tDepartment: "+ tempEmployee.get(keys).getDepartment());
				System.out.println("\tTitle: "+tempEmployee.get(keys).getTitle());
				System.out.printf("\tSalary: %,.2f\n", tempEmployee.get(keys).getSalary());
			}
		}
		if(!found)
			System.out.println("No employee with "+idNum+" works here");
	}
	private static void menu()
	{
		System.out.println("Main Menu:");
		System.out.println("1.\tAdd an Employee");
		System.out.println("2.\tFind an Employee (By Employee ID)");
		System.out.println("3.\tFind an Employee (By Name)");
		System.out.println("4.\tDelete an Employee");
		System.out.println("5.\tDisplay Statistics");
		System.out.println("6.\tDisplay All Employees");
		System.out.println("7.\tExit");
	}
	private static Map<Integer, Employee> load(String fileName) throws FileNotFoundException
	{
		Map<Integer, Employee> tempEmployee = new HashMap<>();
		File file = new File(fileName);
		if(file.exists())
		{
			Scanner inputFile = new Scanner(file);
			while(inputFile.hasNext()) {
				String[] data = inputFile.nextLine().split(",");
			    tempEmployee.put(Integer.parseInt(data[0]),new Employee(Integer.parseInt(data[0]),data[1],data[2],data[3], Double.parseDouble(data[4])));
			}
			inputFile.close();
		}
		return tempEmployee;
	}
	private static void save(Map<Integer,Employee> tempEmployee, File file) throws IOException
	{
		PrintWriter outputFile = new PrintWriter(file);
		for(int keys : tempEmployee.keySet())
		{
			outputFile.append(Integer.toString(keys)+","+tempEmployee.get(keys).getName()+","+tempEmployee.get(keys).getDepartment()+","+tempEmployee.get(keys).getTitle()+","+Double.toString(tempEmployee.get(keys).getSalary()));
			outputFile.append('\n');
		}
		outputFile.close();
	}
}

