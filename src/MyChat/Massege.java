package MyChat;

public class Massege {
	private String massegeAll="";
	
	public void setMassege(String temp){
		massegeAll=massegeAll+temp+'\n';
	}
	public String getMassege(){
		return massegeAll;
	}
}
