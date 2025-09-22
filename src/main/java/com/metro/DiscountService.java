package com.metro.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Small example DiscountService for the MetroRepo.
 * - applyDiscount(amount, code) returns the final price after discount.
 * - addOrUpdateCode allows adding simple percent discounts (0.10 = 10%).
 */
public class DiscountService {
    private final Map<String, BigDecimal> codeRates = new HashMap<>();

    public DiscountService() {
        // example default codes
        codeRates.put("WELCOME10", new BigDecimal("0.10")); // 10%
        codeRates.put("STUDENT15", new BigDecimal("0.15")); // 15%
    }

    /**
     * Apply discount (percent) for a code. Unknown codes => no discount.
     *
     * @param amount total amount (non-null)
     * @param code discount code (nullable)
     * @return final amount after applying discount, scaled to 2 decimals
     */
    public BigDecimal applyDiscount(BigDecimal amount, String code) {
        if (amount == null) throw new IllegalArgumentException("amount required");
        BigDecimal rate = code == null ? BigDecimal.ZERO
                : codeRates.getOrDefault(code.toUpperCase(), BigDecimal.ZERO);
        BigDecimal discount = amount.multiply(rate);
        return amount.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }

    public void addOrUpdateCode(String code, BigDecimal rate) {
        if (code == null || rate == null) throw new IllegalArgumentException("code and rate required");
        codeRates.put(code.toUpperCase(), rate);
    }
}
