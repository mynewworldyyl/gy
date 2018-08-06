package com.gy.base.email;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailService {

    private final static Logger logger = LoggerFactory.getLogger(MailService.class);
   
    @Autowired
    private MailSender mailSender;
	
    @Value("#{configProperties['email.from']}")
    private String from;
	
	public void sendSimpleMail(String to, String subject, String text) {
		logger.info("begin send email, to :" + to +", subject :" + subject + ", text :" + text);
		try{
			SimpleMailMessage msg = new SimpleMailMessage();
			
			msg.setTo(to);
			msg.setSubject(subject);
			msg.setText(text);
			msg.setSentDate(new Date());
			msg.setFrom(from);
			mailSender.send(msg);
			logger.info("email send success!");
		}catch(Exception ex){
			logger.info("emial send fail, cause by :",ex);
		}
	}
	

	public void sendHTMLMail(String to, String subject, String htmlString,String encoding){
		//TODO
	}
	
	public boolean sendSimpleMail(String[] to, String subject, String text) {
        logger.info("begin send email, to :" + to +", subject :" + subject + ", text :" + text);
        try{
            SimpleMailMessage msg = new SimpleMailMessage();
            
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(text);
            msg.setSentDate(new Date());
            msg.setFrom(from);
            mailSender.send(msg);
            logger.info("email send success!");
        }catch(Exception ex){
            logger.error("emial send fail, cause by :" ,ex);
            return false;
        }
        return true;
    }
	

}
