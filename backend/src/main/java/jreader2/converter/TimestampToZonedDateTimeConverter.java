package jreader2.converter;

import com.google.cloud.Timestamp;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class TimestampToZonedDateTimeConverter implements Converter<Timestamp, ZonedDateTime> {

    @Override
    public ZonedDateTime convert(Timestamp timestamp) {
        Assert.notNull(timestamp, "Timestamp is null.");
        return timestamp.toDate().toInstant().atZone(ZoneOffset.UTC);
    }

}
