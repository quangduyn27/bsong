package model.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {
	private int idfavorite;
	private int iduser;
	private int idsong;
	private int trangthai;
}
