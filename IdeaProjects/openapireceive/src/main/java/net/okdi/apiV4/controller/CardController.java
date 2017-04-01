package net.okdi.apiV4.controller;

import java.util.List;

import net.okdi.apiV4.service.CardService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/cardController")
public class CardController extends BaseController{

	@Autowired
	private CardService cardService;
	
	@ResponseBody
	@RequestMapping(value="/publishCard" ,method={RequestMethod.GET,RequestMethod.POST})
	public String publishCard(Long cardId,Long createorMemberId,String type,String content,String imageUrl,String videoUrl){
		return jsonSuccess(cardService.publishCard( cardId, createorMemberId,type, content, imageUrl, videoUrl));
	}
	
	@ResponseBody
	@RequestMapping(value="/cardReadCountAdd" ,method={RequestMethod.GET,RequestMethod.POST})
	public String cardReadCountAdd(Long cardId){
		return jsonSuccess(cardService.cardReadCountAdd(cardId));
	}
	
	@ResponseBody
	@RequestMapping(value="/cardLikeCountAdd" ,method={RequestMethod.GET,RequestMethod.POST})
	public String cardLikeCountAdd(Long cardId,Long memberId){
		return jsonSuccess(cardService.cardLikeCountAdd(cardId,memberId));
	}
	
	@ResponseBody
	@RequestMapping(value="/queryCardDetial" ,method={RequestMethod.GET,RequestMethod.POST})
	public String queryCardDetial(Long cardId,Long memberId){
		return jsonSuccess(cardService.queryCardDetial(cardId,memberId));
	}
	
	@ResponseBody
	@RequestMapping(value="/queryHotCard" ,method={RequestMethod.GET,RequestMethod.POST})
	public String queryHotCard(Long memberId,Integer currentPage,Integer pageCount){
		return jsonSuccess(cardService.queryHotCard(memberId,currentPage,pageCount));
	}
	@ResponseBody
	@RequestMapping(value="/replyCard" ,method={RequestMethod.GET,RequestMethod.POST})
	public String replyCard(Long cardId,String type,Long fromMemberId,Long toMemberId
			,String fromMemberName,String toMemberName,String content){
		return jsonSuccess(cardService.replyCard( cardId, type, fromMemberId, toMemberId
				, fromMemberName, toMemberName,content));
	}
	@ResponseBody
	@RequestMapping(value="/deleteCardComment" ,method={RequestMethod.GET,RequestMethod.POST})
	public String deleteCardComment(Long cardId,Long memberId){
		return jsonSuccess(cardService.deleteCardComment( cardId, memberId));
	}
	@ResponseBody
	@RequestMapping(value="/taHomePage" ,method={RequestMethod.GET,RequestMethod.POST})
	public String taHomePage(Long memberId,Long taMemberId){
		return jsonSuccess(cardService.taHomePage(memberId,taMemberId));
	}
	/**
	 * 
	 * @param fromMemberId 我
	 * @param toMemberId 关注的人
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addAttention" ,method={RequestMethod.GET,RequestMethod.POST})
	public String addAttention(Long fromMemberId,Long toMemberId){
		return jsonSuccess(cardService.addAttention(fromMemberId,toMemberId));
	}
	
	
}
