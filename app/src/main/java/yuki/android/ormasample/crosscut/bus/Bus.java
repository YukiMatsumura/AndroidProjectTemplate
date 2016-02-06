package yuki.android.ormasample.crosscut.bus;

public abstract class Bus {

    private static final BusStream BUS_STREAM = new BusStream();

    public static BusStream stream() {
        return BUS_STREAM;
    }
}
