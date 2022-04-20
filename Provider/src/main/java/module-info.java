import org.darkend.converter.CurrencyConverter;
import org.darkend.provider.EUR;
import org.darkend.provider.USD;

module org.darkend.provider {
    requires org.darkend.converter;
    requires java.net.http;
    provides CurrencyConverter with USD, EUR;
}