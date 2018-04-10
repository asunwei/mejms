package com.learn.java.mejms.pointtopoint;

import java.io.Serializable;

/**
 * 消息对象
 * 
 * @author wei.sun02
 *
 */
public class MqBean implements Serializable{
	private Integer age;
	private String name;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
