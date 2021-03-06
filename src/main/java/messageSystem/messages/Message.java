package messageSystem.messages;

import interfaces.Abonent;
import messageSystem.Address;

public abstract class Message {
    private final Address from;
    private final Address to;

    Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Abonent abonent);
}
