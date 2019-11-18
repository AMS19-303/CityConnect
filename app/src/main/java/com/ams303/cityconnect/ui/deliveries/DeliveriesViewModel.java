package com.ams303.cityconnect.ui.deliveries;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeliveriesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DeliveriesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is deliveries fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}