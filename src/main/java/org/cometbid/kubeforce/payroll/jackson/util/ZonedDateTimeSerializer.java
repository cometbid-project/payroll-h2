/*
 * The MIT License
 *
 * Copyright 2024 samueladebowale.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.cometbid.kubeforce.payroll.jackson.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.cometbid.kubeforce.payroll.common.util.LocalizationContextUtils;
import static org.cometbid.kubeforce.payroll.common.util.TimeZonesConverter.*;

/**
 *
 * @author samueladebowale
 */
@Log4j2
public class ZonedDateTimeSerializer extends StdSerializer<ZonedDateTime> {

    private static final DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("yyyy-MMM-dd hh:mm:ss a z");

    public ZonedDateTimeSerializer() {
        super(ZonedDateTime.class);
    }

    @Override
    public void serialize(ZonedDateTime utcValue, JsonGenerator gen,
            SerializerProvider provider) throws IOException {

        log.info("ZoneDateTime from: {}", utcValue);

        if (!Objects.isNull(utcValue)) {
            ZoneId zoneId = LocalizationContextUtils.getContextZoneId();
            log.info("Zone id: {}", zoneId);
            
            ZonedDateTime convertedValue = convert(utcValue, zoneId);
            log.info("ZoneDateTime to: {}", utcValue);
            
            String dateTimeOffset = formatter.format(convertedValue);
            log.info("ZoneDateTime offset: {}", dateTimeOffset);
            
            gen.writeString(dateTimeOffset);
        }
    }

}
