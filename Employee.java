
public class Employee {
	String emName, emDepartment, emTitle;
	double emSalary;
	int emEID;
	public Employee()
	{
	}
	public Employee(int eid,String name, String department, String title, double salary) {
		// TODO Auto-generated constructor stub
		emEID = eid;
		emName = name;
		emDepartment = department;
		emTitle = title;
		emSalary = salary;
	}
	public String getName()
	{
		return emName;
	}
	public String getDepartment()
	{
		return emDepartment;
	}
	public String getTitle()
	{
		return emTitle;
	}
	public double getSalary()
	{
		return emSalary;
	}
	public int getEID()
	{
		return emEID;
	}
	
}
