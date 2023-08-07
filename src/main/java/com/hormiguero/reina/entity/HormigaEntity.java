package com.hormiguero.reina.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "hormigas")
public class HormigaEntity {
	
	public HormigaEntity() {
		
	}
	
	public HormigaEntity(int id, String type, Date birthday) {
		this.id = id;
		this.type = type;
		this.birthday = birthday;
	}
	
    @Id
    private int id;
    private String type;
    private Date birthday;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

    public boolean isSameType(String tipo) {
    	return tipo.matches(this.type);
    }
}