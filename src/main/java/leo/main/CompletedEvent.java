package leo.main;

public interface CompletedEvent {
    int completed();
    int missed();
    int mistake();
    int speed();

    default String getStatistic() {
        return completed() + " -" +
                (missed() + mistake()) + " " +
                100*completed() / (missed() + mistake() + completed()) + "% x" + speed();
    }
}
