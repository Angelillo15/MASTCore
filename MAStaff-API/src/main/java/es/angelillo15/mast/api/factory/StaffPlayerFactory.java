package es.angelillo15.mast.api.factory;

import es.angelillo15.mast.api.IStaffPlayer;

public interface StaffPlayerFactory<T> {
  T createStaffPlayer(IStaffPlayer player);
}
