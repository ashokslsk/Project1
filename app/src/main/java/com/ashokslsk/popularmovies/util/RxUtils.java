package com.ashokslsk.popularmovies.util;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ashok.kumar on 09/02/16.
 */

public class RxUtils {

    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }

        return subscription;
    }
}
