package com.scb.customer.util;

import java.util.Random;
import java.util.stream.Collectors;

public class BankingUtils {
    public static String generateAccountNumber() {
        return new Random().ints(10, 0, 10)
                .mapToObj(Integer::toString)
                .limit(12)
                .collect(Collectors.joining());
    }
}
