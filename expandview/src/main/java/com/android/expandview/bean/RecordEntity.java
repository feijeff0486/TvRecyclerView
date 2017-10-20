package com.android.expandview.bean;

import java.util.List;

/**
 * 
 * @ClassName RecordEntity
 * @Description 历史实体类
 * @author xieyi
 * @date 2014-9-24 上午10:06:50
 * 
 */
public class RecordEntity {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String time;// 分组的日期
	private List<AppStoreEntity> appStores;// 应用市场的记录
	private List<VideoRecordEntity> movies;// 点播和极清的记录

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getYear(){
		return time.substring(0, 4);
	}
	
	public String getMonthAndDay(){
		return time.substring(5).replace('-', '/');
	}

	public List<AppStoreEntity> getAppStores() {
		return appStores;
	}

	public void setAppStores(List<AppStoreEntity> appStores) {
		this.appStores = appStores;
	}

	public List<VideoRecordEntity> getMovies() {
		return movies;
	}

	public void setMovies(List<VideoRecordEntity> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		return "RecordEntity{" +
				"time='" + time + '\'' +
				", appStores=" + appStores +
				", movies=" + movies +
				'}';
	}
}
