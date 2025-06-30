package cavcav.atm.entity;

public enum AccountStatus {
    ACTIVE("Aktif"),
    BLOCKED("Blokeli"),
    SUSPENDED("Askıya Alınmış");

    private final String description;

    AccountStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
