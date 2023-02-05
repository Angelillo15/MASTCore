package es.angelillo15.mast.velocity;

import com.velocitypowered.api.plugin.annotation.DataDirectory;
import es.angelillo15.mast.velocity.utils.TextUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;

import java.nio.file.Path;

public class MAStaff {
    @Getter
    @Setter
    private Logger Plogger;

    public void onEnable() {
        Plogger = MAStaffLoader.getLogger();
    }
    public void drawLogo() {
        Plogger.info(TextUtils.colorize("&a"));
        Plogger.info(TextUtils.colorize("&a ███▄ ▄███▓ ▄▄▄        ██████ ▄▄▄█████▓ ▄▄▄        █████▒ █████▒"));
        Plogger.info(TextUtils.colorize("&a ▓██▒▀█▀ ██▒▒████▄    ▒██    ▒ ▓  ██▒ ▓▒▒████▄    ▓██   ▒▓██   ▒"));
        Plogger.info(TextUtils.colorize("&a ▓██    ▓██░▒██  ▀█▄  ░ ▓██▄   ▒ ▓██░ ▒░▒██  ▀█▄  ▒████ ░▒████ ░"));
        Plogger.info(TextUtils.colorize("&a ▒██    ▒██ ░██▄▄▄▄██   ▒   ██▒░ ▓██▓ ░ ░██▄▄▄▄██ ░▓█▒  ░░▓█▒  ░"));
        Plogger.info(TextUtils.colorize("&a ▒██▒   ░██▒ ▓█   ▓██▒▒██████▒▒  ▒██▒ ░  ▓█   ▓██▒░▒█░   ░▒█░"));
        Plogger.info(TextUtils.colorize("&a ░ ▒░   ░  ░ ▒▒   ▓▒█░▒ ▒▓▒ ▒ ░  ▒ ░░    ▒▒   ▓▒█░ ▒ ░    ▒ ░"));
        Plogger.info(TextUtils.colorize("&a ░  ░      ░  ▒   ▒▒ ░░ ░▒  ░ ░    ░      ▒   ▒▒ ░ ░      ░"));
        Plogger.info(TextUtils.colorize("&a ░      ░     ░   ▒   ░  ░  ░    ░        ░   ▒    ░ ░    ░ ░"));
        Plogger.info(TextUtils.colorize("&a ░         ░  ░      ░                 ░  ░"));
        Plogger.info(TextUtils.colorize("&a                                                version: " + Constants.VERSION));
    }
}
