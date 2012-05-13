package com.cheapvacations.utility;

import java.io.Serializable;

public class UserData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public UserData(){
		Id = 0;
		type = 0;
		name = null;
	}
	
	public long Id;
	public int type;
	public String name;
}
