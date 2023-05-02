package es.angelillo15.mast.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    public static final String UNKNOWN = "unknown";
    private int ID = 0;
    private String UUID = UNKNOWN;
    private String username = UNKNOWN;
    private String lastIP = UNKNOWN;
    private String regIP = UNKNOWN;
    private Long firstLogin = 0L;
    private Long lastLogin = 0L;
}
