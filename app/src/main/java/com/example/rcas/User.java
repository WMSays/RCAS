package com.example.rcas;

class User {
    private String id, email, firstName, lastName,  userType, PaperTitle, PaperAbstract, PaperRef;
    //blank constructor

    public User()
    {}
    //constructor to initialize
    public User(String id, String email, String firstName, String lastName,
                String userType, String PaperTitle, String PaperAbstract, String PaperRef) {
        this.id=id;
        this.email=email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.PaperTitle= PaperTitle;
        this.PaperAbstract= PaperAbstract;
        this.PaperRef= PaperRef;
    }

    public String getId() {
        return id;
    }

    public String getPaperTitle() { return PaperTitle;}

    public String getPaperAbstract() {return PaperAbstract;}

    public String getPaperRef() {return PaperRef;}

    public String getEmail()
    {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserType() {
        return userType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setPaperTitle(String paperTitle) { this.PaperTitle= PaperTitle;}

    public void setPaperAbstract(String paperAbstract) { this.PaperAbstract= PaperAbstract;}

    public void setPaperRef(String paperRef) {        this.PaperRef= PaperRef;
    }
}
