package es.angelillo15.mast.api;

public enum Permissions {
    STAFF("mast.staff"),
    STAFF_ADMIN("mast.admin"),
    STAFF_STAFFCHAT("mast.staffchat"),
    STAFF_VANISH_SEE("mast.vanish.see"),
    STAFF_BUILD("mast.build"),
    STAFF_LIST("mast.staff.stafflist"),
    STAFF_FREEZE("mast.freeze"),
    STAFF_FREEZE_BYPASS("mast.freeze.bypass"),
    STAFF_ANNOUCER_JOIN("mast.staff.join"),
    STAFF_ANNOUCER_QUIT("mast.staff.quit");

    private String permission;
    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }


}
