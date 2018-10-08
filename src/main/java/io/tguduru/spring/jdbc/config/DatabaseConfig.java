package io.tguduru.spring.jdbc.config;

/**
 * @author Guduru, Thirupathi Reddy
 *         created 10/3/18
 */
public class DatabaseConfig {
    private String url;
    private String userName;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
