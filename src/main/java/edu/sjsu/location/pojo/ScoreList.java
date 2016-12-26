package edu.sjsu.location.pojo;

import java.util.Arrays;

public class ScoreList {

	Score [] score;

	public Score[] getScore() {
		return score;
	}

	public void setScore(Score[] score) {
		this.score = score;
	}

	public ScoreList(Score[] score) {
		super();
		this.score = score;
	}

	public ScoreList() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ScoreList [score=" + Arrays.toString(score) + "]";
	}
	

}
