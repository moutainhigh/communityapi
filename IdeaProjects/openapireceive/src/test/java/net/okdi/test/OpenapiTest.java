package net.okdi.test;

import net.okdi.apiV1.service.ExpressUserService;
import net.okdi.apiV1.service.OutletsService;
import net.okdi.apiV1.service.QueryNearInfoService;
import net.okdi.apiV4.service.AssignPackageService;
import net.okdi.apiV4.service.ProblemPackageService;
import net.okdi.apiV4.service.SendPackageService;
import net.okdi.core.common.page.Page;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OpenapiTest extends BaseTest{
	
	@Autowired
	private QueryNearInfoService queryNearInfoService;
	@Autowired
	private AssignPackageService assignPackageService;
	@Autowired
	private ProblemPackageService problemPackageService;
	@Autowired
	private OutletsService outletsService;
	@Autowired
	private SendPackageService sendPackageService;
	@Autowired
	private ExpressUserService expressUserService;
	
	
	
//	@Test
//	public void addTask() {
//		String detail = sendPackageService.addSendTask(1, 210805402681452L, 1526L,
//				1,-1, 11, 0, 150242899337216L,
//				"13521225785", "张三啊", "13726262555", 
//				0L, "环星大厦A",
//				 new Date(), 0, new Date(),"880052109060",null,null,"北京市海淀区");//210021320114176
//		System.out.println(detail);
//		//parcelId:210053167464449  taskId:210053167464448  memberid:18666666666   环星大厦B
//		//parcelId:210053452677121  taskId:210053452677120  memberid:16688888888  环星大厦A
//	}
	@Test
	public void daipailist() {
		Page page = sendPackageService.querySendTaskList(211506300239872L, 1, 20,"");
		System.out.println(jsonSuccess(page));
	}
//	@Test
//	public void zhengchagnqianshou() throws Exception {
//		sendPackageService.normalSignParcel(212599570743298L, "212599570743296", null, null, 179145206538240L, "15011232453", "赵虎", "环星大厦", "1", "2","1");
//	}
////	  "parcelId": 210058086760448,
////      "parcelNum": "",
////      "replyStatus": "",
////      "sendSmsStatus": "",
////      "sendSmsType": "",
////      "taskId": 210058153869312
//	@Test
//	public void yichangchagnqianshou() throws Exception {
//		sendPackageService.exceptionSignParcel("209866114088960", "209866114088961", "179145206538240", "5", "你妹的太远了", "");
//	}
//	@Test
//	public void xiangqing() throws Exception {
//		HashMap<String, Object> page = sendPackageService.queryParcelDetail("211105356857345");
//		System.out.println(jsonSuccess(page));
//	}
//	@Test
//	public void bianjibaoguo() throws Exception {
//		sendPackageService.updateParcelInfo("211105356857345", "2000", "7546987523658", "15600215141", "五道口5号", "13", "13", "涨涨三");
//	}
//	
//	@Test
//	public void daipaijilulist() throws Exception {
//		HashMap<String, Object> page = sendPackageService.querySendRecordList(179145206538240L, "2016-05-16");
//		System.out.println(jsonSuccess(page));
//	}
//	
//	@Test
//	public void fenpaibaoguo() {
//		Map<String, Object> page = assignPackageService.saveParcelInfo("456", null, null, "张三", "14444444444", null, "北京市海淀区", "大山里", null, null, 179145206538240L, 179145206538240L);
////		String expWaybillNum, Long compId, Long netId, String addresseeName, String addresseeMobile,Long addresseeAddressId,String cityName,
////		String addresseeAddress, BigDecimal freight,BigDecimal codAmount,Long createUserId, Long actualSendMember
//		System.out.println(jsonSuccess(page));//parcelId:209872099360768   #209873238114304
//	}
//	@Test
//	public void daitiList() {
//		Page page = sendPackageService.queryParcelToBeTakenList(211506300239872L, 1, 20);
//		System.out.println(jsonSuccess(page));
//	}
//	@Test
//	public void tihuo() {
//		sendPackageService.pickUpParcel("210053995839488,210054587236352,210055774224384,210056185266176", 179145206538240L, "15011232453");//189752303558656
//	}
//	@Test
//	public void zhuandan() {
//		sendPackageService.changeSendPerson("211506300239872", "211507086671872", "211512539267072,211509798289408");
//	}
//	@Test
//	public void shuliang() {
//		HashMap<String, Object> countAll = sendPackageService.querySendCountAll("209683092914176");
//		System.out.println(jsonSuccess(countAll));
//	}
////	待提包裹数量：0，query=Query: { "actualSendMember" : "165005947641856" , "sendTaskId" :  null  , "parcelEndMark" : { "$ne" : "1"}}, Fields: null, Sort: null
////  待派任务数量：0，query=Query: { "taskType" : 1 , "actorMemberId" : "165005947641856" , "taskFlag" : 0 , "taskIsEnd" : 0}, Fields: null, Sort: null
////	进入查询待派任务列表，query=Query: { "taskType" : 1 , "actorMemberId" : 165005947641856 , "taskFlag" : 0 , "taskIsEnd" : 0}, Fields: null, Sort: { "createTime" : -1}
//	@Test
//	public void piliangqianshou() {
//		sendPackageService.normalSignParcelBatch("209872518168576");//209872518168576
//	}
//	@Test
//	public void chongpai() {
//		problemPackageService.probPackAssignAgain("210922337779713", 151131561205760L, "15600215110");
//	}
//	@Test
//	public void yanzhengshoujihao() {
//		assignPackageService.parcelIsExist("1526",15123232323L);
//	}
}
