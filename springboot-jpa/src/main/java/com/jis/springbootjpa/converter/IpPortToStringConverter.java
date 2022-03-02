package com.jis.springbootjpa.converter;

import com.jis.springbootjpa.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class IpPortToStringConverter implements Converter<IpPort, String> {

    @Override
    public String convert(IpPort ipPort) {
        return String.format("%s:%n",ipPort.getIp(),ipPort.getPort());
    }
}
