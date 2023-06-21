package es.angelillo15.mast.api.exceptions;

public class PlayerNotBannedException extends RuntimeException{
    public PlayerNotBannedException(String message) {
        super(message);
    }
}
