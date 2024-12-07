package ru.serburyat.yandex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ATM2Test {

    @Test
    void When_GetCashAndAtmHasMoney_Expect_Success() {

        var cashMap = new HashMap<Integer, Integer>();
        cashMap.put(5000, 2);
        cashMap.put(1000, 1);
        cashMap.put(500, 1);
        cashMap.put(100, 2);
        cashMap.put(50, 0);
        var atm2 = new ATM2(cashMap);

        var expectedCashList = List.of(5000, 1000, 500, 100, 100);
        assertEquals(expectedCashList, atm2.getCash(6700));

        var remindAtmCash = atm2.getCashMap();

        assertEquals(remindAtmCash.get(5000), 1);
        assertEquals(remindAtmCash.get(1000), 0);
        assertEquals(remindAtmCash.get(500), 0);
        assertEquals(remindAtmCash.get(100), 0);
        assertEquals(remindAtmCash.get(50), 0);
    }

    @Test
    void When_GetCashAndAtmHasMoneyAndSeveralTransactions_Expect_Success() {

        var cashMap = new HashMap<Integer, Integer>();
        cashMap.put(5000, 2);
        cashMap.put(1000, 1);
        cashMap.put(500, 1);
        cashMap.put(100, 2);
        cashMap.put(50, 1);
        var atm2 = new ATM2(cashMap);

        var expectedCashList = List.of(50);
        assertEquals(expectedCashList, atm2.getCash(50));

        var remindAtmCash = atm2.getCashMap();

        assertEquals(remindAtmCash.get(5000), 2);
        assertEquals(remindAtmCash.get(1000), 1);
        assertEquals(remindAtmCash.get(500), 1);
        assertEquals(remindAtmCash.get(100), 2);
        assertEquals(remindAtmCash.get(50), 0);

        assertEquals(List.of(5000, 5000, 100, 100), atm2.getCash(10200));

        assertEquals(remindAtmCash.get(5000), 0);
        assertEquals(remindAtmCash.get(1000), 1);
        assertEquals(remindAtmCash.get(500), 1);
        assertEquals(remindAtmCash.get(100), 0);
        assertEquals(remindAtmCash.get(50), 0);

        assertEquals(List.of(1000, 500), atm2.getCash(1500));

        assertEquals(remindAtmCash.get(5000), 0);
        assertEquals(remindAtmCash.get(1000), 0);
        assertEquals(remindAtmCash.get(500), 0);
        assertEquals(remindAtmCash.get(100), 0);
        assertEquals(remindAtmCash.get(50), 0);
    }

    @Test
    void When_GetCashAndAtmHasNotMoney_Expect_RuntimeExceptionAndMoneyReturn() {

        var cashMap = new HashMap<Integer, Integer>();
        cashMap.put(5000, 0);
        cashMap.put(1000, 4);
        cashMap.put(500, 2);
        cashMap.put(100, 4);
        cashMap.put(50, 1);
        var atm2 = new ATM2(cashMap);

        assertEquals(List.of(1000, 1000, 1000, 1000, 100, 50), atm2.getCash(4150));

        var remindAtmCash = atm2.getCashMap();
        assertEquals(remindAtmCash.get(5000), 0);
        assertEquals(remindAtmCash.get(1000), 0);
        assertEquals(remindAtmCash.get(500), 2);
        assertEquals(remindAtmCash.get(100), 3);
        assertEquals(remindAtmCash.get(50), 0);


        assertThrows(
                RuntimeException.class,
                () -> atm2.getCash(1350),
                "Не смогли выдать наличку для суммы: 6700"
        );

        assertEquals(remindAtmCash.get(5000), 0);
        assertEquals(remindAtmCash.get(1000), 0);
        assertEquals(remindAtmCash.get(500), 2);
        assertEquals(remindAtmCash.get(100), 3);
        assertEquals(remindAtmCash.get(50), 0);
    }

}
