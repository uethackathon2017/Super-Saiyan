package com.supersaiyan.englock.view.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.supersaiyan.englock.R;
import com.supersaiyan.englock.activity.TopicActivity;
import com.supersaiyan.englock.databinding.DialogAddTopicBinding;
import com.supersaiyan.englock.listener.OnAddTopicListener;
import com.supersaiyan.englock.listener.OnDialogClickListener;

/**
 * Created by thanh_000 on 3/10/2017.
 */

public class CreateTopicDialog extends DialogFragment implements OnDialogClickListener, OnAddTopicListener {
    private DialogAddTopicBinding binding;
    private EditText edtTopicName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle(R.string.create_topic);
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_topic, null, false);
        binding.setListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtTopicName = binding.edtTopicName;
    }

    @Override
    public void onPositiveClick() {
        String topicName = edtTopicName.getText().toString().trim();
        if (topicName.isEmpty()) {
            edtTopicName.setError(getString(R.string.name_canot_empty));
        } else {
           // TopicDTOManager.getInstance().addTopic(topicName, this);
        }
    }

    @Override
    public void onNegativeClick() {
        dismiss();
    }


    @Override
    public void onSuccess() {
        dismiss();
       // ((TopicActivity) getActivity()).onCreateTopicSuccess(edtTopicName.getText().toString().trim());
        edtTopicName.setText("");
    }

    @Override
    public void errorWithMessage(int message) {
        edtTopicName.setError(getString(message));
    }
}
