package jreader2.converter;

import com.google.cloud.Timestamp;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class ZonedDateTimeToTimestampConverter implements Converter<ZonedDateTime, Timestamp> {

    @Override
    public Timestamp convert(ZonedDateTime zonedDateTime) {
        Assert.notNull(zonedDateTime, "Date is null.");
        return Timestamp.of(Date.from(zonedDateTime.toInstant()));
    }

}
