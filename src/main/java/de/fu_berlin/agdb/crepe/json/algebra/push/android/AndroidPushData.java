package de.fu_berlin.agdb.crepe.json.algebra.push.android;

public class AndroidPushData
{
    private String message;
    private String firebaseToken;
    private long profilePrimaryKey;

    public AndroidPushData(String message, String firebaseToken, long profilePrimaryKey)
    {
        this.message = message;
        this.firebaseToken = firebaseToken;
        this.profilePrimaryKey = profilePrimaryKey;
    }


}
