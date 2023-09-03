package es.angelillo15.mast.api;

import lombok.Getter;

public class Constants {
    public static final String VERSION = "{version}";
    public static final String COMMIT = "{git-commit}";
    public static final String COMMIT_USER = "{git-user}";
    public static final String COMMIT_TIME = "{git-date}";
    public static final String GIT_BRANCH = "{git-branch}";
    public static final boolean DEV_MODE = false;
    private Constants() {}

    public static String getVersion() {
        return VERSION;
    }


}
