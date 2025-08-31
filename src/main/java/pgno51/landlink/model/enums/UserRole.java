package pgno51.landlink.model.enums;

public enum UserRole {
    ADMIN,
    MODERATOR,
    MARKETING_OFFICER,
    USER,
    GUEST;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
