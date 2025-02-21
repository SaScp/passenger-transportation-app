package org.service;

import org.service.input_port.annotation.FindByParam;
import org.service.input_port.request.FilterParamEntity;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class FindTransportationResolver extends RequestParamMethodArgumentResolver {
    public FindTransportationResolver(boolean useDefaultResolution) {
        super(useDefaultResolution);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(FindByParam.class);
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        return generateEntity(request);
    }

    private static FilterParamEntity generateEntity(NativeWebRequest request) {

        String timeParam = request.getParameter("time");
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("MM/dd/yyyy-HH:mm:ss")
                .toFormatter();
        LocalDateTime time = timeParam == null ? LocalDateTime.now() :
                LocalDateTime.parse(timeParam, formatter);


        return new FilterParamEntity(
                time,
                request.getParameter("type"),
                request.getParameter("from"),
                request.getParameter("to"));
    }


}
