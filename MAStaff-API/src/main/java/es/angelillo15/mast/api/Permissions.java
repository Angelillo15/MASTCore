package es.angelillo15.mast.api;

public enum Permissions {

    STAFF("mast.staff");
    private String permission;
    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }


}
