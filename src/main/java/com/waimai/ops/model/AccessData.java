package com.waimai.ops.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 访问数据
 * 
 * @author wenzhiwei 2015年12月25日 AccessData by eclipse
 */
public class AccessData {

	public static final String APP_COMMON_DATA = "APP_COMMON_DATA";// 用来存储用户信息key

	public long getCurrentRequestId() {
		return currentRequestId;
	}

	private long currentRequestId = System.currentTimeMillis() % 100000000;
	private String uuid; // 用户唯一标识
	private String userToken; // 用户登录token信息
	private long latitude = 0l; //
	private long longitude = 0l;
	private String cType;// 当前用户的app类型，andriod Or Iphone
	private String appVersion;// 当前app的版本信息
	private String deviceVersion;// 设备硬件信息 3
	private long channel;
	private long actual_longitude;// 用户所在地的经度
	private long actual_latitude;// 用户所在地的纬度
	private String ip;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	private String deviceId;
	private Map<String, Object> otherProperties = new HashMap<String, Object>();

	public AccessData(String uuid, String userToken, long latitude, long longitude, String cType, String appVersion,
			String deviceVersion, long channel, long actual_longitude, long actual_latitude, String deviceId,
			String ip) {
		this.uuid = uuid;
		this.userToken = userToken;
		this.latitude = latitude;
		this.longitude = longitude;
		this.cType = cType;
		this.appVersion = appVersion;
		this.deviceVersion = deviceVersion;
		this.channel = channel;
		this.actual_longitude = actual_longitude;
		this.actual_latitude = actual_latitude;
		this.ip = ip;
		this.deviceId = deviceId;
	}

	public AccessData() {

	}

	public void addProperty(String propertyName, Object value) {
		otherProperties.put(propertyName, value);
	}

	public Object getPropertyValue(String propertyName) {

		return this.otherProperties.get(propertyName);
	}

	public boolean isLoginUser() {
		return !(userToken == null || "".equals(userToken));
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public String getcType() {
		return cType;
	}

	public void setcType(String cType) {
		this.cType = cType;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public long getChannel() {
		return channel;
	}

	public void setChannel(long channel) {
		this.channel = channel;
	}

	public long getActual_latitude() {
		return actual_latitude;
	}

	public void setActual_latitude(long actual_latitude) {
		this.actual_latitude = actual_latitude;
	}

	public long getActual_longitude() {
		return actual_longitude;
	}

	public void setActual_longitude(long actual_longitude) {
		this.actual_longitude = actual_longitude;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
