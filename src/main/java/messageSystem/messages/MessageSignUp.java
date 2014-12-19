package messageSystem.messages;

import db.UserProfile;
import interfaces.services.DBService;
import messageSystem.Address;
import utils.ProcessState;

public class MessageSignUp extends MessageToDBService {
    private String login;
    private String password;

    public MessageSignUp(Address addressFrom, Address addressTo, UserProfile user) {
        super(addressFrom, addressTo);
        this.login = user.getLogin();
        this.password = user.getPass();
    }

    @Override
    public void exec(DBService dbService) {
        ProcessState singUpState = ProcessState.Error;
        if (dbService.validateUser(login, password)) {
            singUpState = ProcessState.DoneNotOK;
        }

        dbService.createUser(login, password);
        if (dbService.validateUser(login, password)) {
            singUpState = ProcessState.DoneOK;
        }

        final Message back = new MessageSignedUp(getTo(), getFrom(), login, singUpState);
        dbService.getMessageSystem().sendMessage(back);
    }
}
