package com.dianping.rotate.admin.model;

import com.dianping.ba.base.organizationalstructure.api.user.dto.enu.EmployeeStatus;

public class UserProfile {
	private String name; // 宋顺
	private String userName; // shun.song
	private int loginId;
	private String employeeNumber;

    private com.dianping.ba.base.organizationalstructure.api.user.dto.enu.EmployeeStatus employeeStatus;
    private String email;
    private String mobileNo;
    private int officeExtNo;
    private int reportToLoginId;
    private String reportToName;
    private int departmentId;
    private String departmentName;
    private int directReportToLoginId;
    private int cityId;
    private String cityName;
    private int provinceId;
    private String provinceName;
    //城市名称，省
    //角色：销售，主管，运营
    private int sex;

	/**
	 * 
	 * @return real name like 宋顺
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getOfficeExtNo() {
        return officeExtNo;
    }

    public void setOfficeExtNo(int officeExtNo) {
        this.officeExtNo = officeExtNo;
    }

    public int getReportToLoginId() {
        return reportToLoginId;
    }

    public void setReportToLoginId(int reportToLoginId) {
        this.reportToLoginId = reportToLoginId;
    }

    public String getReportToName() {
        return reportToName;
    }

    public void setReportToName(String reportToName) {
        this.reportToName = reportToName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDirectReportToLoginId() {
        return directReportToLoginId;
    }

    public void setDirectReportToLoginId(int directReportToLoginId) {
        this.directReportToLoginId = directReportToLoginId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    /**
	 * 
	 * @return user name like shun.song
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return String.format("%s|%s|%s|%s", name, userName, loginId,
				employeeNumber);
	}

}
