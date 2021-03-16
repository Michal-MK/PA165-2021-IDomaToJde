package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainAnnotations {
    public static void main(String[] arg){
        ApplicationContext context = new AnnotationConfigApplicationContext("cz.muni.fi.pa165");
        CurrencyConvertorImpl convertor = context.getBean(CurrencyConvertorImpl.class);

        BigDecimal result = convertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), new BigDecimal(1));
        System.out.println(result.toString());
    }
}
