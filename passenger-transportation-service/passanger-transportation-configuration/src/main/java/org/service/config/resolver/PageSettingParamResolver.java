package org.service.config.resolver;

import org.service.entity.PageEntity;
import org.service.input_port.annotation.PageSettingParam;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

public class PageSettingParamResolver extends RequestParamMethodArgumentResolver  {
    public PageSettingParamResolver(boolean useDefaultResolution) {
        super(useDefaultResolution);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PageSettingParam.class);
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        return  new PageEntity(
                request.getParameter("page_num"),
                request.getParameter("page_size")
        );
    }

}
