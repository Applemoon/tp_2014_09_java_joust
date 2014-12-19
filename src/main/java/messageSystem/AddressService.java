package messageSystem;

import interfaces.services.AccountService;
import interfaces.services.DBService;

import java.util.concurrent.atomic.AtomicInteger;

public class AddressService {
    private Address dbService;
    private Address accountService;

    private AtomicInteger accountServiceCounter = new AtomicInteger();

    public void registerDBService(DBService dbService) {
        this.dbService = dbService.getAddress();
    }

    public void registerAccountService(AccountService accountService) {
        this.accountService = accountService.getAddress();
    }

    public Address getDBServiceAddress() {
        return dbService;
    }

    public synchronized Address getAccountServiceAddress() {
        return accountService;
    }
}
