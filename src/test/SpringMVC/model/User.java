package test.SpringMVC.model;

/**
 * Created by gaoshouxin on 2017/5/7 0007.
 */
public class User {
    String userId ;
    String userPhone;
    String userEmail;
    String fileSum;
    String updateTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFileSum() {
        return fileSum;
    }

    public void setFileSum(String fileSum) {
        this.fileSum = fileSum;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
