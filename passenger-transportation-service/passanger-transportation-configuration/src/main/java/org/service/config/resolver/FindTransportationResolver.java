package org.service.config.resolver;

import lombok.extern.slf4j.Slf4j;
import org.service.input_port.annotation.FindByParam;
import org.service.input_port.request.FilterParamEntity;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Slf4j
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

        LocalDateTime time = LocalDateTime.now();
        try {
            String timeParam = request.getParameter("time");
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("MM/dd/yyyy-HH:mm:ss")
                    .toFormatter();

            time = getTransportationTime(timeParam, formatter);
        } catch (Exception e) {
        }


        return new FilterParamEntity(
                time,
                request.getParameter("type"),
                request.getParameter("from"),
                request.getParameter("to"));
    }

    private static LocalDateTime getTransportationTime(String timeParam, DateTimeFormatter formatter) {
        return timeParam == null || (timeParam.isBlank() || timeParam.isEmpty()) ?
                LocalDateTime.now() : LocalDateTime.parse(timeParam, formatter);
    }


}
