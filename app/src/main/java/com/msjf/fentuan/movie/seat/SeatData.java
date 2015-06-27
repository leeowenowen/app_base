package com.msjf.fentuan.movie.seat;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class SeatData {
	public class Coorinate {
		public int mRow;
		public int mCol;
	}

	private int mCount;
	private int mRowMaxCount;
	private List<Integer> mRows = new ArrayList<Integer>();
	private BitSet mSeatFlags = new BitSet();

	public int rowCount() {
		return mRows.size();
	}

	public int rowMaxCount() {
		return mRowMaxCount;
	}

	public int count() {
		return mCount;
	}

	public void addRow(int count, BitSet flags) {
		mRows.add(count);
		for (int i = 0; i < count; ++i) {
			mSeatFlags.set(count + i, flags.get(i));
		}
		mCount += count;
		if (mRowMaxCount < count) {
			mRowMaxCount = count;
		}
	}

	public boolean take(int position) {
		return mSeatFlags.get(position);
	}

	public Coorinate coordinate(int position) {
		Coorinate ret = new Coorinate();
		position += 1;
		int seatCount = 0;
		for (int i = 0; i < mRows.size(); ++i) {
			int sumCount = seatCount + mRows.get(i);
			if (position < sumCount) {
				ret.mRow = i;
				ret.mCol = position - seatCount;
			}
			seatCount = sumCount;
		}
		return ret;

	}
}
