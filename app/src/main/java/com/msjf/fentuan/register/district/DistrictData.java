package com.msjf.fentuan.register.district;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.util.Log;

public class DistrictData extends Observable {
	public List<Province> mProvinces = new ArrayList<Province>();
	public String mCurProvince;
	public int mCurProvinceIndex;
	public String mCurCity;

	private DistrictData() {
		load();
	}

	public static class Province {
		public String mName;
		public String mTitle;
		public List<String> mCitys = new ArrayList<String>();
	}

	public void setCurProvince(int position) {
		mCurProvince = mProvinces.get(position).mName;
		mCurProvinceIndex = position;
		setChanged();
		notifyObservers();
	}

	public List<String> curCity() {
		return mProvinces.get(mCurProvinceIndex).mCitys;
	}

	public void setCurCity(int position) {
		mCurCity = mProvinces.get(mCurProvinceIndex).mCitys.get(position);
		setChanged();
		notifyObservers();
	}

	private void load() {
		InputStream inStream = new ByteArrayInputStream(DistrictConstant.mData.getBytes());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(inStream);
			Element root = dom.getDocumentElement();
			NodeList provinceItems = root.getElementsByTagName("group");
			for (int i = 0; i < provinceItems.getLength(); i++) {
				Province province = new Province();
				Element provinceNode = (Element) provinceItems.item(i);
				if (provinceNode == null) {
					Log.v("xxx", "null");
					continue;
				}
				province.mName = provinceNode.getAttribute("name");
				province.mTitle = provinceNode.getAttribute("title");
				Log.v("xxx", "name:" + province.mName + " title:" + province.mTitle);
				NodeList cityNodes = provinceNode.getElementsByTagName("item");
				for (int j = 0; j < cityNodes.getLength(); j++) {
					Element city = (Element) cityNodes.item(j);
					if (city == null) {
						Log.v("xxx", "null");
						continue;
					}
					province.mCitys.add(city.getAttribute("name"));
					Log.v("xxx", city.getAttribute("name"));
				}
				mProvinces.add(province);
			}
			inStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
