package com.msjf.fentuan.app.hx;

import android.os.Parcel;

import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMMessage;
import com.easemob.chat.FileMessageBody;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.LocationMessageBody;
import com.easemob.chat.MessageBody;
import com.easemob.chat.TextMessageBody;
import com.easemob.chat.VideoMessageBody;
import com.easemob.chat.VoiceMessageBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangli on 15-6-14.
 */
public class CompositeMessageBody extends MessageBody {
    private List<MessageBody> mSubMessageBodies = new ArrayList<MessageBody>();

    private static Map<String, Creator> sCreators = new HashMap<String, Creator>();

    static {
        sCreators.put(EMMessage.Type.TXT.name(), TextMessageBody.CREATOR);
        sCreators.put(EMMessage.Type.IMAGE.name(), ImageMessageBody.CREATOR);
        sCreators.put(EMMessage.Type.VIDEO.name(), VideoMessageBody.CREATOR);
        sCreators.put(EMMessage.Type.LOCATION.name(), LocationMessageBody.CREATOR);
        sCreators.put(EMMessage.Type.VOICE.name(), VoiceMessageBody.CREATOR);
        // sCreators.put(EMMessage.Type.FILE.name(), FileMessageBody.CREATOR);
        sCreators.put(EMMessage.Type.CMD.name(), CmdMessageBody.CREATOR);
    }

    public static final Creator<CompositeMessageBody> CREATOR = new Creator() {
        public CompositeMessageBody createFromParcel(Parcel var1) {
            return new CompositeMessageBody(var1);
        }

        public CompositeMessageBody[] newArray(int var1) {
            return new CompositeMessageBody[var1];
        }
    };

    public CompositeMessageBody(Parcel parcel) {
        int count = parcel.readInt();
        for (int i = 0; i < count; ++i) {
            String typeName = parcel.readString();
            Creator creator = sCreators.get(typeName);
            if (creator != null) {
                MessageBody body = (MessageBody) creator.createFromParcel(parcel);
                mSubMessageBodies.add(body);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mSubMessageBodies.size());
        for (MessageBody body : mSubMessageBodies) {
           // parcel.writeString();
            body.writeToParcel(parcel, i);
        }
    }
}
