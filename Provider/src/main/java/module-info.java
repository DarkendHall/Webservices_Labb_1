import org.darkend.converter.CurrencyConverter;
import org.darkend.provider.USD;

module org.darkend.provider {
    requires org.darkend.converter;
    provides CurrencyConverter with USD;
}