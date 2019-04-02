package pinetree.lifenavi.log;

public class Log {

    private final static boolean isPrinter = true;

    public static void v(String tag, String logs) {
        if (!isPrinter)
            return;
        android.util.Log.v(tag, logs);
    }

    public static void i(String tag, String logs) {
        if (!isPrinter)
            return;
        android.util.Log.i(tag, logs);
    }

    public static void d(String tag, String logs) {
        if (!isPrinter)
            return;
        android.util.Log.d(tag, logs);
    }

    public static void e(String tag, String logs) {
        if (!isPrinter)
            return;
        android.util.Log.e(tag, logs);
    }
}
