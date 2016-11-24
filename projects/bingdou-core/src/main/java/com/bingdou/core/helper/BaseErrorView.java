package com.bingdou.core.helper;

import com.bingdou.core.constants.Constants;
import com.bingdou.tools.constants.KeyGroup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 通用异常回复View
 */
public abstract class BaseErrorView {

    public void baseRender(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        String errorMessage = request.getAttribute(Constants.REQUEST_ERROR_MESSAGE_NAME) == null
                ? "出现异常" : request.getAttribute(Constants.REQUEST_ERROR_MESSAGE_NAME).toString();
        Object object = request.getAttribute(Constants.REQUEST_KEY_GROUP_NAME);
        String data;
        if (object == null) {
            data = errorMessage;
        } else {
            RootResponse errorResponse = new RootResponse(ReturnCode.SERVER_ERROR.getIndex(),
                    errorMessage);
            KeyGroup keyGroup = (KeyGroup) object;
            data = errorResponse.convert2Result(keyGroup);
        }
        byte[] result = data.getBytes("UTF-8");
        response.setContentLength(data.length());
        OutputStream out = response.getOutputStream();
        out.write(result);
        out.flush();
        out.close();
    }

}
