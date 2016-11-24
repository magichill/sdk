package com.bingdou.userserver.service;

import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.DeviceInfo;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.Application;
import com.bingdou.core.model.Os;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.user.LoginBaseService;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.core.utils.UserUtils;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.RequestUtil;
import com.bingdou.tools.constants.HadoopLogAction;
import com.bingdou.userserver.request.GuestLoginRequest;
import com.bingdou.userserver.response.GuestLoginResponse;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * �ο͵�¼������
 */
@Service
public class GuestLoginService extends BaseService implements IMethodService {

//    @Autowired
//    private VipGradeService vipGradeService;
//    @Autowired
//    private DeviceService deviceService;
//    @Autowired
//    private UserStatisticsService userStatisticsService;
//    @Autowired
//    private UserChannelMoveService userChannelMoveService;
//    @Autowired
//    private MessageService messageService;
//    @Autowired
//    private VoucherService voucherService;
//    @Autowired
//    private AliPayNoPwdAuthService aliPayNoPwdAuthService;
    @Autowired
    private LoginBaseService loginBaseService;
//    @Autowired
//    private NoticeService noticeService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GuestLoginRequest guestLoginRequest = new GuestLoginRequest();
        guestLoginRequest.parseRequest(request);
        return guestLoginRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        return null;
    }

    @Override
    public String getMethodName() {
        return "guest_login";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
//        GuestLoginRequest guestLoginRequest = (GuestLoginRequest) baseRequest;
//        if (StringUtils.isEmpty(guestLoginRequest.getDeviceNo4Server())
//                || guestLoginRequest.getDeviceNo4Server().length() > 36) {
//            return ServiceResultUtil.illegal("�Ƿ�����");
//        }
//        Application application = appBaseService.getAppByAppId(guestLoginRequest.getAppId());
//        return deal(request, guestLoginRequest, application, guestLoginRequest.getDeviceNo4Server());
        //TODO ��ʱ���η������˵��ο͵�¼
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GuestLoginRequest guestLoginRequest = (GuestLoginRequest) baseRequest;
        if (guestLoginRequest.getDeviceInfo() == null
                || guestLoginRequest.getOtherInfo() == null) {
            return ServiceResultUtil.illegal("�Ƿ�����");
        }
        DeviceInfo deviceInfo = guestLoginRequest.getDeviceInfo();
        String deviceStr = getDeviceStr(deviceInfo);
        if (StringUtils.isEmpty(deviceStr)) {
            LogContext.instance().warn("�豸��ϢΪ��,�ο͵�¼ʧ��");
            return ServiceResultUtil.illegal("�ο͵�¼ʧ��");
        }
        Application application = getValidApplication4Client(guestLoginRequest);
        return deal(request, guestLoginRequest,application, deviceStr);
    }

//    private ServiceResult deal(HttpServletRequest request, GuestLoginRequest guestLoginRequest,
//                               Application application, String deviceStr) throws Exception {
private ServiceResult deal(HttpServletRequest request, GuestLoginRequest guestLoginRequest,
                           Application application, String deviceStr) throws Exception {
        User userByDevice = userBaseService.getUserByDevice(deviceStr);
        String clientIp = RequestUtil.getClientIp(request);
        boolean isClientRequest = isClientRequest(request);
        if (userByDevice != null && userByDevice.getId() > 0) {
            LogContext.instance().info("�������豸,�������ο��˺�");
            String errorMessage = userByDevice.checkStatus();
            if (StringUtils.isNotEmpty(errorMessage)) {
                return ServiceResultUtil.illegal(errorMessage);
            }
            if (isClientRequest && StringUtils.isNotEmpty(userByDevice.getPassword())) {
                LogContext.instance().info("�ο��˺��Ѿ�����,�·��û���,ʹ���û��������¼");
                GuestLoginResponse guestLoginResponse = new GuestLoginResponse();
                guestLoginResponse.setLoginName(userByDevice.getLoginName());
                guestLoginResponse.setEmail(userByDevice.getEmail());
                guestLoginResponse.setMobile(userByDevice.getMobile());
                DataLogUtils.recordHadoopLog(HadoopLogAction.GUEST_LOGIN_UPDATED, guestLoginRequest, userByDevice,
                        clientIp, "", "", false);
                JsonElement result = JsonUtil.bean2JsonTree(guestLoginResponse);
                return ServiceResultUtil.guestLoginUpdated(result);
            }
            return dealGuestLogin(userByDevice, guestLoginRequest, application, request, false, isClientRequest,
                    clientIp);
        } else {
            LogContext.instance().info("�����豸,�����ο��˺�");
            User newUser = new User();
            String guestLoginName = newUser.generateGuestLoginName();
            boolean exist = userBaseService.isExistsLoginName(guestLoginName);
            if (exist) {
                return ServiceResultUtil.illegal("�ο�ע��ʧ��,������");
            }
            newUser.setLoginName(guestLoginName);
            newUser.setPassword("");
            newUser.setSalt("");
            newUser.setDevice(deviceStr);
            newUser.setMobile("");
            String ua = "";
            String uid = "";
            if (isClientRequest) {
                ua = UserUtils.getOldUa4Client(guestLoginRequest);
                uid = UserUtils.getOldUid4Client(guestLoginRequest);
            }
            boolean createSuccess = userBaseService.createUser(newUser, application.getAppId(),
                    clientIp, uid, ua, UserConstants.SECURE_LEVEL_0);
            if (createSuccess) {
                User alreadyAddUser = userBaseService.getDetailById(newUser.getId());
                return dealGuestLogin(alreadyAddUser, guestLoginRequest, application, request, true, isClientRequest,
                        clientIp);
            } else {
                return ServiceResultUtil.serverError("�ο͵�¼�ڲ�����");
            }
        }
    }

    private ServiceResult dealGuestLogin(User user, GuestLoginRequest guestLoginRequest,
                                         Application application, HttpServletRequest request,
                                         boolean isNewGuestUser, boolean isClient, String clientIp) throws Exception {
        String tokenDevice = "";
        boolean isSupportLive = false;
        boolean isSupportVirtualMoney = false;
        boolean isSigned = false;
        String sdkVersion = "";
        GuestLoginResponse guestLoginResponse = new GuestLoginResponse();
        if (isClient) {
            sdkVersion = guestLoginRequest.getOtherInfo().getSdkVersion();
            tokenDevice = getDeviceNo4Client(guestLoginRequest);
//            userChannelMoveService.addMoveChannelRecord4Client(user.getId(), guestLoginRequest, isNewGuestUser);
//            userStatisticsService.recordUserSourceReport4Client(user.getId(), application, guestLoginRequest, isNewGuestUser);
//            boolean isNewUser = userStatisticsService.recordUserActiveRecord(user.getId(), application);
            Os os = getClientOsByRequest(guestLoginRequest);
//            List<ConfigMessage> configMessageList = messageService.getConfigMessageBox4Login(
//                    guestLoginRequest, os, clientIp, isNewUser, user, true);
//            isSupportVirtualMoney = switchRuleService.isSupportVirtualMoney(application.getAppId(), application.getOs(),
//                    guestLoginRequest.getOtherInfo().getChannel());
//            LogContext.instance().info("��ȡ��Ϸ�ҿ��ؽ��:" + isSupportVirtualMoney);
//            if (Os.IOS.equals(os)) {
//                isSupportLive = switchRuleService.isSupportLive(application.getAppId(), os.getIndex(),
//                        guestLoginRequest.getOtherInfo().getChannel());
//                LogContext.instance().info("��ȡֱ�����ؽ��:" + isSupportLive);
//            }
//            voucherService.giveVoucher4Login(user, guestLoginRequest, application.getAppId(),
//                    clientIp, isNewUser);
                String oldUid = UserUtils.getOldUid4Client(guestLoginRequest);
                String oldUa = UserUtils.getOldUa4Client(guestLoginRequest);
                loginBaseService.setLastLoginInfo(user.getId(), clientIp, oldUid, oldUa);
//            }
//            int unreadMessageCount = messageService.getMessageCountByUserId(user.getId(),
//                    MessageStatus.UNREAD);
//            LogContext.instance().info("δ����Ϣ����:" + unreadMessageCount);
//            guestLoginResponse.setMessageInfoList(UserServerUtils.convertMessageInfoListBy(configMessageList));
//            guestLoginResponse.setUnreadMessageNum(unreadMessageCount);
            String model = os == Os.IOS ? guestLoginRequest.getDeviceInfo().getModel() : "";
//            List<Notice> noticeList = noticeService.getNoticeList4Login(user, guestLoginRequest,
//                    isNewUser, isPad(guestLoginRequest), application.getAppId(), guestLoginRequest.getOtherInfo().getChannel(),
//                    model);
//            guestLoginResponse.setNoticeInfoList(UserServerUtils.covertNoticeInfoListBy(noticeList));
        }
//        UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());
        boolean updateTokenResult = userBaseService.updateToken(user, tokenDevice, getSafeInfo(request),
                isNewGuestUser);
        LogContext.instance().info("����token:" + updateTokenResult);
//        boolean isNewDevice = deviceService.isNewDevice(guestLoginRequest.getDeviceInfo());
//        int virtualMoneyFen = getVirtualMoneyFen4Show(user.getId(), application.getOs(),
//                sdkVersion, isNewGuestUser, isSupportVirtualMoney);
        guestLoginResponse.parseFromUser(user, null, isSupportVirtualMoney, isSupportLive,
                isSigned, 0);
        DataLogUtils.recordHadoopLog(isNewGuestUser ?
                        HadoopLogAction.GUEST_REGISTER : HadoopLogAction.GUEST_LOGIN, guestLoginRequest, user,
                clientIp, "", "", false);
        JsonElement result = JsonUtil.bean2JsonTree(guestLoginResponse);
        return ServiceResultUtil.success(result);
    }

    private String getDeviceStr(DeviceInfo deviceInfo) {
        String deviceStr = "";
        if (deviceInfo.getOs() == Os.ANDROID.getIndex()) {
            deviceStr = StringUtils.isNotEmpty(deviceInfo.getImei()) ? deviceInfo.getImei()
                    : StringUtils.isNotEmpty(deviceInfo.getMac()) ? deviceInfo.getMac()
                    : StringUtils.isNotEmpty(deviceInfo.getAndroidInfo().getAndroidId()) ? deviceInfo.getAndroidInfo().getAndroidId()
                    : StringUtils.isNotEmpty(deviceInfo.getAndroidInfo().getAndroidSerialNumber()) ? deviceInfo.getAndroidInfo().getAndroidSerialNumber() : "";
        } else if (deviceInfo.getOs() == Os.IOS.getIndex()) {
            deviceStr = StringUtils.isNotEmpty(deviceInfo.getIosInfo().getIdfa()) ? deviceInfo.getIosInfo().getIdfa() : "";
        }
        return deviceStr;
    }

}
