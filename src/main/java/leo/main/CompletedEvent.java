package leo.main;

public interface CompletedEvent {
    int completed();
    int missed();
    int mistake();
    int speed();
    String getInfo();
}
