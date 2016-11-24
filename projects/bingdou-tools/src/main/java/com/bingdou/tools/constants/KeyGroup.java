package com.bingdou.tools.constants;

/**
 * Created by gaoshan on 16-10-25.
 */
public enum KeyGroup {

    DEFAULT("8wvu4JNclrnVYfaw", "evzAJZolMVdcb1kVI81ymNbPqWByxuhS", EncodeMethod.AES),
    GROUP_1("U9d4dGnzj0AeZXRP", "naj8J14D3Kk8HGGeDH3KWHU7HnyhZYrH", EncodeMethod.AES),
    GROUP_2("65VY5WUk35SGvQpi", "QumkTMXIyVuig6FuLS9zhEpQghPMXtu8", EncodeMethod.AES),
    GROUP_3("w6sKaeYXPvVwJO68", "4kH14ycNkoWVYpL1kyy4yHdR1G6SHW0k", EncodeMethod.AES),
    IOS_PAYSDK_CLIENT("ZhyoZBnvG9Qjrl2n", "Ax3KsdJnWi7sZTUKcIVXgFbqNmMiIxAi", EncodeMethod.AES),
    ANDROID_PAYSDK_CLIENT("16o1xl8dIrYjTOaB", "Q5npgaKmoMvi7jUzxy8eCxSdGh46jFLq", EncodeMethod.AES);

    private String encodeKey;
    private String signKey;
    private EncodeMethod encodeMethod;

    KeyGroup(String encodeKey, String signKey,
             EncodeMethod encodeMethod) {
        this.encodeKey = encodeKey;
        this.signKey = signKey;
        this.encodeMethod = encodeMethod;
    }

    public String getEncodeKey() {
        return encodeKey;
    }

    public void setEncodeKey(String encodeKey) {
        this.encodeKey = encodeKey;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public EncodeMethod getEncodeMethod() {
        return encodeMethod;
    }

    public void setEncodeMethod(EncodeMethod encodeMethod) {
        this.encodeMethod = encodeMethod;
    }
}
