package edu.sjsu.location.pojo;

public class Score {

	private String gameID;
	private String score;
	private String time ;
	private String playerName;
	private String quesId;
	public String getGameID() {
		return gameID;
	}
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getQuesId() {
		return quesId;
	}
	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}
	@Override
	public String toString() {
		return "Score [gameID=" + gameID + ", score=" + score + ", time=" + time + ", playerName=" + playerName
				+ ", quesId=" + quesId + "]";
	}
	public Score(String gameID, String score, String time, String playerName, String quesId) {
		super();
		this.gameID = gameID;
		this.score = score;
		this.time = time;
		this.playerName = playerName;
		this.quesId = quesId;
	}
	public Score() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	
}
