package edu.sjsu.location.pojo;

public class Question {

	String gameID;
	String ques;
	public String getGameID() {
		return gameID;
	}
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
	public String getQues() {
		return ques;
	}
	public void setQues(String ques) {
		this.ques = ques;
	}
	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Question(String gameID, String ques) {
		super();
		this.gameID = gameID;
		this.ques = ques;
	}
	@Override
	public String toString() {
		return "Question [gameID=" + gameID + ", ques=" + ques + "]";
	}
	
}
