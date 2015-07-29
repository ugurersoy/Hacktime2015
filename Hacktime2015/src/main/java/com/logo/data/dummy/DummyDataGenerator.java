package com.logo.data.dummy;

import java.util.Arrays;
import java.util.Collection;

import com.logo.domain.DashboardNotification;

public abstract class DummyDataGenerator {

//    static String randomFirstName() {
//        String[] names = { "Dave", "Mike", "Katherine", "Jonas", "Linus",
//                "Bob", "Anne", "Minna", "Elisa", "George", "Mathias", "Pekka",
//                "Fredrik", "Kate", "Teppo", "Kim", "Samatha", "Sam", "Linda",
//                "Jo", "Sarah", "Ray", "Michael", "Steve" };
//        return names[(int) (Math.random() * names.length)];
//    }

//    static String randomLastName() {
//        String[] names = { "Smith", "Lehtinen", "Chandler", "Hewlett",
//                "Packard", "Jobs", "Buffet", "Reagan", "Carthy", "Wu",
//                "Johnson", "Williams", "Jones", "Brown", "Davis", "Moore",
//                "Wilson", "Taylor", "Anderson", "Jackson", "White", "Harris",
//                "Martin", "King", "Lee", "Walker", "Wright", "Clark",
//                "Robinson", "Garcia", "Thomas", "Hall", "Lopez", "Scott",
//                "Adams", "Barker", "Morris", "Cook", "Rogers", "Rivera",
//                "Gray", "Price", "Perry", "Powell", "Russell", "Diaz" };
//        return names[(int) (Math.random() * names.length)];
//    }

//    static String randomCompanyName() {
//
//        String name = randomName();
//        if (Math.random() < 0.03) {
//            name += " Technologies";
//        } else if (Math.random() < 0.02) {
//            name += " Investment";
//        }
//        if (Math.random() < 0.3) {
//            name += " Inc";
//        } else if (Math.random() < 0.2) {
//            name += " Ltd.";
//        }
//
//        return name;
//    }

//    static String randomHTML(int words) {
//        StringBuffer sb = new StringBuffer();
//        while (words > 0) {
//            sb.append("<h2>");
//            int len = (int) (Math.random() * 4) + 1;
//            sb.append("</h2>");
//            words -= len;
//            int paragraphs = 1 + (int) (Math.random() * 3);
//            while (paragraphs-- > 0 && words > 0) {
//                sb.append("<p>");
//                len = (int) (Math.random() * 40) + 3;
//                sb.append("</p>");
//                words -= len;
//            }
//        }
//        return sb.toString();
//    }

    static Collection<DashboardNotification> randomNotifications() {
        DashboardNotification n1 = new DashboardNotification();
        n1.setId(1);
//        n1.setFirstName(randomFirstName());
//        n1.setLastName(randomLastName());
//        n1.setAction("created a new report");
//        n1.setPrettyTime("25 minutes ago");
//        n1.setContent(randomText(18));

        DashboardNotification n2 = new DashboardNotification();
        n2.setId(2);
//        n2.setFirstName(randomFirstName());
//        n2.setLastName(randomLastName());
//        n2.setAction("changed the schedule");
//        n2.setPrettyTime("2 days ago");
//        n2.setContent(randomText(10));

        return Arrays.asList(n1, n2);
    }

    public static int[] randomSparklineValues(int howMany, int min, int max) {
        int[] values = new int[howMany];

        for (int i = 0; i < howMany; i++) {
            values[i] = (int) (min + (Math.random() * (max - min)));
        }

        return values;
    }

}