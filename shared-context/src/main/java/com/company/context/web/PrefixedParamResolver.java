package com.company.context.web;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

public class PrefixedParamResolver implements HandlerMethodArgumentResolver {

    private HandlerMethodArgumentResolver inner = new ServletModelAttributeMethodProcessor(false);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PrefixedParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {

        MockedRequest req2 = MockedRequest.build(webRequest, //
                parameter.getParameterAnnotation(PrefixedParam.class).prefix());

        return inner.resolveArgument(parameter, mavContainer, req2, binderFactory);
    }

}