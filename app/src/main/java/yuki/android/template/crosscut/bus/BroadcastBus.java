package yuki.android.template.crosscut.bus;

public abstract class BroadcastBus {

    private static final BusStream BUS_STREAM = new BusStream();

    public static BusStream stream() {
        return BUS_STREAM;
    }
}
