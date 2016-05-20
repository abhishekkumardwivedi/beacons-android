package com.uriio.beacons.model;

import com.uriio.beacons.Storage;

/**
 * Container for an URI object.
 */
public class UriioItem extends EddystoneItem {
    /** Long URL **/
    private String mLongUrl;

    /** Url ID **/
    private long mUrlId;

    /** Url Token **/
    private String mUrlToken;

    private int mTimeToLive;

    private long mExpireTime = 0;
    private byte[] mPrivateKey;

    public UriioItem(long itemId, int flags, long urlId, String urlToken, int ttl,
                     long expireTimestamp, String shortUrl, String longUrl, byte[] privateKey) {
        super(itemId, flags, shortUrl, null);

        mLongUrl = longUrl;
        mUrlId = urlId;
        mUrlToken = urlToken;
        mTimeToLive = ttl;
        mExpireTime = expireTimestamp;
        mPrivateKey = privateKey;
    }

    public byte[] getPrivateKey() {
        return mPrivateKey;
    }

    public String getUrlToken() {
        return mUrlToken;
    }

    public int getTimeToLive() {
        return mTimeToLive;
    }

    public String getLongUrl() {
        return mLongUrl;
    }

    public void updateShortUrl(String shortUrl, long expireTime) {
        setPayload(shortUrl);
        mExpireTime = expireTime;
    }

    public long getMillisecondsUntilExpires() {
        return 0 == mExpireTime ? Long.MAX_VALUE : mExpireTime - System.currentTimeMillis();
    }

    @Override
    public long getScheduledRefreshTime() {
        // schedule refresh 7 seconds before actual server timeout
        return mExpireTime - 7 * 1000;
    }

    public long getActualExpireTime() {
        return mExpireTime;
    }

    public long getUrlId() {
        return mUrlId;
    }

    @Override
    public int getKind() {
        return Storage.KIND_URIIO;
    }

    public boolean updateTTL(int timeToLive) {
        if (timeToLive != mTimeToLive) {
            mTimeToLive = timeToLive;
            return true;
        }
        return false;
    }
}