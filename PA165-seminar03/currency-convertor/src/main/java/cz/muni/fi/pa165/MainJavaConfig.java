package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Currency;


public class MainJavaConfig {


    public static void main(String[] arg){

        ApplicationContext context = new AnnotationConfigApplicationContext(AnnotationHelpers.class);
        CurrencyConvertorImpl convertor = context.getBean(CurrencyConvertorImpl.class);

        BigDecimal result = convertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), new BigDecimal(1));
        System.out.println(result.toString());
    }

}

