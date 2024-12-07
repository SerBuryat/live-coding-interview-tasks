package ru.serburyat.yandex;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Банкомат. Инициализируется набором купюр и умеет выдавать купюры для заданной суммы, либо отвечать отказом. При
 * выдаче купюры списываются с баланса банкомата. Допустимые номиналы: 50₽, 100₽, 500₽, 1000₽, 5000₽.
 */
class ATM {

    private final Map<Integer, Integer> banknotesCountByNominalMap;

    public ATM(Map<Integer, Integer> banknotes) {
        this.banknotesCountByNominalMap = banknotes;
    }

    public Map<Integer, Integer> getPayment(Integer requestAmount) {
        AtomicInteger amount = new AtomicInteger(requestAmount);
        Map<Integer, Integer> resultMap = banknotesCountByNominalMap.entrySet().stream()
                .sorted(Entry.comparingByKey(Comparator.reverseOrder()))
                .map(entry -> {
                    Integer banknoteCount = Math.min(amount.get() / entry.getKey(), entry.getValue());
                    amount.addAndGet(banknoteCount * entry.getKey() * -1);
                    banknotesCountByNominalMap.put(entry.getKey(), entry.getValue() - banknoteCount);
                    return Map.entry(entry.getKey(), banknoteCount);
                })
                .filter(entry -> entry.getValue() > 0)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

        if (amount.get() > 0) {
            resultMap.forEach((key, value) -> banknotesCountByNominalMap.put(
                    key,
                    value + banknotesCountByNominalMap.get(key)
            ));
            throw new RuntimeException("Банкомат не смог выдать нужную сумму денег");
        }

        return resultMap;
    }

    public Map<Integer, Integer> oldSchoolMethod(Integer amount) {
        Map<Integer, Integer> resultMap = new HashMap<>();
        List<Integer> banknotes = banknotesCountByNominalMap.keySet().stream().sorted(Comparator.reverseOrder()).toList();

        for (Integer banknote : banknotes) {
            Integer banknoteCount = Math.min(amount / banknote, banknotesCountByNominalMap.get(banknote));
            if (banknoteCount > 0) {
                resultMap.put(banknote, banknoteCount);
                amount -= banknoteCount * banknote;
                banknotesCountByNominalMap.put(banknote, banknotesCountByNominalMap.get(banknote) - banknoteCount);
            }

            if (amount == 0) {
                break;
            }
        }

        if (amount > 0) {
            resultMap.forEach((key, value) -> banknotesCountByNominalMap.put(
                    key,
                    value + banknotesCountByNominalMap.get(key)
            ));
            throw new RuntimeException("Банкомат не смог выдать нужную сумму денег");
        }

        return resultMap;
    }

}

