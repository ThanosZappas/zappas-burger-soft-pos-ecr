package gr.android.cineflix.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

public class WifiMonitor {
    private final ConnectivityManager connectivityManager;
    private final WifiCallback callback;
    private ConnectivityManager.NetworkCallback networkCallback;

    public interface WifiCallback {
        void onWifiConnected();
        void onWifiDisconnected();
        void onInitialState(boolean isWifiConnected);
    }

    public WifiMonitor(Context context, WifiCallback callback) {
        this.callback = callback;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void startMonitoring() {
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                callback.onWifiConnected();
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
                callback.onWifiDisconnected();
            }
        };

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);

        // Report initial state
        callback.onInitialState(isWifiConnected());
    }

    public void stopMonitoring() {
        if (networkCallback != null) {
            try {
                connectivityManager.unregisterNetworkCallback(networkCallback);
            } catch (Exception ignored) {
                // Handle or log any errors
            }
            networkCallback = null;
        }
    }

    public boolean isWifiConnected() {
        Network network = connectivityManager.getActiveNetwork();
        if (network == null) return false;

        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        return capabilities != null &&
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
    }
}
