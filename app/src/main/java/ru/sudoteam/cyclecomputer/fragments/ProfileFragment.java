package ru.sudoteam.cyclecomputer.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.sudoteam.cyclecomputer.R;
import ru.sudoteam.cyclecomputer.app.App;

public class ProfileFragment extends Fragment {

    private TextView mFirstSecondName;
    private CircleImageView mProfileImage;

    private SharedPreferences mPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, null);
        mPreferences = getActivity().getSharedPreferences(App.SHARED_PREFERENCES, App.MODE_PRIVATE);
        mFirstSecondName = (TextView) v.findViewById(R.id.profile_first_second_name);
        mProfileImage = (CircleImageView) v.findViewById(R.id.profile_image);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        VKRequest request = VKApi.users().get(VKParameters.from(
                VKApiConst.USER_IDS, mPreferences.getString(App.KEY_VK_ID, "1"),
                VKApiConst.FIELDS, VKApiUser.FIELD_PHOTO_200));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKApiUser user = ((VKList<VKApiUser>) response.parsedModel).get(0);
                mFirstSecondName.setText(user.first_name);
                mFirstSecondName.append("\n");
                mFirstSecondName.append(user.last_name);
                Picasso.with(mProfileImage.getContext())
                        .load(user.photo_200)
                        .error(R.drawable.demo_profile)
                        .into(mProfileImage);
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(mProfileImage.getContext(), error.errorReason, Toast.LENGTH_SHORT).show();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }
}
