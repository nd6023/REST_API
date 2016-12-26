package edu.sjsu.location.pojo;

public class FinalScore {
	
	private String gameID;
	private String score;
	private String playerName;
	
	
	
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
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public FinalScore(String gameID, String score, String playerName) {
		super();
		this.gameID = gameID;
		this.score = score;
		this.playerName = playerName;
	}
	public FinalScore() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "FinalScore [gameID=" + gameID + ", score=" + score + ", playerName=" + playerName + "]";
	}
	
	

}
