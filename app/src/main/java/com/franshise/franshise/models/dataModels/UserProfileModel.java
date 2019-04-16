package com.franshise.franshise.models.dataModels;

public class UserProfileModel
{
    private String company_name,company_type,company_phone,fax,admin_phone,admin_conversion;

    public String getCompany_name() {
        return company_name;
    }

    public String getCompany_type() {
        return company_type;
    }

    public String getCompany_phone() {
        return company_phone;
    }

    public String getFax() {
        return fax;
    }

    public String getAdmin_phone() {
        return admin_phone;
    }

    public String getAdmin_conversion() {
        return admin_conversion;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setCompany_type(String company_type) {
        this.company_type = company_type;
    }

    public void setCompany_phone(String company_phone) {
        this.company_phone = company_phone;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setAdmin_phone(String admin_phone) {
        this.admin_phone = admin_phone;
    }

    public void setAdmin_conversion(String admin_conversion) {
        this.admin_conversion = admin_conversion;
    }
}
