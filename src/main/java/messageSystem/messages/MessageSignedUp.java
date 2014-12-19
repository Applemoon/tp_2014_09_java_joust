package messageSystem.messages;

import interfaces.services.AccountService;
import messageSystem.Address;
import utils.ProcessState;


public class MessageSignedUp extends MessageToAccountService {
    private String login;
    private ProcessState registerState;

    public MessageSignedUp(Address from, Address to, String login, ProcessState registerState) {
        super(from, to);
        this.login = login;
        this.registerState = registerState;
    }

    @Override
    public void exec(AccountService accountService) {
        accountService.setSignUpState(login, registerState);
    }
}
