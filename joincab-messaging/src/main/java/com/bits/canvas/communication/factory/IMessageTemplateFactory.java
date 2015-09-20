package com.bits.canvas.communication.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bits.canvas.communication.msg.template.loader.MessageTemplate;

@Component
public class IMessageTemplateFactory {

	@Autowired
	@Qualifier("hopMsgTemplate")
	MessageTemplate hopMsgTemplate;

	@Autowired
	@Qualifier("postMsgTemplate")
	MessageTemplate postMsgTemplate;

	@Autowired
	@Qualifier("confirmMsgTemplate")
	MessageTemplate confirmMsgTemplate;

	@Autowired
	@Qualifier("verifyMsgTemplate")
	MessageTemplate verifyMsgTemplate;
	
	@Autowired
	@Qualifier("rejectMsgTemplate")
	MessageTemplate rejectMsgTemplate;

	@Autowired
	@Qualifier("cancelByHopMsgTemplate")
	MessageTemplate cancelByHopMsgTemplate;
	
	@Autowired
	@Qualifier("cancelByPostMsgTemplate")
	MessageTemplate cancelByPostMsgTemplate;
	
	@Autowired
	@Qualifier("forgotPwdMsgTemplate")
	MessageTemplate forgotPwdMsgTemplate;
	
	@Autowired
	@Qualifier("bookMsgTemplate")
	MessageTemplate bookMsgTemplate;
	
	public MessageTemplate getInstance(String name){
		MessageTemplate messageTemp = null;
		if(name.equals("HOP_TEMPLATE")){
			messageTemp = hopMsgTemplate;
		}else if(name.equals("CONFIRM_TEMPLATE")){
			messageTemp = confirmMsgTemplate;
		}else if(name.equals("VERIFY_TEMPLATE")){
			messageTemp = verifyMsgTemplate;
		}else if(name.equals("POST_TEMPLATE")){
			messageTemp = postMsgTemplate;
		}else if(name.equals("REJECT_TEMPLATE")){
			messageTemp = rejectMsgTemplate;
		}else if(name.equals("CANCEL_BY_POST_TEMPLATE")){
			messageTemp = cancelByPostMsgTemplate;
		}else if(name.equals("CANCEL_BY_HOP_TEMPLATE")){
			messageTemp = cancelByHopMsgTemplate;
		}else if(name.equals("FORGOT_PWD_TEMPLATE")){
			messageTemp = forgotPwdMsgTemplate;
		}else if(name.equals("BOOK_TEMPLATE")){
			messageTemp = bookMsgTemplate;
		}
		return messageTemp;
	}
}