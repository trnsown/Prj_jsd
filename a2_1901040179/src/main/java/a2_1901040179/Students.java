package a2_1901040179;

public class Students {
	private String id;
	private String name;
	private String dob;
	private String address;
	private String email;
	
	
	
	private static int autoId = 2019;
	
	public Students(String name, String dob, String address, String email) {
		
		this.id = "S" + autoId;
		this.name = name;
		this.dob = dob;
		this.address = address;
		this.email = email;
		autoId++;
	}


	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "Students [id=" + id + ", name=" + name + ", dob=" + dob + ", address=" + address + ", email=" + email
				+ "]";
	}
	
	
}


