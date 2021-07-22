package com.eshwarne.reactiveprog;

import rx.Observable;

import java.util.ArrayList;
import java.util.List;

public class ReactiveProg {
    static class User{
        String name;
        double reputation;
        public User(String name, double reputation){
            this.name = name;
            this.reputation = reputation;
        }
        @Override
        public String toString(){
            return name;
        }
    }
    public static void main(String[] args){
        /**
         * BASIC OBSERVABLE AND MAP OPERATOR
         * */
        List<String> greetingsToUsers = new ArrayList<>();

        String[] namesFromDatabase = new String[]{"Eshwar","NE","Sundar"};
        Observable<String> namesFromDatabaseSubject = Observable.from(namesFromDatabase).map(String::toUpperCase);
        namesFromDatabaseSubject.subscribe((user)->greetingsToUsers.add("Welcome, " + user),Throwable::printStackTrace,()->greetingsToUsers.add("NO MORE USERS"));

        System.out.println(greetingsToUsers);

        /**
        * USE GROUP BY OPERATOR ON INCOMING STREAM
        * Let us add users to malicious category if our data source has reputation less than 1
        * d*/
        List<User> usersFromDatabase = new ArrayList<>();
        usersFromDatabase.add(new User("Eshwar",21.0));
        usersFromDatabase.add(new User("NE", 0.9));
        List<User> maliciousUsers = new ArrayList<>();
        List<User> goodUsers = new ArrayList<>();
        Observable<User> userSubject = Observable.from(usersFromDatabase);
        userSubject.groupBy(user -> user.reputation < 1 ? "MALICIOUS" : "GOOD")
                .subscribe(group->{
                    group.subscribe(user -> {
                        if (group.getKey().equals("MALICIOUS")) {
                            maliciousUsers.add(user);
                        } else {
                            goodUsers.add(user);
                        }
                    });
                });
        System.out.println("MALICIOUS USERS: ");
        System.out.println(maliciousUsers);
        System.out.println("GOOD USERS: ");
        System.out.println(goodUsers);


    }
}
