package cavcav.atm.entity;

public enum Role {
    ADMIN("Admin"),
    CUSTOMER("Customer");

    private final String role;
    private Role(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
