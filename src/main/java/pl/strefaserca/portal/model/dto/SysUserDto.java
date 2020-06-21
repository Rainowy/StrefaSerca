package pl.strefaserca.portal.model.dto;

public class SysUserDto {

    private String name;
    /* Class fields */
    private String address;

    private String phone;

    private Integer zipCode;
    /* Getter and Setter methods */
    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "SysUserDto{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }
}