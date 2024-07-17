package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class User {
    private String userId;

    private String userName;

    private String email;

    private String password;

    private Date createTime;

    private Date expireTime;

    private String department;

    private String roleId;

    private String organization;

    private String idInstitution;

    private Double version;

    private String cdPhone;

    private String userFlag;

    private String cdFrozenState;
    
    private boolean flag;

    private String cdFrozenState2;

    private Date dtLogin;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }

    public String getIdInstitution() {
        return idInstitution;
    }

    public void setIdInstitution(String idInstitution) {
        this.idInstitution = idInstitution == null ? null : idInstitution.trim();
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getCdPhone() {
        return cdPhone;
    }

    public void setCdPhone(String cdPhone) {
        this.cdPhone = cdPhone == null ? null : cdPhone.trim();
    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag == null ? null : userFlag.trim();
    }

    public String getCdFrozenState() {
        return cdFrozenState;
    }

    public void setCdFrozenState(String cdFrozenState) {
        this.cdFrozenState = cdFrozenState == null ? null : cdFrozenState.trim();
    }

    public String getCdFrozenState2() {
        return cdFrozenState2;
    }

    public void setCdFrozenState2(String cdFrozenState2) {
        this.cdFrozenState2 = cdFrozenState2 == null ? null : cdFrozenState2.trim();
    }

    public Date getDtLogin() {
        return dtLogin;
    }

    public void setDtLogin(Date dtLogin) {
        this.dtLogin = dtLogin;
    }
    

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public static List<User> createDemoData(Integer num) {
		List<User> users = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			User u = new User();
			users.add(u);
			u.setUserId("用户"+i);
			u.setUserName("用户名"+i);
			u.setCdPhone("1234322349"+i);
			u.setEmail("demo"+i+"@163.com");
			u.setOrganization("公司"+i);
			u.setDepartment("研发");
			u.setFlag(true);
		}
		return users;
	}

	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ ", createTime=" + createTime + ", expireTime=" + expireTime + ", department=" + department
				+ ", roleId=" + roleId + ", organization=" + organization + ", idInstitution=" + idInstitution
				+ ", version=" + version + ", cdPhone=" + cdPhone + ", userFlag=" + userFlag + ", cdFrozenState="
				+ cdFrozenState + ", cdFrozenState2=" + cdFrozenState2 + ", dtLogin=" + dtLogin + "]";
	}
    
    
}