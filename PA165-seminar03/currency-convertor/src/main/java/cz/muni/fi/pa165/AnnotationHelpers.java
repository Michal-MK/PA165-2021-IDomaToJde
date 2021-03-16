package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Currency;

@Configuration
//@ComponentScan("cz.muni.fi.pa165")
@EnableAspectJAutoProxy
public class AnnotationHelpers {

    @Inject
    private ExchangeRateTableImpl table;

    @Bean
    public CurrencyConvertorImpl GetConvertor() {
        return new CurrencyConvertorImpl(table);
    }

    @Bean
    public ExchangeRateTableImpl GetExchangeTable() {
        return new ExchangeRateTableImpl();
    }
}
