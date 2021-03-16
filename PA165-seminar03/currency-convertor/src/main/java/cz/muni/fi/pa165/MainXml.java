package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml {
    public static void main(String[] arg){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-conf.xml");
        CurrencyConvertorImpl convertor = context.getBean(CurrencyConvertorImpl.class);

        BigDecimal result = convertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), new BigDecimal(1));
        System.out.println(result.toString());
    }
}
