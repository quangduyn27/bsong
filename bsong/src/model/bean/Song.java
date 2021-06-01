package model.bean;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {
	private int id;
	private String name;
	private String preview;
	private String detail;
	private Timestamp daycreate;
	private String picture;
	private String mp3;
	private int view;
	private Cat cat;
	
	
	
	
}
