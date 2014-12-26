package messageSystem;

import interfaces.services.DBService;


public class AddressService {
    private Address dbService;

    public void registerDBService(DBService dbService) {
        this.dbService = dbService.getAddress();
    }

    public Address getDBServiceAddress() {
        return dbService;
    }
}
