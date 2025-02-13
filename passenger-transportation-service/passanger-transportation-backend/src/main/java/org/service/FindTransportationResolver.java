package org.service;

import org.service.input_port.annotation.FindByParam;
import org.service.input_port.request.FilterParamEntity;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class FindTransportationResolver extends RequestParamMethodArgumentResolver {
    public FindTransportationResolver(boolean useDefaultResolution) {
        super(useDefaultResolution);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        System.out.println(parameter.hasParameterAnnotation(FindByParam.class));
        return parameter.hasParameterAnnotation(FindByParam.class);
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        return generateEntity(request);
    }

    private static FilterParamEntity generateEntity(NativeWebRequest request) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("MM/dd/yyyy-HH:mm:ss")
                .appendOffset("+H", "Z")
                .toFormatter();
        OffsetDateTime time = OffsetDateTime.parse(request.getParameter("time"), formatter);

        ZonedDateTime zonedDateTime = request.getParameter("time") == null ?
                ZonedDateTime.now() :
                time.toZonedDateTime();
        return new FilterParamEntity(
                zonedDateTime,
                request.getParameter("type"),
                request.getParameter("from"),
                request.getParameter("to"));
    }


}
