package a2_1901040179;

public abstract class Modules {
	
	private String code;
	private String name;
	private int semester;
	private double credits;
	
	private static int[] se = new int[10];

	public Modules(String name, int semester, double credits) {
		if(semester < 1 || semester > 9) {
			return;
		}
		if(se[semester] <= 9) {
			this.code = "M" + semester + "0" + se[semester];
		} else {
			this.code = "M" + semester + se[semester];
		}
		se[semester]++;
		this.name = name;
		this.semester = semester;
		this.credits = credits;
	}



	public String getCode() {
		return code;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getSemester() {
		return semester;
	}



	public double getCredits() {
		return credits;
	}



	public void setCredits(double credits) {
		this.credits = credits;
	}



	@Override
	public String toString() {
		return "Modules [code=" + code + ", name=" + name + ", semester=" + semester + ", credits=" + credits + "]";
	}
	
	
	
}
