package ein.mono.member.controller.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Gmail extends Authenticator{
	@Override

    protected PasswordAuthentication getPasswordAuthentication() {

        return new PasswordAuthentication("yujisang93","dbdlftkd56");

    }




}
