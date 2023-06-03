package network.datatypes;

public class MessageData extends Data {
    private String senderName;
    private String message;

    // data to receive by client handlers / server
    public MessageData(String message) {
        this.message = message;
    }

    // data to receive by clients
    public MessageData(String senderName, String message) {
        this.senderName = senderName;
        this.message = message;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    String getSenderName() {
        return senderName;
    }

    String getMessage() {
        return message;
    }

    public String getFormattedMessage() {
        return senderName + ": " + message;
    }
}
