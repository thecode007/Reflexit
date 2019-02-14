package com.reflex.services.sms;

import com.reflex.services.providers.TriggerRepository;

public class SmsTriggerRepository extends TriggerRepository {

    public static SmsTriggerRepository repository;
    private SmsTriggerRepository() {
        super();
    }

    public static SmsTriggerRepository getInstance() {
        if (repository == null){
            repository = new SmsTriggerRepository();
        }
        return repository;
    }


}
