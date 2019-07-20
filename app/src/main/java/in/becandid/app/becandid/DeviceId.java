package in.becandid.app.becandid;

public class DeviceId {
    private static String r; // server key
    private static String di; // device id

    public static void sr(String v) {
        r = v;
    }

    public static void sdi(String v) {
        di = v;
    }

    public static String gr() {
        return r;
    }

    public static String gdi() {
        return di;
    }
}
