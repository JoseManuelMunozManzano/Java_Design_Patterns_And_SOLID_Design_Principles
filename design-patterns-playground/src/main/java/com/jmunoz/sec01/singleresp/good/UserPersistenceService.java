package com.jmunoz.sec01.singleresp.good;

public class UserPersistenceService {

    private Store store = new Store();

    public void saveUser(User user) {
        store.store(user);
    }
}
