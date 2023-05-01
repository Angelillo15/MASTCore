package es.angelillo15.mast.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class IPBanModel {
    private int id = 0;
    private String ip = "";
    private BanModel banModel = null;
    private int ban_id = 0;
}
