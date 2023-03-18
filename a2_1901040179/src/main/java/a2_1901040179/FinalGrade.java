package a2_1901040179;

public enum FinalGrade {
    E("excellent"), G("good"), P("pass"), F("failed");
	
    private final String assessment;
    FinalGrade(String assessment) {
        this.assessment = assessment;
    }
    public String getAssessment(){
        return this.assessment;
    }
}
