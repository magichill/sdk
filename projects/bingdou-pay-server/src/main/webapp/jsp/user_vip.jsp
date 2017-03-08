<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no"/>
    <title>海马VIP</title>
    <link rel="stylesheet" href="${ctx}/static/user_vip/css/vip.css">
    <script src="${ctx}/static/user_vip/jquery-1.11.0.min.js"></script>
    <script>
        var ctx = '${ctx}';
        $(function () {
            var r = 0; //数字对应用户VIP等级
            $(".v_item").eq(r).addClass("rank").siblings().removeClass("rank");
        })
    </script>
</head>
<body>
<div class="head clearfix" id="slider">
    <div class="user_name">liulina221</div>
    <div class="arrow">
        <div id="left_arr" class="left_arrow"></div>
        <div id="right_arr" class="right_arrow"></div>
    </div>
    <div class="vip_action">
        <div class="v_ac_warp">
            <div class="v_ac">
                <div class="v_item rank">
                    <div class="v_num">V<sub>0</sub></div>
                    <div class="v_three"></div>
                    <div class="money">0</div>
                </div>
                <div class="v_item">
                    <div class="v_num">V<sub>1</sub></div>
                    <div class="v_three"></div>
                    <div class="money">30</div>
                </div>
                <div class="v_item">
                    <div class="v_num">V<sub>2</sub></div>
                    <div class="v_three"></div>
                    <div class="money">100</div>
                </div>
                <div class="v_item">
                    <div class="v_num">V<sub>3</sub></div>
                    <div class="v_three"></div>
                    <div class="money">100</div>
                </div>
                <div class="v_item">
                    <div class="v_num">V<sub>4</sub></div>
                    <div class="v_three"></div>
                    <div class="money">100</div>
                </div>
                <div class="v_item">
                    <div class="v_num">V<sub>5</sub></div>
                    <div class="v_three"></div>
                    <div class="money">100</div>
                </div>
                <div class="v_item">
                    <div class="v_num">V<sub>6</sub></div>
                    <div class="v_three"></div>
                    <div class="money">100</div>
                </div>
                <div class="v_item">
                    <div class="v_num">V<sub>7</sub></div>
                    <div class="v_three"></div>
                    <div class="money">100</div>
                </div>
                <div class="v_item">
                    <div class="v_num">V<sub>8</sub></div>
                    <div class="v_three"></div>
                    <div class="money">100</div>
                </div>
                <div class="v_item">
                    <div class="v_num">V<sub>9</sub></div>
                    <div class="v_three"></div>
                    <div class="money">100</div>
                </div>
                <div class="v_item">
                    <div class="v_num">V<sub>10</sub></div>
                    <div class="v_three"></div>
                    <div class="money">100</div>
                </div>
                <div class="v_item">
                    <div class="v_num">V<sub>11</sub></div>
                    <div class="v_three"></div>
                    <div class="money">100</div>
                </div>
                <div class="v_item">
                    <div class="v_num">V<sub>12</sub></div>
                    <div class="v_three"></div>
                    <div class="money">100</div>
                </div>
                <div class="vip_rate_small">
                    <div style="width: 70%;"></div>
                </div>
            </div>
        </div>

        <span class="upgrade">
            <span class="top_arrow"></span>
            再充<em>10</em>元海马币升级为VIP1
        </span>

        <div class="vip_rate_big" style="display: none">
            <div style="width:70%;"></div>
        </div>
        <div class="vip_line"></div>
    </div>
    <div class="href_btn">
        <div class="href_line"></div>
        <a href="#" id="cz">充值</a>
        <a href="#" id="server">服务说明</a>
        <a href="#" id="login" style="display: none">登录</a>
        <a href="#" id="reg" style="display: none">注册</a>
    </div>
</div>
<div class="vip_info">
    <div class="vip_con">
        <dl>
            <h1>8月13日起充值累加VIP 0</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
        <dl>
            <h1>8月13日起充值累加VIP 1</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
        <dl>
            <h1>8月13日起充值累加VIP 2</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
        <dl>
            <h1>8月13日起充值累加VIP 3</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
        <dl>
            <h1>8月13日起充值累加VIP 4</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
        <dl>
            <h1>8月13日起充值累加VIP 5</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
        <dl>
            <h1>8月13日起充值累加VIP 6</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
        <dl>
            <h1>8月13日起充值累加VIP 7</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
        <dl>
            <h1>8月13日起充值累加VIP 8</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
        <dl>
            <h1>8月13日起充值累加VIP 9</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
        <dl>
            <h1>8月13日起充值累加VIP 10</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
        <dl>
            <h1>8月13日起充值累加VIP 11</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
        <dl>
            <h1>8月13日起充值累加VIP 12</h1>
            <h2>需累计充值100海马币</h2>
            <dt class="cftq">充返特权</dt>
            <dd>
                <p>1.可获得1张20%和1张25%返利卡</p>
                <span>返利卡与线上充返活动优惠不能同时使用</span>
            </dd>
            <dt class="moretq">更多特权</dt>
            <dd>
                <p>1.在线客服QQ：3065861697</p>
                <p>2.普通vip专享平台活动</p>
            </dd>
        </dl>
    </div>
</div>
</body>
<script src="${ctx}/static/user_vip/com.js"></script>
</html>
