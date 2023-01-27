package es.angelillo15.mast.api.report;

import es.angelillo15.mast.api.IMastPlayer;
import lombok.Getter;

public class Report {
    @Getter
    private IMastPlayer reporter;
    @Getter
    private IMastPlayer reported;
    @Getter
    private String reason;

    public Report(IMastPlayer reporter, IMastPlayer reported, String reason) {
        this.reporter = reporter;
        this.reported = reported;
        this.reason = reason;
    }
}
