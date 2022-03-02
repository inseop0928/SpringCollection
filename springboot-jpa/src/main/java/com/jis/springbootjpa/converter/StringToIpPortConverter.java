package com.jis.springbootjpa.converter;

import com.jis.springbootjpa.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {
    @Override
    public IpPort convert(String s) {
        log.info(s);
        String[] splits = s.split(":");

        IpPort ipPort = new IpPort();
        ipPort.setIp(splits[0]);
        ipPort.setPort(Integer.parseInt(splits[1]));
        return ipPort;
    }
}
