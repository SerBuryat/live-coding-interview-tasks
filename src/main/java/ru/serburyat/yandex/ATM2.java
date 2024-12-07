package ru.serburyat.yandex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Банкомат. Инициализируется набором купюр и умеет выдавать купюры для заданной суммы, либо отвечать отказом. При
 * выдаче купюры списываются с баланса банкомата. Допустимые номиналы: 50₽, 100₽, 500₽, 1000₽, 5000₽.
 */
public class ATM2 {

    private final Map<Integer, Integer> cashMap;
    private final List<Integer> gridySortedCashList;

    public ATM2(Map<Integer, Integer> cashMap) {
        this.cashMap = cashMap;
        this.gridySortedCashList =
                cashMap.keySet()
                        .stream()
                        .sorted(Comparator.reverseOrder())
                        .toList();
    }

    public List<Integer> getCash(int amount) {
        var resultCashList = new ArrayList<Integer>();

        for(int cash : gridySortedCashList) {
            var availableCashCount = cashMap.get(cash);
            while(cash <= amount && availableCashCount > 0) {
                amount -= cash;
                availableCashCount--;
                resultCashList.add(cash);
            }
        }

        if(amount != 0) {
            throw new RuntimeException("Не смогли выдать наличку для суммы: " + amount);
        }

        resultCashList.forEach(resultCash -> cashMap.computeIfPresent(resultCash, (k,v) -> v - 1));
        return resultCashList;
    }

    protected Map<Integer, Integer> getCashMap() {
        return this.cashMap;
    }

}
