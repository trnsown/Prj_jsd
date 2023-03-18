package a2_1901040179;

public class Enrolment {
	private Students st;
	private Modules md;
	private float internalMark;
	private float examinationMark;
	
	
	public Enrolment(Students st, Modules md) {
		this.st = st;
		this.md = md;
		this.internalMark = 0;
		this.examinationMark = 0;
	}
	
	
	public String finalGrade() {
		double aggregatedMark = 0.4 * internalMark + 0.6 *examinationMark;
		if(aggregatedMark > 10 || aggregatedMark  < 0) {
			return null;
		}else {
			if(aggregatedMark <= 10 && aggregatedMark >= 8.6 ) {
				return "E";
			}
			else if(aggregatedMark <= 8.5 && aggregatedMark >= 6.6) {
				return "G";
			}
			else if(aggregatedMark <= 6.5 && aggregatedMark >= 5) {
				return "P";	
			}
			else {
				return "F";
			}
		}
	}


	public Students getSt() {
		return st;
	}


	public Modules getMd() {
		return md;
	}


	public float getInternalMark() {
		return internalMark;
	}



	public void setInternalMark(float internalMark) {
		this.internalMark = internalMark;
	}



	public float getExaminationMark() {
		return examinationMark;
	}



	public void setExaminationMark(float examinationMark) {
		this.examinationMark = examinationMark;
	}


	@Override
	public String toString() {
		return "{" + getSt()+ " " + getMd() + "}";
	}


	
	
}
