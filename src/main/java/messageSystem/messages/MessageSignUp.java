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
        ProcessState signUpState = ProcessState.Error;
        if (!dbService.validateUser(login, password)) {
            dbService.createUser(login, password);
            if (dbService.validateUser(login, password)) {
                signUpState = ProcessState.DoneOK;
            }
        } else {
            signUpState = ProcessState.DoneNotOK;
        }

        final Message back = new MessageSignedUp(getTo(), getFrom(), login, signUpState);
        dbService.getMessageSystem().sendMessage(back);
    }
}
