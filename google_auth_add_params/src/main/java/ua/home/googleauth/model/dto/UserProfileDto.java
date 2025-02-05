package ua.home.googleauth.model.dto;

public class UserProfileDto {
    private Long id;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String defaultPictureUrl;
    private String companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDefaultPictureUrl() {
        return defaultPictureUrl;
    }

    public void setDefaultPictureUrl(String defaultPictureUrl) {
        this.defaultPictureUrl = defaultPictureUrl;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
