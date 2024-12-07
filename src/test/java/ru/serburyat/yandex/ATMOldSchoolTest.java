package ru.serburyat.yandex;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ATMOldSchoolTest {

    @Test
    public void whenATMIsEmptyExpectRuntimeException() {
        Map<Integer, Integer> map = Map.of(
                50, 0,
                100, 0,
                500, 0,
                1000, 0,
                5000, 0
        );

        Assertions.assertThrows(RuntimeException.class, () -> new ATM(new HashMap<>(map)).oldSchoolMethod(11000));
    }

    @Test
    public void whenGetPaymentExpectCorrectResult() {
        Map<Integer, Integer> map = Map.of(
                50, 10,
                100, 10,
                500, 10,
                1000, 10,
                5000, 0
        );

        Map<Integer, Integer> expectedResultMap = Map.of(
                1000, 10,
                500, 2
        );
        Assertions.assertEquals(expectedResultMap, new ATM(new HashMap<>(map)).oldSchoolMethod(11000));
    }

    @Test
    public void whenATMCanNotProvideWithRequestedAmountExpectException() {
        Map<Integer, Integer> map = Map.of(
                50, 10,
                100, 10,
                500, 10,
                1000, 10,
                5000, 1
        );

        Assertions.assertThrows(RuntimeException.class, () -> new ATM(new HashMap<>(map)).oldSchoolMethod(11023));
    }

}
