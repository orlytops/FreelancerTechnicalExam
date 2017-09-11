package com.test.flapicall.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.test.flapicall.R;
import com.test.flapicall.api.models.Skill;

import java.util.ArrayList;

/**
 * Created by orly on 9/11/17.
 */

public class SkillsAdapter extends ArrayAdapter<Skill> {

  private TextView _skillsText;

  private ArrayList<Skill> skillList = new ArrayList<>();

  public SkillsAdapter(@NonNull Context context, @LayoutRes int resource,
      ArrayList<Skill> skillList) {
    super(context, 0);
    this.skillList = skillList;
  }

  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
    Skill skill = skillList.get(position);
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.item_skills, parent, false);
    }
    _skillsText = (TextView) convertView.findViewById(R.id.text_skills);
    setItems(skill);
    return convertView;
  }

  private void setItems(Skill skill) {
    _skillsText.setText(skill.getName());
  }

  @Override
  public int getCount() {
    return skillList.size();
  }
}
