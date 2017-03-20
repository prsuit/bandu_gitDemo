package com.tt;
public final class SkEgn {

    static {
        System.loadLibrary("skegn");
    }

    public interface skegn_callback {
        public abstract int run(byte[] id, int type, byte[] data, int size);
    }

    public static int SKEGN_MESSAGE_TYPE_JSON = 1;
    public static int SKEGN_MESSAGE_TYPE_BIN = 2;
    
    public static int SKEGN_OPT_GET_VERSION = 1;
    public static int SKEGN_OPT_GET_MODULES = 2;
    public static int SKEGN_OPT_GET_TRAFFIC = 3;
    //public static int SKEGN_OPT_GET_DEVICE_ID = 4;

    public static native long skegn_new(String cfg, Object context);
    public static native int  skegn_delete(long engine);
    public static native int  skegn_start(long engine, String param, byte[] id,  skegn_callback callback, Object context);
    public static native int  skegn_feed(long engine, byte[] data, int size);
    public static native int  skegn_stop(long engine);
    public static native int  skegn_get_device_id(byte[] device_id, Object context);
    public static native int  skegn_cancel(long engine);
    public static native int  skegn_log(long engine, String log);
    public static native int  skegn_opt(long engine, int opt, byte[] data, int size);
}