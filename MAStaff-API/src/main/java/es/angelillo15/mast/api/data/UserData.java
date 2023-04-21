package es.angelillo15.mast.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private int ID = 0;
    private String UUID = "unknown";
    private String username = "unknown";
    private String lastIP = "unknown";
    private String regIP = "unknown";
    private Long firstLogin = 0L;
    private Long lastLogin = 0L;
}
