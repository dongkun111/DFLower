package app.example.phanmembanhoa.Model;

public class User {
    private String username;
    private String password;
    private String phone;
    private String name;
    private String mail;

    public User(){

    }


    public User(String name, String password, String username,String phone, String mail) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.mail = mail;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

