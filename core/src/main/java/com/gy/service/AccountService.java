package com.gy.service;



public interface AccountService { 
	public static enum Type{
		/** 超级管理员 */
		Root,
		/** 管理员 */
		Admin,
		/** 普通 */
		Normal
	}
	 
	public static enum Status{
		/** 正常 */
		Normal,
		/** 锁定 */
		Lock
		
	}
}
