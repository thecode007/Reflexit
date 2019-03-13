# Reflexit
An open source android automation system for automating almost everything in the android echo system. 
Inspired by Tasker.

# Core diagram
 https://drive.google.com/open?id=1Sql2BCZ3ZmLRbkx6vBg5EB7nCFz07Bu9
 
 # How it is Desgined
 
 All the basis of this app are in the package com.reflex.core
 
 Lets begin with the model:
   - Reflex: is a contract abstracting an action provided by an app.
   - Trigger: is the invoker that an action binds to, it is also provided by an app.
 
 To decouple the action from trigger a ReflexProvider and TriggerProvider are created, which are FlyWieghts patterns 
 and dictionaries that map the name of the reflex and trigger respectively.
 
 So an App will be a TriggerProvider and/or ReflexProvider. An App will use those providers to fulfill a Dependancy injection
 to complete the commander pattern, So the App is the commander and a provider.
 
 # Example of exposing SMS App
 
 ```
public class SmsApp extends App {

    public static SmsApp instance;

    public static SmsApp getInstance() {
        if (instance == null) {
            instance = new SmsApp();
        }
        return instance;
    }

    private SmsApp() {
        super(R.drawable.message_app, new TriggerProvider() {
        }, SmsReflexes.getInstance());
        triggerProvider.register(new SmsReceivedTrigger(this));
    }
}
```
Now lets create a trigger that will be registered to the Sms app trigger provider
 
 ```
 class SmsReceivedTrigger extends Trigger {

    SmsReceivedTrigger(App app) {
        super("received SMS","android.provider.Telephony.SMS_RECEIVED", app);
        fields.add("Number");
        fields.add("Message");
    }

    @Override
    protected void initReceiverBody(Context context,Intent intent) {
        AppProvider appRepository = AppProvider.getInstance();
        Log.wtf(getClass().getName(), "Message received");
        for (ActionBootstrap bootstrap : bootstraps) {
            ObjectNode resultCallback = JsonNodeFactory.instance.objectNode();
            app.execute(ReflexProvider.FILTER_SMS_FROM_PROVIDER, intent, bootstrap.getConstraints(), resultCallback);
            Toast.makeText(context, "Result call back: " + resultCallback.toString(), Toast.LENGTH_LONG).show();
            if (resultCallback.get("matched") == null || !resultCallback.get("matched").asBoolean()) {
                continue;
            }

            App targetApp = appRepository.getApp(bootstrap.getApp());
            if (targetApp == null) {
                continue;
            }
            targetApp.execute(bootstrap.getAction());
        }
    }
}

 ```
After that the SMSReflexes which is the reflex provider of Sms App is created which is a singlton

 
With help of the Jakson JSON parsing library we are bootstraping the trigegrs and the actions. 
Lets automate an event triggered by the Sms app and bind an action from OS which is unmute when SMS unmute message 
received from ahmad's number ( the os is also exposed like SMS app)

 ```
 ActionBootstrap actionBootstrap = new ActionBootstrap("OS", "Unmute",{"number":76690929, "message":"Unmute"}, 
 "Unmute when Ahmad calls");
 DatabaseAccess.addActionToTrigger("SmsApp", "android.provider.Telephony.SMS_RECEIVED", actionBootstrap);
 ```
 After adding this line the JSON File will be updated and once the message is received all the action in the
 JSON File will be binded again to the Trigger and executed according to the filter
 
 For more details check the packages com.reflex.core and their implementation in com.reflex.services

