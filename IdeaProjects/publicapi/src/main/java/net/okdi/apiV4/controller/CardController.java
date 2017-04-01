package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.CardService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/cardController")
public class CardController extends BaseController{

	private Logger logger=Logger.getLogger(CardController.class);
	@Autowired
	private CardService cardService;
	/**
	 * @api {post} /cardController/publishCard  发布帖子
	 * @apiVersion 0.3.0
	 * @apiDescription 发布帖子
	 * @apiGroup 快递圈
	 * @apiParam {Long} createorMemberId 发帖人id
	 * @apiParam {String} type 帖子类型:1单独的文字,2单独图,3单独小视频    4  文字+图片组合  5文字+小视频组合
	 * @apiParam {String} source 来源(ios或者android)
	 * @apiParam {String} content 内容
	 * @apiParam {String} creatorName 发帖人姓名
	 * @apiParam {MultipartFile} image1 第一个图片
	 * @apiParam {MultipartFile} image2 第二个图片
	 * @apiParam {MultipartFile} image3 第三个图片
	 * @apiParam {MultipartFile} image4 第四个图片
	 * @apiParam {MultipartFile} video 视频
	 * @apiSampleRequest /cardController/publishCard
	 * @apiSuccess {String} data 客户id
	 * @apiSuccessExample Success-Response:
	  {"data":"208621067493376","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 *     
	 */
	@ResponseBody
	@RequestMapping(value="/publishCard1" ,method={RequestMethod.GET,RequestMethod.POST})
	public String publishCard1(Long createorMemberId,String type,String source,String content, @RequestParam(value = "", required=false) MultipartFile... video){
		if(PubMethod.isEmpty(createorMemberId)){
			return paramsFailure("CardController.publishCard.001", "创建人不能为空");
		}
		MultipartFile image1=null;
		MultipartFile image2=null;
		MultipartFile image3=null;
		MultipartFile image4=null;
		MultipartFile video1=null;
//		for (MultipartFile mf : video) {
//			video[0];
//		}
		
		if(PubMethod.isEmpty(type)){
			return paramsFailure("CardController.publishCard.002", "类型不能为空");
		}
		return cardService.publishCard(createorMemberId,type,source, content, image1,image2,image3,image4, video1);
//		return cardService.publishCard1(createorMemberId,type,source, content, video);

//		return "";
	}
	@ResponseBody
	@RequestMapping(value="/publishCard2" ,method={RequestMethod.GET,RequestMethod.POST})
	public String publishCard2(Long createorMemberId,String type,String source,String content, 
			@RequestParam(value = "image1", required=false) MultipartFile image1,
			@RequestParam(value = "image2", required=false) MultipartFile image2,
			@RequestParam(value = "image3", required=false) MultipartFile image3,
			@RequestParam(value = "image4", required=false) MultipartFile image4,
			@RequestParam(value = "video", required=false) MultipartFile video) throws IOException{
		if(PubMethod.isEmpty(createorMemberId)){
			return paramsFailure("CardController.publishCard.001", "创建人不能为空");
		}
		if(PubMethod.isEmpty(type)){
			return paramsFailure("CardController.publishCard.002", "类型不能为空");
		}
		
		return cardService.publishCard(createorMemberId,type,source, content, image1,image2,image3,image4, video);
//		return cardService.publishCard1(createorMemberId,type,source, content, video);
		
//		return "";
	}
	
	@ResponseBody
	@RequestMapping(value="/publishCard" ,method={RequestMethod.GET,RequestMethod.POST})
	public String publishCard(HttpServletRequest request) throws IOException{
	
		logger.info("进入发布帖子：。。。。。。。。。。。。。。。。。");
		String createorMemberId=request.getParameter("createorMemberId");
		String type=request.getParameter("type");
		String source=request.getParameter("source");
		String content=request.getParameter("content");
		String creatorName=request.getParameter("creatorName");
		List<MultipartFile> video =new ArrayList<MultipartFile>();
		if((request instanceof MultipartHttpServletRequest)) {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
			MultiValueMap<String, MultipartFile> mMap = mreq.getMultiFileMap();
			Iterator<String> iter = mMap.keySet().iterator();
			while(iter.hasNext()) {
				String key = iter.next();
				List<MultipartFile> list =  mMap.get(key);
				video.add(list.get(0));

			}
		}
		
		return cardService.publishCard1(createorMemberId,type,source, content, video,creatorName);
	}
	
	
	/**
	 * @author mcg
	 * @api {post} /cardController/cardReadCountAdd 阅读数加1
	 * @apiVersion 0.2.0
	 * @apiDescription 阅读数加1
	 * @apiGroup 快递圈
	 * @apiParam {Long} cardId 帖子id
	 * @apiSampleRequest /cardController/cardReadCountAdd
	 * @apiSuccessExample Success-Response:
	   {"data":"208621067493376","success":true}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/cardReadCountAdd" ,method={RequestMethod.GET,RequestMethod.POST})
	public String cardReadCountAdd(Long cardId){
		if(PubMethod.isEmpty(cardId)){
			return paramsFailure("CardController.cardReadCountAdd.001", "cardId不能为空");
		}
		return cardService.cardReadCountAdd(cardId);
	}
	/**
	 * @author mcg
	 * @api {post} /cardController/cardLikeCountAdd 点赞
	 * @apiVersion 0.2.0
	 * @apiDescription 点赞
	 * @apiGroup 快递圈
	 * @apiParam {Long} cardId 帖子id
	 * @apiParam {Long} memberId memberId
	 * @apiParam {String} type 点赞or取消(1:点赞,2:取消赞)
	 * @apiSampleRequest /cardController/cardLikeCountAdd
	 * @apiSuccessExample Success-Response:
	   {"data":"1","success":true}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/cardLikeCountAdd" ,method={RequestMethod.GET,RequestMethod.POST})
	public String cardLikeCountAdd(Long cardId,Long memberId,String type){
		if(PubMethod.isEmpty(cardId)){
			return paramsFailure("CardController.cardLikeCountAdd.001", "cardId不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("CardController.cardLikeCountAdd.002", "memberId不能为空");
		}
		if(PubMethod.isEmpty(type)){
			return paramsFailure("CardController.cardLikeCountAdd.002", "type不能为空");
		}
		return cardService.cardLikeCountAdd(cardId,memberId,type);
	}
	/**
	 * @author mcg
	 * @api {post} /cardController/queryCardDetial 动态详情
	 * @apiVersion 0.2.0
	 * @apiDescription 动态详情
	 * @apiGroup 快递圈
	 * @apiParam {Long} cardId 帖子id
	 * @apiParam {Long} memberId memberId
	 * @apiParam {Long} currentPage 当前页
	 * @apiParam {Long} pageCount 一页显示多少数据
	 * @apiSampleRequest /cardController/queryCardDetial
	 * @apiSuccess {String} cardId 帖子id
	 * @apiSuccess {String} comments 评论
	 * @apiSuccess {String} content 评论内容(comments下的content)
	 * @apiSuccess {String} creatorMemberId 创建人id
	 * @apiSuccess {String} cardCommentId 帖子评论id
	 * @apiSuccess {String} commentTime 评论时间
	 * @apiSuccess {String} commentType 评论类型（1：评论，2：回复）
	 * @apiSuccess {String} fromMemberId 评论人id
	 * @apiSuccess {String} fromMemberName 评论人姓名
	 * @apiSuccess {String} fromMemberUrl 评论人头像
	 * @apiSuccess {String} toMemberId 被回复人id
	 * @apiSuccess {String} toMemberName 被回复人姓名
	 * @apiSuccess {String} content 帖子内容(data下的)
	 * @apiSuccess {String} createTime 帖子创建时间
	 * @apiSuccess {String} createorMemberId 帖子创建人id
	 * @apiSuccess {String} createorName 帖子创建人姓名
	 * @apiSuccess {String} imageUrl 图片url
	 * @apiSuccess {String} readCount 阅读数量
	 * @apiSuccess {String} countComment 评论数
	 * @apiSuccess {String} type 帖子类型
	 * @apiSuccess {String} upCount 点赞数量
	 * @apiSuccess {String} videoUrl 视频url
	 * @apiSuccess {String} like 自己是否点赞(0 否 1 是)
	 * @apiSuccess {String} videoPictureUrl 视频图片第一帧
	 * @apiSuccess {Boolean} isAttention 是否关注过（true:关注过，false：未关注）
	 * @apiSuccess {String} shareUrl 分享的jsp页面地址
	 * @apiSuccess {String} status 审核状态（0：未审核，1：审核通过，2：审核不通过）
	 * @apiSuccess {String} flag 删除标示（1：已删除帖子，2：未删除）
	 * @apiSuccess {String} cardLikes 点赞过的人的memberId
	 * @apiSuccessExample Success-Response:
		{
		  "data": {
		    "baseUrl": "http://publicapi.okdit.net/nfs_data/mob/card/", 
		    "cardId": 218198283132929, 
		    "comments": [
		      {
		        "cardCommentId": 219083916492800, 
		        "cardId": 218198283132929, 
		        "commentTime": 1466221093391, 
		        "commentType": "1", 
		        "content": "哈哈哈哈哈哈哈哈", 
		        "fromMemberId": 4565211335, 
		        "fromMemberName": "测试数据", 
		        "fromMemberUrl": "http://cas.okdit.net/nfs_data/mob/head/4565211335.jpg",
		        "toMemberId": 8611132332, 
		        "toMemberName": "马建鑫"
		      }
		    ], 
		    "content": "[test]This is my html which I'm generating dynamically using drag and drop functionality.", 
		    "countComment": 1, 
		    "createTime": 1465798790553, 
		    "createorMemberId": 4381409032185002, 
		    "createorName": "", 
		    "imageUrl": [
		      "http://127.0.0.1/images//periodic-table-of-seo-2015-800x600.png"
		    ], 
		    "like": "0", 
		    "readCount": 13, 
		    "status": "", 
		    "type": "2", 
		    "upCount": 0, 
		    "videoUrl": "",
		    "videoPictureUrl":"",
		    "flag":"",
		    "cardLikes":[
                143757781835776,
                212192209453056
            ]
		  }, 
		  "success": true
		}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/queryCardDetial" ,method={RequestMethod.GET,RequestMethod.POST})
	public String queryCardDetial(Long cardId,Long memberId,Integer currentPage,Integer pageCount){
		if(PubMethod.isEmpty(cardId)){
			return paramsFailure("CardController.queryCardDetial.001", "cardId不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("CardController.queryCardDetial.002", "memberId不能为空");
		}
		return cardService.queryCardDetial(cardId,memberId,currentPage,pageCount);
	}
	/**
	 * @author mcg
	 * @api {post} /cardController/queryHotCard 热门
	 * @apiVersion 0.2.0
	 * @apiDescription 热门
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId memberId
	 * @apiParam {Long} currentPage 当前页
	 * @apiParam {Long} pageCount 一页显示多少数据
	 * @apiSampleRequest /cardController/queryHotCard
	 * @apiSuccess {String} baseUrl 基础地址(头像地址=基础地址+创建人id)
	 * @apiSuccess {String} cardId 帖子id
	 * @apiSuccess {String} commentCount 评论数量
	 * @apiSuccess {String} creatorMemberId 创建人id
	 * @apiSuccess {String} imageUrl 图片地址
	 * @apiSuccess {String} isUpLike 是否点赞(1：点赞过，0：没点赞)
	 * @apiSuccess {String} type 帖子类型
	 * @apiSuccess {String} videoUrl 视频地址
	 * @apiSuccess {String} createTime 创建时间
	 * @apiSuccess {String} readCount 阅读人数
	 * @apiSuccess {String} upCount 点赞人数
	 * @apiSuccess {String} creatorName 姓名
	 * @apiSuccess {String} videoPictureUrl 视频图片第一帧
	 * @apiSuccess {String} simageUrl 缩略图片地址
	 * @apiSuccess {String} cardLikes 点赞过的人的memberId
	 * @apiSuccess {Boolean} isAttention 是否关注过（true：关注过，false：未关注）
	 * @apiSuccess {Boolean} isAdmin 是否是管理员（true：是，false：否）
	 * @apiSuccess {List} cardLikes 点赞人的memberId
	 * @apiSuccessExample Success-Response:
	   {
	    "data": [
	        {
	            "baseUrl": "http://cas.okdit.net/nfs_data/mob/head/",
	            "cardId": 233462642827264,
	            "cardLikes": [
	                222932519665664
	            ],
	            "commentCount": 0,
	            "content": "不困了",
	            "createTime": 1473077370349,
	            "creatorMemberId": 222932519665664,
	            "creatorName": "甜甜",
	            "imageUrl": [],
	            "isAdmin": false,
	            "isAttention": false,
	            "isUpLike": "0",
	            "readCount": 3,
	            "simageUrl": [],
	            "status": "0",
	            "type": "1",
	            "upCount": 1,
	            "videoPictureUrl": "",
	            "videoUrl": ""
	        }
	    ]
	    "success": true
	}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/queryHotCard" ,method={RequestMethod.GET,RequestMethod.POST})
	public String queryHotCard(Long memberId,Integer currentPage,Integer pageCount){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("CardController.queryHotCard.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("CardController.queryHotCard.002", "currentPage不能为空");
		}
		if(PubMethod.isEmpty(pageCount)){
			return paramsFailure("CardController.queryHotCard.003", "pageCount不能为空");
		}
		return cardService.queryHotCard(memberId,currentPage,pageCount);
	}
	/**
	 * @author mcg
	 * @api {post} /cardController/replyCard 评论或者回复帖子
	 * @apiVersion 0.2.0
	 * @apiDescription 评论或者回复帖子
	 * @apiGroup 快递圈
	 * @apiParam {Long} cardId 帖子id
	 * @apiParam {Long} type 类型(1:评论  2：回复)
	 * @apiParam {Long} fromMemberId 评论人memberId
	 * @apiParam {Long} toMemberId 被回复人memberId
	 * @apiParam {Long} fromMemberName 评论人姓名
	 * @apiParam {Long} toMemberName 被回复人姓名
	 * @apiParam {String} content 评论内容
	 * @apiParam {Long} forCommentId 被回复的评论Id
	 * @apiSampleRequest /cardController/replyCard
	 * @apiSuccessExample Success-Response:
	   {"data":"1","success":true}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/replyCard" ,method={RequestMethod.GET,RequestMethod.POST})
	public String replyCard(Long cardId,String type,Long fromMemberId,Long toMemberId
			,String fromMemberName,String toMemberName,String content,Long forCommentId){
		return cardService.replyCard( cardId, type, fromMemberId, toMemberId
				, fromMemberName, toMemberName,content,forCommentId);
	}
	/**
	 * @author mcg
	 * @api {post} /cardController/deleteCardComment 删除帖子评论
	 * @apiVersion 0.2.0
	 * @apiDescription 删除帖子评论
	 * @apiGroup 快递圈
	 * @apiParam {Long} cardCommentId 帖子评论id
	 * @apiParam {Long} cardId 帖子id
	 * @apiSampleRequest /cardController/deleteCardComment
	 * @apiSuccessExample Success-Response:
	   {"data":"1","success":true}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/deleteCardComment" ,method={RequestMethod.GET,RequestMethod.POST})
	public String deleteCardComment(Long cardCommentId,Long cardId){
		return cardService.deleteCardComment( cardCommentId,cardId);
	}
	/**
	 * @author mcg
	 * @api {post} /cardController/taHomePage 他的动态
	 * @apiVersion 0.2.0
	 * @apiDescription 我的动态
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId memberId
	 * @apiParam {Long} taMemberId 他的taMemberId
	 * @apiParam {Long} currentPage 当前页
	 * @apiParam {Long} pageCount 一页有多少数据
	 * @apiSampleRequest /cardController/taHomePage
	 * @apiSuccess {String} baseUrl 基础路径
	 * @apiSuccess {String} cards 动态信息
	 * @apiSuccess {String} cardCommentCout 评论数量
	 * @apiSuccess {String} content 帖子内容
	 * @apiSuccess {String} imageUrl 图片地址
	 * @apiSuccess {String} taMemberName 姓名
	 * @apiSuccess {String} upCount 点赞数量
	 * @apiSuccess {String} videoUrl 视频url
	 * @apiSuccess {String} taMemberId 他的memberId
	 * @apiSuccess {Boolean} isAttention 是否关注(true:关注过,fasle:没有关注过)
	 * @apiSuccess {String} sendAmount 派件数
	 * @apiSuccess {String} takeAmount 取件数
	 * @apiSuccess {String} videoPictureUrl 视频图片第一帧
	 * @apiSuccess {Boolean} isLike 是否点赞(true:点赞过,fasle:没有点赞过)
	 * @apiSuccess {Long} cardId cardId 
	 * @apiSuccess {Long} readCount 阅读数 
	 * @apiSuccess {String} compName 所属快递
	 * @apiSuccess {String} netName 所属站点 
	 * @apiSuccess {Long} attentionCount 关注人数 
	 * @apiSuccess {Long} fansCount 粉丝数
	 * @apiSuccess {Long} friendCount 好友数
	 * @apiSuccessExample Success-Response:
	   {
    "data": {
        "baseUrl": "http://cas.okdit.net/nfs_data/mob/head/",
        "cards": [
            {
                "cardCommentCout": 0,
                "cardId": 226502335848448,
                "content": "",
                "createTime": 1469758452273,
                "imageUrl": [
                    "http://publicapi.okdit.net/nfs_data/mob/card/3_226502335848448_1.jpg",
                    "http://publicapi.okdit.net/nfs_data/mob/card/3_226502335848448_2.jpg",
                    "http://publicapi.okdit.net/nfs_data/mob/card/3_226502335848448_3.jpg",
                    "http://publicapi.okdit.net/nfs_data/mob/card/3_226502335848448_4.jpg"
                ],
                "isLike": false,
                "readCount": 4,
                "taMemberName": "逆光。",
                "upCount": 0,
                "videoPictureUrl": "",
                "videoUrl": ""
            }
        ],
        "compName": "北京西城区宣武公司",
        "attentionCount": 5,
        "fansCount": 5,
        "friendCount": 6,
        "isAttention": false,
        "netName": "韵达快递",
        "taMemberId": 203725182648320,
        "taMemberName": "逆光。"
    },
    "success": true
}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/taHomePage" ,method={RequestMethod.GET,RequestMethod.POST})
	public String taHomePage(Long memberId,Long taMemberId,Integer currentPage,Integer pageCount){
		return cardService.taHomePage(memberId,taMemberId,currentPage,pageCount);
	}
	
	/**
	 * @author mcg
	 * @api {post} /cardController/addAttention 添加关注
	 * @apiVersion 0.2.0
	 * @apiDescription 添加关注
	 * @apiGroup 快递圈
	 * @apiParam {Long} fromMemberId 我的memberId
	 * @apiParam {Long} fromMemberName 我的名字
	 * @apiParam {Long} toMemberId 被关注人的memberId
	 * @apiParam {Long} toMemberName 被关注人的名字
	 * @apiSampleRequest /cardController/addAttention
	 * @apiSuccessExample Success-Response:
	   {"data":"1","success":true}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/addAttention" ,method={RequestMethod.GET,RequestMethod.POST})
	public String addAttention(Long fromMemberId,Long toMemberId,String fromMemberName,String toMemberName){
		return cardService.addAttention(fromMemberId,toMemberId,fromMemberName,toMemberName);
	}
	
	/**
	 * @author mjx
	 * @api {post} /cardController/cardReport 帖子举报
	 * @apiVersion 0.2.0
	 * @apiDescription 帖子举报
	 * @apiGroup 快递圈
	 * @apiParam {Long} informerId 举报人Id    
	 * @apiParam {Long} beReportId 被举报人Id   
	 * @apiParam {String} informerName 举报人名字  
	 * @apiParam {String} beReportName 被举报人名字 
	 * @apiParam {Long} cardId 被举报帖子Id      
	 * @apiParam {Long} reasonId 举报原因Id
	 * @apiParam {String} resonText 举报原因内容    
	 * @apiParam {String} reportCont 举报内容备注 
	 * @apiSampleRequest /cardController/cardReport
	 * @apiSuccessExample Success-Response:
	   {"data":"1","success":true}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/cardReport" ,method={RequestMethod.GET,RequestMethod.POST})
	public String cardReport(Long informerId,Long beReportId,String informerName,String beReportName,Long cardId,
			Long reasonId, String resonText,String reportCont){
		if(PubMethod.isEmpty(informerId)){
			return paramsFailure("net.okdi.apiV4.controller.CardController.cardReport", "informerId不能为空");
		}
		if(PubMethod.isEmpty(beReportId)){
			return paramsFailure("net.okdi.apiV4.controller.CardController.cardReport", "beReportId不能为空");
		}
		if(PubMethod.isEmpty(informerName)){
			return paramsFailure("net.okdi.apiV4.controller.CardController.cardReport", "informerName不能为空");
		}
		/*if(PubMethod.isEmpty(beReportName)){
			return paramsFailure("net.okdi.apiV4.controller.CardController.cardReport", "beReportName不能为空");
		}*/
		if(PubMethod.isEmpty(cardId)){
			return paramsFailure("net.okdi.apiV4.controller.CardController.cardReport", "cardId不能为空");
		}
		if(PubMethod.isEmpty(reasonId)){
			return paramsFailure("net.okdi.apiV4.controller.CardController.cardReport", "reasonId不能为空");
		}
		if(PubMethod.isEmpty(resonText)){
			return paramsFailure("net.okdi.apiV4.controller.CardController.cardReport", "resonText不能为空");
		}
		try{	
			return cardService.cardReport(informerId,beReportId,informerName,beReportName,cardId,reasonId,resonText,reportCont);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author mjx
	 * @api {post} /cardController/queryReasonList 查询帖子举报原因列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询帖子举报原因列表
	 * @apiGroup 快递圈
	 * @apiSampleRequest /cardController/queryReasonList
	 * @apiSuccessExample Success-Response:
	   {
	    "data": [
		        {
		            "reasonId": 1,
		            "resonText": "色情低俗"
		        },
		        {
		            "reasonId": 2,
		            "resonText": "广告骚扰"
		        }
		    ],
		    "success": true
		}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/queryReasonList", method={RequestMethod.POST, RequestMethod.GET})
	public String queryReasonList(){
		try {
			return cardService.queryReasonList();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author mcg
	 * @api {post} /cardController/deleteCard 删除帖子
	 * @apiVersion 0.2.0
	 * @apiDescription 删除帖子
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId 删除人的memberId(当前登录人的memberId)
	 * @apiParam {Long} cardId 帖子id
	 * @apiSampleRequest /cardController/deleteCard
	 * @apiSuccessExample Success-Response:
	   {"data":"1","success":true}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/deleteCard" ,method={RequestMethod.GET,RequestMethod.POST})
	public String deleteCard(Long memberId,Long cardId){
		try{	
			return cardService.deleteCard( memberId,cardId);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author mcg
	 * @api {post} /cardController/queryCardReader 查询阅读帖子的人
	 * @apiVersion 0.2.0
	 * @apiDescription 查询阅读帖子的人
	 * @apiGroup 快递圈
	 * @apiParam {Long} cardId 帖子id
	 * @apiParam {Long} memberId 当前登录人memberId
	 * @apiParam {Long} currentPage 当前页
	 * @apiParam {Long} pageCount 一页显示多少数据
	 * @apiSuccess {String} baseUrl 头像的基本url(baseUrl+memberId+".jpg")
	 * @apiSuccess {Boolean} isAttention 是否关注过(true:关注过，false：没有关注）
	 * @apiSuccess {String} memberName 阅读人的姓名
	 * @apiSuccess {Long} createTime 阅读时间
	 * @apiSampleRequest /cardController/queryCardReader
	 * @apiSuccessExample Success-Response:
	  {
    "data":{
        "baseUrl":"http://cas.okdit.net/nfs_data/mob/head/",
        "readerList":[
            {
                "cardId":224512544702465,
                "createTime":1471413901429,
                "isAttention":false,
                "memberId":3435096,
                "memberName":"",
                "randomNum":"XshxoIr2"
            },
            {
                "cardId":224512544702465,
                "createTime":1471414053294,
                "isAttention":false,
                "memberId":120711297679360,
                "memberName":"",
                "randomNum":"Vwlxar2z"
            }
        ]
    },
    "success":true
}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/queryCardReader" ,method={RequestMethod.GET,RequestMethod.POST})
	public String queryCardReader(Long cardId,Long memberId,Integer currentPage,Integer pageCount){
		try{	
			return cardService.queryCardReader( cardId,memberId,currentPage,pageCount);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author mcg
	 * @api {post} /cardController/queryCardLike 查询点赞帖子的人
	 * @apiVersion 0.2.0
	 * @apiDescription 查询点赞帖子的人
	 * @apiGroup 快递圈
	 * @apiParam {Long} cardId 帖子id
	 * @apiParam {Long} memberId 当前登录人memberId
	 * @apiParam {Long} currentPage 当前页
	 * @apiParam {Long} pageCount 一页显示多少数据
	 * @apiSuccess {String} baseUrl 头像的基本url(baseUrl+memberId+".jpg")
	 * @apiSuccess {Boolean} isAttention 是否关注过(true:关注过，false：没有关注）
	 * @apiSuccess {String} memberName 点赞人的姓名
	 * @apiSuccess {Long} createTime 点赞时间
	 * @apiSampleRequest /cardController/queryCardLike
	 * @apiSuccessExample Success-Response:
	  {
    "data":{
        "LikeList":[
            {
                "cardId":220191944663040,
                "createTime":1466749957269,
                "isAttention":false,
                "memberId":150242899337216,
                "memberName":"",
                "randomNum":""
            }
        ],
        "baseUrl":"http://cas.okdit.net/nfs_data/mob/head/"
    },
    "success":true
}
	 * @apiErrorExample Error-Response:
	 *   HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value="/queryCardLike" ,method={RequestMethod.GET,RequestMethod.POST})
	public String queryCardLike(Long cardId,Long memberId,Integer currentPage,Integer pageCount){
		try{	
			return cardService.queryCardLike( cardId,memberId,currentPage,pageCount);
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
}
