package es.angelillo15.mast.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private int ID;
    private String UUID;
    private String username;
    private String lastIP;
    private String regIP;
    private Long firstLogin;
    private Long lastLogin;
}
