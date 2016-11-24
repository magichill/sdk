package com.bingdou.cdn.view;

import com.bingdou.core.helper.BaseErrorView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 通用异常回复View
 */
public class ErrorView extends BaseErrorView implements View {

    @Override
    public void render(Map<String, ?> modelView, HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        baseRender(request, response);
    }

    @Override
    public String getContentType() {
        return "application/json";
    }
}
