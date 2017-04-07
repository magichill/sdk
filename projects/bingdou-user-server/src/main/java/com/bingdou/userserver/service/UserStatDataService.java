package com.bingdou.userserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.UserStat;
import com.bingdou.core.model.UserVipGrade;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.ConsumeService;
import com.bingdou.core.service.live.GiftService;
import com.bingdou.core.service.live.LiveService;
import com.bingdou.core.service.pay.VipGradeService;
import com.bingdou.core.service.user.FocusService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.userserver.request.GetUserStatRequest;
import com.bingdou.userserver.response.UserStatsDataResponse;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/2/16.
 */
@Service
public class UserStatDataService extends BaseService implements IMethodService {

    @Autowired
    private VipGradeService vipGradeService;

    @Autowired
    private FocusService focusService;

    @Autowired
    private LiveService liveService;

    @Autowired
    private ConsumeService consumeService;

    @Autowired
    private GiftService giftService;

    @Override
    public String getMethodName() {
        return "get_user_data";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetUserStatRequest getUserStatRequest = (GetUserStatRequest)baseRequest;
        return dealUserStatData(request,getUserStatRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetUserStatRequest getUserStatRequest = (GetUserStatRequest)baseRequest;
        return dealUserStatData(request,getUserStatRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetUserStatRequest getUserStatRequest = new GetUserStatRequest();
        getUserStatRequest.parseRequest(request);
        return getUserStatRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetUserStatRequest getUserStatRequest = (GetUserStatRequest)baseRequest;
        User user = userBaseService.getUserDetailByAccount(getUserStatRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getUserStatRequest.getAccount());
        return user;
    }

    private ServiceResult dealUserStatData(HttpServletRequest request, GetUserStatRequest getUserStatRequest, User user) throws Exception {

        if (StringUtils.isEmpty(getUserStatRequest.getAccount())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }

        user = getUser(getUserStatRequest);
        if(user == null){
            return ServiceResultUtil.illegal("用户不存在");
        }
        UserStatsDataResponse userStatsDataResponse = new UserStatsDataResponse();
        UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());
        Integer followers = focusService.getFansCount(user.getId());
        Integer likeCount = focusService.getFollowerCount(user.getId());
        Integer liveCount = liveService.getLiveCountByMid(user.getId());

        Integer income = consumeService.getIncomePrice(user);
        Integer consume = consumeService.getConsumePrice(user);

        Integer giftIncome = giftService.getReceiveMoney(user);
        Integer giftConsume = giftService.getSendMoney(user);

        Integer incomeLoyalty = income+giftIncome;
        Integer consumeLoyalty = consume+giftConsume;
        UserStat userStat = new UserStat();
        userStat.setLikeCount(likeCount==null?0:likeCount);
        userStat.setFollowers(followers==null?0:followers);
        userStat.setLiveCount(liveCount==null?0:liveCount);
        userStat.setConsumeLoyalty(consumeLoyalty==null?0:consumeLoyalty);
        userStat.setIncomeLoyalty(incomeLoyalty==null?0:incomeLoyalty);
        //TODO earning
        userStatsDataResponse.parseFromUser(user,userVipGrade,userStat);
        JsonElement result = JsonUtil.bean2JsonTree(userStatsDataResponse);
        LogContext.instance().info("获取用户数据信息成功");
        return ServiceResultUtil.success(result);
    }
}
