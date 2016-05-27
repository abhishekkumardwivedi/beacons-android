package com.uriio.beacons.model;

import com.uriio.beacons.ble.Advertiser;
import com.uriio.beacons.ble.AdvertisersManager;
import com.uriio.beacons.ble.EddystoneAdvertiser;

import org.uribeacon.beacon.UriBeacon;

/**
 * Created on 5/21/2016.
 */
public class EddystoneURL extends EddystoneBase {
    private String mURL;

    public EddystoneURL(long id, String url, byte[] lockKey, @Beacon.AdvertiseMode int mode,
                        @Beacon.AdvertiseTxPower int txPowerLevel, String name) {
        super(id, Beacon.EDDYSTONE_URL, lockKey, mode, txPowerLevel, name);
        mURL = url;
    }

    public EddystoneURL(String url, @Beacon.AdvertiseMode int mode,
                        @Beacon.AdvertiseTxPower int txPowerLevel, String name) {
        this(0, url, null, mode, txPowerLevel, name);
    }

    public EddystoneURL(String url, @Beacon.AdvertiseMode int mode,
                        @Beacon.AdvertiseTxPower int txPowerLevel) {
        this(0, url, null, mode, txPowerLevel, null);
    }

    public EddystoneURL(String url, byte[] lockKey) {
        super(Beacon.EDDYSTONE_URL, lockKey);
        mURL = url;
    }

    public EddystoneURL(String url) {
        this(url, null);
    }

    @Override
    public Advertiser createBeacon(AdvertisersManager advertisersManager) {
        byte[] data = UriBeacon.encodeUri(mURL);
        if (null == data) return null;

        int len = data.length;
        return setBeacon(new EddystoneAdvertiser(data, 0, len, advertisersManager, getAdvertiseMode(),
                getTxPowerLevel(), isConnectable(), getFlags()));
    }

    @Override
    public EddystoneURLEditor edit() {
        return new EddystoneURLEditor();
    }

    public String getURL() {
        return mURL;
    }

    public class EddystoneURLEditor extends EddystoneEditor {
        public EddystoneURLEditor setUrl(String url) {
            if (null == url || null == mURL || !url.equals(mURL)) {
                mURL = url;
                mRestartBeacon = true;
            }
            return this;
        }
    }
}