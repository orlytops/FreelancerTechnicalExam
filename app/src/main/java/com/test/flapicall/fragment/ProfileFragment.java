package com.test.flapicall.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.test.flapicall.R;
import com.test.flapicall.adapters.ExamsAdapter;
import com.test.flapicall.adapters.SkillsAdapter;
import com.test.flapicall.api.models.Exam;
import com.test.flapicall.api.models.Skill;
import com.test.flapicall.api.models.User;

import java.util.ArrayList;

/**
 * Created by orly on 9/9/17.
 */

public class ProfileFragment extends Fragment {

  private static final String USER_STRING = "user";
  private String _userJson;

  private User _user;

  private Gson _gson;

  private TextView _aboutDescriptionText;
  private ListView _skillList;
  private ListView _examsList;

  private SkillsAdapter _skillsAdapter;
  private ExamsAdapter  _examsAdapter;
  private ArrayList<Skill> _skillArrayList = new ArrayList<>();
  private ArrayList<Exam>  _examArrayList  = new ArrayList<>();

  public ProfileFragment newInstance(String userJson) {
    ProfileFragment profileFragment = new ProfileFragment();
    Bundle args = new Bundle();
    args.putString(USER_STRING, userJson);
    profileFragment.setArguments(args);
    return profileFragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    _userJson = getArguments().getString(USER_STRING);
    _gson = new Gson();
    _user = _gson.fromJson(_userJson, User.class);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_profile, container, false);
    initViews(view);
    initAdapter();
    populateViews();
    return view;
  }

  private void initViews(View view) {
    _aboutDescriptionText = (TextView) view.findViewById(R.id.text_about_description);
    _skillList = (ListView) view.findViewById(R.id.list_skills);
    _examsList = (ListView) view.findViewById(R.id.list_exams);

    View footerViewSkills = ((LayoutInflater) getActivity().getSystemService(
        Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_skills, null, false);
    View footerViewExams = ((LayoutInflater) getActivity().getSystemService(
        Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_exams, null, false);

    _skillList.addFooterView(footerViewSkills);
    _examsList.addFooterView(footerViewExams);
  }

  private void initAdapter() {
    _skillArrayList.addAll(_user.getSkills());
    _skillsAdapter = new SkillsAdapter(getContext(), R.layout.item_skills, _skillArrayList);
    _skillList.setAdapter(_skillsAdapter);
    _skillsAdapter.notifyDataSetChanged();

    _examArrayList.addAll(_user.getExams());
    _examsAdapter = new ExamsAdapter(getContext(), R.layout.item_exam, _examArrayList);
    _examsList.setAdapter(_examsAdapter);
    _examsAdapter.notifyDataSetChanged();
  }

  private void populateViews() {
    _aboutDescriptionText.setText(_user.getAbout());
  }
}
