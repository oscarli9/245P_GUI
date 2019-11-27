public class StudentInfo {
    private String id;
    private String firstName;
    private String lastName;
    private String major;
    private String currentGrade;
    private String gradeOption;
    private String honorStatus;
    private String note;
    private String photoSrc;

    public StudentInfo(String id, String firstName, String lastName, String major, String currentGrade,
                       String gradeOption, String honorStatus, String note, String photoSrc) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.currentGrade = currentGrade;
        this.gradeOption = gradeOption;
        this.honorStatus = honorStatus;
        this.note = note;
        this.photoSrc = photoSrc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCurrentGrade() {
        return currentGrade;
    }

    public void setCurrentGrade(String currentGrade) {
        this.currentGrade = currentGrade;
    }

    public String getGradeOption() {
        return gradeOption;
    }

    public void setGradeOption(String gradeOption) {
        this.gradeOption = gradeOption;
    }

    public String getHonorStatus() {
        return honorStatus;
    }

    public void setHonorStatus(String honorStatus) {
        this.honorStatus = honorStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhotoSrc() {
        return photoSrc;
    }

    public void setPhotoSrc(String photoSrc) {
        this.photoSrc = photoSrc;
    }
}
