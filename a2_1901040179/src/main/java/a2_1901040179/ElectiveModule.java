package a2_1901040179;

public class ElectiveModule extends Modules {
	private String departmentName;

	

	public ElectiveModule(String name, int semester, double credits, String departmentName) {
		super(name, semester, credits);
		this.departmentName = departmentName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
