package com.ruixin.administrator.ruixinapplication.utils;

import com.ruixin.administrator.ruixinapplication.RuiXinApplication;

/**
 * 作者：Created by ${李丽} on 2018/3/20.
 * 邮箱：543815830@qq.com
 * 网络测试接口
 */

public class URL {
 //120.78.87.50/app/Popup_Advertisements.php
 public String getAd_Url = RuiXinApplication.getInstance().getUrl() + "app/Popup_Advertisements.php";
    /*测试接口*/
    //   public  String RuiXinApplication.getInstance().getUrl()+"app/" ="http://120.78.87.50/app/";
    public String uriPrefix = RuiXinApplication.getInstance().getUrl() + "app/";
   // http://120.78.87.50/app/inc/Menu.php
   public String menu = RuiXinApplication.getInstance().getUrl() + "app/inc/Menu.php";
    //幸运大转盘
    public String luckyRound_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_LuckRound.php";
    public String luckyRoundact_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_LuckRound.php?act=sum";
    //注册页面
    public String REGISTER_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Reg_App.php?act=reg";
    //登录页面http://qqclub.320.io/app/User_Login_App.php?act=login
    public String LOGIN_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Login_App.php?act=login";
    //获取验证码http://192.168.1.24/app/ajax_mobile_App.php
    public String AJAX_MOBILE_APP = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_mobile_App.php?action=post";
    /*验证验证码：http://192.168.1.24/app/ajax_mobile_App.php?action=yz*/
    public String CODE_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "ajax_mobile_App.php?action=yz";
    //忘记密码http://qqclub.320.io/app/User_Findpassword_App.php?act=forget
    public String PWD_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Findpassword_App.php?act=forget";
    /*首页*/
    public String INDEX_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "index_App.php";
    /*新闻公告*/
    public String News_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "News_List_App.php";
    //排行榜http://192.168.1.24/app/Game_Top_App.php
    public String Rank_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_Top_App.php";
    //活动列表http://192.168.1.24/app/Hd_List_App.php
    public String ACT_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Hd_List_App.php";
    //游戏试玩http://qqclub.320.io/app/game_App.php
    public String ADE_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "game_App.php";
    //热门奖品http://qqclub.320.io/app/Prize_Hotlist_App.php
    public String Prize_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Prize_Hotlist_App.php";
    //最新奖品http://120.78.87.50/app/Prize_Newlist_App.php
    public String NewPrize_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Prize_Newlist_App.php";
    //奖品分类http://120.78.87.50/app/Prize_Typelist_App.php
    public String PrizeType_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Prize_Typelist_App.php";
    //分类下的奖品http://120.78.87.50/app/Prize_Index_App.php
    public String PrizeType_Prize_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Prize_Index_App.php";
    //活动详情http://qqclub.320.io/app/Hd_Detail_App.php?id=54
    public String ACTDetial_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Hd_Detail_App.php";
    //新闻公告的详情http://qqclub.320.io/app/News_Detail_App.php?id=66
    public String NewsDetial_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "News_Detail_App.php";
    //回调地址http://qqclub.320.io/plugin/qc/callback.php
    public String CallBack_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "qc/callback.php";
    //QQ登录的接口http://qqclub.320.io/app/User_Login_App.php?act=login
    public String QQLogin_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Login_App.php?act=login";
    //轮播图接口http://qqclub.320.io/app/index_banner_App.php
    public String Banner_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "index_banner_App.php";
    //修改资料http://120.78.87.50/app/User_Edit_App.php?act=modify
    public String UserInfo_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Edit_App.php?act=modify";
    //账号动态http://120.78.87.50/app/User_Login_Prize_App.php
    public String UserState_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_LoginLog_App.php";
    //站内银行http://120.78.87.50/app/User_Bank_App.php?act=access
    public String InsideBank_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Bank_App.php?act=access";
    //推广下线http://127.78.87.50/app/User_Recom_App.php
    public String PromoteOffline_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Recom_App.php";
    //推广下线-我的下线http://120.78.87.50/app/User_Recom_App.php?act=MyRecom
    public String MyOffline_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Recom_App.php?act=MyRecom";
    //推广收益 http://120.78.87.50/app/User_Recomprofit_App.php
    public String PromoteEarnings_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Recomprofit_App.php";
    //点卡使用http://120.78.87.50/app/User_Recharge_App.php?act=card
    public String CardUse_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Recharge_App.php?act=card";
    //批量充值http://localhost/app/User_Recharge_App.php?act=pil
    public String MaxCard_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Recharge_App.php?act=pil";
    //救济数据http://127.78.87.50/app/User_Alms_App.php
    public String Relief_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Alms_App.php";
    //领取救济http://127.78.87.50/app/User_Alms_App.php?act=almsgiving
    public String GRelief_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Alms_App.php?act=almsgiving";
    public String GXnb_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_Xn28_Get_App.php";
    //工资数据http://120.78.87.50/app/User_Wage_App.php
    public String Salary_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Wage_App.php";
    //领取工资http://120.78.87.50/app/User_Wage_App.php?act=lingqu
    public String GSalary_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Wage_App.php?act=lingqu";
    //首充返利http://127.78.87.50/app/User_Bonus_App.php
    public String Rebate_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Bonus_App.php";
    //领取返利http://127.78.87.50/app/User_Bonus_App.php?act=lingqu
    public String GRebate_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Bonus_App.php?act=lingqu";
    //亏损返利http://127.78.87.50/app/User_ReWard_App.php
    public String LRebate_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_ReWard_App.php";
    //下线亏损领取http://127.78.87.50/app/User_ReWard_App.php?act=xxlingqu
    public String LxRebate_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_ReWard_App.php?act=xxlingqu";
    //日领取http://127.78.87.50/app/User_ReWard_App.php?act=lingqu
    public String LdRebate_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_ReWard_App.php?act=lingqu";
    //周领取
    public String LwRebate_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_ReWard_App.php?act=qrflget";
    //站内红包http://120.78.87.50/app/User_RedPack_App.php
    public String IBag_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_RedPack_App.php?act=data";
    //我发出的红包http://120.78.87.50/app/User_RedPackMine_App.php
    public String Mysendbag_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_RedPackMine_App.php";
    //我收到的红包http://120.78.87.50/app/User_RedPackMine_App.php
    public String Mygetbag_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_RedPackMine_App.php?act=rec";
    //返还红包
    public String Mybackbag_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_RedPackMine_App.php?act=fh";
    //红包详情
    public String Mydetailbag_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_RedPackMine_App.php?act=detail";
    //发送红包http://120.78.87.50/app/User_RedPack_App.php?act=send
    public String Send_Bag_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_RedPack_App.php?act=send";
    //站内信箱http://120.78.87.50/app/User_Sms_App.php
    public String MailBox_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Sms_App.php";
    //信箱删除http://120.78.87.50/app/User_Sms_App.php?act=lock
    public String DelMailBox_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Sms_App.php?act=lock";
    //信箱详情http://120.78.87.50/app/User_Sms_App.php?act=look&id=消息ID
    public String MailContent_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Sms_App.php?act=look";
    // 签到记录http://120.78.87.50/app/User_Sign_App.php?act=qdlist
    public String SignRecord_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Sign_App.php?act=qdlist";
    //签到中心数据http://120.78.87.50/app/User_Sign_App.php
    public String SignState_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Sign_App.php";
    //签到中心http://120.78.87.50/app/User_Sign_App.php?act=qd
    public String Sign_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Sign_App.php?act=qd";
    //密保设置问题http://120.78.87.50/app/User_BindSecQues_App.php
    public String PwdQue_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_BindSecQues_App.php";
    //密保绑定问题http://120.78.87.50/app/User_BindSecQues_App.php?act=bind
    public String SPwdQue_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_BindSecQues_App.php?act=bind";
    //支付宝支付http://120.78.87.50/plugin/aliself/aliself.php
    public String PayAlipy_URL = RuiXinApplication.getInstance().getUrl() + "plugin/aliself/aliself.php";
    //微信支付http://120.78.87.50/plugin/aliself/wechatself.php
    public String PayWe_URL = RuiXinApplication.getInstance().getUrl() + "plugin/aliself/wechatself.php";
    //发送邮箱验证码http://120.78.87.50/app/Ajax_Email_App.php
    public String Email_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Email_App.php";
    //保存邮箱 http://120.78.87.50/app/User_BindEmail_App.php
    public String SEmail_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_BindEmail_App.php";
    //砸蛋蛋http://120.78.87.50/app/Egg_data_App.php
    public String SmshEgg_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Egg_data_App.php";
    //代理商http://120.78.87.50/app/User_MyRecharge_App.php
    public String Agency_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_MyRecharge_App.php";
    //密保卡设置http://120.78.87.50/app/User_Safe_App.php?act=bdmbk
    public String PwdCard_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Safe_App.php?act=bdmbk";
    //短信相关配置http://120.78.87.50/app/User_Safe_App.php?act=sms
    public String SmsVersion_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Safe_App.php?act=sms";
    //查看密保卡http://120.78.87.50/app/User_Protectcard_Show_App.php
    public String QPwdCard_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Protectcard_Show_App.php";
    //密保卡登录http://127.78.87.50/app/User_Login_App.php?act=codetxt
    public String LPwdCard_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Login_App.php?act=codetxt";
    //解绑密保卡http://127.78.87.50/app/User_Safe_App.php?act=jcmbk
    public String UbPwdCard_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Safe_App.php?act=jcmbk";
    //qq验证http://120.78.87.50/app/User_Safe_App.php?act=saveqc
    public String QqVersion_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Safe_App.php?act=saveqc";
    //域名信息http://120.78.87.50/app/User_SelfLine_App.php
    public String DmainName_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_SelfLine_App.php";
    //修改密码http://120.78.87.50/app/User_Editpwd_App.php?act=modifyPwd
    public String Update_Pwd = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Editpwd_App.php?act=modifyPwd";
    //开通域名信息http://127.78.87.50/app/User_SelfLine_App.php?type=msg
    public String Dmain_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_SelfLine_App.php?type=msg";
    //购买域名http://120.78.87.50/app/Ajax_SelfLine_App.php?type=buy
    public String OpenDmain_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_SelfLine_App.php?type=buy";
    //续费域名http://120.78.87.50/app/Ajax_SelfLine_App.php?type=xf
    public String XDmain_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_SelfLine_App.php?type=xf";
    //用户信息初始化http://120.78.87.50/app/User_Msg_App.php
    public String Info_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Msg_App.php";
    //二维码扫描登录http://120.78.87.50/app/Ajax_ScanLogin.php
    public String QrCode_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_ScanLogin.php?act=login";
    public String PreQrCode_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_ScanLogin.php?act=prelogin";
    //游戏帮助
    public String GameHelp_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_Help.php";
    //游戏列表+热门游戏列表http://120.78.87.50/app/Ajax_Game_list.php
    public String GameName_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_list.php";
    //我的投注http://120.78.87.50/app/Ajax_Game_mybet.php
    public String MyBet_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_mybet.php";
    //投注详情http://120.78.87.50/app/Game_BetDetail_App.php
    public String MyBetDetail_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_BetDetail_App.php";
    //投注内容详情
    public String MyBetContentDetail_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_BetDetail_App.php?act=detail";
    //获取游戏开奖列表http://120.78.87.50/app/Ajax_Game_Index.php
    public String GameHome_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_Index.php";
    //获取盈利列表http://120.78.87.50/app/Game_Statistics_App.php
    public String GameProfit_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_Statistics_App.php";
    //http://120.78.87.50/app/Game_Info_App.php
    public String GameCenBar_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_Info_App.php";
    //保存模式http://120.78.87.50/app/Ajax_ModelSave.php
    public String SaveBetMode_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_ModelSave.php";
    //对号投注的信息http://120.78.87.50/app/Ajax_Game_Contrast.php
    public String MyMode_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_Contrast.php";
    //对号投注http://120.78.87.50/app/Ajax_Game_Contrast.php?act=submit
    public String CheckNumberMode_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_Contrast.php?act=submit";
    public String UCheckNumberMode_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_Contrast.php?act=alter";
    //保存模式http://120.78.87.50/app/Ajax_Game_SetModel.php
    public String SaveMode_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_SetModel.php";
    //修改模式
    public String USaveMode_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_SetModel.php";
    //托管方案数据http://120.78.87.50/app/User_Auto_App.php
    public String TrusteeInfo_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Auto_App.php";
    //托管方案删除http://120.78.87.50/app/User_Auto_App.php?act=del
    public String TrusteeDel_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Auto_App.php?act=del";
    //托管方案停止http://120.78.87.50/app/User_Auto_App.php?act=stop
    public String TrusteeStop_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Auto_App.php?act=stop";
    //托管方案运行http://120.78.87.50/app/User_Auto_App.php?act=start
    public String TrusteeStart_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Auto_App.php?act=start";
    //托管方案详情http://120.78.87.50/app/User_Auto_Detail_App.php
    public String TrusteeDetail_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Auto_Detail_App.php";
    //托管方案详情保存http://120.78.87.50/app/User_Auto_Detail_App.php?act=save
    public String TrusteeSave_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Auto_Detail_App.php?act=save";
    //120.78.87.50/app/User_Auto_App.php
    public String Zero_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Auto_App.php?act=reset";
    //自动投注的数据信息http://120.78.87.50/app/Ajax_Game_AutoSet.php
    public String AutoBetInfo_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_AutoSet.php";
    //自动投注的开始投注http://120.78.87.50/app/Ajax_Game_AutoSet.php?act=submit
    public String AutoBetStart_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_AutoSet.php?act=submit";
    //翻倍投注开始http://120.78.87.50/app/Ajax_Game_Double.php?act=submit
    public String DoublingBet_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_Double.php?act=submit";
    public String UDoublingBet_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_Double.php?act=alter";
    public String DoublingBetInfo_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_Double.php";
    //修改方案
    public String AutoBetUpdate_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_AutoSet.php?act=alter";
    //投注模式的投注数据http://120.78.87.50/app/Ajax_Game_Model.php
    public String MyBetMode_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_Model.php";
    //删除投注模式http://120.78.87.50/app/Ajax_Game_SetModel.php?act=del
    public String DelMyBetMode_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_SetModel.php?act=del";
    //游戏走势图http://120.78.87.50/app/Game_chart_10.php
    public String GameMap_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_chart_10.php";
    public String GameMap1_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_chart_36.php";
    public String GameMap2_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_chart_pkww.php";
    public String GameMap3_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_chart_ssc.php";
    public String GameMap4_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_chart_xync.php";
    public String GameMap5_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_chart_tbww.php";
    public String GameMap6_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Game_chart_bjl.php";
    //确认投注http://120.78.87.50/app/Ajax_Game_Invest.php
    public String GameBet_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_Invest.php";
    //获取上期投注http://120.78.87.50/app/Ajax_Game_LastBet.php
    public String GameLastBet_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_LastBet.php";
    //真实赔率 http://120.78.87.50/app/Ajax_Game_BetOdd.php
    public String GameTrueLvBet_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_BetOdd.php";
    public String GameTrueMyBet_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Game_BetOdd.php?act=mypoints";
    //http://domain/app/Ajax_Check_Open.php
    public String CheckGame_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Check_Open.php";
    //奖品详情http://120.78.87.50/app/Prize_Detail_App.php?id=商品id
    public String PrizeDetail_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Prize_Detail_App.php";
    //确认兑换详情http://120.78.87.50/app/Prize_Buy_App.php?id=商品id
    public String ConversionDetail_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Prize_Buy_App.php";
    //兑换须知http://120.78.87.50/app/Prize_Buy_Detail_App.html
    public String ConversionNotice_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Prize_Buy_Detail_App.html";
    //商品介绍http://120.78.87.50/app/Prize_DetailContent_App.php
    public String IntroducePrize_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Prize_DetailContent_App.php";
    //规格参数http://120.78.87.50/app/Prize_DetailSizes_App.php
    public String PrizeSize_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Prize_DetailSizes_App.php";
    //确认兑换http://120.78.87.50/app/Prize_Buy_App.php?act=but&id=商品id
    public String Conversion_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Prize_Buy_App.php?act=buy";
    //对讲记录http://120.78.87.50/app/User_Prize_App.php
    public String ConversionRecord_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Prize_App.php";
    //取消http://120.78.87.50/app/User_Prize_App.php?act=del
    public String CaConversionRecord_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Prize_App.php?act=del";
    //重发http://120.78.87.50/app/User_Prize_App.php?act=chongfa
    public String ReConversionRecord_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Prize_App.php?act=chongfa";
    //闯关奖励http://120.78.87.50/app/Cg_Getprize_App.php
    public String AdvanceRecord_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Cg_Getprize_App.php";
    //领取闯关奖励http://120.78.87.50/app/Ajax_Cg_Getprize.php
    public String AdvanceReward_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_Cg_Getprize.php";
    //消息通知http://120.78.87.50/app/User_Newinfo_App.php
    public String Notice_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Newinfo_App.php";
    //通知详情http://120.78.87.50/app/User_Sms_App.php?act=look
    public String NoticeContent_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Newinfo_App.php?act=look";
    //删除通知http://120.78.87.50/app/User_Newinfo_App.php?act=del
    public String DelNotice_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Newinfo_App.php?act=del";
    //标记已读http://120.78.87.50/app/User_Newinfo_App.php?act=morelook
    public String RedNotice_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "User_Newinfo_App.php?act=morelook";
    //更新未读http://120.78.87.50/app/Ajax_resetNewinfo.php
    public String unRedNotice_URL = RuiXinApplication.getInstance().getUrl() + "app/" + "Ajax_resetNewinfo.php";
//120.78.87.50/app/User_MyWithdraw_App.php User_MyWithdraw_App.class.php提现金币的数据

    public String deposit_info = RuiXinApplication.getInstance().getUrl() + "app/" + "User_MyWithdraw_App.php";

    private static URL instance;

    private URL() {
    }

    public static URL getInstance() {

        instance = new URL();

        return instance;
    }
}
