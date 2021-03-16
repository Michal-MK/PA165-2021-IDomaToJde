package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import javax.inject.Named;

@Named
public class ExchangeRateTableImpl implements ExchangeRateTable{


    public ExchangeRateTableImpl() {
    }

    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) throws ExternalServiceFailureException {
        if(sourceCurrency.equals(Currency.getInstance("EUR")) && targetCurrency.equals(Currency.getInstance("CZK")))
            return new BigDecimal("27");

        return null;
    }
}
