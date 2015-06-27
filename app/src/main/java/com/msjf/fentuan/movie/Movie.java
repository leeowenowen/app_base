package com.msjf.fentuan.movie;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
	public String mId;
	public String mName;
	public Bitmap mPoster;
	public String mType;
	public int mFansBuyNumber;
	public int mLength;
	public String mPlayTime;
	public String mArea;
	public boolean mHasFanSpecialPerformance;
	public int mZanNum;
	public int mBaoDianYingNum;
	public int mBaoFansSpecialPerformanceNum;
	public String mDescription;
	public List<CreatorItem> mCreators = new ArrayList<CreatorItem>();

	public class CreatorItem {
		public Bitmap mPhoto;
		public String mName;

		public CreatorItem(Bitmap photo, String name) {
			mPhoto = photo;
			mName = name;
		}
	}

	public void addCreator(Bitmap photo, String name) {
		mCreators.add(new CreatorItem(photo, name));
	}

	public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
		public Movie createFromParcel(Parcel source) {
			Movie movie = new Movie();
			movie.mId = source.readString();
			movie.mName = source.readString();
			movie.mPoster = Bitmap.CREATOR.createFromParcel(source);
			movie.mType = source.readString();
			movie.mFansBuyNumber = source.readInt();
			movie.mLength = source.readInt();
			movie.mPlayTime = source.readString();
			movie.mArea = source.readString();
			movie.mHasFanSpecialPerformance = source.readInt() != 0;
			movie.mZanNum = source.readInt();
			movie.mBaoDianYingNum = source.readInt();
			movie.mBaoFansSpecialPerformanceNum = source.readInt();
			movie.mDescription = source.readString();
			int number = source.readInt();
			for (int i = 0; i < number; ++i) {
				Bitmap photo = Bitmap.CREATOR.createFromParcel(source);
				String name = source.readString();
				movie.addCreator(photo, name);
			}
			return movie;
		}

		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mId);
		dest.writeString(mName);
		mPoster.writeToParcel(dest, flags);
		dest.writeString(mType);
		dest.writeInt(mFansBuyNumber);
		dest.writeInt(mLength);
		dest.writeString(mPlayTime);
		dest.writeString(mArea);
		dest.writeInt(mHasFanSpecialPerformance ? 1 : 0);
		dest.writeInt(mZanNum);
		dest.writeInt(mBaoDianYingNum);
		dest.writeInt(mBaoFansSpecialPerformanceNum);
		dest.writeString(mDescription);
		dest.writeInt(mCreators.size());
		for (CreatorItem item : mCreators) {
			item.mPhoto.writeToParcel(dest, flags);
			dest.writeString(item.mName);
		}
	}
}
