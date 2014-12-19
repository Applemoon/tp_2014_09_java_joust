package messageSystem.messages;

import interfaces.Abonent;
import interfaces.services.DBService;
import messageSystem.Address;


public abstract class MessageToDBService extends Message {
    public MessageToDBService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof DBService) {
            exec((DBService) abonent);
        }
    }

    protected abstract void exec(DBService service);
}
