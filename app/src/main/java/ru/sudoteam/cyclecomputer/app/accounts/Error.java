package ru.sudoteam.cyclecomputer.app.accounts;

/**
 * Created by bagrusss on 23.05.16.
 * *
 */
public class Error {

    public final String msg;
    public final int code;

    public Error(final int code, final String msg) {
        this.msg = msg;
        this.code = code;
    }


}
