package es.angelillo15.mast.api.factory;

public interface CommonFactory<C, F> {
  C create(F factory);
}
